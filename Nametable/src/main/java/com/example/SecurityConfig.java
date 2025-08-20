package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Repository.EmployeeRepository;

@Configuration //アプリケーションの設定クラス
@EnableWebSecurity //Spring Securityの有効化
public class SecurityConfig {
	
	//private final EmployeeRepository employeeRepository;
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(EmployeeRepository employeeRepository, UserDetailsService userDetailsService) {
		//this.employeeRepository = employeeRepository;
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
	 http
	 	.authorizeHttpRequests(authorize -> authorize
	 			//このページはログイン必須で設定
	 			.requestMatchers("/employees").authenticated()
	 			.requestMatchers("/employees/**").authenticated()
	 			//このページはログインなしでアクセスを許可
	 			.requestMatchers("/login").permitAll()
	 			//上記以外のページはすべてログインなしでアクセス許可
	 					.anyRequest().permitAll()
	 					)
	 			.formLogin(formLogin -> formLogin
	 					//このURLにアクセスしたら、springSecurityが用意したログイン画面を表示
	 					.loginPage("/login")
	 					//ログインに成功したら、このURLへ
	 					.defaultSuccessUrl("/employees", true)
	 					)
	 			.logout(logout -> logout
	 					//ログアウトに成功したら、このURLへ
	 					.logoutSuccessUrl("/login")
	 			);
	 	return http.build();
	 	
	}
	
	//パスワードをハッシュ化するメソッド
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
//	@Bean
	// ユーザー照会
//	UserDetailsService userDetailsService() {
		
//		テスト用ユーザ名の設定
//		UserDetails user = User.withUsername("user")
//				//パスワードを設定ハッシュ化
//				.password(passwordEncoder().encode("password"))
//				//権限付与
//				.roles("USER")
//				.build();
//		//ユーザ情報をメモリ上に一時保管するメソッド
//		return new InMemoryUserDetailsManager(user);
		
		// 入力された名前を受けて処理を開始
//		return username -> {
//			// 名前探索
//			Employee employee = employeeRepository.findByName(username)
//					// 名前がなかった時の処理
//					.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//			
//			// 照会した名前から、認証情報を組み立てる
//			return User.withUsername(employee.getName())
//					// パスワードを設定
//					.password(employee.getPassword())
//					// 権限を与える
//					.roles("USER")
//					.build();
//		};
		
		// 上記メソッドは重複していたので、実装クラスを再利用
//		return userDetailsServiceImp;
//	}
}
