/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.Aluno;
import java.sql.*;

public class AlunoDAO {
    public Aluno findByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT id,usuario_id,curso,periodo,turno,telefone,DATE_FORMAT(data_nascimento, '%Y-%m-%d') as data_nascimento FROM alunos WHERE usuario_id = ?";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setId(rs.getInt("id"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    a.setCurso(rs.getString("curso"));
                    a.setPeriodo(rs.getString("periodo"));
                    a.setTurno(rs.getString("turno"));
                    a.setTelefone(rs.getString("telefone"));
                    a.setDataNascimento(rs.getString("data_nascimento"));
                    return a;
                }
            }
        }
        return null;
    }

    public void createOrUpdate(Aluno a) throws SQLException {
        if (a.getId() > 0) {
            String sql = "UPDATE alunos SET curso=?,periodo=?,turno=?,telefone=?,data_nascimento=? WHERE id=?";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, a.getCurso());
                ps.setString(2, a.getPeriodo());
                ps.setString(3, a.getTurno());
                ps.setString(4, a.getTelefone());
                ps.setString(5, a.getDataNascimento());
                ps.setInt(6, a.getId());
                ps.executeUpdate();
            }
        } else {
            String sql = "INSERT INTO alunos(usuario_id,curso,periodo,turno,telefone,data_nascimento) VALUES(?,?,?,?,?,?)";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, a.getUsuarioId());
                ps.setString(2, a.getCurso());
                ps.setString(3, a.getPeriodo());
                ps.setString(4, a.getTurno());
                ps.setString(5, a.getTelefone());
                ps.setString(6, a.getDataNascimento());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) a.setId(keys.getInt(1)); }
            }
        }
    }
}
