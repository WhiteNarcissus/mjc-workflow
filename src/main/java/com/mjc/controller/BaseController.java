package com.mjc.controller;

import com.mjc.response.CommonReturnType;
import com.mjc.erro.BusinessException;
import com.mjc.erro.EmBussinessErr;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {


    static  final  String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    //定义ExceptionHandle 来解决controller的异常 因为controller是最后一层
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest httpRequest, Exception ex) {
        Map<String, Object> responseMap = new HashMap<>();
        if (ex instanceof  BusinessException){
            BusinessException businessException = (BusinessException) ex;

            responseMap.put("errCode", businessException.getErrCode());
            responseMap.put("errMsg", businessException.getErrMsg());
        }else {
            responseMap.put("errCode", EmBussinessErr.UNKNOWN_ERROR.getErrCode());
            responseMap.put("errMsg", EmBussinessErr.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseMap,"fail");
    }
}
