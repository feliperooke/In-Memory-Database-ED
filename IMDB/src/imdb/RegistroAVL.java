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
public class RegistroAVL extends Registro implements TreePrinter.PrintableNode{
    
    public static final int PESO_ESQUERDA = -1;
    public static final int BALANCEADO = 0;
    public static final int PESO_DIREITA = 1;
    
    public int equilibrio;
    public boolean consulta = false;
            
    //RegistroAVL registroDireita;
    //RegistroAVL registroEsquerda;

    public RegistroAVL() {
        this.indices = new Lista<>();
        this.valores = new Lista<>();
        equilibrio = BALANCEADO;
    }
    
    public RegistroAVL(String json){
        this.indices = new Lista<>();
        this.valores = new Lista<>();
        equilibrio = BALANCEADO;
        
        this.setJson(json);
    }
    
    @Override
    public RegistroAVL clone(){
        
        RegistroAVL registroAVL = new RegistroAVL();
        
        registroAVL.indices = this.indices.clone();
        registroAVL.valores = this.valores.clone();
        registroAVL.equilibrio = BALANCEADO;
        
        return registroAVL;
    }
    
    
}
