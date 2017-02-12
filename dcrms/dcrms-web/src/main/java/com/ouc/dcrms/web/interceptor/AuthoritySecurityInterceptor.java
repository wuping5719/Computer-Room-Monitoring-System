package com.ouc.dcrms.web.interceptor;
/*package com.ouc.rbac.platform.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.haier.openplatform.hac.dto.HacResourceDTO;
import com.haier.openplatform.hac.resource.dto.enu.ResourceTypeEnum;
import com.haier.openplatform.hac.resource.service.HacResourceServiceClient;
import com.haier.openplatform.security.AbstractAuthenticator;
import com.haier.openplatform.security.Authentication;
import com.haier.openplatform.security.DefaultUrlAuthenticator;
import com.haier.openplatform.security.SessionSecurityConstants;
import com.ouc.rbac.platform.interceptor.AuthResources;

public class AuthoritySecurityInterceptor implements HandlerInterceptor {
	
	@Autowired
	//private static HacResourceServiceClient resourceServiceClient; 
	
	*//**
	 * 无权限跳转的页面
	 *//*
	private static final String NOAUTHPAGE = "/NoAuth.jsp";

	//当前用户所拥有的资源
	//private List<HacResourceDTO> resources;
	
	//判断资源是否已获取的布尔变量
	private boolean HACRES_ISGET = false;
	
	*//** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     *//* 
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession httpSession = request.getSession();
		
		String userCode = (String)httpSession.getAttribute(SessionSecurityConstants.KEY_USER_NAME);
		// KEY_AUTHENTICATION  用户授权信息
		String authentication = (String)httpSession.getAttribute(SessionSecurityConstants.KEY_AUTHENTICATION);
		AuthResources auth = new AuthResources();
		
		String userType = (String)httpSession.getAttribute("userType");
		
		// 根据应用编码、用户名、当前语言和版本获取该用户所拥有的权限列表
		if (authentication == null || auth.getUrlResources().isEmpty()) {
			
			if (!HACRES_ISGET){
				ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
						new String[]{"classpath:dubbo-consumer.xml","classpath:springmvc-servlet.xml"},true);
				
				cxt.start();
				
				resourceServiceClient = (HacResourceServiceClient)cxt.getBean("resourceServiceClient");
				
				//测试供应商端权限示例用户：wangxi，测试海尔端权限示例用户：SUP0010880
				//resources = resourceServiceClient.getResourcesByAppAndUser("H001", "wangxi", "zh_CN", "1.0");
				//resources = resourceServiceClient.getResourcesByAppAndUser("H001", "SUP0010880", "zh_CN", "1.0");
				
				System.out.println("当前登录用户名:" + userCode);
				if (userType.equals("supplier")){
					System.out.println("1.当前登录用户类型:" + userType);
					resources = resourceServiceClient.getResourcesByAppAndUser("H001", "wangxi", "zh_CN", "1.0");
				}else{
					System.out.println("2.当前登录用户类型:" + userType);
					resources = resourceServiceClient.getResourcesByAppAndUser("H001", userCode, "zh_CN", "1.0");
				}
				
				//resources = resourceServiceClient.getResourcesByAppAndUser("H001", userCode, "zh_CN", "1.0");
				
				HACRES_ISGET = true;
			}
			
			//System.out.println(userCode + "用户未得到授权，请先联系管理员，到权限中心为您授权！");
			
			// Session存放的用戶登录名   KEY_USER_NAME
			// String userCode = (String)httpSession.getAttribute(SessionSecurityConstants.KEY_USER_NAME);
			//authentication = getAuthentication(userCode, request);
			//httpSession.setAttribute(SessionSecurityConstants.KEY_AUTHENTICATION, authentication);
		}
		
		if (!HACRES_ISGET){
			ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
					new String[]{"classpath:dubbo-consumer.xml","classpath:springmvc-servlet.xml"},true);
			
			cxt.start();
			
			resourceServiceClient = (HacResourceServiceClient)cxt.getBean("resourceServiceClient");
			
			resources = resourceServiceClient.getResourcesByAppAndUser("H001", "wangxi", "zh_CN", "1.0");
			
			System.out.println("当前登录用户名:" + userCode);
			//resources = resourceServiceClient.getResourcesByAppAndUser("H001", userCode, "zh_CN", "1.0");
			
			HACRES_ISGET = true;
		}
		
		//System.out.println("王玺所拥有的权限列表:" + resources.size());
		System.out.println(userCode + "所拥有的权限列表:" + resources.size());
		for(int i=0;i<resources.size();i++)
		{
			System.out.println("资源序号: " + (i+1));
			System.out.println("AppName: " + resources.get(i).getAppName());
			System.out.println("AppCode: " + resources.get(i).getAppCode());
			System.out.println("BaseUrl: " + resources.get(i).getBaseUrl());
			System.out.println("UserId: " + resources.get(i).getUserId());
			System.out.println("AppId: " + resources.get(i).getAppId());
			System.out.println("CreateBy: " + resources.get(i).getCreateBy());
			System.out.println("Code: " + resources.get(i).getCode());
			System.out.println("Configuration: " + resources.get(i).getConfiguration());
			System.out.println("Name: " + resources.get(i).getName());
			System.out.println("ModuleId: " + resources.get(i).getModuleId());
			System.out.println("Url: " + resources.get(i).getUrl());
			System.out.println("FileId: " + resources.get(i).getFileId());
			System.out.println("Status: " + resources.get(i).getStatus());
			System.out.println();
		}
		
		for(HacResourceDTO resource : resources){
			ResourceTypeEnum resourceType = ResourceTypeEnum.toEnum(resource.getType());
			if(resourceType == ResourceTypeEnum.URL_RESOURCE || resourceType == ResourceTypeEnum.TODO_RESOURCE){
				auth.getUrlResources().add(resource.getUrl());
				if(StringUtils.isNotEmpty(resource.getCode())){
					auth.getComponentResources().add(resource.getCode());
				}
			}else if(resourceType == ResourceTypeEnum.COMPONENT_RESOURCE){
				auth.getComponentResources().add(resource.getCode());
			}
		}
		
		String contextPath = request.getContextPath();
		String currentUrl = request.getServletPath();
		System.out.println("目前的URL："+currentUrl);
		
		if(auth.getUrlResources().size()>0){
			//System.out.println("以下权限比对URL：");
			for(int j=0; j<auth.getUrlResources().size();j++){
				//System.out.println(auth.getUrlResources().get(j));
				if(auth.getUrlResources().get(j).equals(currentUrl)){
					return true;
				}
			}
		}
			
		if(!authenticator.hasUrlAuth(currentUrl)){
			response.sendRedirect(NOAUTHPAGE);
			return false;
		}
		
		response.sendRedirect(contextPath + NOAUTHPAGE);

		return false;
	}
	
	*//** 
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。 
     *//* 
    
    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("----在Action 方法执行完毕之后,无论是否抛出异常,通常用来进行异常处理----------");
    }
    
    *//** 
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操 
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像， 
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor 
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。 
     *//*  
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    	 System.out.println("----在Action方法执行完毕之后,执行(没有抛异常的话)----------");
    }
	
}
*/