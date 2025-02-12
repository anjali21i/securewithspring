package com.ianjali.securewithspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/")
public class IndexLaunch {
	
	@GetMapping("/")
	public String index() {		
		return "Welcome to index page";		
	}
	
	@GetMapping("welcome")
	public String welcomeLaunch() {		
		return "Welcome to test page";		
	}
	
	//same site script or new session each time then no require csrf
	@ResponseBody
	@GetMapping("/getCsrfToken")
	public CsrfToken getCsrfToken(CsrfToken csrfToken) {
		return csrfToken;
	}
}
