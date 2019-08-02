package com.guestbook.service.impl;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.guestbook.dao.GuestBookDAO;
import com.guestbook.dao.GuestRoleDAO;
import com.guestbook.dao.GuestUserDAO;
import com.guestbook.dao.UserRoleDAO;
import com.guestbook.model.GuestRole;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;

@DataJpaTest
public class GuestUserDetailsServiceImplTest {

	
	private static Logger  logger = LogManager.getLogger(GuestUserDetailsServiceImplTest.class.toString());
	
		@Autowired
	GuestUserDAO guestUserDao;
	
	@Autowired
	GuestRoleDAO guestRoleDao;
	
	@Autowired
	UserRoleDAO userRoleDao;
	
	@Autowired
	GuestBookDAO guestBookDao;
	
	TestingAuthenticationToken testingAuthenticationToken;
	User user;
	
	@Test
	public void testLoadUserByUsername() throws Exception {
		        
        user = new User("test101","", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        
        GuestUser guestUserData = new GuestUser(user.getUsername(), user.getPassword(), true);
        guestUserDao.save(guestUserData);
        
        GuestRole guestRoleData = new GuestRole("ROLE_ADMIN");
        userRoleDao.save(guestRoleData);
        
        UserRole userRoleData = new UserRole(guestUserData, guestRoleData);
        guestRoleDao.save(userRoleData);
		
		List<String> roleNames = guestRoleDao.getRoleNames(guestUserData.getUserId());
		 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails guestDetails = (UserDetails) new User(guestUserData.getUserName(), //
        		guestUserData.getEncrytedPassword(), grantList);

		assertTrue(guestDetails.isCredentialsNonExpired());
	}
}