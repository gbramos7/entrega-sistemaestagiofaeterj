<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Meus Relatórios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        /* CSS Específico para esta página */
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .page-container { max-width: 800px; margin: 0 auto; }
        
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .logo { color: #1a4384; font-weight: bold; font-size: 24px; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 5px 15px; border-radius: 5px; background: white; }

        /* Card de Upload */
        .card-upload { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); margin-bottom: 40px; }
        .card-title { color: #1a4384; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        select, input[type="file"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        
        .btn-enviar { background-color: #27ae60; color: white; border: none; padding: 12px; width: 100%; border-radius: 5px; font-weight: bold; cursor: pointer; font-size: 16px; transition: 0.3s; }
        .btn-enviar:hover { background-color: #219150; }

        /* Lista de Histórico */
        .historico-list { list-style: none; padding: 0; }
        .historico-item { 
            background: white; padding: 20px; margin-bottom: 15px; border-radius: 8px; 
            box-shadow: 0 2px 5px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center;
            border-left: 5px solid #1a4384;
        }
        
        .info h4 { margin: 0 0 5px 0; color: #333; }
        .info small { color: #888; }
        
        .status-badge { padding: 5px 12px; border-radius: 20px; font-size: 12px; font-weight: bold; }
        .status-analise { background: #f39c12; color: white; } /* Laranja */
        .status-aprovado { background: #27ae60; color: white; } /* Verde */
        .status-rejeitado { background: #c0392b; color: white; } /* Vermelho */
    </style>
</head>
<body>

    <div class="page-container">
        <div class="header">
            <div class="logo">FAETERJ <span style="color: #8cc63f;">ESTÁGIO</span></div>
            <a href="dashboard.jsp" class="btn-voltar">Voltar</a>
        </div>

        <div class="card-upload">
            <h3 class="card-title">📤 Novo Envio</h3>
            <form action="AlunoRelatoriosServlet" method="post">
                <div class="form-group">
                    <label>Tipo de Relatório:</label>
                    <select name="tipo">
                        <option value="Relatório Parcial (2 meses)">Relatório Parcial (2 meses)</option>
                        <option value="Relatório Final">Relatório Final</option>
                        <option value="Declaração de Atividades">Declaração de Atividades</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Arquivo (PDF):</label>
                    <input type="file" disabled style="background: #eee; cursor: not-allowed;">
                    <p style="font-size: 12px; color: red; margin-top: 5px;">* Upload de arquivo desativado na versão de demonstração.</p>
                </div>

                <button type="submit" class="btn-enviar">Enviar Relatório</button>
            </form>
        </div>

        <h3 style="color: #555;">📜 Histórico de Envios</h3>
        
        <ul class="historico-list">
            <c:if test="${empty relatorios}">
                <li style="text-align: center; color: #999; padding: 20px;">Nenhum relatório enviado ainda.</li>
            </c:if>

            <c:forEach items="${relatorios}" var="r">
                <li class="historico-item">
                    <div class="info">
                        <h4>${r.tipo}</h4>
                        <small>Enviado em: ${not empty r.enviadoEm ? r.enviadoEm : 'Recentemente'}</small>
                    </div>
                    
                    <c:choose>
                        <c:when test="${r.status == 'Aprovado'}">
                            <span class="status-badge status-aprovado">Aprovado</span>
                        </c:when>
                        <c:when test="${r.status == 'Rejeitado'}">
                            <span class="status-badge status-rejeitado">Rejeitado</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-badge status-analise">Em Análise</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </div>

</body>
</html>