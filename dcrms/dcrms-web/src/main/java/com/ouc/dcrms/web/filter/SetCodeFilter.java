package com.ouc.dcrms.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 编码过滤器
 * 
 * @author WuPing
 */

public class SetCodeFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain filterchain) throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	req.setCharacterEncoding("UTF-8");
	resp.setHeader("Pragma", "No-cache");
	resp.setDateHeader("Expires", 0);
	resp.setHeader("Cache-Control", "no-cache");
	filterchain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
