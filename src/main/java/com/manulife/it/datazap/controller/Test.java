package com.manulife.it.datazap.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.swagger.annotations.ApiOperation;
@Controller

public class Test  {
	
	
	@GetMapping("/")
    public String showForm(LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/")
    public String validateLoginInfo(Model model, @Valid LoginForm loginForm, BindingResult bindingResult) {
        if (!loginForm.getPassword().equals("pavan")) {
        	return "login";
			
		}
        model.addAttribute("user", loginForm.getuserName());
        return "home";
    }


	
	

}

