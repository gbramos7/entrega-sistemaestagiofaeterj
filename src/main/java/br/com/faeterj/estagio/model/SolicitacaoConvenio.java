/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.model;

import java.time.LocalDateTime;

public class SolicitacaoConvenio {
    private int id;
    private int alunoId;
    
    // Campo Auxiliar (Vem do JOIN com a tabela Usuarios)
    private String nomeAluno;
    
    private String nomeEmpresa;
    private String emailEmpresa;
    private String siteEmpresa;
    private String status;
    private LocalDateTime dataSolicitacao;

    public SolicitacaoConvenio() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    
    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
    
    public String getEmailEmpresa() { return emailEmpresa; }
    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }
    
    public String getSiteEmpresa() { return siteEmpresa; }
    public void setSiteEmpresa(String siteEmpresa) { this.siteEmpresa = siteEmpresa; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
}