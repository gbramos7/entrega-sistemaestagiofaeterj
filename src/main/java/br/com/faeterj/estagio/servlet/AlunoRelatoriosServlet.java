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

@WebServlet("/AlunoRelatoriosServlet")
public class AlunoRelatoriosServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioLogado");
        if (u == null) { resp.sendRedirect("login.jsp"); return; }

        RelatorioDAO dao = new RelatorioDAO();
        List<RelatorioEstagio> lista = dao.listarPorAluno(u.getId());
        req.setAttribute("relatorios", lista);
        req.getRequestDispatcher("/relatorios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Simulação de envio (sem upload de arquivo real para simplificar agora)
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioLogado");
        String tipo = req.getParameter("tipo"); // Parcial ou Final
        
        RelatorioEstagio r = new RelatorioEstagio();
        r.setAlunoId(u.getId());
        r.setTipo(tipo);
        r.setCaminhoArquivo("arquivo_simulado.pdf"); // Placeholder

        RelatorioDAO dao = new RelatorioDAO();
        dao.salvar(r);
        
        resp.sendRedirect("AlunoRelatoriosServlet");
    }
}