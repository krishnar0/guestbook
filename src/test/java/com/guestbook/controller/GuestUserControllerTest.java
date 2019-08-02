package com.guestbook.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import com.guestbook.model.GuestRole;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;
import com.guestbook.service.GuestBookDetailsService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class GuestUserControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	TestingAuthenticationToken testingAuthenticationToken;
	
	@BeforeAll
    public void init(){
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        
        User user = new User("test101","", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        
	}
	
	@Test
	public void testLoginPage() throws Exception {
		mockMvc.perform(post("/")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                )
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/loginPage.jsp"));
		
	}

	@Test
	public void testLogoutSuccessfulPage() throws Exception {
		mockMvc.perform(post("/logout")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                )
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/logoutSuccessfulPage.jsp"));
	}

	@Test
	public void testGuestBookInfo() throws Exception {
		mockMvc.perform(post("/guestBookInfo")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                )
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/guestEntriesPage.jsp"));
	}

	@Test
	public void testAccessDenied() throws Exception {
		String message = "Hi " + testingAuthenticationToken.getName() //
        + "<br> You do not have permission to access this page!";
		mockMvc.perform(get("/403")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                )
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/403Page.jsp"))
				.andExpect(model().attribute("message", message));
	}
}