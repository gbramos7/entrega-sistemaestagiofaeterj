package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.UsuarioDAO;
import br.com.faeterj.estagio.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String tipo = req.getParameter("tipo"); // aluno, empresa, secretaria

        Usuario usuario = new Usuario(0, nome, email, senha, tipo);

        UsuarioDAO dao = new UsuarioDAO();
        boolean ok = dao.inserir(usuario);

        if (ok) {
            resp.sendRedirect("login.jsp?msg=ok");
        } else {
            resp.sendRedirect("cadastro.jsp?erro=1");
        }
    }
}

