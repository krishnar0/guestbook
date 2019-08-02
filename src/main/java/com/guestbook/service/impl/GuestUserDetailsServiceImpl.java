package com.guestbook.service.impl;

import java.util.ArrayList;
import java.util.List;
 
import com.guestbook.dao.GuestUserDAO;
import com.guestbook.model.GuestUser;
import com.guestbook.controller.GuestUserController;
import com.guestbook.dao.GuestRoleDAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
public class GuestUserDetailsServiceImpl implements UserDetailsService {

	private static Logger  logger = LogManager.getLogger(GuestUserController.class);
	
    @Autowired
    private GuestUserDAO guestUserDAO;
 
    @Autowired
    private GuestRoleDAO guestRoleDAO;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	GuestUser guestUser = guestUserDAO.findUserAccount(userName);
 
        if (guestUser == null) {
            logger.info("User not found!", () -> userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        logger.info("User found", () -> guestUser);
 
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = guestRoleDAO.getRoleNames(guestUser.getUserId());
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
 
        UserDetails guestDetails = (UserDetails) new User(guestUser.getUserName(), //
        		guestUser.getEncrytedPassword(), grantList);
 
        return guestDetails;
    }
 
}
