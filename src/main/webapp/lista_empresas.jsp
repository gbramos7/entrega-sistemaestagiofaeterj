<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Empresas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 1100px; margin: 0 auto; }
        .header-card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }
        
        .table-card { background: white; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); overflow: hidden; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 15px 20px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #1a4384; color: white; font-size: 14px; text-transform: uppercase; }
        tr:hover { background-color: #f9f9f9; }
        
        .empty-state { padding: 50px; text-align: center; color: #777; }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
            <h2 style="margin:0; color: #1a4384;">Empresas Parceiras</h2>
            <a href="${pageContext.request.contextPath}/secretaria/dashboard" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <div class="table-card">
            <c:choose>
                <c:when test="${empty empresas}">
                    <div class="empty-state">Nenhuma empresa cadastrada.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Nome da Empresa</th>
                                <th>Email de Contato</th>
                                <th>ID no Sistema</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${empresas}" var="e">
                                <tr>
                                    <td><strong>${e.nome}</strong></td>
                                    <td>${e.email}</td>
                                    <td><span style="background:#eee; padding:2px 6px; border-radius:4px;">#${e.id}</span></td>
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