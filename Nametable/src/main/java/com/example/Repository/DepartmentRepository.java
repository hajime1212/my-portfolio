package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
