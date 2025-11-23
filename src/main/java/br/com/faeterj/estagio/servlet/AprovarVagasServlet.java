/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.VagaDAO;
import br.com.faeterj.estagio.model.Usuario;
import br.com.faeterj.estagio.model.Vaga;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AprovarVagasServlet")
public class AprovarVagasServlet extends HttpServlet {

    // CARREGA A LISTA
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

        VagaDAO dao = new VagaDAO();
        List<Vaga> lista = dao.listarTodasParaAdmin();
        
        request.setAttribute("vagas", lista);
        request.getRequestDispatcher("/aprovar_vagas.jsp").forward(request, response);
    }

    // PROCESSA AÇÕES
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String acao = request.getParameter("acao"); // ativar, desativar, excluir
        
        VagaDAO dao = new VagaDAO();
        
        if ("ativar".equals(acao)) {
            dao.alterarStatus(id, true);
        } else if ("desativar".equals(acao)) {
            dao.alterarStatus(id, false);
        } else if ("excluir".equals(acao)) {
            dao.excluir(id);
        }
        
        response.sendRedirect("AprovarVagasServlet?msg=ok");
    }
}