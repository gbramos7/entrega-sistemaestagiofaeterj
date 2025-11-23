<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>${titulo}</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

    <nav style="background:#222; padding:15px;">
        <span style="color:white; font-size:20px;">Sistema de Estágio</span>

        <span style="float:right;">
            <a href="../logout" style="color:white; margin-left:20px;">Sair</a>
        </span>
    </nav>

    <div style="padding:25px;">
        <jsp:include page="${conteudo}"/>
    </div>

</body>
</html>
