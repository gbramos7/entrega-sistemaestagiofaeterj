<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.faeterj.estagio.model.Usuario"%>
<%
    // Verifica se o usuário está logado e se é secretaria
    Usuario u = (Usuario) session.getAttribute("usuarioLogado");
    if (u == null || !"secretaria".equalsIgnoreCase(u.getTipo())) {
        // Redireciona para o login usando caminho absoluto
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Secretaria - FAETERJ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    
    <style>
        /* CSS LOCAL (Ajustes Específicos para a Secretaria) */
        body { font-family: 'Segoe UI', sans-serif; background-color: #f0f2f5; margin: 0; padding: 0; }
        
        /* Cabeçalho com borda azul */
        .header { 
            background: white; padding: 15px 30px; display: flex; 
            justify-content: space-between; align-items: center; 
            box-shadow: 0 2px 5px rgba(0,0,0,0.05); border-bottom: 4px solid #1a4384; 
        }
        
        .logo { color: #1a4384; font-weight: 800; font-size: 24px; }
        
        .user-info { display: flex; align-items: center; gap: 15px; color: #555; }
        
        .btn-logout { 
            color: #e74c3c; text-decoration: none; font-weight: bold; 
            border: 1px solid #e74c3c; padding: 6px 15px; border-radius: 5px; transition: 0.3s; 
        }
        .btn-logout:hover { background: #e74c3c; color: white; }

        /* Container Centralizado */
        .dashboard-container { 
            max-width: 1100px; margin: 40px auto; padding: 0 20px; text-align: center; 
        }
        
        .welcome-msg { font-size: 28px; color: #333; margin-bottom: 5px; }
        .subtitle { color: #777; margin-bottom: 40px; }

        /* Grid 3x3 */
        .main-grid { 
            display: grid; grid-template-columns: repeat(3, 1fr); gap: 30px; margin-top: 40px; 
        }
        
        /* Responsividade */
        @media (max-width: 900px) { .main-grid { grid-template-columns: repeat(2, 1fr); } }
        @media (max-width: 600px) { .main-grid { grid-template-columns: 1fr; } }

        /* Cartão */
        .menu-card {
            background: white; padding: 40px 20px; border-radius: 12px; text-align: center;
            text-decoration: none; color: #333; box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            transition: transform 0.2s, border-color 0.2s; border: 1px solid transparent;
            display: flex; flex-direction: column; align-items: center; height: 100%;
            box-sizing: border-box; border-top: 5px solid transparent;
        }

        /* Hover Azul (Identidade da Secretaria) */
        .menu-card:hover { 
            transform: translateY(-5px); 
            border-top-color: #1a4384; 
            box-shadow: 0 10px 25px rgba(0,0,0,0.1); 
        }

        .icon { font-size: 45px; margin-bottom: 20px; display: block; }
        .card-title { font-size: 20px; font-weight: 800; color: #1a4384; margin-bottom: 10px; display: block; }
        .card-desc { font-size: 14px; color: #777; line-height: 1.5; }

        /* Badge de Notificação */
        .badge-notify {
            background: #e74c3c; color: white; padding: 2px 8px; border-radius: 10px;
            font-size: 12px; font-weight: bold; margin-left: 5px; vertical-align: middle;
        }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #1a4384;">SECRETARIA</span></div>
        <div class="user-info">
            <span>Olá, <strong>Secretaria</strong></span>
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn-logout">Sair</a>
        </div>
    </div>

    <div class="dashboard-container">
        <h1 class="welcome-msg">Painel Administrativo</h1>
        <p class="subtitle">Gerenciamento de Estágios e Convênios.</p>

        <div class="main-grid">
            
            <a href="${pageContext.request.contextPath}/AprovarConveniosServlet" class="menu-card">
                <span class="icon">🏢</span>
                <span class="card-title">
                    Novos Convênios <span class="badge-notify">!</span>
                </span>
                <span class="card-desc">Aprovar solicitações de parceria de empresas.</span>
            </a>

            <a href="${pageContext.request.contextPath}/GerenciarCandidaturasServlet" class="menu-card">
                <span class="icon">🎓</span>
                <span class="card-title">Candidaturas</span>
                <span class="card-desc">Encaminhar alunos para as empresas.</span>
            </a>

            <a href="${pageContext.request.contextPath}/ValidarRelatoriosServlet" class="menu-card">
                <span class="icon">✅</span>
                <span class="card-title">
                    Relatórios <span class="badge-notify">!</span>
                </span>
                <span class="card-desc">Validar documentos dos alunos.</span>
            </a>

            <a href="${pageContext.request.contextPath}/ListarAlunosServlet" class="menu-card">
                <span class="icon">🎓</span>
                <span class="card-title">Alunos</span>
                <span class="card-desc">Consultar lista de alunos.</span>
            </a>

            <a href="${pageContext.request.contextPath}/ListarEmpresasServlet" class="menu-card">
                <span class="icon">🤝</span>
                <span class="card-title">Empresas</span>
                <span class="card-desc">Gerenciar parceiros ativos.</span>
            </a>

           <a href="${pageContext.request.contextPath}/ConfiguracoesServlet" class="menu-card">
                <span class="icon">⚙️</span>
                <span class="card-title">Configurações</span>
                <span class="card-desc">Gerenciar acesso e perfil.</span>
            </a>

        </div>
    </div>

</body>
</html>