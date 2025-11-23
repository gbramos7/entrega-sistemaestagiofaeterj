<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Vagas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 1100px; margin: 0 auto; }
        
        .header-card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); overflow: hidden; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 15px 20px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #1a4384; color: white; text-transform: uppercase; font-size: 14px; }
        
        /* Badges de Status */
        .badge-ativa { background: #eafaf1; color: #27ae60; padding: 4px 8px; border-radius: 10px; font-weight: bold; font-size: 12px; }
        .badge-inativa { background: #fdedec; color: #c0392b; padding: 4px 8px; border-radius: 10px; font-weight: bold; font-size: 12px; }

        /* Botões */
        .btn-acao { border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer; font-weight: bold; margin-right: 5px; transition: 0.3s; }
        .btn-ativar { background: #27ae60; color: white; }
        .btn-desativar { background: #f39c12; color: white; }
        .btn-excluir { background: #c0392b; color: white; }
        
        .btn-ativar:hover { background: #219150; }
        .btn-desativar:hover { background: #d68910; }
        .btn-excluir:hover { background: #a93226; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
            <h2 style="margin:0; color: #1a4384;">Gerenciar Vagas</h2>
            <a href="dashboardsecretaria.jsp" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <div class="table-card">
            <c:choose>
                <c:when test="${empty vagas}">
                    <div style="text-align: center; padding: 50px; color: #777;">
                        <h3>Nenhuma vaga cadastrada.</h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Vaga</th>
                                <th>Empresa / Local</th>
                                <th>Status</th>
                                <th style="text-align: center;">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${vagas}" var="v">
                                <tr>
                                    <td>
                                        <strong>${v.titulo}</strong><br>
                                        <small style="color: #888;">ID: ${v.id}</small>
                                    </td>
                                    <td>
                                        ${v.nomeEmpresa}<br>
                                        <small>📍 ${v.cidade}</small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${v.ativa}">
                                                <span class="badge-ativa">ATIVA</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-inativa">INATIVA</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="text-align: center;">
                                        <form action="AprovarVagasServlet" method="post">
                                            <input type="hidden" name="id" value="${v.id}">
                                            
                                            <c:choose>
                                                <c:when test="${v.ativa}">
                                                    <button type="submit" name="acao" value="desativar" class="btn-acao btn-desativar" title="Ocultar Vaga">
                                                        ⏸
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" name="acao" value="ativar" class="btn-acao btn-ativar" title="Aprovar Vaga">
                                                        ▶
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>

                                            <button type="submit" name="acao" value="excluir" class="btn-acao btn-excluir" title="Excluir Definitivamente" onclick="return confirm('Tem certeza que deseja excluir esta vaga? Isso apagará as candidaturas dela.')">
                                                🗑
                                            </button>
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