<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Candidaturas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 1100px; margin: 0 auto; }
        .header-card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        .card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center; }
        .info h3 { margin: 0 0 5px 0; color: #1a4384; font-size: 18px; }
        .info p { margin: 0; color: #666; font-size: 14px; }
        
        .btn-ok { background: #27ae60; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; font-weight: bold; }
        .btn-no { background: #c0392b; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; font-weight: bold; margin-left: 5px; }
        
        .tag-empresa { background: #eee; padding: 2px 6px; border-radius: 4px; font-weight: bold; font-size: 12px; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
    <h2 style="margin:0; color: #1a4384;">Candidaturas Pendentes</h2>
    <a href="${pageContext.request.contextPath}/secretaria/dashboard" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <c:if test="${empty candidaturas}">
            <div style="text-align: center; padding: 50px; background: white; border-radius: 10px;">
                <h3>Tudo em dia! ✅</h3>
                <p>Nenhuma candidatura aguardando aprovação.</p>
            </div>
        </c:if>

        <c:forEach items="${candidaturas}" var="c">
            <div class="card">
                <div class="info">
                    <h3>${c.nomeAluno}</h3>
                    <p>Aplicando para: <strong>${c.vagaTitulo}</strong></p>
                    <p><span class="tag-empresa">🏢 ${c.nomeEmpresa}</span></p>
                </div>
                
                <div>
                   <form action="${pageContext.request.contextPath}/GerenciarCandidaturasServlet" method="post">
                        <input type="hidden" name="id" value="${c.id}">
                        
                        <button type="submit" name="acao" value="encaminhar" class="btn-ok" title="Encaminhar para Empresa">
                            Encaminhar ➤
                        </button>
                        
                        <button type="submit" name="acao" value="rejeitar" class="btn-no" title="Rejeitar" onclick="return confirm('Rejeitar candidatura?')">
                            ✕
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

</body>
</html>