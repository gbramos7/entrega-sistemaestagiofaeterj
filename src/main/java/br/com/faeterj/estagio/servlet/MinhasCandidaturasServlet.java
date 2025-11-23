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

@WebServlet("/MinhasCandidaturasServlet")
public class MinhasCandidaturasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioLogado");
        if (u == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        CandidaturaDAO dao = new CandidaturaDAO();
        List<Candidatura> lista = dao.listarPorAluno(u.getId());

        req.setAttribute("candidaturas", lista);
        req.getRequestDispatcher("/minhas_candidaturas.jsp").forward(req, resp);
    }
}