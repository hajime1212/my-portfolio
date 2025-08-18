package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Employee;

@Repository //Jpaを継承することで、データベースの基本動作を継承
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	// nameをもとに検索するメソッド追加（ログインセキュリティのため）
	Optional<Employee> findByName(String name);

}
