package ksmart.mybatis.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("SID");
		
		if(sessionId == null) {
			response.sendRedirect("/member/login"); 
			return false;
		}else {
			String requestUri = request.getRequestURI();
			int sessionLevel = (int) session.getAttribute("SLEVEL");
			if(sessionLevel == 2) {
				if(		requestUri.indexOf("/member/memberList") > -1 
				  ||	requestUri.indexOf("/member/modify") 	 > -1
				  ||	requestUri.indexOf("/member/remove") 	 > -1
				  ||	requestUri.indexOf("/goods/modify") 	 > -1
				  ||	requestUri.indexOf("/goods/remove") 	 > -1)	{
					response.sendRedirect("/");
					return false;
				}
			}
		}
				
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
