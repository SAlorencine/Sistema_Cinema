// script_detalhes.js

const API_BASE_URL = "/api/v1/filmes";
let filmeId; // Variável global para armazenar o ID do filme

$(document).ready(function() {
    // 1. Obter o ID do filme da URL
    const params = new URLSearchParams(window.location.search);
    filmeId = params.get('id');

    if (filmeId) {
        carregarDetalhesFilme(filmeId);
    } else {
        alert("ID do filme não encontrado na URL.");
        $("#tituloFilme").text("Filme não encontrado.");
    }

    // 2. Evento de Submit do Formulário de Análise
    $("#formAnalise").submit(function(event) {
        event.preventDefault();
        
        const novaAnalise = {
            autor: $("#autor").val(),
            comentario: $("#comentario").val(),
            nota: parseFloat($("#nota").val()) // Converte para número
        };

        enviarAnalise(novaAnalise);
    });
});

// FUNÇÃO GET - Carrega os detalhes do filme e as análises
function carregarDetalhesFilme(id) {
    $.ajax({
        url: `${API_BASE_URL}/${id}`,
        type: "GET",
        success: function(filme) {
            // Preenche os detalhes do filme
            $("#tituloFilme").text(filme.titulo);
            $("#generoFilme").text(filme.genero);
            $("#anoFilme").text(filme.anoLancamento);
            $("#sinopseFilme").text(filme.sinopse);

            // Carrega e exibe as análises
            exibirAnalises(filme.analises);
        },
        error: function() {
            $("#tituloFilme").text("Erro ao carregar detalhes do filme.");
        }
    });
}

// FUNÇÃO POST - Envia a nova análise
function enviarAnalise(analise) {
    $.ajax({
        url: `${API_BASE_URL}/${filmeId}/analise`, // Endpoint: /api/v1/filmes/{id}/analise
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(analise),
        success: function(novaAnaliseSalva) {
            alert("Análise enviada com sucesso!");
            
            // Limpa o formulário e atualiza a lista de análises
            $("#formAnalise")[0].reset();
            
            // Recarrega todos os detalhes para ver a análise nova
            carregarDetalhesFilme(filmeId); 
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Erro ao enviar análise:", errorThrown);
            alert("Erro ao enviar análise. Verifique o servidor.");
        }
    });
}

// FUNÇÃO Auxiliar - Renderiza a lista de análises
function exibirAnalises(analises) {
    $("#listaAnalises").empty();
    $("#countAnalises").text(analises.length);
    
    if (analises.length === 0) {
        $("#listaAnalises").append('<li class="list-group-item">Nenhuma análise registrada ainda. Seja o primeiro!</li>');
        return;
    }

    $.each(analises, function(i, analise) {
        const item = `
            <li class="list-group-item">
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Nota: ${analise.nota} / 10</h5>
                    <small>Por: ${analise.autor || 'Anônimo'}</small>
                </div>
                <p class="mb-1">${analise.comentario}</p>
                <small class="text-muted">Data: ${new Date(analise.dataAnalise).toLocaleDateString()}</small>
            </li>
        `;
        $("#listaAnalises").append(item);
    });
}