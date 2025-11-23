/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.UsuarioDAO;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ListarAlunosServlet")
public class ListarAlunosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Segurança
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Busca apenas ALUNOS
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.listarPorTipo("aluno");
        
        request.setAttribute("alunos", lista);
        request.getRequestDispatcher("/lista_alunos.jsp").forward(request, response);
    }
}