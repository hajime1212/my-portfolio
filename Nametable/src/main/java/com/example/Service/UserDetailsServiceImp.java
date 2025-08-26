package com.example.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

	// ユーザーがログインした際に、このメソッドがSpring Securityから呼ばれる。
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ユーザ照合
		Employee employee = employeeRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
		
		// 照合したユーザの権限を並べて、柔軟性のあるボックスへ収納
		Collection<? extends GrantedAuthority> authorities = employee.getRoles().stream()
				//　.mapは権限名をSpring Securityが理解できるように加工
				//　SimpleGrantedAuthority←Spring Securityが理解するための権限
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				//　権限をそれぞれ、Listに詰めて、collectにまとめる。
				.collect(Collectors.toList());
		
		// Spring Securityへ情報を返す。
		return new User(employee.getName(), employee.getPassword(), authorities);
		
//		// usersテーブルからユーザ名を探す
//		Optional<Employee> employeeOpt = employeeRepository.findByName(username);
//
//		//　データが入ってるか入ってないかを判断
//		if (employeeOpt.isPresent()) {
//			//　合致したユーザのレコードを取得
//			Employee employee = employeeOpt.get();
//
//			//　レコードの内容を第3引数まで、セット。権限は複数になる可能性もあるので、リスト形式。
//			return new User(employee.getName(), employee.getPassword(), Collections.emptyList());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
	}

}
