package com.jeesung.dollarwatch.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberController {
	
	@Autowired
	private MemberDAO mDAO;
	
	@RequestMapping(value = "/member.join.go", method = RequestMethod.GET)
	public String joinGo(HttpServletRequest req) {
		req.setAttribute("cp", "join.jsp");
		return "index";
	}
	
	@RequestMapping(value = "/member.join", method = RequestMethod.POST)
	public String join(Member m,HttpServletRequest req) {
		mDAO.join(m, req);
		req.setAttribute("cp", "login.jsp");
		return "index";
	}
	
	@RequestMapping(value = "/member.login.go", method = RequestMethod.GET)
	public String loginGo(HttpServletRequest req) {
		req.setAttribute("cp", "login.jsp");
		return "index";
	}
	
	@RequestMapping(value = "/member.login", method = RequestMethod.POST)
	public String login(Member m, HttpServletRequest req) {
		mDAO.login(m, req);
		if (mDAO.isLogined(req)) {
			req.setAttribute("cp", "home.jsp");
		} else {
			req.setAttribute("cp", "login.jsp");
		}
		return "index";
		
	}
	
	@RequestMapping(value = "/member.logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		mDAO.logout(req);
		req.setAttribute("cp", "home.jsp");
		return "index";
	}

}
