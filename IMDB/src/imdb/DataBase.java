/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Celula;
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
    
    public void removeRegistro(String nomeTabela, String... chave){
        this.getTabela(nomeTabela).remove(chave);
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
    
    public Lista<RegistroAVL[]> innerJoin(Tabela tabelaComChavePrimaria, Tabela tabelaComChaveEstrangeira, String nomeChavePrimaria, String nomeChaveEstrangeira){
        
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
        
        innerJoin((RegistroAVL)tabelaComChaveEstrangeira.arvore.raiz, uniao, tabelaComChavePrimaria, posicaoChaveEstrangeira, false);
        
        return uniao;
    }
    
    private void innerJoin(RegistroAVL raizDaTabelaComChaveEstrangeira, Lista<RegistroAVL[]> uniao, Tabela tabelaComChavePrimaria, int posicaoDaChavePrimaria, boolean marcar){
        
        if(raizDaTabelaComChaveEstrangeira == null) return;
        
        //pega o valor do indice para realizar busca na tabela com chave primária.
        String chaveEstrangeira = raizDaTabelaComChaveEstrangeira.indices.get(posicaoDaChavePrimaria);

        //busca na tabela
        RegistroAVL noDaBusca = tabelaComChavePrimaria.buscaEMarca(chaveEstrangeira);

        if (noDaBusca == null) {
            System.out.println("Nó da Busca: " + chaveEstrangeira + " é nulo.");
        } else {
            //adiciona o resultado na união
            RegistroAVL[] tupla = new RegistroAVL[2];
            tupla[0] = noDaBusca;
            tupla[1] = raizDaTabelaComChaveEstrangeira;

            if (marcar) {
                noDaBusca.consulta = true;
            }
            
            uniao.add(tupla);
        }
        
        innerJoin((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroEsquerda, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria, false);
        innerJoin((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroDireita, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria, false);
        
    }
    
    public Lista<RegistroAVL[]> leftOuterJoinFlag(Tabela tabelaComChavePrimaria, Tabela tabelaComChaveEstrangeira, String nomeChavePrimaria, String nomeChaveEstrangeira){
        
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
        
        innerJoin((RegistroAVL)tabelaComChaveEstrangeira.arvore.raiz, uniao, tabelaComChavePrimaria, posicaoChaveEstrangeira, true);
        
        desmarcaBuscando((RegistroAVL)tabelaComChavePrimaria.arvore.raiz, uniao);
        
        return uniao;
    }
    
    private void desmarcaBuscando(RegistroAVL raiz, Lista<RegistroAVL[]> uniao){
        
        if(raiz == null) return;
        
        
        if(!raiz.consulta){
            //adiciona o resultado na união
            RegistroAVL[] tupla = new RegistroAVL[2];
            tupla[0] = raiz;
            tupla[1] = null;

            uniao.add(tupla);
        }else{
            raiz.consulta = false;
        }
        
        desmarcaBuscando((RegistroAVL) raiz.registroEsquerda, uniao);
        desmarcaBuscando((RegistroAVL) raiz.registroDireita, uniao);
        
    }
   
    public Lista<RegistroAVL[]> rightOuterJoin(Tabela tabelaComChavePrimaria, Tabela tabelaComChaveEstrangeira, String nomeChavePrimaria, String nomeChaveEstrangeira){
        
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
        
        rightOuterJoin((RegistroAVL)tabelaComChaveEstrangeira.arvore.raiz, uniao, tabelaComChavePrimaria, posicaoChaveEstrangeira);
        
        return uniao;
    }
    
    private void rightOuterJoin(RegistroAVL raizDaTabelaComChaveEstrangeira, Lista<RegistroAVL[]> uniao, Tabela tabelaComChavePrimaria, int posicaoDaChavePrimaria){
        
        if(raizDaTabelaComChaveEstrangeira == null) return;
        
        //pega o valor do indice para realizar busca na tabela com chave primária.
        String chaveEstrangeira = raizDaTabelaComChaveEstrangeira.indices.get(posicaoDaChavePrimaria);

        //busca na tabela
        RegistroAVL noDaBusca = tabelaComChavePrimaria.busca(chaveEstrangeira);

        //adiciona o resultado na união
        RegistroAVL[] tupla = new RegistroAVL[2];
        tupla[0] = noDaBusca;
        tupla[1] = raizDaTabelaComChaveEstrangeira;

        uniao.add(tupla);

        
        rightOuterJoin((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroEsquerda, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria);
        rightOuterJoin((RegistroAVL) raizDaTabelaComChaveEstrangeira.registroDireita, uniao, tabelaComChavePrimaria, posicaoDaChavePrimaria);
        
    }
        
    private void reindexa(ArvoreAVL arvoreAVL, RegistroAVL raiz, int posicaoChaveEstrangeira){
        
        if(raiz == null) return;
        
        //clona o nó
        RegistroAVL novoNo = raiz.clone();
        
        //troca os indices
        String indiceInicial = novoNo.getIndice(0);
        novoNo.setIndice(0, novoNo.getIndice(posicaoChaveEstrangeira));
        novoNo.setIndice(posicaoChaveEstrangeira, indiceInicial);
        
        //insere na arvore reindexada
        arvoreAVL.add(novoNo);
        
        reindexa(arvoreAVL, (RegistroAVL) raiz.registroEsquerda, posicaoChaveEstrangeira);
        reindexa(arvoreAVL, (RegistroAVL) raiz.registroDireita, posicaoChaveEstrangeira);
    }
   
    public Lista<RegistroAVL[]> leftOuterJoin(Tabela tabelaComChavePrimaria, Tabela tabelaComChaveEstrangeira, String nomeChavePrimaria, String nomeChaveEstrangeira){
        
        Lista<RegistroAVL[]> inner = innerJoin(tabelaComChavePrimaria, tabelaComChaveEstrangeira, nomeChavePrimaria, nomeChaveEstrangeira);
        
        
        //confirmar se nomeChavePrimaria é a chave da primeira posição da tabela
        if(!tabelaComChavePrimaria.getIndices().get(0).equals(nomeChavePrimaria)){ 
            throw new IllegalArgumentException("O campo "+nomeChavePrimaria+" não é chave primária da tabela");
        }
        
        
        //---------------------- reindexa 
        //pega em qual posição está nomeChavePrimaria
        int posicaoChavePrimaria = tabelaComChavePrimaria.getIndices().indexOf(nomeChavePrimaria);
                
        //pega em qual posição está nomeChaveEstrangeira
        int posicaoChaveEstrangeira = tabelaComChaveEstrangeira.getIndices().indexOf(nomeChaveEstrangeira);
        
        
        //reindexa tabelaComChaveEstrangeira com base na chave primária da tabelaComChavePrimaria
        ArvoreAVL arvoreReindexada = new ArvoreAVL();
        reindexa(arvoreReindexada, (RegistroAVL)tabelaComChaveEstrangeira.arvore.raiz, posicaoChaveEstrangeira);
        //-----------------------
        
        //faz percurso inordem em arvoreCopia
        //e pega o que sobrou e adiciona no inner
        leftOuterJoin((RegistroAVL)tabelaComChavePrimaria.arvore.raiz, inner, arvoreReindexada, posicaoChaveEstrangeira);
        
        return inner;
    }
    
    private void leftOuterJoin(RegistroAVL raizDaTabelaComChavePrimaria, Lista<RegistroAVL[]> uniao, ArvoreAVL arvoreReindexada, int posicaoChaveEstrangeira){
        
        if(raizDaTabelaComChavePrimaria == null) return;
        
        //pega o valor do indice para realizar busca na tabela com chave primária.
        String chavePrimária = raizDaTabelaComChavePrimaria.indices.get(0);

        //busca na tabela
        RegistroAVL noDaBusca = (RegistroAVL) arvoreReindexada.busca(chavePrimária);
        
        if( noDaBusca == null){
            //adiciona o resultado na união
            RegistroAVL[] tupla = new RegistroAVL[2];
            tupla[0] = raizDaTabelaComChavePrimaria;
            tupla[1] = null;

            uniao.add(tupla);
        }
        
        leftOuterJoin((RegistroAVL) raizDaTabelaComChavePrimaria.registroEsquerda, uniao, arvoreReindexada, posicaoChaveEstrangeira);
        leftOuterJoin((RegistroAVL) raizDaTabelaComChavePrimaria.registroDireita, uniao, arvoreReindexada, posicaoChaveEstrangeira);
        
    }
    
    public Lista<RegistroAVL> select(Tabela tabela, String[]... restricao){
        
        String[][] matriz = null;
        
        if (restricao != null) {
            matriz = new String[restricao.length][restricao[0].length + 1];

            //identifica a posição dos campos da restrição e cria uma matriz de mapeamento
            for (int i = 0; i < restricao.length; i++) {
                //verifica se é índice ou campo
                int indice = tabela.getIndices().indexOf(restricao[i][0]);
                //se é indice
                if (indice != -1) {
                    matriz[i][0] = Integer.toString(indice);
                    matriz[i][1] = restricao[i][1];
                    matriz[i][2] = "i";
                } else {
                    matriz[i][0] = Integer.toString(tabela.getCampos().indexOf(restricao[i][0]));
                    matriz[i][1] = restricao[i][1];
                    matriz[i][2] = "c";
                }
            }
        }
        
        Lista<RegistroAVL> select = new Lista<>();
        
        //Faz percurso inordem e para cada registro, verifica se todas as condições são atendidas.
        select((RegistroAVL) tabela.arvore.raiz, select, matriz);
        
        return select;
    }
    
    private void select(RegistroAVL raiz, Lista<RegistroAVL> resultado, String[][] restricao){
        if(raiz == null) return;
        
        boolean adiciona = true;
        
        if (restricao != null) {
            for (String[] campoRestricaoTipo : restricao) {

                if (campoRestricaoTipo[2].equals("i")) {
                    if (!raiz.getIndice(Integer.parseInt(campoRestricaoTipo[0])).equals(campoRestricaoTipo[1])) {
                        adiciona = false;
                        break;
                    }
                } else if (!raiz.getValor(Integer.parseInt(campoRestricaoTipo[0])).equals(campoRestricaoTipo[1])) {
                    adiciona = false;
                    break;
                }
            }
        }
        
        if(adiciona) resultado.add(raiz);
        
        select((RegistroAVL)raiz.registroEsquerda, resultado, restricao);
        select((RegistroAVL)raiz.registroDireita, resultado, restricao);
        
    }
    
    
    
    
    
}
