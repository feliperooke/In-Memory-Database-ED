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
        /*for (int i = 0; i < this.tabelas.tamanho(); i++) {
            Tabela tabela = this.tabelas.get(i);
            if(tabela.getNome().equals(nomeTabela)){
                return tabela;
            }
        }*/
        
        Tabela aux = new Tabela();
        aux.setNome(nomeTabela);
        
        return this.tabelas.get(aux);
        
        //throw new IllegalArgumentException("Tabela não existe");
    }
    
    
    
    public void addRegistro(String nomeTabela, RegistroAVL registro){
        this.getTabela(nomeTabela).add(registro);
    }
    
    public Registro getRegistro(String nomeTabela, String... indices) {
        return getTabela(nomeTabela).busca(indices);
    }
    
   
    public String toString(){
        String retorno = "[\n";
        
        for (int i = 0; i < tabelas.tamanho(); i++) {
            retorno += "\t" + tabelas.get(i).getJson() + ",\n";
        }
        
        return retorno + "]";
    }
    
    public Lista<RegistroAVL[]> join(Tabela tabelaComChavePrimaria, Tabela tabelaComChaveEstrangeira, String nomeChavePrimaria, String nomeChaveEstrangeira){
        
        //confirmar se tabelaComChavePrimaria não tem chave composta
        if(tabelaComChavePrimaria.getIndices().tamanho()>1)
            System.out.println("A tabela "+tabelaComChavePrimaria.getNome()+" apresenta chave composta");
        
        //confirmar se nomeChavePrimaria é a chave da primeira posição da tabela
        if(!tabelaComChavePrimaria.getIndices().get(0).equals(nomeChavePrimaria)){ 
            throw new IllegalArgumentException("O campo "+nomeChavePrimaria+" não é chave primária da tabela");
        }
        
        //pega em qual posição está nomeChaveEstrangeira
        int posicaoChaveEstrangeira = tabelaComChaveEstrangeira.getIndices().indexOf(nomeChaveEstrangeira);
                
        //faz percurso inordem em tabelaComChaveEstrangeira
        //e pega o campo nomeChaveEstrangeira do nó e realiza busca na tabelaComChavePrimaria
        Lista<RegistroAVL[]> uniao = new Lista<>();
        
        join((RegistroAVL)tabelaComChaveEstrangeira.arvore.raiz, uniao, tabelaComChavePrimaria, posicaoChaveEstrangeira);
        
        return uniao;
    }
    
    private void join(RegistroAVL raizDaTabelaComChaveEstrangeira, Lista<RegistroAVL[]> uniao, Tabela tabelaComChavePrimaria, int posicaoDaChavePrimaria){
        
        if(raizDaTabelaComChaveEstrangeira == null) return;
        
        //pega o valor do indice para realizar busca na tabela com chave primária.
        String chaveEstrangeira = raizDaTabelaComChaveEstrangeira.indices.get(posicaoDaChavePrimaria);

        //busca na tabela
        RegistroAVL noDaBusca = tabelaComChavePrimaria.busca(chaveEstrangeira);

        if (noDaBusca == null) {
            System.out.println("Nó da Busca: " + chaveEstrangeira + " é nulo.");
        } else {
            //adiciona o resultado na união
            RegistroAVL[] tupla = new RegistroAVL[2];
            tupla[0] = noDaBusca;
            tupla[1] = raizDaTabelaComChaveEstrangeira;

            uniao.add(tupla);
        }
        
        join((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroEsquerda, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria);
        join((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroDireita, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria);
        
    }
    
    public Lista<RegistroAVL[]> select(Tabela tabela, String[]... restricao){
        
        //identifica a posição dos campos da restrição e cria uma matriz de mapeamento
        for (String[] campoOperadorRestricao : restricao) {
            campoOperadorRestricao[0] = Integer.toString(tabela.getIndices().indexOf(campoOperadorRestricao[0])); 
        }
        
        //Faz percurso inordem e para cada registro, verifica se todas as condições são atendidas.
        
        return null;
    }
    
    
    
    
}
