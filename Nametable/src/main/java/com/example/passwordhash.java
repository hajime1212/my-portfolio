package com.example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordhash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = "password"; // ★ここに新しいパスワードを設定★
        String hashedPassword = encoder.encode(newPassword);
        System.out.println("ハッシュ化されたパスワード: " + hashedPassword);
    }
}