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
public class ArvoreBinaria {

    Registro raiz = null;

    public void add(Registro registro) {

        /**
         * TODO Tratar restrições do registro de acordo com os campos da tabela
         */
        addNaoRecursivo(this.raiz, registro);
    }

    private void add(Registro raiz, Registro novo) {
        //se vazio 
        if (raiz == null) {
            raiz = novo;
        } else //verifica se vai adicionar a direita
        if (raiz.comparaCom(novo) < 0) {
            //verifica se raiz tem elemento a direita
            if (raiz.registroDireita != null) {
                //se tem chama o add novamente
                add(raiz.registroDireita, novo);
            } else {
                //se está vazio
                raiz.registroDireita = novo;
            }
        } else if (raiz.registroEsquerda != null) {
            //se tem chama o add novamente
            add(raiz.registroEsquerda, novo);
        } else {
            //se está vazio
            raiz.registroEsquerda = novo;
        }
    }

    private void addNaoRecursivo(Registro raiz, Registro novo) {

        //se vazio 
        if (raiz == null) {
            raiz = novo;
        } else {
            Registro aux = raiz;
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
    public Registro busca(String... chave) {

        Registro aux = new Registro();
        for (String key : chave) {
            aux.indices.add(key);
        }

        return busca(raiz, aux);
    }

    private Registro busca(Registro raiz, Registro registroAux) {
        //se vazio 
        if (raiz == null) {
            return null;
        } else {
            int comparacao = raiz.comparaCom(registroAux);
            if (comparacao == 0) {
                return raiz;
            } else if (comparacao < 0) {
                //verifica se raiz tem elemento a direita
                if (raiz.registroDireita != null) {
                    //se tem chama a busca novamente
                    busca(raiz.registroDireita, registroAux);
                } else {
                    //se está vazio, quer dizer que o elemento não está na árvore
                    return null;
                }
                //verifica se raiz tem elemento a esquerda    
            } else if (raiz.registroEsquerda != null) {
                //se tem chama a busca novamente
                add(raiz.registroEsquerda, registroAux);
            } else {
                //se está vazio, quer dizer que o elemento não está na árvore
                return null;
            }
        }

        return null;
    }

}
