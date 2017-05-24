/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Lista;
import java.util.ArrayList;

/**
 *
 * @author felip
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        

        ArrayList<Integer> array = new ArrayList<>();

        array.add(1000);
        array.add(10000);
        array.add(100000);
        array.add(500000);
        array.add(600000);
        array.add(700000);
        array.add(800000);
        array.add(900000);
        array.add(1000000);
        array.add(2000000);

        DataBase db = null;
        Tabela tabela1 = null;
        Tabela tabela2 = null;
        
        
        for (Integer integer : array) {

            db = new DataBase();

            tabela1 = new Tabela();
            tabela1.setNome("tabela1");
            tabela1.addIndice("indice1");
            tabela1.addCampo("campo");
            db.addTabela(tabela1);

            tabela2 = new Tabela();
            tabela2.setNome("tabela2");
            tabela2.addIndice("indice2");
            tabela2.addIndice("indice1");
            tabela2.addCampo("campo");
            db.addTabela(tabela2);
            
            
            for (int i = 0; i < integer; i++) {
                RegistroAVL registroAVL1 = new RegistroAVL();
                registroAVL1.addIndice(Integer.toString(i));
                registroAVL1.addValor("Reg 1");

                db.addRegistro("tabela1", registroAVL1);
                
                RegistroAVL registroAVL2 = new RegistroAVL();
                registroAVL2.addIndice(Integer.toString(i / 50));
                registroAVL2.addIndice(Integer.toString(i));
                registroAVL2.addValor("Reg 2");

                db.addRegistro("tabela2", registroAVL2);
            }

            System.out.println("Iniciando consulta");

            long tempoInicial = System.currentTimeMillis();

            Lista<RegistroAVL[]> uniao = db.leftOuterJoinFlag(db.getTabela("tabela1"), db.getTabela("tabela2"), "indice1", "indice1");

            System.out.println(uniao.tamanho());

            System.out.println("o metodo executou em " + (System.currentTimeMillis() - tempoInicial));

        }

    }

}
