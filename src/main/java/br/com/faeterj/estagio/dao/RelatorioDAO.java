/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.RelatorioEstagio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    
    // SALVAR RELATÓRIO
    public void salvar(RelatorioEstagio r) {
        // Forçamos o status "Em Análise" e a data atual (NOW())
        String sql = "INSERT INTO RelatorioEstagio (aluno_id, tipo, caminho_arquivo, status, enviado_em) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, r.getAlunoId());
            ps.setString(2, r.getTipo());
            ps.setString(3, r.getCaminhoArquivo());
            ps.setString(4, "Em Análise"); // <--- CORREÇÃO: Define status padrão
            
            ps.executeUpdate();
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // LISTAR RELATÓRIOS
    public List<RelatorioEstagio> listarPorAluno(int alunoId) {
        List<RelatorioEstagio> lista = new ArrayList<>();
        String sql = "SELECT * FROM RelatorioEstagio WHERE aluno_id = ? ORDER BY id DESC";
        
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                RelatorioEstagio r = new RelatorioEstagio();
                r.setId(rs.getInt("id"));
                r.setAlunoId(rs.getInt("aluno_id"));
                r.setTipo(rs.getString("tipo"));
                r.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                
                // Trata status nulo visualmente
                String st = rs.getString("status");
                r.setStatus(st == null ? "Enviado" : st);
                
                // Pega a data
                Timestamp ts = rs.getTimestamp("enviado_em");
                if (ts != null) r.setEnviadoEm(ts.toLocalDateTime());
                
                lista.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    public List<RelatorioEstagio> listarPendentesAdmin() {
        List<RelatorioEstagio> lista = new ArrayList<>();
        
        // JOIN para pegar o nome do aluno
        String sql = "SELECT r.*, u.nome as nome_aluno " +
                     "FROM RelatorioEstagio r " +
                     "JOIN usuarios u ON r.aluno_id = u.id " +
                     "WHERE r.status = 'Em Análise' " +
                     "ORDER BY r.enviado_em ASC";

        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RelatorioEstagio r = new RelatorioEstagio();
                r.setId(rs.getInt("id"));
                r.setAlunoId(rs.getInt("aluno_id"));
                r.setTipo(rs.getString("tipo"));
                r.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                r.setStatus(rs.getString("status"));
                
                // Preenche o nome do aluno
                r.setNomeAluno(rs.getString("nome_aluno"));
                
                Timestamp ts = rs.getTimestamp("enviado_em");
                if (ts != null) r.setEnviadoEm(ts.toLocalDateTime());

                lista.add(r);
            }
        } catch (SQLException e) {}
        return lista;
    }

    // 2. Aprovar ou Rejeitar Relatório
    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE RelatorioEstagio SET status = ? WHERE id = ?";
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, novoStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {}
    }
}
