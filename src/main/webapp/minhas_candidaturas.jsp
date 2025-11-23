<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Minhas Candidaturas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        /* CSS do Modal */
        .modal-overlay { 
            position: fixed; top: 0; left: 0; width: 100%; height: 100%; 
            background: rgba(0,0,0,0.5); z-index: 1000; 
            display: none; justify-content: center; align-items: center; 
            backdrop-filter: blur(2px); 
        }
        
        .modal-content { 
            background: white; width: 90%; max-width: 500px; 
            padding: 30px; border-radius: 12px; 
            box-shadow: 0 10px 25px rgba(0,0,0,0.2); 
            position: relative; animation: popIn 0.3s; 
        }
        
        @keyframes popIn { from {transform: scale(0.8); opacity: 0;} to {transform: scale(1); opacity: 1;} }
        .close-btn { position: absolute; top: 15px; right: 20px; font-size: 24px; cursor: pointer; color: #999; }
        .modal-header h3 { margin: 0 0 10px 0; color: #1a4384; }
        .modal-label { font-weight: bold; color: #333; display: block; margin-top: 15px; }
        
        /* Cores */
        .status-finalizada { background: #333; color: white; font-weight: bold; padding: 5px 10px; border-radius: 15px; font-size: 12px; }
        .status-em-analise { background: #2980b9; color: white; font-weight: bold; padding: 5px 10px; border-radius: 15px; font-size: 12px; }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">FAETERJ <span style="color: #8cc63f;">ESTÁGIO</span></div>
        <a href="dashboard.jsp" class="btn-logout" style="border-color: #ccc; color: #555;">Voltar ao Painel</a>
    </div>

    <div class="dashboard-container">
        <h2 class="section-title">Histórico de Candidaturas</h2>

        <c:choose>
            <c:when test="${empty candidaturas}">
                <div style="text-align: center; padding: 50px; background: white; border-radius: 10px;">
                    <h3>Nenhuma candidatura encontrada.</h3>
                    <p>Vá em "Vagas Disponíveis" para se inscrever.</p>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="table-container">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Vaga / Local</th>
                                <th>Status</th>
                                <th style="text-align: center;">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${candidaturas}" var="c">
                                <tr>
                                    <td>
                                        <strong style="font-size: 16px; color: #333;">${c.vagaTitulo}</strong>
                                        <div style="font-size: 13px; color: #666; margin-top: 4px;">
                                            📍 ${c.vagaCidade}
                                        </div>
                                        <div style="font-size: 11px; color: #aaa; margin-top: 2px;">#ID: ${c.id}</div>
                                    </td>
                                    
                                    <td>
                                        <c:choose>
                                            <c:when test="${c.status == 'Aprovado'}"><span class="status-aprovado">Aprovado</span></c:when>
                                            <c:when test="${c.status == 'Finalizada'}"><span class="status-finalizada">Finalizada</span></c:when>
                                            <c:when test="${c.status == 'Em análise'}"><span class="status-em-analise">Em análise</span></c:when>
                                            <c:otherwise><span class="status-pendente">${c.status}</span></c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td style="text-align: center;">
                                        <div id="desc-oculta-${c.id}" style="display: none;">${c.vagaDescricao}</div>

                                        <button class="btn-detalhes" 
                                                onclick="mostrarDetalhes('${c.id}', '${c.vagaTitulo}', '${c.status}', '${c.vagaCidade}')">
                                            Ver Detalhes
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div id="modalDetalhes" class="modal-overlay">
        <div class="modal-content">
            <span class="close-btn" onclick="fecharModal()">&times;</span>
            
            <div class="modal-header">
                <h3 id="mTitulo">Título</h3>
                <span id="mStatus" class="status-pendente">Status</span>
            </div>
            
            <div class="modal-body">
                <span class="modal-label">Local:</span>
                <p id="mLocal">Local</p>

                <span class="modal-label">Descrição da Vaga:</span>
                <p id="mDesc" style="white-space: pre-wrap;">Descrição</p>
                
                <span class="modal-label">Protocolo:</span>
                <p id="mId" style="font-family: monospace; background: #eee; padding: 5px; display: inline-block;">#000</p>
                
                <div style="margin-top: 20px; padding-top: 15px; border-top: 1px solid #eee; font-size: 13px; color: #888;">
                    Em caso de dúvidas, entre em contato com a secretaria.
                </div>
            </div>
        </div>
    </div>

    <script>
        function mostrarDetalhes(id, titulo, status, cidade) {
            // 1. Pega a descrição da DIV oculta (Isso evita erros de aspas/enter)
            var divDescricao = document.getElementById('desc-oculta-' + id);
            var textoDescricao = divDescricao ? divDescricao.innerHTML : "Descrição não disponível.";

            // 2. Preenche o Modal
            document.getElementById('mTitulo').innerText = titulo;
            document.getElementById('mDesc').innerHTML = textoDescricao; // innerHTML preserva formatação
            document.getElementById('mLocal').innerText = cidade;
            document.getElementById('mId').innerText = '#' + id;
            
            // 3. Define a cor
            var span = document.getElementById('mStatus');
            span.innerText = status;
            span.className = '';
            
            if(status === 'Aprovado') span.className = 'status-aprovado';
            else if(status === 'Finalizada') span.className = 'status-finalizada';
            else if(status === 'Em análise') span.className = 'status-em-analise';
            else span.className = 'status-pendente';

            // 4. Abre
            document.getElementById('modalDetalhes').style.display = 'flex';
        }

        function fecharModal() {
            document.getElementById('modalDetalhes').style.display = 'none';
        }
        
        window.onclick = function(e) {
            if (e.target == document.getElementById('modalDetalhes')) fecharModal();
        }
    </script>

</body>
</html>