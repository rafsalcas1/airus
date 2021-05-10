package com.tfgrafsalcas1.airus.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("")
	public String index(final Map<String, Object> model) {
		//return "/templatemo_536_dream_pulse/index";
		return "/home";
	}
	
}