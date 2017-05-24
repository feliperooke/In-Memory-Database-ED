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
public class Lista<O>{
    
    public Celula<O> primeira;
    public Celula<O> ultima;
    private int tamanho = 0;
    
    public void add(O elemento){
        if (this.tamanho == 0) {
            this.addNoComeco(elemento);
        } else {
            Celula nova = new Celula<>(elemento);
            this.ultima.setProxima(nova);
            this.ultima = nova;
            this.tamanho++;
        }
    }
    
    public void add(int posicao, O elemento) {
        if (posicao == 0) { // No começo.
            this.addNoComeco(elemento);
        } else if (posicao == this.tamanho) { // No fim.
            this.add(elemento);
        } else {
            Celula anterior = this.getCelula(posicao - 1);
            Celula nova = new Celula(elemento,anterior.getProxima());
            anterior.setProxima(nova);
            this.tamanho++;
        }
    }
    
    public void substitui(int posicao, O elemento) {
        this.getCelula(posicao).setValor(elemento);
    }
    
    private void addNoComeco(O elemento) {
        Celula nova = new Celula(elemento, this.primeira);
        this.primeira = nova;

        if(this.tamanho == 0){
          // caso especial da lista vazia
          this.ultima = this.primeira;
        }
        this.tamanho++;
    }
    
    public O get(int posicao) {
        return getCelula(posicao).getValor();
    }
    
    private Celula<O> getCelula(int posicao) {
        if(!(posicao >= 0 && posicao < this.tamanho)){
            throw new IllegalArgumentException("Posição não existe");
        }
        
        Celula<O> atual = primeira;
        for (int i = 0; i < posicao; i++) {
          atual = atual.getProxima();
        }
        return atual;
    }
    
    public O get(O objeto) {
        return getCelula(objeto).getValor();
    }
    
    private Celula<O> getCelula(O valor) {
        if(valor == null){
            throw new IllegalArgumentException("Posição não existe");
        }
        
        Celula<O> atual = primeira;
        for (int i = 0; i < tamanho; i++) {
          if(atual.getValor().equals(valor)) return atual;
          atual = atual.getProxima();
        }
        return null;
    }
    
    
    
    private void removePrimeira(int posicao){
        if(!(posicao >= 0 && posicao < this.tamanho)){
            throw new IllegalArgumentException("Posição não existe");
        }
        this.primeira = this.primeira.getProxima();
        this.tamanho--;

        if (this.tamanho == 0) {
          this.ultima = null;
        }
    }
    
    public void remove(int posicao) {
        if (!(posicao >= 0 && posicao < this.tamanho)) {
            throw new IllegalArgumentException("Posição não existe");
        }
        if (this.tamanho == 1) {
            this.removePrimeira(posicao);
        } else {
            Celula penultima = getCelula(posicao-1);
            penultima.setProxima(null);
            this.ultima = penultima;
            this.tamanho--;
        }
    }
    
    public void remove(O elemento){
        int indice = indexOf(elemento);
        remove(indice);
    }
    
    public int tamanho() {
        return this.tamanho;
    }
    
    public boolean contem(O valor) {
        if(this.primeira == null) return false;
        
        Celula<O> atual = this.primeira;
        while (atual != null){
            if(atual.getValor().equals(valor)) return true;
            atual = atual.getProxima();
        } 
        
        return false;
    }
    
    public int indexOf(O valor) {
        if(this.primeira == null) 
            throw new IllegalArgumentException("Não existem elementos");
        
        
        Celula<O> atual = this.primeira;
        int i = 0;
        while (atual != null){
            if(atual.getValor().equals(valor)) return i;
            i++;
            atual = atual.getProxima();
        } 
        
        return -1;
    }

    public boolean isEmpty(){
        return tamanho == 0;
    }
    
    @Override
    public String toString() {

        // Verificando se a Lista está vazia
        if (this.tamanho == 0) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[");
        Celula atual = primeira;

        // Percorrendo até o penúltimo elemento.
        for (int i = 0; i < this.tamanho - 1; i++) {
            builder.append(atual.getValor());
            builder.append(", ");
            atual = atual.getProxima();
        }

        // último elemento
        builder.append(atual.getValor());
        builder.append("]");

        return builder.toString();
    }

    @Override
    public Lista<O> clone() {
        
        Lista<O> lista = new Lista<>();
        
        Celula<O> atual = primeira;
        for (int i = 0; i < this.tamanho; i++) {
            lista.add(atual.getValor());
            atual = atual.getProxima();
        }        
        
        return lista;
    }
    
}
