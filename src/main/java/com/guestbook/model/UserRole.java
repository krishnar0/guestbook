package com.guestbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
@Entity
@Table(name = "User_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "USER_ROLE_UK", columnNames = { "User_Id", "Role_Id" }) })
public class UserRole {
 
	public UserRole() {}
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private GuestUser guestUser;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_Id", nullable = false)
    private GuestRole guestRole;
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public GuestUser getGuestUser() {
        return guestUser;
    }
 
    public void setGuestUser(GuestUser guestUser) {
        this.guestUser = guestUser;
    }
 
    public GuestRole getGuestRole() {
        return guestRole;
    }
 
    public void setGuestRole(GuestRole guestRole) {
        this.guestRole = guestRole;
    }

	public UserRole(GuestUser guestUser, GuestRole guestRole) {
		this.guestUser = guestUser;
		this.guestRole = guestRole;
	}
     
    
}