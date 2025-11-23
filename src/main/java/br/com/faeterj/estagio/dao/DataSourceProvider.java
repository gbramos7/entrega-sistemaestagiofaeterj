package br.com.faeterj.estagio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // 1. Tenta pegar as variáveis de ambiente do Railway (Nuvem)
        String cloudUrl = System.getenv("MYSQL_URL");
        
        if (cloudUrl != null) {
            // --- MODO NUVEM (RAILWAY) ---
            // O Railway fornece a URL completa com usuário e senha
            return DriverManager.getConnection(cloudUrl);
        } else {
            // --- MODO LOCAL (XAMPP) ---
            String localUrl = "jdbc:mysql://localhost:3306/sistema_estagio?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
            String localUser = "root";
            String localPass = ""; // Senha vazia do XAMPP
            
            return DriverManager.getConnection(localUrl, localUser, localPass);
        }
    }
    
    public static DataSourceProvider getDataSource() {
        return new DataSourceProvider();
    }
}