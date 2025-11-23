/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.Candidatura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CandidaturaDAO {

    // 1. SALVAR CANDIDATURA
    public void candidatar(Candidatura c) {
        String sql = "INSERT INTO candidatura (aluno_id, vaga_id, status, data_candidatura) VALUES (?, ?, ?, NOW())";
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getAlunoId());
            ps.setInt(2, c.getVagaId());
            // Garante que nunca grava status nulo
            ps.setString(3, (c.getStatus() != null ? c.getStatus() : "Pendente"));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 2. LISTAR POR ALUNO (VERSÃO BLINDADA CONTRA ERROS)
    public List<Candidatura> listarPorAluno(int alunoId) {
        List<Candidatura> lista = new ArrayList<>();
        
        // SQL Completo: Garante que estamos pedindo 'v.cidade' e 'v.descricao'
        String sql = "SELECT c.id, c.vaga_id, c.status, c.data_candidatura, " +
                     "v.titulo, v.descricao, v.cidade " + 
                     "FROM candidatura c " +
                     "JOIN Vaga v ON c.vaga_id = v.id " +
                     "WHERE c.aluno_id = ? ORDER BY c.id DESC LIMIT 10"; 

        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Candidatura c = new Candidatura();
                
                // --- BLOCO DE LEITURA SEGURO ---
                try {
                    c.setId(rs.getInt("id"));
                    c.setVagaId(rs.getInt("vaga_id"));
                    
                    // Status
                    String status = rs.getString("status");
                    c.setStatus(status != null ? status : "Pendente");
                    
                    // Título
                    String titulo = rs.getString("titulo");
                    c.setVagaTitulo(titulo != null ? titulo : "Vaga sem título");
                    
                    // Descrição (Proteção extra contra nulo)
                    String desc = rs.getString("descricao");
                    if (desc != null) {
                        c.setVagaDescricao(desc);
                    } else {
                        c.setVagaDescricao("Descrição não disponível.");
                    }
                    
                    // Cidade (Proteção extra contra nulo) - AQUI DAVA O ERRO
                    String cid = rs.getString("cidade");
                    if (cid != null) {
                        c.setVagaCidade(cid);
                    } else {
                        c.setVagaCidade("Local a definir");
                    }

                    // Data
                    Timestamp ts = rs.getTimestamp("data_candidatura");
                    if (ts != null) {
                        c.setDataCandidatura(ts.toLocalDateTime());
                    }
                    
                } catch (Exception ex) {
                    System.out.println("Erro ao ler linha da candidatura: " + ex.getMessage());
                    // Não para o loop, tenta ler a próxima
                }

                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("ERRO CRÍTICO NO DAO: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // 3. LISTAR PARA A EMPRESA (Mantido igual)
    public List<Candidatura> listarCandidatosPorVaga(int vagaId) {
        List<Candidatura> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.status, c.data_candidatura, u.nome, u.email, u.curso, u.telefone " +
                     "FROM candidatura c JOIN usuarios u ON c.aluno_id = u.id " +
                     "WHERE c.vaga_id = ? ORDER BY c.data_candidatura DESC";
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, vagaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidatura c = new Candidatura();
                c.setId(rs.getInt("id"));
                c.setStatus(rs.getString("status"));
                c.setNomeAluno(rs.getString("nome"));
                c.setEmailAluno(rs.getString("email"));
                c.setCursoAluno(rs.getString("curso"));
                c.setTelefoneAluno(rs.getString("telefone"));
                Timestamp ts = rs.getTimestamp("data_candidatura");
                if (ts != null) c.setDataCandidatura(ts.toLocalDateTime());
                lista.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    // ... métodos anteriores ...

    // --- ÁREA DA SECRETARIA ---

    // 1. Listar todas as candidaturas pendentes (com todos os dados)
    public List<Candidatura> listarPendentesSecretaria() {
        List<Candidatura> lista = new ArrayList<>();
        
        // JOIN QUÁDRUPLO: Candidatura -> Vaga -> Usuario(Aluno) -> Usuario(Empresa)
        String sql = "SELECT c.id, c.status, c.data_candidatura, " +
                     "aluno.nome AS nome_aluno, " +
                     "v.titulo AS nome_vaga, " +
                     "empresa.nome AS nome_empresa " +
                     "FROM candidatura c " +
                     "JOIN usuarios aluno ON c.aluno_id = aluno.id " +
                     "JOIN Vaga v ON c.vaga_id = v.id " +
                     "JOIN usuarios empresa ON v.empresa_id = empresa.id " +
                     "WHERE c.status = 'Pendente' " +
                     "ORDER BY c.data_candidatura ASC";

        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Candidatura c = new Candidatura();
                c.setId(rs.getInt("id"));
                c.setStatus(rs.getString("status"));
                
                // Preenche os dados cruzados
                c.setNomeAluno(rs.getString("nome_aluno"));
                c.setVagaTitulo(rs.getString("nome_vaga"));
                c.setNomeEmpresa(rs.getString("nome_empresa"));
                
                Timestamp ts = rs.getTimestamp("data_candidatura");
                if (ts != null) c.setDataCandidatura(ts.toLocalDateTime());

                lista.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // 2. Atualizar status (Encaminhar ou Rejeitar)
    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE candidatura SET status = ? WHERE id = ?";
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}