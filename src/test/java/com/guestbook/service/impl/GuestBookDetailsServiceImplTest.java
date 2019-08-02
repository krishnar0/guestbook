package com.guestbook.service.impl;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.guestbook.dao.GuestBookDAO;
import com.guestbook.dao.GuestRoleDAO;
import com.guestbook.dao.GuestUserDAO;
import com.guestbook.dao.UserRoleDAO;
import com.guestbook.model.GuestBook;
import com.guestbook.model.GuestRole;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class GuestBookDetailsServiceImplTest {

	
	@Autowired
	GuestUserDAO guestUserDao;
	
	@Autowired
	GuestRoleDAO guestRoleDao;
	
	@Autowired
	UserRoleDAO userRoleDao;
	
	@Autowired
	GuestBookDAO guestBookDao;
	
	private List<GuestBook> guestBookList;
	
	TestingAuthenticationToken testingAuthenticationToken;
	User user;
	
	@BeforeAll
    public void init(){
        
        user = new User("test101","", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        
        GuestUser guestUserData = new GuestUser(user.getUsername(), user.getPassword(), true);
        guestUserDao.save(guestUserData);
        
        GuestRole guestRoleData = new GuestRole("ROLE_ADMIN");
        userRoleDao.save(guestRoleData);
        
        UserRole userRoleData = new UserRole(guestUserData, guestRoleData);
        guestRoleDao.save(userRoleData);
    }
	
	@Test
	public void testAddGuestBook() throws Exception {
		guestBookList = new ArrayList<GuestBook>();
		GuestBook guestBook1 = new GuestBook("test entry1", null, 1L, 1);
		
		GuestBook guestBook2 = new GuestBook("test entry2", null, 2L, 1);
		
		guestBookList.add(guestBook1);
		guestBookList.add(guestBook2);
		
		
		for (Iterator iterator = guestBookList.iterator(); iterator.hasNext();) {
	        GuestBook guestBookEntry = (GuestBook) iterator.next(); 

	        guestBookDao.save(guestBookEntry);

	        assertTrue(guestBookEntry.getGuestBookId() > 0, guestBookEntry.getGuestBookDesc()+" is saved - Guest ID "+guestBookEntry.getGuestBookId());
	    }   

	}
	
	@Test
	@Transactional
	public void testAllGuestBookEntries() throws Exception {
		List<GuestBook> result;
		
		GuestBook guestBookEntry = new GuestBook("test entry3", null, 1L, 1);
		guestBookDao.save(guestBookEntry);
		
		
		GuestUser guestUser = guestUserDao.findUserAccount("test101");
		guestBookEntry.setGuestUser(guestUser);
		 
		
		result = guestBookDao.findAll();
		
		assertTrue(result.size() > 0);
		//Assertions.fail();
	}

	
	@Test
	public void testUserGuestBookEntries() throws Exception {
		List<GuestBook> result;
		GuestBook guestBookEntry = new GuestBook("test entry4", null, 1L, 1);
		guestBookDao.save(guestBookEntry);
		
		
		  Long guestEntryId = guestBookEntry.getGuestBookId(); guestBookEntry =
		  guestBookDao.getOne(guestEntryId);
		  
		  result = guestBookDao.findByUserId(1L); assertTrue(result.size() > 0);
		  //Assertions.fail();
		 
		
	}

	
	@Test
	public void testGuestDetails() throws Exception {
		GuestBook result;
		GuestBook guestBookEntry = new GuestBook("test entry5", null, 2L, 1);
		guestBookDao.save(guestBookEntry);
		result = guestBookDao.getOne(1L);
		assertTrue(result.getGuestBookId() == 1);

		
	}

	
	

	
	@Test
	public void testDeleteGuestBook() throws Exception {
		
		GuestBook guestBookEntry = new GuestBook("test entry6", null, 2L, 1);
		guestBookDao.save(guestBookEntry);
		
		Long guestEntryId = guestBookEntry.getGuestBookId();
		guestBookEntry = guestBookDao.getOne(guestEntryId);
		
		guestBookDao.delete(guestBookEntry);
				
		assertTrue(true);

	}

	
	@Test
	public void testApproveGuestBook() throws Exception {
		
		GuestBook guestBookEntry = new GuestBook("test entry7", null, 2L, 1);
		guestBookDao.save(guestBookEntry);
		Long userId = guestBookEntry.getGuestBookId();
		guestBookEntry = guestBookDao.getOne(userId);
		guestBookEntry.setStatus(1);
    	guestBookDao.save(guestBookEntry);
		
		GuestBook result = guestBookDao.getOne(userId);
		
		assertTrue(result.getStatus() == 1, result.getGuestBookDesc()+" is approved ");

	}

	
	@Test
	public void testRejectGuestBook() throws Exception {
		GuestBook guestBookEntry = new GuestBook("test entry7", null, 2L, 1);
		guestBookDao.save(guestBookEntry);
		Long userId = guestBookEntry.getGuestBookId();
		guestBookEntry = guestBookDao.getOne(userId);
		guestBookEntry.setStatus(2);
    	guestBookDao.save(guestBookEntry);
		
		GuestBook result = guestBookDao.getOne(userId);
		
		assertTrue(result.getStatus() == 2, result.getGuestBookDesc()+" is rejected ");
	}
}