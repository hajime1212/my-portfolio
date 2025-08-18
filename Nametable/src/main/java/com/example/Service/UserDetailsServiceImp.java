package com.example.Service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Entity.Employee;
import com.example.Repository.EmployeeRepository;

// データベースからユーザ情報を取得するためのサービス
@Service
public class UserDetailsServiceImp implements UserDetailsService {

	private final EmployeeRepository employeeRepository;

	
	public UserDetailsServiceImp(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// usersテーブルからユーザ名を探す
		Optional<Employee> employeeOpt = employeeRepository.findByName(username);

		//　データが入ってるか入ってないかを判断
		if (employeeOpt.isPresent()) {
			//　合致したユーザのレコードを取得
			Employee employee = employeeOpt.get();

			//　レコードの内容を第3引数まで、セット。権限は複数になる可能性もあるので、リスト形式。
			return new User(employee.getName(), employee.getPassword(), Collections.emptyList());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
