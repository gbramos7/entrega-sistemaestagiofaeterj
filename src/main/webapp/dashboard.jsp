<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.faeterj.estagio.model.Usuario"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuarioLogado");
    if (u == null) {
        u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - FAETERJ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    
    <style>
        /* --- CSS LOCAL PARA O GRID 3x3 --- */
        
        /* Container principal centralizado */
        .dashboard-container {
            max-width: 1100px; /* Largura ideal para 3 cards */
            margin: 40px auto; /* Centraliza vertical e horizontalmente */
            padding: 0 20px;
            text-align: center; /* Centraliza os textos do título */
        }

        /* O Grid Mágico */
        .main-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr); /* Cria exatamente 3 colunas iguais */
            gap: 30px; /* Espaço entre os cards */
            margin-top: 40px;
        }

        /* Responsividade: Se a tela diminuir (Tablet), vira 2 colunas */
        @media (max-width: 900px) {
            .main-grid { grid-template-columns: repeat(2, 1fr); }
        }

        /* Responsividade: Se a tela for celular, vira 1 coluna */
        @media (max-width: 600px) {
            .main-grid { grid-template-columns: 1fr; }
        }

        /* Ajuste visual dos Cards para ficarem uniformes */
        .menu-card {
            background: white;
            padding: 40px 20px;
            border-radius: 12px;
            text-align: center;
            text-decoration: none;
            color: #333;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            transition: transform 0.2s, border-color 0.2s, box-shadow 0.2s;
            border: 1px solid transparent;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100%; /* Garante altura igual */
        }

        .menu-card:hover {
            transform: translateY(-5px);
            border-color: #8cc63f;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        /* Ícones maiores */
        .icon { font-size: 45px; margin-bottom: 20px; display: block; }
        .card-title { font-size: 20px; font-weight: 800; color: #1a4384; margin-bottom: 10px; display: block; }
        .card-desc { font-size: 14px; color: #777; line-height: 1.5; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #8cc63f;">ESTÁGIO</span></div>
        <div class="user-info">
            <span>Olá, <strong><%= u.getNome() %></strong></span>
            <a href="login.jsp" class="btn-logout">Sair</a>
        </div>
    </div>

    <div class="dashboard-container">
        <h1 class="welcome-msg" style="color: #333;">Painel do Aluno</h1>
        <p class="subtitle">Bem-vindo! Selecione uma opção abaixo.</p>

        <div class="main-grid">
            
            <a href="AlunoVagasServlet" class="menu-card">
                <span class="icon">🔍</span>
                <span class="card-title">Vagas Disponíveis</span>
                <span class="card-desc">Pesquise e candidate-se.</span>
            </a>

            <a href="MinhasCandidaturasServlet" class="menu-card">
                <span class="icon">📄</span>
                <span class="card-title">Minhas Candidaturas</span>
                <span class="card-desc">Acompanhe seus status.</span>
            </a>

            <a href="AlunoRelatoriosServlet" class="menu-card">
                <span class="icon">📝</span>
                <span class="card-title">Relatórios</span>
                <span class="card-desc">Envie documentos obrigatórios.</span>
            </a>

            <a href="https://www.faeterj-prc.faetec.rj.gov.br/est%C3%A1gio" target="_blank" class="menu-card">
                <span class="icon">🤝</span>
                <span class="card-title">Empresas Parceiras</span>
                <span class="card-desc">Veja a lista oficial.</span>
            </a>

            <a href="solicitar_convenio.jsp" class="menu-card">
                <span class="icon">🏢</span>
                <span class="card-title">Solicitar Convênio</span>
                <span class="card-desc">Indique uma nova empresa.</span>
            </a>

            <a href="AlunoPerfilServlet" class="menu-card">
                <span class="icon">⚙️</span>
                <span class="card-title">Meu Perfil</span>
                <span class="card-desc">Meus dados e senha.</span>
            </a>

        </div>
    </div>

</body>
</html>