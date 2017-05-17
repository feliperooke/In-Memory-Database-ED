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
public class Registro implements TreePrinter.PrintableNode{
    
    protected Lista<String> indices;
    protected Lista<String> valores;
        
    public Registro registroDireita;
    public Registro registroEsquerda;

    public Registro() {
        this.indices = new Lista<>();
        this.valores = new Lista<>();
    }
    
    public Registro(String json){
        this.indices = new Lista<>();
        this.valores = new Lista<>();
        
        this.setJson(json);
    }
    
    public void addIndice(String indice){
        this.indices.add(indice);
    }
    
    public void addValor(String valor){
        this.valores.add(valor);
    }
    
    public String getIndice(int posicao){
        return this.indices.get(posicao);
    }
    
    public String getValor(int posicao){
        return this.valores.get(posicao);
    }
    
    public Lista<String> getIndices(){
        return this.indices;
    }
    
    public Lista<String> getValores(){
        return this.valores;
    }
    
    public void setIndice(int posicao, String valor){
        this.indices.substitui(posicao,valor);
    }
    
    public void setValor(int posicao, String valor){
        this.valores.substitui(posicao,valor);
    }

    public void setIndices(Lista<String> valor){
        this.indices = valor;
    }
    
    public void setValores(Lista<String> valor){
        this.valores = valor;
    }
    
    
    
    public int comparaCom(Registro noParaComparacao){
        
        for (int i = 0; i < Math.min(this.indices.tamanho(), noParaComparacao.indices.tamanho()); i++) {
            //o String.compareTo compara caracter a caracter e seu pior caso é o tamanho da menor string
            int comparacao = this.indices.get(i).compareTo(noParaComparacao.indices.get(i));
            if( comparacao < 0) return -1;
            else if(comparacao > 0) return 1;
            //caso seja igual ele vai para o próximo indice da lista
        }
        
        //caso sejam todas as chaves iguais
        return 0;
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
    
    public void setJson(String json){
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
    
    @Override
    public TreePrinter.PrintableNode getLeft() {
        return this.registroEsquerda;
    }

    @Override
    public TreePrinter.PrintableNode getRight() {
        return this.registroDireita;
    }

    @Override
    public String getText() {
        return indices.get(0);
    }
    
}
