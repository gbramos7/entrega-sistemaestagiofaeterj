/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.model;

public class Vaga {
    private int id;
    private int empresaId;
    private String titulo;
    private String descricao;
    private String cidade;
    private boolean remoto;
    private boolean ativa; // Campo importante para a Secretaria
    
    // Campo auxiliar (não existe na tabela, vem do JOIN)
    private String nomeEmpresa; 

    // --- A CORREÇÃO ESTÁ AQUI ---
    // Você precisa deste construtor vazio para o 'new Vaga()' funcionar no DAO
    public Vaga() {
    }
    // ----------------------------

    // Construtor Completo (opcional, mas útil)
    public Vaga(int id, int empresaId, String titulo, String descricao, String cidade, boolean remoto, boolean ativa) {
        this.id = id;
        this.empresaId = empresaId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cidade = cidade;
        this.remoto = remoto;
        this.ativa = ativa;
    }

    // --- GETTERS E SETTERS ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public boolean isRemoto() { return remoto; }
    public void setRemoto(boolean remoto) { this.remoto = remoto; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
}