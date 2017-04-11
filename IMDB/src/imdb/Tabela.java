/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Lista;

/**
 *
 * @author felip
 */
public class Tabela {
    private String nome;
    private Lista<String> campos;
    private Lista<String> indices;
    private Lista<String> numericos;
    private RegistroTree registroTree;
    
    public Tabela(String json) {
        
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Lista<String> getCampos() {
        return campos;
    }

    public void setCampos(Lista<String> campos) {
        this.campos = campos;
    }

    public Lista<String> getIndices() {
        return indices;
    }

    public void setIndices(Lista<String> indices) {
        this.indices = indices;
    }

    public Lista<String> getNumericos() {
        return numericos;
    }

    public void setNumericos(Lista<String> numericos) {
        this.numericos = numericos;
    }

    public RegistroTree getRegistroTree() {
        return registroTree;
    }

    public void setRegistroTree(RegistroTree registroTree) {
        this.registroTree = registroTree;
    }
    
    
     
    public void getJson(){}
    public void setJson(){}
    
    
}
