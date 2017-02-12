package com.ouc.dcrms.web.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ouc.dcrms.web.utils.SessionConstants;

public class UserSecurityInterceptor implements HandlerInterceptor {

    // private List<String> excludedUrls;

    private static final String DEFAULT_LOGIN = "/login.jsp";

    private List<String> noLoginAuthUrlList = new ArrayList<String>();
    
    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
     * ，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
     * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
	    Object obj) throws Exception {
	System.out.println("---在Action之前执行,如果返回true,则继续向后执行---");
	// 请求的路径
	String contextPath = req.getContextPath();
	String url = req.getServletPath().toString();
	System.out.println(contextPath);
	HttpSession session = req.getSession();
	String userName = (String) session.getAttribute(SessionConstants.KEY_USER_NAME);
	String trueName = (String) session.getAttribute(SessionConstants.KEY_True_NAME);
	String userId = (String) session.getAttribute(SessionConstants.KEY_USER_ID);
	System.out.println("用户ID：" + userId);
	System.out.println("用户名：" + userName);
	System.out.println("真实姓名：" + trueName);

	// 这里可以根据session的用户来判断角色的权限，根据权限来重定向不同的页面，简单起见，这里只是做了一个重定向
	if (StringUtils.isEmpty(userName)) {
	    // 被拦截，重定向到login界面
	    res.sendRedirect(contextPath + DEFAULT_LOGIN + "?redirectURL="
		    + URLEncoder.encode(url));
	    return false;
	}
	return true;
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
     * 也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */

    @Override
    public void afterCompletion(HttpServletRequest arg0,
	    HttpServletResponse arg1, Object arg2, Exception ex)
	    throws Exception {
	System.out.println("---在Action方法执行完毕之后,无论是否抛出异常,通常用来进行异常处理---");
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
     * 它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
     * ，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
     * ，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法
     * ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前
     * ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
	    Object arg2, ModelAndView arg3) throws Exception {
	System.out.println("---在Action方法执行完毕之后,执行(没有抛异常的话)---");
    }

    protected boolean isLogin(HttpServletRequest httpServletRequest) {
	String currentUrl = httpServletRequest.getRequestURI();

	noLoginAuthUrlList.add("/login.jsp");

	for (String url : noLoginAuthUrlList) {
	    if (currentUrl.startsWith(url)) {
		return true;
	    }
	}

	HttpSession httpSession = httpServletRequest.getSession();
	String userName = (String) httpSession.getAttribute(SessionConstants.KEY_USER_NAME);
	if (userName == null) {
	    // 仅仅记住get请求的链接
	    if (StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),
		    "GET")) {
		HttpSession session = httpServletRequest.getSession();
		String servletPath = httpServletRequest.getServletPath();
		String fullURL = new StringBuffer(servletPath).append(
			toParameterString(httpServletRequest)).toString();
		session.setAttribute("KEY_USER_NAME", fullURL);
	    }
	    return false;
	}
	return true;
    }

    /**
     * 
     * @param httpServletRequest
     * @return
     */
    private String toParameterString(HttpServletRequest httpServletRequest) {
	@SuppressWarnings("unchecked")
	Enumeration<String> paramEnumeration = httpServletRequest
		.getParameterNames();
	if (!paramEnumeration.hasMoreElements()) {
	    return "";
	}
	StringBuffer stringBuffer = new StringBuffer();
	while (paramEnumeration.hasMoreElements()) {
	    String paramName = paramEnumeration.nextElement();
	    stringBuffer.append("&");
	    stringBuffer.append(paramName);
	    stringBuffer.append("=");
	    stringBuffer.append(httpServletRequest.getParameter(paramName));
	}
	stringBuffer.replace(0, 1, "?");
	return stringBuffer.toString();
    }
}
