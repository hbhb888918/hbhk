package org.hbhk.module.framework.server.exception;

import org.springframework.stereotype.Component;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:异常转化器</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1  2011-3-27  steven.cheng 新增
* </div>  
********************************************
 */
@Component
public class ExceptionConvert implements IExceptionConvert {
    
	/**
	 * 
	 * @see com.deppon.foss.framework.server.components.exception.IExceptionConvert#convert(java.lang.Throwable)
	 * convert
	 * @param target
	 * @return
	 * @since:0.6
	 */
    @Override
    public GeneralException convert(Throwable target) {
        if (GeneralException.class.isAssignableFrom(target.getClass())) {
            return (GeneralException) target;
        }
        //对spring捕捉数据库的异常进行特别处理
        if (org.springframework.dao.DuplicateKeyException.class.isAssignableFrom(target.getClass())) {
            return new DuplicateKeyException(target);
        }
        
        if (org.springframework.dao.TypeMismatchDataAccessException.class.isAssignableFrom(target.getClass())) {
            return new TypeMismatchAccessException(target);
        }
        if (org.springframework.dao.DataIntegrityViolationException.class.isAssignableFrom(target.getClass())) {
            return new DataIntegrityViolationException(target);
        }
        
        return new UnknowGeneralException(target.toString(),target);
    }
    
    /**
     * 
     * @see com.deppon.foss.framework.server.components.exception.IExceptionConvert#nativeConvert(java.lang.Throwable)
     * nativeConvert
     * @param target
     * @return
     * @since:0.6
     */
    @Override
    public Exception nativeConvert(Throwable target) {
        if (BusinessException.class.isAssignableFrom(target.getClass())) {
            return ((BusinessException) target);
        }
        final String msg = parseExceptionMessage(target);
        
        if (GeneralException.class.isAssignableFrom(target.getClass())) {
            final GeneralException nativeGe = (GeneralException) target;
            return new GeneralServiceException(msg, nativeGe.getErrorCode(), nativeGe.getErrorArguments());
        }
        return new UnknowGeneralException(msg , target);
        
    }
    
    @Override
    public String parseExceptionMessage(Throwable target) {
        return target.toString();
    }
}
