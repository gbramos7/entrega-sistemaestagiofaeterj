/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// IMPORTANTE: Este endereço deve bater com o redirecionamento do LoginServlet
@WebServlet("/secretaria/dashboard")
public class SecretariaDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verifica se existe sessão
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        // 2. Verifica se o usuário é do tipo 'secretaria'
        if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
            // Se não for secretaria ou não estiver logado, manda pro login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 3. Encaminha para a tela visual (JSP)
        // Aponta para a raiz, onde definimos que o arquivo ficaria
        request.getRequestDispatcher("/dashboardsecretaria.jsp").forward(request, response);
    }
}