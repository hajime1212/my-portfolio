package com.example.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //データベースに対応するデータ構造
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	@Id
	//自動採番、主キー
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id")
	private Long id;
	
	//nullを許容しない列
	@Column(name = "name", nullable = false)
	@NotBlank(message ="名前は必須項目です。")
	private String name;
	
	@Column(name = "email")
	@Email(message = "入力形式が正しくありません。")
	@NotBlank(message = "メールアドレスは必須項目です。")
	private String email;
	
	@Column(name ="password", nullable = false)
	private String password;

	
	@ManyToOne //多対一の関係に
	//usersテーブルに新しいカラムを用意、従業員がどこかの部署に所属しなけらば行けないことを強制
	@JoinColumn(name = "department_id", nullable = false)
	//Departmentクラスに直接アクセスできるように
	private Department department;
	
	// 多対多の関係。usersテーブルの情報を取得したら、app_rolesテーブルの情報も取得する
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "app_users_roles", //使用中間テーブル
		joinColumns = @JoinColumn(name = "user_id"), // usersテーブル外部キー
		inverseJoinColumns = @JoinColumn(name = "role_id") // rolesテーブル外部キー
		)
	private Set<Role> roles = new HashSet<>(); // null以外のデータが入る、データ重複のない情報が入る
	
}
