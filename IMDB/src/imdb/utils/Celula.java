/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.utils;

/**
 *
 * @author felip
 * @param <O> Object
 */
public class Celula<O> {
    private O valor;
    private Celula<O> proxima;

    public Celula(O valor) {
        this.valor = valor;
    }
    
    public Celula(O valor, Celula<O> proxima) {
        this.valor = valor;
        this.proxima = proxima;
    }

    public O getValor() {
        return valor;
    }

    public void setValor(O valor) {
        this.valor = valor;
    }

    public Celula<O> getProxima() {
        return proxima;
    }

    public void setProxima(Celula<O> proxima) {
        this.proxima = proxima;
    }

}
