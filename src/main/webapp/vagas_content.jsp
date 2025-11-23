<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.faeterj.estagio.model.Vaga"%>
<%@page import="br.com.faeterj.estagio.dao.VagaDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Lógica Java para buscar os dados
    VagaDAO dao = new VagaDAO();
    List<Vaga> listaVagas = new ArrayList<>();
    
    try {
        listaVagas = dao.listarTodas();
    } catch (Exception e) {
        out.println("");
    }
    
    request.setAttribute("vagas", listaVagas);
%>

<div class="main-content" style="padding: 20px;">

    <h2 style="color: #1a4384; margin-bottom: 20px;">Vagas em destaque</h2>

    <div class="filtros-container">
        <input type="text" placeholder="Cidade" class="filtro-input">
        <input type="text" placeholder="Área" class="filtro-input">
        <select class="filtro-input">
            <option value="">Modelo</option>
            <option value="remoto">Remoto</option>
            <option value="presencial">Presencial</option>
        </select>
        <button class="btn-filtrar">Filtrar</button>
    </div>

    <div class="vagas-grid">
        <c:choose>
            <c:when test="${not empty vagas}">
                <c:forEach items="${vagas}" var="vaga">
                    
                    <div class="vaga-card">
                        <div class="vaga-header">
                            <div class="empresa-logo-container" style="width: 50px; height: 50px; background: #eee; border-radius: 50%; margin-bottom: 10px; display: flex; align-items: center; justify-content: center;">
                                <span style="font-size: 20px;">🏢</span>
                            </div>
                            
                            <div class="vaga-info">
                                <h3 class="vaga-titulo">${vaga.titulo}</h3>
                                
                                <span class="badge-modelo" style="color: ${vaga.remoto ? '#8cc63f' : '#666'}; font-weight: bold; text-transform: uppercase; font-size: 12px;">
                                    ${vaga.remoto ? 'Remoto' : 'Presencial'}
                                </span>
                                
                                <p class="empresa-nome">
                                    Paracambiense • ${not empty vaga.cidade ? vaga.cidade : 'Paracambi - RJ'}
                                </p>
                                <p class="tempo-anuncio">Há 2 dias</p>
                            </div>
                        </div>

                        <div class="vaga-actions">
                            <a href="CandidaturaServlet?vagaId=${vaga.id}" class="btn-candidatar">Candidatar-se</a>
                            <button class="btn-indicar" onclick="alert('Indicação enviada!')">Indicar</button>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            
            <c:otherwise>
                <div class="no-vagas" style="grid-column: 1/-1; text-align: center; padding: 40px; background: white; border-radius: 10px;">
                    <h3>Nenhuma vaga encontrada :(</h3>
                    <p>Tente ajustar os filtros ou volte mais tarde.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>