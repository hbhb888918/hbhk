package org.hbhk.module.framework.server.exception;
public interface IException {
    
    String getErrorCode();
    
    String getNativeMessage();
    
    void setErrorArguments(Object... objects);
    
    Object[] getErrorArguments();
    
}
