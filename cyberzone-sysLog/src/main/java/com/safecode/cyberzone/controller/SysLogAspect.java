package com.safecode.cyberzone.controller;

import com.alibaba.fastjson.JSON;
import com.safecode.cyberzone.pojo.MyLog;
import com.safecode.cyberzone.pojo.SysLog;
import com.safecode.cyberzone.service.SysLogService;
import com.safecode.cyberzone.utils.ContextUtils;
import com.safecode.cyberzone.utils.IpUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;
    
    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.safecode.cyberzone.pojo.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
    	SysLog sysLog = new SysLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
        	//获得参数列表
            Object[] arguments = joinPoint.getArgs();
            //请求的参数
//            Object[] args = joinPoint.getArgs();
            //将参数所在的数组转换成json
//            String params = JSON.toJSONString(args);
            
            Object value = (Object) arguments[0];
            if(value != null) {
            	sysLog = assemble(joinPoint, method, myLog, value);
            }
        }
        sysLogService.insert(sysLog);
    }
    
    public static Map<String,Object> getStringToMap(Object value){
        //根据逗号截取字符串数组
        String[] str1 = (value.toString().replaceAll(" ","")).split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split("=");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0],str2[1]);
        }
        return map;
    }
    
    public SysLog assemble(JoinPoint joinPoint, Method method, MyLog myLog, Object value) {
    	SysLog sysLog = new SysLog();
    	String v = "";
    	Map<String, Object> map = getStringToMap(value);
    	if(myLog.type().equals("update")) {
    		v = "id: " + (String) map.get("id");
    	} else if(myLog.type().equals("upload")){
    		v = "文件名: " + (String) map.get("fileName");
    	}
    	
    	//操作内容
    	String remark = myLog.remark();
    	//获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = className + "." + method.getName();
        //操作系统
        String projectName = myLog.projectName();
        //获取用户名
//        sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());
        //获取用户ip地址
        HttpServletRequest request = ContextUtils.getRequest();
    	
        sysLog.setRemark(remark + "  " + v);//保存获取的操作
        sysLog.setCreateTime(new Date());
        sysLog.setMethod(methodName);
        sysLog.setProjectName(projectName);
        sysLog.setIpAddr(IpUtils.getIpAddr(request));
        
		return sysLog;
    }
}
