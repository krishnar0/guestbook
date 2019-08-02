package com.guestbook.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guestbook.dao.GuestBookDAO;
import com.guestbook.model.GuestBook;
import com.guestbook.service.GuestBookDetailsService;

@Service
@Transactional
public class GuestBookDetailsServiceImpl implements GuestBookDetailsService{
	
	private final GuestBookDAO guestBookDAO;
	
	public GuestBookDetailsServiceImpl(GuestBookDAO guestBookDAO) {
        this.guestBookDAO = guestBookDAO;
    }
	
	public List<GuestBook> allGuestBookEntries() {
		
		return guestBookDAO.findAll();
	}
	
	public List<GuestBook> userGuestBookEntries(Long id) {
		
		return guestBookDAO.findByUserId(id);
	}
	
	public GuestBook guestDetails(Long guestBookId) {
		
		return guestBookDAO.getOne(guestBookId);
		
	}
	
	public GuestBook addGuestBook(GuestBook entity) {
    	
		guestBookDAO.save(entity);
		
		return entity;
    }
    
    public Long deleteGuestBook(Long id) {
    	
    	guestBookDAO.deleteById(id);
    	
    	guestBookDAO.findById(id);
    	return id;
    }
    
    public GuestBook approveGuestBook(Long id) {
    	
    	GuestBook guestBook = guestBookDAO.getOne(id);
    	guestBook.setStatus(1);
    	guestBookDAO.save(guestBook);
    	
    	return guestBook;
    }
    
    public GuestBook rejectGuestBook(Long id) {
    	
    	GuestBook guestBook = guestBookDAO.getOne(id);
    	guestBook.setStatus(2);
    	guestBookDAO.save(guestBook);
    	
    	return guestBook;
    }
}
