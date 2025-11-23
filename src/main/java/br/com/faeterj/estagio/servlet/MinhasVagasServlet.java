/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.VagaDAO;
import br.com.faeterj.estagio.model.Vaga;
import br.com.faeterj.estagio.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/empresa/minhas-vagas")
public class MinhasVagasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario empresa = (Usuario) req.getSession().getAttribute("usuario");

        VagaDAO dao = new VagaDAO();
        List<Vaga> vagas = dao.listarAtivas(); // pode filtrar por empresa

        req.setAttribute("vagas", vagas);
        req.getRequestDispatcher("/empresa/minhas-vagas.jsp").forward(req, resp);
    }
}
