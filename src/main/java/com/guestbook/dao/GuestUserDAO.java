package com.guestbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guestbook.model.GuestUser;
 
@Repository
@Transactional
public interface GuestUserDAO extends JpaRepository<GuestUser, Long>{
	
    @Query("SELECT g FROM GuestUser g  WHERE g.userName = :userName")
    GuestUser findUserAccount(@Param("userName") String userName);
}