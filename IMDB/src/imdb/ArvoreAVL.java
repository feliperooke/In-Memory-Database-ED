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
public class ArvoreAVL {
     RegistroAVL raiz = null;
     
     //Flag utilizada para indicar se altura da arvore aumentou
     private boolean aumento;
     private boolean retornoDaInclusao;

    public boolean add(RegistroAVL registro) {
        aumento = true;
        raiz = add(this.raiz, registro);
        return retornoDaInclusao;
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
            raiz.registroEsquerda = add(raiz.registroEsquerda, novo);
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
            raiz.registroDireita = add(raiz.registroDireita, novo);
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
        RegistroAVL filhoEsquerdo = raiz.registroEsquerda;
        
        //Vê se esta mais pesado a esquerda-direita --> nesse caso será necessário rotação dupla
        if(filhoEsquerdo.equilibrio > RegistroAVL.BALANCEADO){
            RegistroAVL filhoEsquerdoDireito = filhoEsquerdo.registroDireita;
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
        RegistroAVL filhoDireita = raiz.registroDireita;
        
        //Vê se esta mais pesado a direita-esquerda --> nesse caso será necessário rotação dupla
        if(filhoDireita.equilibrio < RegistroAVL.BALANCEADO){
            RegistroAVL filhoDireitoEsquerdo = filhoDireita.registroEsquerda;
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

    private void addNaoRecursivo(RegistroAVL raiz, RegistroAVL novo) {

        //se vazio 
        if (raiz == null) {
            raiz = novo;
        } else {
            RegistroAVL aux = raiz;
            while (true) {
                //verifica se vai adicionar a direita
                if (aux.comparaCom(novo) < 0) {
                    //verifica se aux tem elemento a direita, se tem, tenho que continuar caminhando
                    if (aux.registroDireita != null) {
                        aux = aux.registroDireita;
                        //se não tem, é aqui que vou adicionar    
                    } else {
                        aux.registroDireita = novo;

                        System.out.println("registro: " + novo.indices.get(0) + " direita");

                        break;

                    }
                    //se não é a direita é a esquerda
                } else //verifica se aux tem elemento a esquerda, se tem, tenho que continuar caminhando
                if (aux.registroEsquerda != null) {
                    aux = aux.registroEsquerda;
                    //se não tem, é aqui que vou adicionar    
                } else {
                    aux.registroEsquerda = novo;

                    System.out.println("registro: " + novo.indices.get(0) + " esquerda");

                    break;
                }

            }

        }

    }

    /**
     * @TODO Fazer método de remoção da árvore
     * @TODO Testar BUSCA
     */
    public RegistroAVL busca(String... chave) {

        RegistroAVL aux = new RegistroAVL();
        for (String key : chave) {
            aux.indices.add(key);
        }

        return busca(raiz, aux);
    }

    private RegistroAVL busca(RegistroAVL raiz, RegistroAVL registroAux) {
        //se vazio 
        if (raiz == null) {
            return null;
        } else {
            int comparacao = raiz.comparaCom(registroAux);
            if (comparacao == 0) {
                return raiz;
            } else if (comparacao < 0) {
                //verifica se raiz tem elemento a direita
                return busca(raiz.registroDireita, registroAux);
            } else {
                //se tem chama a busca novamente
                return busca(raiz.registroEsquerda, registroAux);
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
        no = raiz.registroEsquerda;
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
        no = raiz.registroDireita;
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
        raiz.registroEsquerda = rotacaoSimplesEsquerda(raiz.registroEsquerda);
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
        raiz.registroDireita = rotacaoSimplesDireita(raiz.registroDireita);
        return rotacaoSimplesEsquerda(raiz);  
    }
}
