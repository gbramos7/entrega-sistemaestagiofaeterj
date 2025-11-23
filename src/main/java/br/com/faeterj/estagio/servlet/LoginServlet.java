package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.UsuarioDAO;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Tenta buscar no banco
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.verificarLogin(email, senha);

        // --- MODO DE EMERGÊNCIA (APENAS ALUNO E SECRETARIA) ---
        if (usuario == null) {
            System.out.println("Login pelo banco falhou. Tentando modo de emergência...");
            
            if ("aluno@faeterj.br".equals(email) && "123".equals(senha)) {
                usuario = new Usuario();
                usuario.setId(999);
                usuario.setNome("Aluno (Modo Emergência)");
                usuario.setTipo("aluno");
                usuario.setEmail("aluno@faeterj.br");
            } 
            else if ("admin@faeterj.br".equals(email) && "123".equals(senha)) {
                usuario = new Usuario();
                usuario.setNome("Secretaria (Modo Emergência)");
                usuario.setTipo("secretaria");
            }
            // O acesso de empresa foi removido daqui
        }
        // ---------------------------------------------------------------

        if (usuario != null) {
            // Login Sucesso
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("usuario", usuario);

            String base = request.getContextPath();
            String tipo = usuario.getTipo();

            // Redirecionamento apenas para os tipos permitidos
            if ("aluno".equalsIgnoreCase(tipo)) {
                response.sendRedirect(base + "dashboard.jsp");
                
            } else if ("secretaria".equalsIgnoreCase(tipo)) {
                response.sendRedirect(base + "dashboardsecretaria.jsp");
                
            } else {
                // Se for empresa ou outro tipo, volta para o login (Acesso Negado)
                request.setAttribute("erro", "Acesso permitido apenas para Alunos e Secretaria.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } else {
            // Falha Total
            System.out.println("Login falhou totalmente.");
            request.setAttribute("erro", "E-mail ou senha incorretos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}