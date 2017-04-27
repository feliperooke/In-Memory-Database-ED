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
public class DataBase {
    private Lista<Tabela> tabelas;

    public DataBase() {
        this.tabelas = new Lista<Tabela>();
    }
    
    public Lista<Tabela> getTabelas() {
        return tabelas;
    }

    public void setTabelas(Lista<Tabela> tabelas) {
        this.tabelas = tabelas;
    }
    
    public void addTabela(Tabela tabela){
        this.tabelas.add(tabela);
    }
    
    public Tabela getTabela(String nomeTabela){
        for (int i = 0; i < this.tabelas.tamanho(); i++) {
            Tabela tabela = this.tabelas.get(i);
            if(tabela.getNome().equals(nomeTabela)){
                return tabela;
            }
        }
        
        throw new IllegalArgumentException("Tabela não existe");
    }
    
    
    
    public void addRegistro(String nomeTabela, RegistroAVL registro){
        this.getTabela(nomeTabela).add(registro);
    }
    
   
    public String toString(){
        String retorno = "[\n";
        
        for (int i = 0; i < tabelas.tamanho(); i++) {
            retorno += "\t" + tabelas.get(i).getJson() + ",\n";
        }
        
        return retorno + "]";
    }
}
