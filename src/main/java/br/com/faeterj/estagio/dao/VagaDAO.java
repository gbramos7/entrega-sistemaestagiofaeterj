/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.Vaga;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO {

    // 1. LISTAR TODAS ATIVAS (Para Alunos)
    public List<Vaga> listarAtivas() {
        List<Vaga> vagas = new ArrayList<>();
        String sql = "SELECT v.*, u.nome as nome_empresa " +
                     "FROM Vaga v " +
                     "JOIN usuarios u ON v.empresa_id = u.id " +
                     "WHERE v.ativa = true " +
                     "ORDER BY v.id DESC"; 

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vagas.add(montarVaga(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vagas ativas: " + e.getMessage());
            e.printStackTrace();
        }
        return vagas;
    }

    // 2. FILTRAR (Para Alunos)
    public List<Vaga> filtrar(String cidade, String titulo, String modelo) {
        List<Vaga> vagas = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
                "SELECT v.*, u.nome as nome_empresa " +
                "FROM Vaga v " +
                "JOIN usuarios u ON v.empresa_id = u.id " +
                "WHERE v.ativa = true");
        
        List<Object> parametros = new ArrayList<>();

        if (cidade != null && !cidade.trim().isEmpty()) {
            sql.append(" AND v.cidade LIKE ?");
            parametros.add("%" + cidade + "%");
        }
        if (titulo != null && !titulo.trim().isEmpty()) {
            sql.append(" AND v.titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (modelo != null && !modelo.isEmpty()) {
            sql.append(" AND v.remoto = ?");
            parametros.add(Boolean.valueOf(modelo));
        }
        
        sql.append(" ORDER BY v.id DESC");

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) ps.setString(i + 1, (String) param);
                else if (param instanceof Boolean) ps.setBoolean(i + 1, (Boolean) param);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vagas.add(montarVaga(rs));
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return vagas;
    }

    // 3. MÉTODO AUXILIAR (Monta o objeto a partir do ResultSet)
    private Vaga montarVaga(ResultSet rs) throws SQLException {
        Vaga vaga = new Vaga();
        vaga.setId(rs.getInt("id"));
        vaga.setTitulo(rs.getString("titulo"));
        vaga.setDescricao(rs.getString("descricao"));
        vaga.setCidade(rs.getString("cidade"));
        vaga.setRemoto(rs.getBoolean("remoto"));
        vaga.setEmpresaId(rs.getInt("empresa_id"));
        vaga.setAtiva(rs.getBoolean("ativa"));
        
        // Tenta pegar o nome da empresa (se vier do JOIN)
        try {
            vaga.setNomeEmpresa(rs.getString("nome_empresa"));
        } catch (SQLException e) {
            // Se não tiver a coluna, ignora
        }
        
        return vaga;
    }

    // 4. CRIAR VAGA (Onde estava o seu erro)
    public void criar(Vaga vaga) {
        String sql = "INSERT INTO Vaga (titulo, descricao, cidade, remoto, empresa_id, ativa) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // CORREÇÃO: Usamos os getters do objeto 'vaga', não variáveis soltas
            ps.setString(1, vaga.getTitulo());
            ps.setString(2, vaga.getDescricao());
            ps.setString(3, vaga.getCidade());
            ps.setBoolean(4, vaga.isRemoto());
            ps.setInt(5, vaga.getEmpresaId());
            ps.setBoolean(6, true); // Ativa por padrão
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 5. LISTAR TODAS PARA ADMIN (Secretaria) - Vê ativas e inativas
    public List<Vaga> listarTodasParaAdmin() {
        List<Vaga> vagas = new ArrayList<>();
        String sql = "SELECT v.*, u.nome as nome_empresa " +
                     "FROM Vaga v " +
                     "JOIN usuarios u ON v.empresa_id = u.id " +
                     "ORDER BY v.id DESC";

        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vagas.add(montarVaga(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return vagas;
    }

    // 6. ALTERAR STATUS (Secretaria Aprovar/Rejeitar)
    public void alterarStatus(int id, boolean ativa) {
        String sql = "UPDATE Vaga SET ativa = ? WHERE id = ?";
        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, ativa);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 7. EXCLUIR (Secretaria)
    public void excluir(int id) {
        String sql = "DELETE FROM Vaga WHERE id = ?";
        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    // 8. LISTAR POR EMPRESA (Para painel da empresa, se usar no futuro)
    public List<Vaga> listarPorEmpresa(int empresaId) {
        List<Vaga> vagas = new ArrayList<>();
        String sql = "SELECT * FROM Vaga WHERE empresa_id = ? ORDER BY id DESC";
        try (Connection conn = DataSourceProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empresaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vagas.add(montarVaga(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return vagas;
    }
}