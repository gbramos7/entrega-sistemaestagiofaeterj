package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.UsuarioDAO;
import br.com.faeterj.estagio.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AlunoPerfilServlet")
public class AlunoPerfilServlet extends HttpServlet {

    // Carrega a página
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;

        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        request.setAttribute("usuario", u);
        request.getRequestDispatcher("/perfil_aluno.jsp").forward(request, response);
    }

    // Salva as alterações
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");

        if (u != null) {
            // Atualiza objeto com dados do formulário
            u.setNome(request.getParameter("nome"));
            u.setSenha(request.getParameter("senha"));
            u.setMatricula(request.getParameter("matricula"));
            u.setCurso(request.getParameter("curso"));
            u.setPeriodo(request.getParameter("periodo"));
            u.setTurno(request.getParameter("turno"));
            u.setTelefone(request.getParameter("telefone"));
            u.setDataNascimento(request.getParameter("data_nascimento"));

            // Grava no banco
            UsuarioDAO dao = new UsuarioDAO();
            dao.atualizar(u);

            // Atualiza sessão
            session.setAttribute("usuarioLogado", u);
            
            request.setAttribute("msg", "Dados atualizados com sucesso!");
            request.setAttribute("usuario", u);
            request.getRequestDispatcher("/perfil_aluno.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}