package org.hbhk.aili.security.server.controller;

import javax.annotation.Resource;

import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.SecurityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SecurityConstant.moduleName)
public class LoginController {

	@Resource
	private IUserService userService;

	@RequestMapping("/login")
	private String login(String username, String password) {
		userService.login(username, password);
		return "login";
	}

}