<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.com.faeterj.estagio.model.Usuario"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuarioLogado");
    if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Configurações - Secretaria</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { background-color: #f0f2f5; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        
        .header-card { 
            background: white; padding: 20px; border-radius: 8px; margin-bottom: 30px; 
            display: flex; justify-content: space-between; align-items: center; border-left: 5px solid #1a4384; 
        }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 8px 15px; border-radius: 5px; background: white; }

        /* Grid para dividir a tela */
        .config-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }
        @media (max-width: 768px) { .config-grid { grid-template-columns: 1fr; } }

        .card-config { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        
        h3 { color: #1a4384; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
        
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; margin-bottom: 15px; box-sizing: border-box; }
        
        .btn-salvar { background-color: #1a4384; color: white; border: none; padding: 12px; width: 100%; border-radius: 5px; font-weight: bold; cursor: pointer; transition: 0.3s; }
        .btn-salvar:hover { background-color: #0f2b57; }

        .btn-criar { background-color: #27ae60; color: white; border: none; padding: 12px; width: 100%; border-radius: 5px; font-weight: bold; cursor: pointer; transition: 0.3s; }
        .btn-criar:hover { background-color: #219150; }

        .alert { padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <a href="login.jsp" class="btn-logout">Sair</a>
    </div>

    <div class="container">
        <div class="header-card">
            <h2 style="margin:0; color: #1a4384;">Configurações do Sistema</h2>
            <a href="dashboardsecretaria.jsp" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <c:if test="${param.msg == 'perfil_ok'}">
            <div class="alert alert-success">✅ Seus dados foram atualizados com sucesso!</div>
        </c:if>
        <c:if test="${param.msg == 'admin_criado'}">
            <div class="alert alert-success">✅ Novo administrador cadastrado!</div>
        </c:if>

        <div class="config-grid">
            
            <div class="card-config">
                <h3>👤 Meus Dados de Acesso</h3>
                <form action="ConfiguracoesServlet" method="post">
                    <input type="hidden" name="acao" value="editar_perfil">
                    
                    <label>Nome de Exibição:</label>
                    <input type="text" name="nome" value="<%= u.getNome() %>" required>

                    <label>E-mail de Login:</label>
                    <input type="email" name="email" value="<%= u.getEmail() %>" required>

                    <label>Nova Senha:</label>
                    <input type="text" name="senha" value="<%= u.getSenha() %>" required style="background: #fffbf0; border-color: #e6dbb9;">

                    <button type="submit" class="btn-salvar">Salvar Alterações</button>
                </form>
            </div>

            <div class="card-config">
                <h3>🛡️ Cadastrar Nova Secretaria</h3>
                <p style="color: #666; font-size: 14px; margin-bottom: 20px;">
                    Adicione um novo membro à equipe administrativa. Ele terá acesso total a este painel.
                </p>
                
                <form action="ConfiguracoesServlet" method="post">
                    <input type="hidden" name="acao" value="novo_admin">
                    
                    <label>Nome:</label>
                    <input type="text" name="novo_nome" placeholder="Ex: Coordenação Adjunta" required>

                    <label>E-mail:</label>
                    <input type="email" name="novo_email" placeholder="Ex: adjunto@faeterj.br" required>

                    <label>Senha Inicial:</label>
                    <input type="password" name="novo_senha" required>

                    <button type="submit" class="btn-criar">Cadastrar Administrador</button>
                </form>
            </div>

        </div>
    </div>

</body>
</html>