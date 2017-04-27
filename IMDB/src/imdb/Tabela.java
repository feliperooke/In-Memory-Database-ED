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
    public ArvoreAVL arvore;

    public Tabela() {
        this.indices = new Lista<>();
        this.campos = new Lista<>();
        //this.arvore = new ArvoreBinaria();
        this.arvore = new ArvoreAVL();
    }
    
    public Tabela(String json) {
        this.indices = new Lista<>();
        this.campos = new Lista<>();
//        this.arvore = new ArvoreBinaria();
        this.arvore = new ArvoreAVL();
        
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
    
    public void add(RegistroAVL registro){
        this.arvore.add(registro);
    }
    
    public RegistroAVL busca(String... chave) {
        return this.arvore.busca(chave);
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
