package org.hbhk.aili.security.server.controller;

import javax.annotation.Resource;

import org.hbhk.aili.security.server.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class UserController {

	@Resource
	private IUserService userService;

	@RequestMapping("/login")
	private String login(String username, String password) {
		userService.login(username, password);

		return null;
	}

}