package com.guestbook.controller;

import java.security.Principal;

import com.guestbook.GuestbooktestApplication;
import com.guestbook.utils.WebUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class GuestUserController {
 
	private static Logger  logger = LogManager.getLogger(GuestUserController.class);
    
	@RequestMapping(value = { "/", "/login" }, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model) { 
		logger.info("GuestUserController", () -> "loginPage method");
        return "loginPage";
    }
 
     
 
    @RequestMapping(value = {"/logout","/logoutSuccess"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        model.addAttribute("message", "Logout Success");
        logger.info("GuestUserController", () -> "logoutSuccessfulPage method");
        return "logoutSuccessfulPage";
    }
 
    
    
    @RequestMapping(value = "/guestBookInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String guestBookInfo(Model model, Principal principal) {
 
        // After user login successfully.
        String userName = principal.getName();
 
        logger.info("user Name", () -> userName);
 
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
        String guestBookInfo = WebUtils.toString(loginedUser);
        model.addAttribute("guestBookInfo", guestBookInfo);
        logger.info("GuestUserController", () -> "guestBookInfo method");
 
        return "userInfoPage";
    }
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("userInfo", userInfo);
 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
 
        }
        logger.info("GuestUserController", () -> "accessDenied method");
 
        return "403Page";
    }
 
}