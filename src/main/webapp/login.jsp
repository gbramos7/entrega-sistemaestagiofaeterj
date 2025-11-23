<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema de Estágio</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body class="login-body">

    <div class="login-card">
        <img src="assets/img/logo_faeterj.png" alt="FAETERJ" class="login-logo" onerror="this.src='https://via.placeholder.com/150x50?text=FAETERJ'">
        
        <h3 style="color: #555; margin-bottom: 20px;">SISTEMA DE ESTÁGIO FAETERJ</h3>

        <c:if test="${not empty erro}">
            <div class="login-error">
                ${erro}
            </div>
        </c:if>

        <form action="LoginServlet" method="post">
            <input type="email" name="email" class="login-input" placeholder="E-mail..." required>
            <input type="password" name="senha" class="login-input" placeholder="Senha..." required>
            
            <button type="submit" class="btn-entrar">Entrar</button>
        </form>

        <br>
        <a href="cadastro.jsp" style="color: #0056b3; text-decoration: none;">Criar conta</a>
    </div>

</body>
</html>