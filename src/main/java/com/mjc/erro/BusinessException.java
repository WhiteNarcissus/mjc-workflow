package com.mjc.erro;

//包装器业务异常类实现
public class BusinessException  extends Exception implements CommonError{


  //强关联一个 CommonError
   private CommonError commonError ;

   //直接接受 异常枚举类的传参构造器

   public BusinessException(CommonError commonError){
       //继承了这个类Exception 有些方法要初始化
       super();
       this.commonError = commonError;
   }


    public BusinessException(CommonError commonError,String errMsg){
        //继承了这个类Exception 有些方法要初始化
        super();
        this.commonError = commonError;
        setErrMsg(errMsg);
    }



    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
       this.commonError.setErrMsg(errMsg);
       return this;
    }
}
