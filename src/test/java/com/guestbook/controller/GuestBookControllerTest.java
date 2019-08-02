package com.guestbook.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.guestbook.dao.GuestBookDAO;
import com.guestbook.dao.GuestRoleDAO;
import com.guestbook.dao.GuestUserDAO;
import com.guestbook.dao.UserRoleDAO;
import com.guestbook.model.GuestBook;
import com.guestbook.model.GuestRole;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;
import com.guestbook.service.GuestBookDetailsService;
	
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class GuestBookControllerTest {

	private MockMvc mockMvc;

	@MockBean
    private GuestBookDetailsService guestBookDetailsService;

	@Autowired
    private WebApplicationContext webApplicationContext;
	
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
	
	@BeforeAll
    public void init(){
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        
        user = new User("test101","", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        
        GuestUser guestUserData = new GuestUser(user.getUsername(), user.getPassword(), true);
        guestUserDao.save(guestUserData);
        
        GuestRole guestRoleData = new GuestRole("ROLE_ADMIN");
        userRoleDao.save(guestRoleData);
        
        UserRole userRoleData = new UserRole(guestUserData, guestRoleData);
        guestRoleDao.save(userRoleData);
    }

	@SuppressWarnings("unchecked")
	@Test
	public void testUserInfo() throws Exception {
		
		//SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        
		GuestBook guestBookEntry1= new GuestBook("test entry1", null, 1L, 1);
		GuestBook guestBookEntry2 = new GuestBook("test entry2", null, 1L, 1);
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		guestBookList.add(guestBookEntry1);
		guestBookList.add(guestBookEntry2);
		
		List<String> guestRoles = new ArrayList<String>();
		guestRoles.add("ROLE_ADMIN");
		when(guestBookDetailsService.allGuestBookEntries()).thenReturn(Arrays.asList(guestBookEntry1, guestBookEntry2));
		mockMvc.perform(post("/guestBook")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                .param("userName", "test")
                )
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/jsp/guestEntriesPage.jsp"))
		        .andExpect(model().attribute("guestBookEntries",guestBookList))
		        .andExpect(model().attribute("roleInfo", guestRoles));
		
		verify(guestBookDetailsService, times(1)).allGuestBookEntries();

		//Assertions.fail("");
	}

	@Test
	public void testSave() throws Exception {
		
		//SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
		
		GuestBook guestBookEntry1= new GuestBook("test entry3", null, 1L, 1);
		
		List<String> guestRoles = new ArrayList<String>();
		guestRoles.add("ROLE_ADMIN");
		when(guestBookDetailsService.addGuestBook(guestBookEntry1)).thenReturn(guestBookEntry1);
		
		
		mockMvc.perform(post("/saveOrUpdate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                .param("inputType", "Text")
                .flashAttr("guestEntry", guestBookEntry1)
                )
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/guestBook"));
		
		

	}
	
	@Test
	public void testApproveGuestBook() throws Exception {
		
		
		List<String> guestRoles = new ArrayList<String>();
		guestRoles.add("ROLE_ADMIN");
		
		GuestBook guestBookEntry1= new GuestBook("test entry4", null, 1L, 0);
		guestBookDao.save(guestBookEntry1);
		
		when(guestBookDetailsService.approveGuestBook(guestBookEntry1.getGuestBookId())).thenReturn(guestBookEntry1);
		
		
		mockMvc.perform(post("/approve/"+guestBookEntry1.getGuestBookId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                .param("inputType", "Text")
                .flashAttr("guestEntry", guestBookEntry1)
                )
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/guestBook"));
	}

	
	@Test
	public void testRejectGuestBook() throws Exception {
		
		GuestBook guestBookEntry1= new GuestBook("test entry5", null, 1L, 1);
		guestBookDao.save(guestBookEntry1);
		
		List<String> guestRoles = new ArrayList<String>();
		guestRoles.add("ROLE_ADMIN");
		when(guestBookDetailsService.rejectGuestBook(guestBookEntry1.getGuestBookId())).thenReturn(guestBookEntry1);
		
		
		mockMvc.perform(get("/reject/{id}",guestBookEntry1.getGuestBookId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                )
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/guestBook"));
	}

	
	@Test
	public void testDeleteGuestBook() throws Exception {
		
		GuestBook guestBookEntry1= new GuestBook("test entry6", null, 1L, 1);
		guestBookDao.save(guestBookEntry1);
		
		List<String> guestRoles = new ArrayList<String>();
		guestRoles.add("ROLE_ADMIN");
		when(guestBookDetailsService.deleteGuestBook(guestBookEntry1.getGuestBookId())).thenReturn(guestBookEntry1.getGuestBookId());
		
		
		mockMvc.perform(get("/reject/{id}",guestBookEntry1.getGuestBookId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.principal(testingAuthenticationToken)
                .param("inputType", "Text")
                .flashAttr("guestEntry", guestBookEntry1)
                )
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/guestBook"));
	}
}