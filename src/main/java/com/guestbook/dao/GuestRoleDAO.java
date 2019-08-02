package com.guestbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.guestbook.model.GuestBook;
import com.guestbook.model.GuestUser;
import com.guestbook.model.UserRole;
 
@Repository
@Transactional
public interface GuestRoleDAO extends JpaRepository<UserRole, Long>{
 
	@Query("SELECT g.guestRole.roleName FROM UserRole g  WHERE g.guestUser.userId = :userId")
    List<String> getRoleNames(@Param("userId") Long userId);
}