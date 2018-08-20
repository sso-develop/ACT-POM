package com.lambert.act.web.act.controller;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	
	
	@RequestMapping(value="/main.htm",method = RequestMethod.GET) 
	public String main(HttpServletRequest request, ServletResponse response, Model model){
		return "index";
	}
	
}
