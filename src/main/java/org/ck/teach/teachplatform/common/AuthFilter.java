package org.ck.teach.teachplatform.common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Object staff = httpServletRequest.getSession().getAttribute(AppConst.USER_LOGIN_SESSION);
        if (httpServletRequest.getRequestURI().startsWith("/user/")
                || httpServletRequest.getRequestURI().startsWith("/student/")) {
            if (staff == null) {
                httpServletResponse.sendRedirect("/login.html?url="+httpServletRequest.getRequestURI());
                return ;
            }
        }
        chain.doFilter(request, response);
    }
}
