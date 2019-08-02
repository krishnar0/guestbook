package com.guestbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Guest_Book", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "GUEST_BOOK_UK", columnNames = "GUEST_BOOK_ID") })
public class GuestBook {
	
	public GuestBook(){}
	
	@Id
    @GeneratedValue
    @Column(name = "GUEST_BOOK_ID", nullable = false)
    private Long guestBookId;
 
    @Column(name = "GUEST_BOOK_DESC", length = 2048, nullable = true)
    private String guestBookDesc;
    
    @Lob
    @Column( name = "GUEST_BOOK_IMAGE" )
    private byte[ ] guestBookImage ;
    
    @Column(name = "User_Id", nullable = false)
    private Long userId;
    
    @Column(name = "STATUS", nullable = false)
    private int status;
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "User_Id",  insertable=false, updatable=false)
    private GuestUser guestUser;

	public Long getGuestBookId() {
		return guestBookId;
	}

	public void setGuestBookId(Long guestBookId) {
		this.guestBookId = guestBookId;
	}

	public String getGuestBookDesc() {
		return guestBookDesc;
	}

	public void setGuestBookDesc(String guestBookDesc) {
		this.guestBookDesc = guestBookDesc;
	}

	public byte[] getGuestBookImage() {
		return guestBookImage;
	}

	public void setGuestBookImage(byte[] guestBookImage) {
		this.guestBookImage = guestBookImage;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public GuestUser getGuestUser() {
		return guestUser;
	}

	public void setGuestUser(GuestUser guestUser) {
		this.guestUser = guestUser;
	}
    
	public GuestBook(Long guestBookId, String guestBookDesc,byte[] guestBookImage,Long userId,int status)
	{
		this.guestBookId = guestBookId;
		this.guestBookDesc = guestBookDesc;
		this.guestBookImage = guestBookImage;
		this.userId = userId;
		this.status= status;
	}
    
	public GuestBook( String guestBookDesc,byte[] guestBookImage,Long userId,int status)
	{
		this.guestBookDesc = guestBookDesc;
		this.guestBookImage = guestBookImage;
		this.userId = userId;
		this.status= status;
	}
}
