/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.model;

import java.time.LocalDateTime;

public class RelatorioEstagio {
    private int id;
    private int alunoId;
    private String tipo;
    private String caminhoArquivo;
    private LocalDateTime enviadoEm;
    private String status;
    private String observacao;

    // getters/setters
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public int getAlunoId(){ return alunoId; }
    public void setAlunoId(int a){ this.alunoId = a; }
    public String getTipo(){ return tipo; }
    public void setTipo(String t){ this.tipo = t; }
    public String getCaminhoArquivo(){ return caminhoArquivo; }
    public void setCaminhoArquivo(String c){ this.caminhoArquivo = c; }
    public LocalDateTime getEnviadoEm(){ return enviadoEm; }
    public void setEnviadoEm(LocalDateTime e){ this.enviadoEm = e; }
    public String getStatus(){ return status; }
    public void setStatus(String s){ this.status = s; }
    public String getObservacao(){ return observacao; }
    public void setObservacao(String o){ this.observacao = o; }
    
    // ... (Mantenha os outros atributos e getters/setters) ...

    // --- CAMPO AUXILIAR (Para a Secretaria ver o nome) ---
    private String nomeAluno;

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
}

