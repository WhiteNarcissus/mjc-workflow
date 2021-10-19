package com.mjc.controller;

import com.mjc.common.redis.RedisUtils;
import com.mjc.controller.viewObject.UserVo;
import com.mjc.response.CommonReturnType;
import com.mjc.erro.BusinessException;
import com.mjc.erro.EmBussinessErr;
import com.mjc.service.UserService;
import com.mjc.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {


    @Autowired
    UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisUtils redisUtils;


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUserById(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        Integer a = null;
        a.byteValue();
        if (userModel == null) {
            throw new BusinessException(EmBussinessErr.USER_NOT_EXIST);
        }

        UserVo userVo = convertFromModel(userModel);
        return CommonReturnType.create(userVo);
    }

    private UserVo convertFromModel(UserModel userModel) {
        if (userModel == null)
            return null;

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }


    //短信验证码

    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getUserOtp(@RequestParam(name = "telphone") String telphone) {

        //生成 随机验证码
        Random random = new Random();
        int radonNum = random.nextInt(999999);
        radonNum += 100000;
        //将用户手机号 和 验证码关联 (正常来说这里肯定是要用redis 来的 redis 可以设置过期时间 多次点击同)
        redisUtils.set(telphone, radonNum, 1000 * 60);
        System.out.println("telphone:" + telphone + "----" + "code:" + redisUtils.get(telphone));

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password
    ) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {

        String localOtpCode = redisUtils.get(telphone).toString();
        //先验证码
        if (!otpCode.equals(localOtpCode)) {
            throw new BusinessException(EmBussinessErr.PARAMETER_VALIDATION_ERROR, "短信验证不通过");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byPhone");
        userModel.setAge(age);
        userModel.setGender(gender.byteValue());
        userModel.setEncreptPassword(encodeByMd5(password));
        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    //登入
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone, @RequestParam(name = "password") String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (StringUtils.isBlank(telphone) || StringUtils.isBlank(password)) {
            throw new BusinessException(EmBussinessErr.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.login(telphone, password);
        if (!encodeByMd5(password).equals(userModel.getEncreptPassword())) {
            throw new BusinessException(EmBussinessErr.USER_LOOGIN_FAIL);
        }
        return CommonReturnType.create(null);

    }


    private String encodeByMd5(String encreptPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encodePassword = base64Encoder.encode(md5.digest(encreptPassword.getBytes("utf-8")));
        return encodePassword;
    }
}
