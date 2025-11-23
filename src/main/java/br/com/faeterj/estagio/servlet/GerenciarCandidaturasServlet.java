/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.CandidaturaDAO;
import br.com.faeterj.estagio.model.Candidatura;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// O NOME AQUI DEVE SER IGUAL AO LINK DO DASHBOARD
@WebServlet("/GerenciarCandidaturasServlet")
public class GerenciarCandidaturasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Segurança (Apenas Secretaria)
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        
        if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Busca dados no Banco
        CandidaturaDAO dao = new CandidaturaDAO();
        List<Candidatura> lista = null;
        
        try {
            // Chama o método que lista tudo que é 'Pendente' com nomes
            lista = dao.listarPendentesSecretaria();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 3. Manda para a tela visual
        request.setAttribute("candidaturas", lista);
        request.getRequestDispatcher("/gerenciar_candidaturas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 4. Processa a ação dos botões (Encaminhar / Rejeitar)
        int id = Integer.parseInt(request.getParameter("id"));
        String acao = request.getParameter("acao");
        
        CandidaturaDAO dao = new CandidaturaDAO();
        
        if ("encaminhar".equals(acao)) {
            dao.atualizarStatus(id, "Encaminhado");
        } else if ("rejeitar".equals(acao)) {
            dao.atualizarStatus(id, "Rejeitado");
        }
        
        // Recarrega a página
        response.sendRedirect(request.getContextPath() + "/GerenciarCandidaturasServlet?msg=ok");
    }
}