package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.UserRole;
import com.example.Entity.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
