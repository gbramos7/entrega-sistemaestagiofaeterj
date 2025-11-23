/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.faeterj.estagio.model;

import java.time.LocalDateTime;

public class Notificacao {
    private int id;
    private int usuarioId;
    private String mensagem;
    private String link;
    private boolean lida;
    private LocalDateTime criadoEm;

    // getters/setters...
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public int getUsuarioId(){ return usuarioId; }
    public void setUsuarioId(int u){ this.usuarioId = u; }
    public String getMensagem(){ return mensagem; }
    public void setMensagem(String m){ this.mensagem = m; }
    public String getLink(){ return link; }
    public void setLink(String l){ this.link = l; }
    public boolean isLida(){ return lida; }
    public void setLida(boolean l){ this.lida = l; }
    public LocalDateTime getCriadoEm(){ return criadoEm; }
    public void setCriadoEm(LocalDateTime c){ this.criadoEm = c; }
}
