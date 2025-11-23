<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meu Perfil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; }
        .page-container { width: 100%; max-width: 800px; margin-top: 20px; }
        
        .profile-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        h2 { color: #1a4384; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 25px; }
        
        /* Layout em 2 Colunas */
        .form-row { display: flex; gap: 20px; margin-bottom: 15px; }
        .form-group { flex: 1; }
        
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; font-size: 14px; }
        input, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; font-size: 14px; }
        input:focus, select:focus { border-color: #1a4384; outline: none; }
        input:disabled { background-color: #f0f0f0; color: #777; cursor: not-allowed; }

        .btn-salvar { background: #1a4384; color: white; border: none; padding: 12px; border-radius: 5px; cursor: pointer; width: 100%; font-size: 16px; font-weight: bold; margin-top: 20px; transition: 0.3s; }
        .btn-salvar:hover { background: #0f2b57; }
        
        .alert { padding: 12px; background: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 20px; text-align: center; border: 1px solid #c3e6cb; }
        
        .btn-voltar { display: block; text-align: center; margin-top: 15px; color: #777; text-decoration: none; font-size: 14px; }
    </style>
</head>
<body>

    <div class="page-container">
        <div class="profile-card">
            <h2>✏️ Editar Perfil</h2>
            
            <c:if test="${not empty msg}">
                <div class="alert">✅ ${msg}</div>
            </c:if>

            <form action="AlunoPerfilServlet" method="post">
                <div class="form-row">
                    <div class="form-group">
                        <label>Nome Completo:</label>
                        <input type="text" name="nome" value="${usuario.nome}" required>
                    </div>
                    <div class="form-group">
                        <label>E-mail (Login):</label>
                        <input type="email" value="${usuario.email}" disabled>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Matrícula:</label>
                        <input type="text" name="matricula" value="${usuario.matricula}" placeholder="Ex: 20231001">
                    </div>
                    <div class="form-group">
                        <label>Telefone:</label>
                        <input type="text" name="telefone" value="${usuario.telefone}" placeholder="(21) 99999-9999">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Curso:</label>
                        <select name="curso">
                            <option value="">Selecione...</option>
                            <option value="Análise de Sistemas" ${usuario.curso == 'Análise de Sistemas' ? 'selected' : ''}>Análise de Sistemas</option>
                            <option value="Gestão Ambiental" ${usuario.curso == 'Gestão Ambiental' ? 'selected' : ''}>Gestão Ambiental</option>
                            <option value="Processos Gerenciais" ${usuario.curso == 'Processos Gerenciais' ? 'selected' : ''}>Processos Gerenciais</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Data de Nascimento:</label>
                        <input type="date" name="data_nascimento" value="${usuario.dataNascimento}">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Período:</label>
                        <select name="periodo">
                            <option value="">Selecione...</option>
                            <option value="1º Período" ${usuario.periodo == '1º Período' ? 'selected' : ''}>1º Período</option>
                            <option value="2º Período" ${usuario.periodo == '2º Período' ? 'selected' : ''}>2º Período</option>
                            <option value="3º Período" ${usuario.periodo == '3º Período' ? 'selected' : ''}>3º Período</option>
                            <option value="4º Período" ${usuario.periodo == '4º Período' ? 'selected' : ''}>4º Período</option>
                            <option value="5º Período" ${usuario.periodo == '5º Período' ? 'selected' : ''}>5º Período</option>
                            <option value="6º Período" ${usuario.periodo == '6º Período' ? 'selected' : ''}>6º Período</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Turno:</label>
                        <select name="turno">
                            <option value="">Selecione...</option>
                            <option value="Manhã" ${usuario.turno == 'Manhã' ? 'selected' : ''}>Manhã</option>
                            <option value="Noite" ${usuario.turno == 'Noite' ? 'selected' : ''}>Noite</option>
                        </select>
                    </div>
                </div>

                <hr style="border:0; border-top:1px solid #eee; margin:20px 0;">

                <div class="form-group">
                    <label>Senha de Acesso:</label>
                    <input type="text" name="senha" value="${usuario.senha}" required style="background-color:#fffbf0; border-color:#e6dbb9;">
                </div>

                <button type="submit" class="btn-salvar">Salvar Alterações</button>
                <a href="dashboard.jsp" class="btn-voltar">Voltar ao Painel</a>
            </form>
        </div>
    </div>

</body>
</html>