/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.SolicitacaoConvenio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoConvenioDAO {

    public List<SolicitacaoConvenio> listarPendentes() {
        List<SolicitacaoConvenio> lista = new ArrayList<>();
        
        // SQL: Busca dados da solicitação + Nome do Aluno (JOIN)
        String sql = "SELECT s.*, u.nome as nome_aluno " +
                     "FROM solicitacao_convenio s " +
                     "JOIN usuarios u ON s.aluno_id = u.id " +
                     "WHERE s.status = 'Pendente' " +
                     "ORDER BY s.data_solicitacao DESC";

        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SolicitacaoConvenio s = new SolicitacaoConvenio();
                s.setId(rs.getInt("id"));
                s.setAlunoId(rs.getInt("aluno_id"));
                
                // Pega o nome do aluno do JOIN
                s.setNomeAluno(rs.getString("nome_aluno"));
                
                s.setNomeEmpresa(rs.getString("nome_empresa"));
                s.setEmailEmpresa(rs.getString("email_empresa"));
                s.setSiteEmpresa(rs.getString("site_empresa"));
                s.setStatus(rs.getString("status"));
                
                Timestamp ts = rs.getTimestamp("data_solicitacao");
                if (ts != null) s.setDataSolicitacao(ts.toLocalDateTime());

                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE solicitacao_convenio SET status = ? WHERE id = ?";
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, novoStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch (SQLException e) { e.printStackTrace(); }
    }
}