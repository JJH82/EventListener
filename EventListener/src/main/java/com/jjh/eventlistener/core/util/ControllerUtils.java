package com.jjh.eventlistener.core.util;


import com.jjh.eventlistener.core.controller.SplitStrRequest;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class ControllerUtils {
    
    public static void NotifyAnnotation(SplitStrRequest req) throws Exception {
        StringBuilder errorMsg = new StringBuilder();
        
        try {
            final Map<String,Object> beanMap = req.getCtx().getBeansWithAnnotation(req.getTargetClass());
            
            for(final Object obj: beanMap.values()) {

            	for(final Method m : getTargetClass(obj).getMethods()) {
            		
                    if(m.isAnnotationPresent(req.getTargetMethod())) {
                    	
                    	errorMsg.append(getTargetClass(obj)).append(".").append(m.getName()).append("()");
                    	
	                    if(req.isExecutable(m.getAnnotation(req.getTargetMethod()))) {	                
	                    	m.invoke(req.getCtx().getBean(obj.getClass()), req);
	                    }
	                    errorMsg.delete(0, errorMsg.length());
                    }
                    
                }
                
            }
        }catch(IllegalAccessException e) {
        	errorMsg.append(" @KeywordMapping 사용시 하나의");
            errorMsg.append(req.getClass().getTypeName());
            errorMsg.append("형의 파라미터를 여야합니다.");
            throw new Exception(errorMsg.toString());        	
        }catch(Throwable t) {
            throw new Exception(t);
        }
    }
    
    private static Class getTargetClass(final Object obj) {
        return AopUtils.isAopProxy(obj) ? AopProxyUtils.getSingletonTarget(obj).getClass() : obj.getClass();
    }
    
}