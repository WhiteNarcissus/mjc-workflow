package com.mjc.erro;


//这个接口的功能是 不仅仅可以接受定义好的 错误代码对应的错误信息 ，也可以自己改写要返回的错误信息
public interface CommonError {
     int getErrCode();
     String getErrMsg();
     CommonError setErrMsg(String errMsg);
}
