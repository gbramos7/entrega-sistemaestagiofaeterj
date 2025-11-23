<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Vagas Disponíveis - FAETERJ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    
    <style>
        /* --- CSS Básico da Página --- */
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        
        /* Container Centralizado */
        .page-container { max-width: 1200px; margin: 0 auto; }
        
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .logo { color: #1a4384; font-weight: bold; font-size: 24px; }
        .btn-voltar { color: #555; text-decoration: none; border: 1px solid #ccc; padding: 5px 15px; border-radius: 5px; background: white; font-size: 14px; }
        
        /* Filtros */
        .filtros-container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); display: flex; gap: 15px; margin-bottom: 30px; flex-wrap: wrap; }
        .filtro-input { padding: 10px; border: 1px solid #ddd; border-radius: 5px; flex: 1; min-width: 150px; }
        .btn-filtrar { background-color: #1a4384; color: white; border: none; padding: 10px 25px; border-radius: 5px; cursor: pointer; font-weight: bold; }
        .link-limpar { color: #666; text-decoration: none; align-self: center; margin-left: 10px; font-size: 14px; }

        /* Grid e Cards */
        .vagas-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 25px; }
        .vaga-card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); transition: transform 0.2s; display: flex; flex-direction: column; justify-content: space-between; }
        .vaga-card:hover { transform: translateY(-5px); box-shadow: 0 8px 20px rgba(0,0,0,0.1); }
        
        .vaga-header { display: flex; gap: 15px; margin-bottom: 15px; }
        .vaga-titulo { font-size: 1.2rem; font-weight: 800; color: #333; margin: 0 0 5px 0; }
        
        /* Badge */
        .badge-modelo { font-size: 0.75rem; font-weight: bold; text-transform: uppercase; display: inline-block; padding: 2px 6px; border-radius: 4px; margin-bottom: 5px; }
        
        /* Botões */
        .btn-candidatar { background-color: #0056b3; color: white; display: block; text-align: center; padding: 10px; border-radius: 6px; text-decoration: none; font-weight: bold; margin-bottom: 10px; transition: 0.3s; }
        .btn-candidatar:hover { background-color: #1a4384; }
        .btn-indicar { background-color: white; color: #0056b3; border: 1px solid #0056b3; width: 100%; padding: 10px; border-radius: 6px; font-weight: bold; cursor: pointer; transition: 0.3s; }
        .btn-indicar:hover { background-color: #eaf4ff; }

        /* --- ESTILOS DO MODAL DE INDICAÇÃO --- */
        .modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 2000; display: none; justify-content: center; align-items: center; backdrop-filter: blur(3px); }
        .modal-box { background: white; width: 90%; max-width: 450px; padding: 30px; border-radius: 12px; box-shadow: 0 15px 40px rgba(0,0,0,0.2); animation: popIn 0.3s; position: relative; }
        @keyframes popIn { from {transform: scale(0.9); opacity: 0;} to {transform: scale(1); opacity: 1;} }

        .modal-title { margin: 0 0 20px 0; color: #1a4384; font-size: 20px; text-align: center; border-bottom: 1px solid #eee; padding-bottom: 15px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; font-size: 14px; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; font-size: 14px; }
        
        .modal-actions { display: flex; gap: 10px; margin-top: 25px; }
        .btn-cancelar { background: #f1f2f6; color: #555; border: none; padding: 12px; border-radius: 6px; cursor: pointer; flex: 1; font-weight: bold; }
        .btn-enviar-ind { background: #1a4384; color: white; border: none; padding: 12px; border-radius: 6px; cursor: pointer; flex: 1; font-weight: bold; }

        /* Alertas */
        .alerta-azul { position: fixed; top: 80px; right: 20px; background: #3498db; color: white; padding: 15px 25px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.2); z-index: 3000; display: none; animation: slideIn 0.5s; }
        @keyframes slideIn { from { transform: translateX(100%); opacity: 0; } to { transform: translateX(0); opacity: 1; } }
    </style>
</head>
<body>

    <c:if test="${param.msg == 'sucesso'}">
        <div id="alertaSucesso" style="position: fixed; top: 20px; right: 20px; background: #2ecc71; color: white; padding: 15px 25px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.2); z-index: 3000; animation: slideIn 0.5s;">
            ✅ <strong>Sucesso!</strong> Candidatura enviada.
        </div>
        <script>setTimeout(() => document.getElementById('alertaSucesso').style.display='none', 4000);</script>
    </c:if>

    <div id="alertaIndicacao" class="alerta-azul">
        📧 Indicação enviada para: <br>
        <strong id="nomeIndicadoTexto"></strong> com sucesso!
    </div>

    <div id="modalIndicar" class="modal-overlay">
        <div class="modal-box">
            <h3 class="modal-title">Indicar Amigo</h3>
            <p style="color: #666; font-size: 14px; margin-bottom: 20px;">
                Você está indicando a vaga: <br>
                <strong id="modalVagaNome" style="color: #333;"></strong>
            </p>
            
            <div class="form-group">
                <label>Nome do Amigo:</label>
                <input type="text" id="indNome" placeholder="Ex: Carlos Silva">
            </div>
            <div class="form-group">
                <label>E-mail do Amigo:</label>
                <input type="email" id="indEmail" placeholder="Ex: carlos@email.com">
            </div>

            <div class="modal-actions">
                <button class="btn-cancelar" onclick="fecharModalIndicar()">Cancelar</button>
                <button class="btn-enviar-ind" onclick="enviarIndicacao()">Enviar Indicação</button>
            </div>
        </div>
    </div>

    <div class="page-container">
        <div class="header">
            <div class="logo">FAETERJ <span style="color: #8cc63f;">ESTÁGIO</span></div>
            <a href="dashboard.jsp" class="btn-voltar">Voltar ao Painel</a>
        </div>

        <h2 style="color: #1a4384; margin-bottom: 20px;">Oportunidades de Estágio</h2>

        <form action="AlunoVagasServlet" method="GET" class="filtros-container">
            <input type="text" name="cidade" placeholder="Cidade" class="filtro-input" value="${filtroCidade}">
            <input type="text" name="titulo" placeholder="Área" class="filtro-input" value="${filtroTitulo}">
            <select name="modelo" class="filtro-input">
                <option value="">Todos os Modelos</option>
                <option value="true" ${filtroModelo == 'true' ? 'selected' : ''}>Remoto</option>
                <option value="false" ${filtroModelo == 'false' ? 'selected' : ''}>Presencial</option>
            </select>
            <button type="submit" class="btn-filtrar">Filtrar</button>
            <a href="AlunoVagasServlet" class="link-limpar">Limpar</a>
        </form>

        <div class="vagas-grid">
            <c:if test="${empty vagas}">
                <div style="grid-column: 1/-1; text-align: center; padding: 40px; background: white; border-radius: 10px;">
                    <h3>Nenhuma vaga encontrada.</h3>
                    <p>Tente limpar os filtros para ver todas.</p>
                </div>
            </c:if>

            <c:forEach items="${vagas}" var="vaga">
                <div class="vaga-card">
                    <div class="vaga-header">
                        <div style="width: 50px; height: 50px; background: #eee; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 24px;">🏢</div>
                        <div class="vaga-info">
                            <h3 class="vaga-titulo">${vaga.titulo}</h3>
                            
                            <span class="badge-modelo" 
                                  style="background-color: ${vaga.remoto ? '#e8f5e9' : '#e3f2fd'}; color: ${vaga.remoto ? '#2e7d32' : '#1565c0'};">
                                ${vaga.remoto ? 'REMOTO' : 'PRESENCIAL'}
                            </span>
                            
                            <p class="empresa-nome" style="font-size: 14px; color: #666; margin: 5px 0;">
                                <strong>${not empty vaga.nomeEmpresa ? vaga.nomeEmpresa : 'Empresa Parceira'}</strong> 
                                <br>📍 ${not empty vaga.cidade ? vaga.cidade : 'Local a definir'}
                            </p>
                        </div>
                    </div>

                    <div style="color: #555; font-size: 14px; margin-bottom: 15px; height: 60px; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;">
                        ${vaga.descricao}
                    </div>

                    <div class="vaga-actions">
                        <a href="CandidaturaServlet?vagaId=${vaga.id}" class="btn-candidatar">Candidatar-se</a>
                        <button class="btn-indicar" onclick="abrirModalIndicar('${vaga.titulo}')">Indicar</button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
        function abrirModalIndicar(nomeVaga) {
            document.getElementById('modalVagaNome').innerText = nomeVaga;
            document.getElementById('indNome').value = "";
            document.getElementById('indEmail').value = "";
            document.getElementById('modalIndicar').style.display = 'flex';
        }

        function fecharModalIndicar() {
            document.getElementById('modalIndicar').style.display = 'none';
        }

        function enviarIndicacao() {
            let nome = document.getElementById('indNome').value;
            let email = document.getElementById('indEmail').value;

            if (nome === "" || email === "") {
                alert("Por favor, preencha o nome e o e-mail.");
                return;
            }
            fecharModalIndicar();
            document.getElementById("nomeIndicadoTexto").innerText = nome;
            let alerta = document.getElementById("alertaIndicacao");
            alerta.style.display = "block";
            setTimeout(function() { alerta.style.display = "none"; }, 4000);
        }
        
        // Fechar modal clicando fora
        window.onclick = function(e) {
            let modal = document.getElementById('modalIndicar');
            if (e.target == modal) fecharModalIndicar();
        }
    </script>

</body>
</html>