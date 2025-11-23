<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Solicitar Convênio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        
        .card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        h2 { color: #1a4384; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 15px; }
        
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; margin-bottom: 15px; box-sizing: border-box; }
        
        .btn-enviar { background-color: #1a4384; color: white; border: none; padding: 12px; width: 100%; border-radius: 5px; font-weight: bold; cursor: pointer; font-size: 16px; }
        .btn-enviar:hover { background-color: #0f2b57; }
        
        .btn-voltar { display: block; text-align: center; margin-top: 15px; color: #777; text-decoration: none; }
        
        .alert { padding: 12px; background: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 20px; text-align: center; }
    </style>
</head>
<body>

    <div class="container">
        <div class="card">
            <h2>🏢 Solicitar Novo Convênio</h2>
            
            <c:if test="${param.msg == 'sucesso'}">
                <div class="alert">✅ Solicitação enviada! A secretaria analisará o pedido.</div>
            </c:if>

            <p style="color: #666; font-size: 14px; margin-bottom: 20px;">
                Preencha os dados da empresa que você deseja que a FAETERJ faça parceria.
            </p>

            <form action="SolicitarConvenioServlet" method="post">
                <label>Nome da Empresa:</label>
                <input type="text" name="nome_empresa" required placeholder="Ex: Tech Solutions Ltda">

                <label>E-mail de Contato (RH ou Responsável):</label>
                <input type="email" name="email_empresa" required placeholder="Ex: rh@empresa.com">

                <label>Site ou LinkedIn da Empresa (Opcional):</label>
                <input type="text" name="site_empresa" placeholder="Ex: www.empresa.com.br">

                <button type="submit" class="btn-enviar">Enviar Solicitação</button>
                <a href="dashboard.jsp" class="btn-voltar">Voltar ao Painel</a>
            </form>
        </div>
    </div>

</body>
</html>