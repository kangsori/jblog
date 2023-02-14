package com.douzone.jblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "/user/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}

		//아이디 체크(중복일경우 로그인페이지 재로드 + 메세지 출력)
		if(userService.checkId(vo.getId())) {
		    model.addAttribute("idmessage","중복 아이디 입니다. ");
			return "/user/join";
		}
		
		//유효성 통과후 가입 
		userService.join(vo);
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserVo vo,Model model,HttpServletRequest request) {
		// 사용자 정보 찾기 
		UserVo authUser = userService.getUser(vo);
		
		// 사용자가 아닐경우 
		if(authUser == null) {
			model.addAttribute("id",vo.getId());
			model.addAttribute("loginMessage","아이디 및 비밀번호가 맞지않습니다. 다시 시도해 주세요.");
			return "/user/login";
		}
		
		// 사용자일 경우 session에 사용자 정보 저장 
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		// 사용자 session 정보 지우기 
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
}
