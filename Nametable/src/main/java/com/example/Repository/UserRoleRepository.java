package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.UserRole;
import com.example.Entity.UserRoleId;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
