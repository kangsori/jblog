package com.douzone.jblog.security;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;


public class AuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	public BlogService blogService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHanlder가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		// casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// Hanlder Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// Handler Method에 @Auth가 없으면 Type(Class)에 붙어 있는지 확인한다.
		if(auth == null) {
			auth = handlerMethod.getBean().getClass().getAnnotation(Auth.class);
		}
	
		// Type이나 Method에 @Auth가 없는 경우 인증 
		if(auth == null) {
			return true;
		}
		
		/****************************** Auth가 있는 경우 ******************************/ 
		
		//@PathVariable 값을 가져오기 
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		// 열람하려는 blog의 vo를 확인하여 없으면 등록 
		BlogVo blogVo=(BlogVo)servletContext.getAttribute("blogVo");
		if(blogVo==null ) {
			blogVo = blogService.getBlog((String)pathVariables.get("id"));
			servletContext.setAttribute("blogVo",blogVo);
		}
		
		// 열람하려는 blog의 vo를 확인하여 다르면 등록 
		if(!blogVo.getId().equals((String)pathVariables.get("id")) ) {
			blogVo = blogService.getBlog((String)pathVariables.get("id"));
			servletContext.setAttribute("blogVo",blogVo);
		}
	
		//권한(Authorization) 체크를 위해 @Auth의 admin 가져오기 
		boolean admin = auth.admin(); 
				
		// auth가 false일 경우 모든 사용자가능한 접근이기때문에 인증 
		if(!admin) {
			return true;
		}
	
		// auth가 true : 로그인을 하지않았으면 인증 실패 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// auth가 true : 세션과 사용자가 같지않으면 인증 실패 
		if(!authUser.getId().equals(pathVariables.get("id"))) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		//인증 확인
		return true;
	}

}