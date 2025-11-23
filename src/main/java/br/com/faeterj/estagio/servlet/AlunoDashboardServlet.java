package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AlunoDashboardServlet")
public class AlunoDashboardServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verifica se está logado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Pega dados do usuário
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        request.setAttribute("usuario", usuario);

        // 3. CORREÇÃO AQUI: Caminho direto na raiz (sem a pasta /aluno/)
        RequestDispatcher rd = request.getRequestDispatcher("/dashboardsecretaria.jsp");
        
        if (rd != null) {
            rd.forward(request, response);
        } else {
            // Se ainda der erro, vai aparecer essa mensagem na tela
            response.getWriter().println("ERRO: O arquivo 'dashboard.jsp' não foi encontrado na raiz de Web Pages.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ADICIONE ISSO NA PRIMEIRA LINHA DO MÉTODO:
        request.setCharacterEncoding("UTF-8");
}
}