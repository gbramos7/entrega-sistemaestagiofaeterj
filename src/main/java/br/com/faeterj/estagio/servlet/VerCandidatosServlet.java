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

@WebServlet("/VerCandidatosServlet")
public class VerCandidatosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Verifica login da empresa
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioLogado");
        if (u == null || !"empresa".equalsIgnoreCase(u.getTipo())) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            int vagaId = Integer.parseInt(req.getParameter("vagaId"));
            String vagaTitulo = req.getParameter("titulo"); // Pega o título da URL

            CandidaturaDAO dao = new CandidaturaDAO();
            List<Candidatura> lista = dao.listarCandidatosPorVaga(vagaId);

            req.setAttribute("candidatos", lista);
            req.setAttribute("vagaTitulo", vagaTitulo);
            
            req.getRequestDispatcher("/ver_candidatos.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            resp.sendRedirect("MinhasVagasEmpresaServlet");
        }
    }
}