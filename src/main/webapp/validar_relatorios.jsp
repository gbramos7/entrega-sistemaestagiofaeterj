<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Validar Relatórios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .header { border-bottom: 4px solid #1a4384; }
        .container { max-width: 1000px; margin: 40px auto; }
        
        .header-card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        .card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center; }
        
        .info h3 { margin: 0 0 5px 0; color: #1a4384; font-size: 18px; }
        .info p { margin: 0; color: #666; font-size: 14px; }
        .badge-file { background: #eee; padding: 4px 8px; border-radius: 4px; font-size: 12px; color: #333; display: inline-block; margin-top: 5px; }
        
        .btn-ok { background: #27ae60; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; font-weight: bold; }
        .btn-no { background: #c0392b; color: white; border: none; padding: 8px 15px; border-radius: 5px; cursor: pointer; font-weight: bold; margin-left: 5px; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
            <h2 style="margin:0; color: #1a4384;">Relatórios Pendentes</h2>
            <a href="dashboardsecretaria.jsp" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <c:if test="${empty relatorios}">
            <div style="text-align: center; padding: 50px; background: white; border-radius: 10px;">
                <h3>Tudo em dia! ✅</h3>
                <p>Nenhum relatório aguardando validação.</p>
            </div>
        </c:if>

        <c:forEach items="${relatorios}" var="r">
            <div class="card">
                <div class="info">
                    <h3>${r.nomeAluno}</h3>
                    <p><strong>Documento:</strong> ${r.tipo}</p>
                    <span class="badge-file">📄 ${r.caminhoArquivo}</span>
                </div>
                
                <div>
                    <form action="ValidarRelatoriosServlet" method="post">
                        <input type="hidden" name="id" value="${r.id}">
                        
                        <button type="button" onclick="alert('Baixando arquivo...')" style="background:#1a4384; color:white; border:none; padding:8px; border-radius:5px; margin-right:10px; cursor:pointer;">📥 Baixar</button>
                        
                        <button type="submit" name="acao" value="aprovar" class="btn-ok" title="Validar">✓ Validar</button>
                        <button type="submit" name="acao" value="rejeitar" class="btn-no" title="Rejeitar" onclick="return confirm('Rejeitar este relatório?')">✕</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

</body>
</html>