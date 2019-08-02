package com.guestbook.service;

import java.util.List;
import java.util.Optional;

import com.guestbook.model.GuestBook;

public interface GuestBookDetailsService {
	
	public List<GuestBook> allGuestBookEntries();
	
	public List<GuestBook> userGuestBookEntries(Long id);
	
	public GuestBook guestDetails(Long guestBookId);
    
    public GuestBook addGuestBook(GuestBook entity);
    
    public Long deleteGuestBook(Long id);
    
    public GuestBook approveGuestBook(Long id);
    
    public GuestBook rejectGuestBook(Long id);
}
