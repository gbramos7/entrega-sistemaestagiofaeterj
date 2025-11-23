/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.UsuarioDAO;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ConfiguracoesServlet")
public class ConfiguracoesServlet extends HttpServlet {

    // CARREGA A TELA
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        
        if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.getRequestDispatcher("/configuracoes.jsp").forward(request, response);
    }

    // SALVA AS ALTERAÇÕES
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        UsuarioDAO dao = new UsuarioDAO();

        if ("editar_perfil".equals(acao)) {
            // Edita o próprio perfil da secretaria
            HttpSession session = request.getSession();
            Usuario u = (Usuario) session.getAttribute("usuarioLogado");
            
            u.setNome(request.getParameter("nome"));
            u.setEmail(request.getParameter("email"));
            u.setSenha(request.getParameter("senha"));
            
            dao.atualizarDadosAcesso(u);
            session.setAttribute("usuarioLogado", u); // Atualiza sessão
            
            response.sendRedirect("ConfiguracoesServlet?msg=perfil_ok");

        } else if ("novo_admin".equals(acao)) {
            // Cria um novo usuário tipo 'secretaria'
            Usuario novo = new Usuario();
            novo.setNome(request.getParameter("novo_nome"));
            novo.setEmail(request.getParameter("novo_email"));
            novo.setSenha(request.getParameter("novo_senha"));
            novo.setTipo("secretaria"); // Força ser secretaria
            
            dao.inserir(novo);
            
            response.sendRedirect("ConfiguracoesServlet?msg=admin_criado");
        }
    }
}