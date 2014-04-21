package org.hbhk.aili.security.server.controller;

import org.hbhk.aili.security.share.define.SecurityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SecurityConstant.moduleName)
public class LoginController {

	@RequestMapping("/loginpage")
	private String loginpage() {
		return "loginpage";
	}
	
	@RequestMapping("/logout")
	private String logout() {
		
		return "loginpage";
	}

}