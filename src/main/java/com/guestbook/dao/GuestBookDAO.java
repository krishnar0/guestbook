package com.guestbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guestbook.model.GuestBook;

@Repository
public interface GuestBookDAO extends JpaRepository<GuestBook, Long>{
 
	@Query("SELECT g FROM GuestBook g  WHERE g.userId = :userId")
    List<GuestBook> findByUserId(@Param("userId") Long userId);
}