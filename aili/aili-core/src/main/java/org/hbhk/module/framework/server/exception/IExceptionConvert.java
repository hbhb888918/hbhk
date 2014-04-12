package org.hbhk.module.framework.server.exception;


public interface IExceptionConvert {
    /**
     * 转化成GeneralException
     */
    GeneralException convert(Throwable target);
    
    /**
     * 转化成Exception
     */
    Exception nativeConvert(Throwable target);
    
    /**
     * 转化成字符串
     */
    String parseExceptionMessage(Throwable target);
}
