package br.com.faeterj.estagio.servlet;

import br.com.faeterj.estagio.dao.VagaDAO;
import br.com.faeterj.estagio.model.Vaga;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;

@WebServlet("/AlunoVagasServlet")
public class AlunoVagasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        VagaDAO dao = new VagaDAO();
        List<Vaga> listaVagas = null;
        
        // 1. Pega os filtros da URL (se existirem)
        String cidade = request.getParameter("cidade");
        String titulo = request.getParameter("titulo"); // Vamos usar "titulo" para filtrar a "Área"
        String modelo = request.getParameter("modelo");
        
        try {
            // 2. Verifica se o usuário está tentando filtrar
            boolean temFiltro = (cidade != null && !cidade.isEmpty()) || 
                                (titulo != null && !titulo.isEmpty()) || 
                                (modelo != null && !modelo.isEmpty());

            if (temFiltro) {
                // Busca filtrada
                listaVagas = dao.filtrar(cidade, titulo, modelo);
            } else {
                // Busca tudo
                listaVagas = dao.listarAtivas();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("vagas", listaVagas);
        
        // Devolve os valores para os inputs não ficarem em branco depois de filtrar
        request.setAttribute("filtroCidade", cidade);
        request.setAttribute("filtroTitulo", titulo);
        request.setAttribute("filtroModelo", modelo);

        RequestDispatcher rd = request.getRequestDispatcher("/vagas.jsp");
        if (rd != null) {
            rd.forward(request, response);
        }
    }
}