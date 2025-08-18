package com.example.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_users_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class) 
public class UserRole {
	
	@Id //明示的な前の宣言不要、not null制約も付く
	@Column(name = "user_id") // データベース記述の名を他のエンティティで使用したため、明示的に、記述
	private Long userId;
	
	@Id
	@Column(name = "role_id")
	private Long roleId;
	
	

}
