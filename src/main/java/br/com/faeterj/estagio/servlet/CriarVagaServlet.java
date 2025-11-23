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

@WebServlet("/CriarVagaServlet")
public class CriarVagaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 1. Pega o usuário da sessão (padronizado para usuarioLogado)
        Usuario empresa = (Usuario) req.getSession().getAttribute("usuarioLogado");
        
        if (empresa == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 2. Pega dados do formulário
        String titulo = req.getParameter("titulo");
        String desc = req.getParameter("descricao");
        String cidade = req.getParameter("cidade"); 
        boolean remoto = "true".equals(req.getParameter("remoto"));

        // 3. CORREÇÃO: Cria o objeto usando o construtor vazio e Setters
        // Isso evita o erro de contagem de argumentos
        Vaga v = new Vaga();
        v.setEmpresaId(empresa.getId());
        v.setTitulo(titulo);
        v.setDescricao(desc);
        v.setCidade(cidade != null ? cidade : "Local a definir"); // Garante que não vá nulo
        v.setRemoto(remoto);
        v.setAtiva(true); // Vaga nasce ativa

        // 4. Salva no banco
        VagaDAO dao = new VagaDAO();
        dao.criar(v);

        // 5. Redireciona (ajuste o destino conforme seu fluxo)
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}