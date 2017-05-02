/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb.utils;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Celula<?> other = (Celula<?>) obj;
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }

    

    
    
}
