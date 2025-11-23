package br.com.faeterj.estagio.model;

import java.time.LocalDateTime;

public class Candidatura {
    private int id;
    private int vagaId;
    private int alunoId;
    private LocalDateTime dataCandidatura;
    private String status;
    
    // --- CAMPOS AUXILIARES (Obrigatórios para o DAO funcionar) ---
    private String vagaTitulo; 
    private String vagaDescricao; 
    private String vagaCidade;
    
    private String nomeAluno;
    private String emailAluno;
    private String cursoAluno;
    private String telefoneAluno;

    public Candidatura() {}

    public Candidatura(int id, int alunoId, int vagaId) {
        this.id = id;
        this.alunoId = alunoId;
        this.vagaId = vagaId;
        this.status = "Pendente"; 
        this.dataCandidatura = LocalDateTime.now();
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getVagaId() { return vagaId; }
    public void setVagaId(int vagaId) { this.vagaId = vagaId; }
    
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    
    public LocalDateTime getDataCandidatura() { return dataCandidatura; }
    public void setDataCandidatura(LocalDateTime d) { this.dataCandidatura = d; }
    
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }

    // --- Getters e Setters da Vaga (Onde o erro estava) ---
    public String getVagaTitulo() { return vagaTitulo; }
    public void setVagaTitulo(String t) { this.vagaTitulo = t; }
    
    public String getVagaDescricao() { return vagaDescricao; }
    public void setVagaDescricao(String d) { this.vagaDescricao = d; }
    
    public String getVagaCidade() { return vagaCidade; }
    public void setVagaCidade(String c) { this.vagaCidade = c; }

    // --- Getters e Setters do Aluno ---
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String n) { this.nomeAluno = n; }
    public String getEmailAluno() { return emailAluno; }
    public void setEmailAluno(String e) { this.emailAluno = e; }
    public String getCursoAluno() { return cursoAluno; }
    public void setCursoAluno(String c) { this.cursoAluno = c; }
    public String getTelefoneAluno() { return telefoneAluno; }
    public void setTelefoneAluno(String t) { this.telefoneAluno = t; }
    
    private String nomeEmpresa;

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
}
