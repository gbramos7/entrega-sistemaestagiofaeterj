package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.DataSourceProvider;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SolicitarConvenioServlet")
public class SolicitarConvenioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // 1. Pega o usuário logado
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        
        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Pega dados do formulário
        String nomeEmpresa = request.getParameter("nome_empresa");
        String emailEmpresa = request.getParameter("email_empresa");
        String siteEmpresa = request.getParameter("site_empresa");

        // 3. Salva no Banco
        String sql = "INSERT INTO solicitacao_convenio (aluno_id, nome_empresa, email_empresa, site_empresa) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DataSourceProvider.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, u.getId());
            ps.setString(2, nomeEmpresa);
            ps.setString(3, emailEmpresa);
            ps.setString(4, siteEmpresa);
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Redireciona com mensagem de sucesso
        response.sendRedirect("solicitar_convenio.jsp?msg=sucesso");
    }
}