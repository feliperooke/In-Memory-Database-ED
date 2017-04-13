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
public class Registro {
    
    Lista<String> indices;
    Lista<String> valores;
        
    Registro registroDireita;
    Registro registroEsquerda;

    public Registro() {
        this.indices = new Lista<>();
        this.valores = new Lista<>();
    }
    
    public Registro(String json){
        this.indices = new Lista<>();
        this.valores = new Lista<>();
        
        this.setJson(json);
    }
    
    public String getJson(){
        JSONObject object = new JSONObject();
        try {
            String[] arrayIndices = new String[this.indices.tamanho()];
            for (int i = 0; i < arrayIndices.length; i++) {
                arrayIndices[i] = this.indices.get(i);
            }
            object.put("indices", arrayIndices);
            
            String[] arrayCampos = new String[this.valores.tamanho()];
            for (int i = 0; i < arrayCampos.length; i++) {
                arrayCampos[i] = this.valores.get(i);
            }
            object.put("campos", arrayCampos);
            
        } catch (JSONException e) {
            throw new IllegalArgumentException("Problema no Json");
        }

        return object.toString();
    }
    
    private void setJson(String json){
        JSONObject obj = new JSONObject(json);
        
        JSONArray arr = obj.getJSONArray("indices");
        for (int i = 0; i < arr.length(); i++) {
            this.indices.add(arr.getString(i));
        }
        
        arr = obj.getJSONArray("valores");
        for (int i = 0; i < arr.length(); i++) {
            this.valores.add(arr.getString(i));
        }
    }
    
    public void addIndice(String indice){
        this.indices.add(indice);
    }
    
    public void addValor(String valor){
        this.valores.add(valor);
    }
    
    public int comparaCom(Registro noParaComparacao){
        
        for (int i = 0; i < this.indices.tamanho(); i++) {
            //o String.compareTo compara caracter a caracter e seu pior caso é o tamanho da menor string
            int comparacao = this.indices.get(i).compareTo(noParaComparacao.indices.get(i));
            if( comparacao < 0) return -1;
            else if(comparacao > 0) return 1;
            //caso seja igual ele vai para o próximo indice da lista
        }
        
        //caso sejam todas as chaves iguais, considera que é maior
        return 0;
    }
}
