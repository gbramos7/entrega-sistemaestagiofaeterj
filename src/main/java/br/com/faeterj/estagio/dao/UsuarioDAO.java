package br.com.faeterj.estagio.dao;

import br.com.faeterj.estagio.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    // LOGIN: Busca todos os dados, incluindo o perfil completo
    public Usuario verificarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        
        try (Connection c = DataSourceProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, senha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipo(rs.getString("tipo"));
                    
                    // Novos campos
                    u.setMatricula(rs.getString("matricula"));
                    u.setCurso(rs.getString("curso"));
                    u.setPeriodo(rs.getString("periodo"));
                    u.setTurno(rs.getString("turno"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setDataNascimento(rs.getString("data_nascimento"));
                    
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ATUALIZAR: Salva as edições do perfil
    public void atualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nome=?, senha=?, matricula=?, curso=?, periodo=?, turno=?, telefone=?, data_nascimento=? WHERE id=?";
        
        try (Connection c = DataSourceProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, u.getNome());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getMatricula());
            ps.setString(4, u.getCurso());
            ps.setString(5, u.getPeriodo());
            ps.setString(6, u.getTurno());
            ps.setString(7, u.getTelefone());
            ps.setString(8, u.getDataNascimento());
            ps.setInt(9, u.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar perfil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // INSERIR (Cadastro novo - mantém básico)
    public boolean inserir(Usuario u) {
        String sql = "INSERT INTO usuarios(nome, email, senha, tipo) VALUES(?, ?, ?, ?)";
        try (Connection c = DataSourceProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());
            ps.setString(4, (u.getTipo() == null) ? "aluno" : u.getTipo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // ... (mantenha os métodos verificarLogin, atualizar, inserir) ...

    // NOVO MÉTODO: Listar usuários por tipo (aluno, empresa, secretaria)
    public java.util.List<Usuario> listarPorTipo(String tipo) {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo = ? ORDER BY nome ASC";

        try (Connection c = DataSourceProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTipo(rs.getString("tipo"));
                
                // Dados específicos de aluno (se tiver)
                u.setMatricula(rs.getString("matricula"));
                u.setCurso(rs.getString("curso"));
                u.setTelefone(rs.getString("telefone"));
                
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // ATUALIZAR DADOS BÁSICOS (Para Secretaria e Empresa)
    public void atualizarDadosAcesso(Usuario u) {
        String sql = "UPDATE usuarios SET nome=?, email=?, senha=? WHERE id=?";
        
        try (Connection c = DataSourceProvider.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());
            ps.setInt(4, u.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
