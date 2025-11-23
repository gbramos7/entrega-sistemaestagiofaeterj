/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.CandidaturaDAO;
import br.com.faeterj.estagio.model.Candidatura;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// CORREÇÃO 1: O nome deve ser exatamente igual ao link do JSP
@WebServlet("/CandidaturaServlet")
public class CandidaturaServlet extends HttpServlet {

    // CORREÇÃO 2: Mudamos de doPost para doGet, pois o link <a href> é um GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verifica login
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Pega o ID da vaga
        String vagaIdStr = request.getParameter("vagaId");
        
        if (vagaIdStr != null && !vagaIdStr.isEmpty()) {
            try {
                int vagaId = Integer.parseInt(vagaIdStr);
                
                // 3. Monta e Salva a Candidatura
                Candidatura c = new Candidatura();
                c.setAlunoId(usuario.getId());
                c.setVagaId(vagaId);
                c.setStatus("Pendente"); 

               CandidaturaDAO dao = new CandidaturaDAO();
                dao.candidatar(c); 

                // MUDANÇA AQUI: Adiciona ?msg=sucesso na URL
                response.sendRedirect("AlunoVagasServlet?msg=sucesso");

            } catch (IOException | NumberFormatException e) {
                response.sendRedirect("AlunoVagasServlet");
            }
        } else {
            response.sendRedirect("AlunoVagasServlet");
        }
    }
}