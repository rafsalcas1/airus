package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tfgrafsalcas1.airus.documents.User;
import com.tfgrafsalcas1.airus.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	private static final String VIEWS_USER_CREATE_FORM = "users/createUserForm";
	private static final String VIEWS_USER_LIST = "users/listUsers";

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/usuarios")
	public static String listUsers(final Map<String, Object> model) throws IOException {
		List<User> users = UserService.findAll();
		System.out.println(users.size());
		User user = users.get(0);
		System.out.println(user.getNombre());
		model.put("users", users);
		return VIEWS_USER_LIST;
	}
	
	/*
	@GetMapping("/new")
	public String add(Model model) {		
		model.addAttribute("user", new User());
		return VIEWS_USER_CREATE_FORM;
	}

	@PostMapping(value = "/new")
	public String addUser(@ModelAttribute @Validated User user) {
		userService.saveUser(user);
		return VIEWS_USER_LIST;
	}
	*/

	@RequestMapping("/hola")
    public static String diHola(){
		return "hola";
    }
/*
	@GetMapping("/users/{userId}")
	public ModelAndView showUser(@PathVariable("userId") final int userId) {
		ModelAndView mav = new ModelAndView("users/userDetails");
		mav.addObject(this.userService.findUserById(userId));
		return mav;
	}
*/	
}
