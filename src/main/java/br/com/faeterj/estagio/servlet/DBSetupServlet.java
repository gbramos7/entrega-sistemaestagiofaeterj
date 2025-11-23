/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.DataSourceProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DBSetupServlet")
public class DBSetupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>Iniciando Configuração do Banco...</h1>");
            
            try (Connection con = DataSourceProvider.getConnection();
                 Statement stmt = con.createStatement()) {
                
                // 1. CRIA AS TABELAS
                String[] tabelas = {
                    "CREATE TABLE IF NOT EXISTS usuarios (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100), email VARCHAR(100), senha VARCHAR(100), tipo VARCHAR(20), matricula VARCHAR(20), curso VARCHAR(50), periodo VARCHAR(20), turno VARCHAR(20), telefone VARCHAR(20), data_nascimento VARCHAR(20))",
                    
                    "CREATE TABLE IF NOT EXISTS Vaga (id INT AUTO_INCREMENT PRIMARY KEY, titulo VARCHAR(100), descricao TEXT, cidade VARCHAR(100), remoto BOOLEAN, empresa_id INT, ativa BOOLEAN DEFAULT TRUE)",
                    
                    "CREATE TABLE IF NOT EXISTS candidatura (id INT AUTO_INCREMENT PRIMARY KEY, aluno_id INT, vaga_id INT, status VARCHAR(20) DEFAULT 'Pendente', data_candidatura TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
                    
                    "CREATE TABLE IF NOT EXISTS RelatorioEstagio (id INT AUTO_INCREMENT PRIMARY KEY, aluno_id INT, tipo VARCHAR(50), caminho_arquivo VARCHAR(255), status VARCHAR(20) DEFAULT 'Em Análise', enviado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
                    
                    "CREATE TABLE IF NOT EXISTS solicitacao_convenio (id INT AUTO_INCREMENT PRIMARY KEY, aluno_id INT, nome_empresa VARCHAR(100), email_empresa VARCHAR(100), site_empresa VARCHAR(100), status VARCHAR(20) DEFAULT 'Pendente', data_solicitacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
                };

                for (String sql : tabelas) {
                    stmt.executeUpdate(sql);
                    out.println("<p>Tabela verificada/criada com sucesso.</p>");
                }

                // 2. INSERE DADOS DE TESTE (Apenas se não existirem)
                // Usamos IGNORE para não duplicar se você rodar duas vezes
                String[] inserts = {
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo, matricula, curso) VALUES (1, 'Gabriel Aluno', 'aluno@faeterj.br', '123', 'aluno', '20231001', 'Sistemas')",
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo) VALUES (2, 'Coordenação', 'admin@faeterj.br', '123', 'secretaria')",
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo) VALUES (3, 'Tech Solutions', 'rh@tech.com', '123', 'empresa')",
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo) VALUES (10, 'DataCorp', 'rh@data.com', '123', 'empresa')",
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo) VALUES (11, 'JavaHouse', 'rh@java.com', '123', 'empresa')",
                    "INSERT IGNORE INTO usuarios (id, nome, email, senha, tipo) VALUES (12, 'Pixel Design', 'rh@pixel.com', '123', 'empresa')",
                    
                    "INSERT IGNORE INTO Vaga (id, titulo, descricao, cidade, remoto, empresa_id, ativa) VALUES (101, 'Dev Java Jr', 'Spring Boot e API.', 'Rio de Janeiro - RJ', 0, 11, 1)",
                    "INSERT IGNORE INTO Vaga (id, titulo, descricao, cidade, remoto, empresa_id, ativa) VALUES (102, 'Dados Jr', 'Python e SQL.', 'São Paulo - SP', 1, 10, 1)",
                    
                    "INSERT IGNORE INTO candidatura (aluno_id, vaga_id, status) VALUES (1, 101, 'Pendente')"
                };

                for (String sql : inserts) {
                    try {
                        stmt.executeUpdate(sql);
                        out.println("<p>Dado inserido.</p>");
                    } catch (Exception e) {
                        // Ignora erro de chave duplicada
                    }
                }

                out.println("<h2 style='color:green'>SUCESSO TOTAL! O BANCO ESTÁ PRONTO.</h2>");
                out.println("<a href='login.jsp'>Ir para Login</a>");

            } catch (Exception e) {
                out.println("<h2 style='color:red'>ERRO: " + e.getMessage() + "</h2>");
                e.printStackTrace(out);
            }
        }
    }
}