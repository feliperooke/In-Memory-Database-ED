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
public class RegistroAVL extends Registro{
    
    public static final int PESO_ESQUERDA = -1;
    public static final int BALANCEADO = 0;
    public static final int PESO_DIREITA = 1;
    
    public int equilibrio;
            
    RegistroAVL registroDireita;
    RegistroAVL registroEsquerda;

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
    
}
