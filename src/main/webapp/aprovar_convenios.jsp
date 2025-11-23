<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Validar Convênios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        
        .header-card { 
            background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; 
            display: flex; justify-content: space-between; align-items: center; 
            border-left: 5px solid #1a4384; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); overflow: hidden; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 15px 20px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #1a4384; color: white; text-transform: uppercase; font-size: 14px; }
        
        .btn-aprovar { background: #27ae60; color: white; border: none; padding: 8px 12px; border-radius: 5px; cursor: pointer; font-weight: bold; transition: 0.3s; }
        .btn-rejeitar { background: #c0392b; color: white; border: none; padding: 8px 12px; border-radius: 5px; cursor: pointer; font-weight: bold; margin-left: 5px; transition: 0.3s; }
        
        .empty-state { text-align: center; padding: 50px; color: #777; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
            <h2 style="margin:0; color: #1a4384;">Solicitações Pendentes</h2>
            <a href="secretaria/dashboard" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <div class="table-card">
            <c:choose>
                <c:when test="${empty solicitacoes}">
                    <div class="empty-state">
                        <h3>Tudo limpo! ✅</h3>
                        <p>Nenhuma solicitação pendente no momento.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Empresa / Data</th>
                                <th>Indicado Por</th>
                                <th>Contato / Site</th>
                                <th style="text-align: center;">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${solicitacoes}" var="s">
                                <tr>
                                    <td>
                                        <strong>${s.nomeEmpresa}</strong><br>
                                        <small style="color: #888;">${s.dataSolicitacao}</small>
                                    </td>
                                    <td>
                                        ${not empty s.nomeAluno ? s.nomeAluno : 'Aluno Desconhecido'}
                                        <br><small>ID: ${s.alunoId}</small>
                                    </td>
                                    <td style="font-size: 14px;">
                                        📧 ${s.emailEmpresa}<br>
                                        🌐 ${s.siteEmpresa}
                                    </td>
                                    <td style="text-align: center;">
                                        <form action="AprovarConveniosServlet" method="post">
                                            <input type="hidden" name="id" value="${s.id}">
                                            <button type="submit" name="acao" value="aprovar" class="btn-aprovar" title="Aprovar">✓</button>
                                            <button type="submit" name="acao" value="rejeitar" class="btn-rejeitar" title="Rejeitar" onclick="return confirm('Rejeitar esta empresa?')">✕</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>