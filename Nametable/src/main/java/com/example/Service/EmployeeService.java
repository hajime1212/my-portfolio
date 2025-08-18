package com.example.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; //jakartaよりも安定

import com.example.Entity.Department;
import com.example.Entity.Employee;
import com.example.Repository.DepartmentRepository;
import com.example.Repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}

	//登録更新
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);

	}
	
	//users全件取得
	@Transactional(readOnly = true) //読み取り専用の指示をデータベースに送る
	public List<Employee>findAll(){
		return employeeRepository.findAll();
	}
	
	//department全件取得
	@Transactional(readOnly = true)
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}
	
	//ID検索　どのデータを更新するか指定する
	@Transactional(readOnly = true)
	public Optional<Employee>findById(Long id){
		return employeeRepository.findById(id);
	}
	
	//削除
	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}
	

}
