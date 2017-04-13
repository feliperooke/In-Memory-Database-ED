/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Lista;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author felip
 */
public class Tabela {
    private String nome;
    private Lista<String> campos;
    private Lista<String> indices;
    private Registro registroTree;

    public Tabela() {
        this.indices = new Lista<>();
        this.campos = new Lista<>();
    }
    
    public Tabela(String json) {
        this.indices = new Lista<>();
        this.campos = new Lista<>();
        
        this.setJson(json);
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

    public void addCampo(String campo){
        this.campos.add(campo);
    }
    
    public Lista<String> getIndices() {
        return indices;
    }

    public void setIndices(Lista<String> indices) {
        this.indices = indices;
    }
    
    public void addIndice(String indice){
        this.indices.add(indice);
    }
    
    public void add(Registro registro){
        
        /**
         * TODO Tratar restrições do registro de acordo com os campos da tabela
         */
        
        add(this.registroTree, registro);
    }
    
    private void add(Registro raiz, Registro novo) {
        //se vazio 
        if (registroTree == null) {
            registroTree = novo;
        } else //verifica se vai adicionar a direita
        if (raiz.comparaCom(novo) < 0) {
            //verifica se raiz tem elemento a direita
            if (raiz.registroDireita != null) {
                //se tem chama o add novamente
                add(raiz.registroDireita, novo);
            } else {
                //se está vazio
                raiz.registroDireita = novo;
            }
        } else if (raiz.registroEsquerda != null) {
            //se tem chama o add novamente
            add(raiz.registroEsquerda, novo);
        } else {
            //se está vazio
            raiz.registroEsquerda = novo;
        }
    }
    
    /**
     * @TODO Fazer método de remoção da árvore
     * @TODO Testar BUSCA
     */
    
    public Registro busca(String... chave){
        
        Registro aux = new Registro();
        for (String key : chave) {
            aux.indices.add(key);
        }
        
        return busca(registroTree, aux);
    }
    
    private Registro busca(Registro raiz, Registro registroAux){
        //se vazio 
        if (registroTree == null) {
            return null;
        } else {
            int comparacao = raiz.comparaCom(registroAux);
            if (comparacao == 0) return raiz; 
            else if (comparacao < 0) {
                //verifica se raiz tem elemento a direita
                if (raiz.registroDireita != null){
                    //se tem chama a busca novamente
                    busca(raiz.registroDireita, registroAux);
                } else {
                    //se está vazio, quer dizer que o elemento não está na árvore
                    return null;
                }
            //verifica se raiz tem elemento a esquerda    
            } else if (raiz.registroEsquerda != null) {
                //se tem chama a busca novamente
                add(raiz.registroEsquerda, registroAux);
            } else {
                //se está vazio, quer dizer que o elemento não está na árvore
                return null;
            }
        }
        
        return null;
    }
    
    
    public String getJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("nome", this.nome);
            String[] arrayCampos = new String[this.campos.tamanho()];
            for (int i = 0; i < arrayCampos.length; i++) {
                arrayCampos[i] = this.campos.get(i);
            }
            object.put("campos", arrayCampos);
            String[] arrayIndices = new String[this.indices.tamanho()];
            for (int i = 0; i < arrayIndices.length; i++) {
                arrayIndices[i] = this.indices.get(i);
            }
            object.put("indices", arrayIndices);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Problema no Json");
        }

        return object.toString();
    }
    
    private void setJson(String json){
        JSONObject obj = new JSONObject(json);
        this.nome = obj.getString("nome");

        JSONArray arr = obj.getJSONArray("campos");
        for (int i = 0; i < arr.length(); i++) {
            this.campos.add(arr.getString(i));
        }
        
        arr = obj.getJSONArray("indices");
        for (int i = 0; i < arr.length(); i++) {
            this.indices.add(arr.getString(i));
        }
    }
    
    
}
