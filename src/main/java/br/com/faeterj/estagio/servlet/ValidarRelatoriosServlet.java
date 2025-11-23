/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.RelatorioDAO;
import br.com.faeterj.estagio.model.RelatorioEstagio;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ValidarRelatoriosServlet")
public class ValidarRelatoriosServlet extends HttpServlet {

    // Carrega a lista
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Segurança (Só secretaria entra)
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        
        if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
            response.sendRedirect("login.jsp");
            return;
        }

        RelatorioDAO dao = new RelatorioDAO();
        List<RelatorioEstagio> lista = dao.listarPendentesAdmin();
        
        request.setAttribute("relatorios", lista);
        request.getRequestDispatcher("/validar_relatorios.jsp").forward(request, response);
    }

    // Processa a ação (Aprovar/Rejeitar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String acao = request.getParameter("acao");
        
        RelatorioDAO dao = new RelatorioDAO();
        
        if ("aprovar".equals(acao)) {
            dao.atualizarStatus(id, "Aprovado");
        } else if ("rejeitar".equals(acao)) {
            dao.atualizarStatus(id, "Rejeitado");
        }
        
        response.sendRedirect("ValidarRelatoriosServlet?msg=ok");
    }
}