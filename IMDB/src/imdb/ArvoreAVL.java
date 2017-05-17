/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

/**
 *
 * @author felip
 */
public class ArvoreAVL extends ArvoreBinaria{
     //RegistroAVL raiz = null;
     
     //Flag utilizada para indicar se altura da arvore aumentou
     private boolean aumento;
     private boolean diminui;
     private boolean retornoDaInclusao;
     private boolean retornoDaDelecao;

    public boolean add(RegistroAVL registro) {
        aumento = true;
        raiz = add((RegistroAVL)this.raiz, registro);
        return retornoDaInclusao;
    }

    public Registro busca(String... chave) {

        Registro aux = new Registro();
        for (String key : chave) {
            aux.indices.add(key);
        }

        return busca(this.raiz, aux);
    }
    
    private RegistroAVL add(RegistroAVL raiz, RegistroAVL novo) {
        //se vazio 
        if (raiz == null) {
            retornoDaInclusao = true;
            aumento = true;
            return novo;
        } 
        
        //se for igual não insere e permanece inalterada
        if (raiz.comparaCom(novo) == 0) {
            //item ja existe na arvore
            aumento = false;
            retornoDaInclusao = false;
            return raiz;
        }
        
        //verifica se vai adicionar a esquerda
        if (raiz.comparaCom(novo) > 0) {
            raiz.registroEsquerda = add((RegistroAVL)raiz.registroEsquerda, novo);
            //se aumentou autura da subarvore esquerda
            if(aumento){
                diminuiEquilibrio(raiz);
                if(raiz.equilibrio < RegistroAVL.PESO_ESQUERDA){
                    aumento = false;
                    return reequilibraEsquerda(raiz);
                }
            }
            //rebalanceamento desnecessário
            return raiz;
        }else{
            raiz.registroDireita = add((RegistroAVL)raiz.registroDireita, novo);
            //se aumentou autura da subarvore direita
            if(aumento){
                aumentaEquilibrio(raiz);
                if(raiz.equilibrio > RegistroAVL.PESO_DIREITA){
                    aumento = false;
                    return reequilibraDireita(raiz);
                }
            }
            //rebalanceamento desnecessário
            return raiz;
        }
    }
    
    private RegistroAVL reequilibraEsquerda(RegistroAVL raiz){
        RegistroAVL filhoEsquerdo = (RegistroAVL)raiz.registroEsquerda;
        
        //Vê se esta mais pesado a esquerda-direita --> nesse caso será necessário rotação dupla
        if(filhoEsquerdo.equilibrio > RegistroAVL.BALANCEADO){
            RegistroAVL filhoEsquerdoDireito = (RegistroAVL)filhoEsquerdo.registroDireita;
            //Atualiza os equilibrios
            if(filhoEsquerdoDireito.equilibrio < RegistroAVL.BALANCEADO){
                filhoEsquerdo.equilibrio = RegistroAVL.BALANCEADO;
                filhoEsquerdoDireito.equilibrio = RegistroAVL.BALANCEADO;
                raiz.equilibrio = RegistroAVL.PESO_DIREITA;
            }else{
                filhoEsquerdo.equilibrio = RegistroAVL.PESO_ESQUERDA;
                filhoEsquerdoDireito.equilibrio = RegistroAVL.BALANCEADO;
                raiz.equilibrio = RegistroAVL.BALANCEADO;
            }
            //efetua rotação
            raiz.registroEsquerda = rotacaoSimplesEsquerda(filhoEsquerdo);
        }else{
            filhoEsquerdo.equilibrio = RegistroAVL.BALANCEADO;
            raiz.equilibrio = RegistroAVL.BALANCEADO;
        }
        
        return rotacaoSimplesDireita(raiz);
    }
    
    private RegistroAVL reequilibraDireita(RegistroAVL raiz){
        RegistroAVL filhoDireita = (RegistroAVL)raiz.registroDireita;
        
        //Vê se esta mais pesado a direita-esquerda --> nesse caso será necessário rotação dupla
        if(filhoDireita.equilibrio < RegistroAVL.BALANCEADO){
            RegistroAVL filhoDireitoEsquerdo = (RegistroAVL)filhoDireita.registroEsquerda;
            //Atualiza os equilibrios
            if(filhoDireitoEsquerdo.equilibrio > RegistroAVL.BALANCEADO){
                filhoDireita.equilibrio = RegistroAVL.BALANCEADO;
                filhoDireitoEsquerdo.equilibrio = RegistroAVL.BALANCEADO;
                raiz.equilibrio = RegistroAVL.PESO_ESQUERDA;
            }else{
                filhoDireita.equilibrio = RegistroAVL.PESO_DIREITA;
                filhoDireitoEsquerdo.equilibrio = RegistroAVL.BALANCEADO;
                raiz.equilibrio = RegistroAVL.BALANCEADO;
            }
            //efetua rotação
            raiz.registroDireita = rotacaoSimplesDireita(filhoDireita);
        }else{
            filhoDireita.equilibrio = RegistroAVL.BALANCEADO;
            raiz.equilibrio = RegistroAVL.BALANCEADO;
        }
        
        return rotacaoSimplesEsquerda(raiz);
    }
    
    private void diminuiEquilibrio(RegistroAVL raiz){
        raiz.equilibrio--;
        if(raiz.equilibrio == RegistroAVL.BALANCEADO){
            aumento = false;
        }
    }
    
    private void aumentaEquilibrio(RegistroAVL raiz){
        raiz.equilibrio++;
        if(raiz.equilibrio == RegistroAVL.BALANCEADO){
            aumento = false;
        }
    }

    
    /**
     * @TODO Fazer método de remoção da árvore
     */
    public boolean remove(String... chave){
        
        diminui = false;
        retornoDaDelecao = false;
        
        RegistroAVL aux = new RegistroAVL();
        for (String key : chave) {
            aux.indices.add(key);
        }

        this.raiz = remove((RegistroAVL)this.raiz, aux);
        
        return retornoDaDelecao;
    }
    
    public RegistroAVL remove(RegistroAVL atual, RegistroAVL buscado) {
        if (atual == null) {
            retornoDaDelecao = false;
            return null;

        } else {
            
            int comparacao = buscado.comparaCom(atual);
            
            if (comparacao < 0) {
                atual.registroEsquerda = remove((RegistroAVL) atual.registroEsquerda, buscado);
                if(diminui){
                    aumentaEquilibrio(atual);
                    if(atual.equilibrio > RegistroAVL.PESO_DIREITA){
                        return reequilibraDireita(atual);
                    }
                }
                return atual;
            } else if (comparacao > 0) {
                atual.registroDireita = remove((RegistroAVL) atual.registroDireita, buscado);
                if(diminui){
                    diminuiEquilibrio(atual);
                    if(atual.equilibrio < RegistroAVL.PESO_ESQUERDA){
                        return reequilibraEsquerda(atual);
                    }
                }
                return atual;

            } else if (comparacao == 0) {
                
                retornoDaDelecao = true;
                diminui = true;
                
                //se não tem registro a esquerda, retorna o registro a direita
                if(atual.registroEsquerda == null){
                    return (RegistroAVL) atual.registroDireita;
                    
                //se não tem registro a direita, retorna o registro a esquerda
                }else if(atual.registroDireita == null){
                    return (RegistroAVL) atual.registroEsquerda;
                
                //se tem os dois filhos pego o filho mais a direita da subarvore esquerda
                }else{
                    
                    //se o filho esquerdo não tem filho direito, copia o esquerdo pra cima.
                    if(atual.registroEsquerda.registroDireita == null){
                        atual.setIndices(atual.registroEsquerda.getIndices());
                        atual.setValores(atual.registroEsquerda.getValores());
                        
                        atual.registroEsquerda.setIndices(atual.registroEsquerda.registroEsquerda.getIndices());
                        atual.registroEsquerda.setValores(atual.registroEsquerda.registroEsquerda.getValores());
                        
                        aumentaEquilibrio(atual);
                        if(atual.equilibrio > RegistroAVL.PESO_DIREITA){
                            reequilibraDireita(atual);
                        }
                        
                        return atual;
                    
                    } else {
                        
                    }
                }
                
            }
        }
    }
    

    /**
     * Transforma:...Em:........
     * .......9...........8.....
     * ....8...........5.....9..
     * ..5......................
     * 
     * @param raiz 
     * @return  Nova Raiz      
     */
    public RegistroAVL rotacaoSimplesDireita(RegistroAVL raiz){
        RegistroAVL no;
        no = (RegistroAVL)raiz.registroEsquerda;
        raiz.registroEsquerda = no.registroDireita;
        no.registroDireita = raiz;        
        return no;  
    }
    
    /**
     * Transforma:...Em:........
     * ..5................8.....
     * ....8...........5.....9..
     * .......9.................
     * 
     * @param raiz 
     * @return  Nova Raiz 
     */
    public RegistroAVL rotacaoSimplesEsquerda(RegistroAVL raiz){
        RegistroAVL no;
        no = (RegistroAVL)raiz.registroDireita;
        raiz.registroDireita = no.registroEsquerda;
        no.registroEsquerda = raiz;
               
        return no;  
    }
   
    /**
     * Transforma:...Em:........
     * .......10..........9.....
     * ....8...........8.....10.
     * .......9.................
     * 
     * @param raiz 
     * @return  Nova Raiz      
     */
    public RegistroAVL rotacaoEsquerdaDireita(RegistroAVL raiz){
        raiz.registroEsquerda = rotacaoSimplesEsquerda((RegistroAVL)raiz.registroEsquerda);
        return rotacaoSimplesDireita(raiz);  
    }
    
    /**
     * Transforma:...Em:........
     * ..8................9.....
     * ....10..........8.....10.
     * ..9......................
     * 
     * @param raiz 
     * @return  Nova Raiz       
     */
    public RegistroAVL rotacaoDireitaEsquerda(RegistroAVL raiz){
        raiz.registroDireita = rotacaoSimplesDireita((RegistroAVL)raiz.registroDireita);
        return rotacaoSimplesEsquerda(raiz);  
    }
}
