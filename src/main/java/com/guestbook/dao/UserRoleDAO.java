package com.guestbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guestbook.model.GuestRole;

public interface UserRoleDAO extends JpaRepository<GuestRole, Long>{

}
