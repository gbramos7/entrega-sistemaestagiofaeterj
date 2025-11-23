<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Candidatos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f4f7f6; font-family: 'Segoe UI', sans-serif; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; }
        
        .header-box { background: white; padding: 20px; border-radius: 8px; margin-bottom: 30px; display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        .cand-card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center; }
        .cand-info h3 { margin: 0 0 5px 0; color: #333; }
        .cand-info p { margin: 0; color: #666; font-size: 14px; }
        .cand-meta { font-size: 13px; color: #888; margin-top: 5px; }
        
        .btn-email { background: #1a4384; color: white; text-decoration: none; padding: 8px 15px; border-radius: 5px; font-weight: bold; font-size: 14px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-box">
            <div>
                <h2 style="margin:0; color:#1a4384;">Candidatos</h2>
                <p style="margin:5px 0 0 0; color:#666;">Vaga: <strong>${vagaTitulo}</strong></p>
            </div>
            <a href="MinhasVagasEmpresaServlet" class="btn-voltar">Voltar</a>
        </div>

        <c:if test="${empty candidatos}">
            <div style="text-align: center; padding: 50px; color: #777; background: white; border-radius: 8px;">
                <h3>Nenhum candidato ainda.</h3>
                <p>Assim que um aluno se inscrever, ele aparecerá aqui.</p>
            </div>
        </c:if>

        <c:forEach items="${candidatos}" var="c">
            <div class="cand-card">
                <div class="cand-info">
                    <h3>${c.nomeAluno}</h3>
                    <p>🎓 ${not empty c.cursoAluno ? c.cursoAluno : 'Curso não informado'}</p>
                    <div class="cand-meta">
                        📧 ${c.emailAluno} | 📞 ${not empty c.telefoneAluno ? c.telefoneAluno : '--'}
                    </div>
                </div>
                <div>
                    <a href="mailto:${c.emailAluno}?subject=Entrevista - ${vagaTitulo}" class="btn-email">Contatar</a>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>