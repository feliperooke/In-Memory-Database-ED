
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

/**
 *
 * @author Felipe Rooke
 */
public class IMDB {
    public static void main(String[] args) {
        
        /*
        Inspiração http://lokijs.org/#/
        */
        
        
        
        DataBase db = new DataBase();
        
        System.out.println("Iniciando criação das tabelas...");
        
        /**
         * Criando tabela passando parametros para o objeto
         */
        
        Tabela data_src = new Tabela();
        //Nome
        data_src.setNome("data_src");
        //Indice
        data_src.addIndice("datasrc_id");
        //Campos
        data_src.addCampo("authors");
        data_src.addCampo("title");
        data_src.addCampo("year");
        data_src.addCampo("journal");
        data_src.addCampo("vol_city");
        data_src.addCampo("issue_state");
        data_src.addCampo("start_page");
        data_src.addCampo("end_page");
        
        db.addTabela(data_src);
        
        
        /**
         * Criando as outras tabelas com json
         */
        
        db.addTabela(new Tabela("{nome: 'datsrcln', campos:[], indices:['ndb_no','nutr_no','datasrc_id']}"));
        db.addTabela(new Tabela("{nome:'deriv_cd', campos:['derivcd_desc'], indices:['deriv_cd']}"));
        db.addTabela(new Tabela("{nome:'fd_group', campos:['fddrp_desc'], indices:['fdgrp_cd']}"));
        db.addTabela(new Tabela("{nome:'food_des', campos:['fdgrp_cd','long_desc','shrt_desc','comname','manufacname','survey','ref_desc','refuse','sciname','n_factor','pro_factor','fat_factor','cho_factor'], indices:['ndb_no']}"));
        db.addTabela(new Tabela("{nome:'footnote', campos:['footnt_typ','nutr_no','footnt_txt'], indices:['ndb_no','footnt_no']}"));
        db.addTabela(new Tabela("{nome:'nut_data', campos:['nutr_val','num_data_pts','std_error','src_cd','deriv_cd','ref_ndb_no','add_nutr_mark','num_studies','min','max','df','low_eb','up_eb','stat_cmt','cc'], indices:['ndb_no','nutr_no']}"));
        db.addTabela(new Tabela("{nome:'nutr_def', campos:['units','tagname','nutrdesc','num_dec','sr_order'], indices:['nutr_no']}"));
        db.addTabela(new Tabela("{nome:'src_cd', campos:['srccd_desc'], indices:['src_cd']}"));
        db.addTabela(new Tabela("{nome:'weight', campos:['amount','msre_desc','gm_wgt','num_data_pts','std_dev'], indices:['ndb_no','seq']}"));

        /**
         * Exemplo de como adicionar registro na tabela data_src 
         */
        
        /*
        Registro r1 = new Registro();
        r1.addIndice("D1066");
        r1.addValor("G.V. Mann");
        r1.addValor("The Health and Nutritional status of Alaskan Eskimos.");
        r1.addValor("1962");
        r1.addValor("American Journal of Clinical Nutrition");
        r1.addValor("11");
        r1.addValor("");
        r1.addValor("31");
        r1.addValor("76");
        
        db.addRegistro("data_src", r1);
        */

        
        /**
         * Exemplo de como criar registro utilizando JSON
        */
        
        /*
        Registro r3 = new Registro("{"
                + "indices:['D1073'], "
                + "valores:["
                    + "'J.P. McBride, R.A. Maclead',"
                    + "'Sodium and potassium in fish from the Canadian Pacific coast.',"
                    + "'1956',"
                    + "'Journal of the American Dietetic Association',"
                    + "'32',"
                    + "'',"
                    + "'636',"
                    + "'638']"
                + "}");
        */

        System.out.println("Tabelas criadas:");
        System.out.println(db.toString());
        
        System.out.println("Iniciando leitura do arquivo usda.sql para inserção dos registros...");

        try (BufferedReader buffRead = new BufferedReader(new FileReader("usda.sql"))) {
            String linha = "";
            int[] posicaoDosIndices = null;
            int[] posicaoDosCampos = null;
            boolean leitura = false;
            Tabela tabela = null;
            while (true) {
                if (linha != null) {
                    
                    
                    //testa se chegou em uma linha com a palavra COPY
                    if(linha.matches("COPY.*")){
                        //verifica qual tabela é e inicia a flag de leitura dos registros
                        String[] aux = linha.split("COPY ")[1].split(" \\(");
                        String nomeDaTabela = aux[0];
                        String[] campos = aux[1].split("\\)")[0].replaceAll(" |\"", "").split(",");
                        
                        tabela = db.getTabela(nomeDaTabela);
                        
                        int tamanhoIndices = tabela.getIndices().tamanho();
                        posicaoDosIndices = new int[tamanhoIndices];
                        
                        //defino em quais posições estarão os indices
                        for (int i = 0; i < tamanhoIndices; i++) {
                            for (int j = 0; j < campos.length; j++) {
                                if(tabela.getIndices().get(i).equals(campos[j])){
                                    //se achei a posição do indice no array de campos, armazeno a posição dele
                                    posicaoDosIndices[i] = j;
                                    break;
                                }
                            }
                        }
                        
                        int tamanhoCampos = tabela.getCampos().tamanho();
                        posicaoDosCampos = new int[tamanhoCampos];
                        
                        //defino em quais posições estarão os campos
                        for (int i = 0; i < tamanhoCampos; i++) {
                            for (int j = 0; j < campos.length; j++) {
                                if(tabela.getCampos().get(i).equals(campos[j])){
                                    //se achei a posição do indice no array de campos, armazeno a posição dele
                                    posicaoDosCampos[i] = j;
                                    break;
                                }
                            }
                        }
                        
                        leitura = true;
                        
                        System.out.print("\nInserindo registros na tabela: "+nomeDaTabela);
                        
                    }else if(linha.matches("\\\\.")){
                        //termina leitura
                        leitura = false;
                        
                        posicaoDosIndices = null;
                        posicaoDosCampos = null;
                        leitura = false;
                        tabela = null;
                        
                        System.out.println("\t................ OK");
                    }else if(leitura){
                        
                        //pega os valores
                        String[] valores = linha.split("\t",-1);
                        
                        RegistroAVL r = new RegistroAVL();
                        
                        for (int i = 0; i < posicaoDosIndices.length; i++) {
                            r.addIndice(valores[posicaoDosIndices[i]].trim());
                        }
                        
                        for (int i = 0; i < posicaoDosCampos.length; i++) {
                            r.addValor(valores[posicaoDosCampos[i]].trim());
                        }
                        
                        try{
                            tabela.add(r);
                        } catch (Exception e) {
                            System.out.println("Problema ao adicionar registro");
                        }
                        
                        
                        
                    }
                    
                    
                    

                } else {
                    break;
                }
                linha = buffRead.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        String busca = "______                            \n" +
//                      "| ___ \\                         _ \n" +
//                      "| |_/ / _   _  ___   ___  __ _ (_)\n" +
//                      "| ___ \\| | | |/ __| / __|/ _` |   \n" +
//                      "| |_/ /| |_| |\\__ \\| (__| (_| | _ \n" +
//                      "\\____/  \\__,_||___/ \\___|\\__,_|(_)\n" +
//                      "                                  " ;
        
        String menu = "___  ___                 \n" +
        "|  \\/  |                 \n" +
        "| .  . | ___ _ __  _   _ \n" +
        "| |\\/| |/ _ \\ '_ \\| | | |\n" +
        "| |  | |  __/ | | | |_| |\n" +
        "\\_|  |_/\\___|_| |_|\\__,_|\n" +
        "                         \n";

        menu += "Escolha uma opção: \n\n"+
        "1 - Inserir Registro \n" +
        "2 - Buscar Registro \n"+
        "3 - Apagar Registro \n"+
        "4 - Join \n"+
        "5 - Select \n";
        
        
        
        
        Scanner leitor = new Scanner(System.in);

        while (true) {
            System.out.println(menu);
        
            System.out.print("Digite a opção:");
            
            String menuPrincipal = leitor.next();
            leitor.nextLine();
            Tabela tabela = null;
            Tabela tabela2 = null;
            
            switch (menuPrincipal) {
                case "1":
                    System.out.print("Digite o nome da tabela do registro a ser inserido: ");
                    while (tabela == null) {
                        String nomeTabela = leitor.next();
                        leitor.nextLine();
                        try {
                            tabela = db.getTabela(nomeTabela);
                        } catch (Exception e) {
                            System.out.println("Tabela não encontrada, tente novamente.");
                        }
                    }
                    
                    System.out.println("\n Insira os valores dos campos da tabela separando-os por \";\" respeitando a seguinte estrutura: \n"
                            +tabela.getJson() + "\n");
                    System.out.print("Insira os Indices:");
                    String[] indices = leitor.nextLine().split(";");
                    
                    if(indices.length == tabela.getIndices().tamanho()){
                        RegistroAVL registroAVL = new RegistroAVL();
                        for (String indice : indices) {
                            registroAVL.addIndice(indice);
                        }
                        
                        System.out.print("\nInsira os Campos:");
                        String[] campos = leitor.nextLine().split(";");
                        if(campos.length == tabela.getCampos().tamanho()){
                            for (String campo : campos) {
                                registroAVL.addValor(campo);
                            }
                        }else{
                            System.out.println("\nOps... faltaram campos. Não foi possível inserir.\n");
                        }
                        
                        tabela.add(registroAVL);
                    }else{
                        System.out.println("\nOps... faltaram índices. Não foi possível inserir.\n");
                    }

                    break;
                case "2":
                    System.out.print("Digite o nome da tabela do registro a ser buscado: ");
                    while (tabela == null) {
                        String nomeTabela = leitor.next();
                        leitor.nextLine();
                        try {
                            tabela = db.getTabela(nomeTabela);
                        } catch (Exception e) {
                            System.out.println("Tabela não encontrada, tente novamente.");
                        }
                    }
                    
                    System.out.print("Digite o indice do registro a ser buscado (caso o registro seja composto por mais de um indice, separe-os por ';'): ");
                    Registro registroBusca = null;
                    while (registroBusca == null) {                
                            String indicesDoRegistro = leitor.next();
                            leitor.nextLine();
                            registroBusca = tabela.busca(indicesDoRegistro.split(";"));
                            if(registroBusca == null) {
                                System.out.println("Registro não encontrada, tente novamente.");
                            }
                    }

                    System.out.println("O Registro buscado foi:");
                    System.out.println(registroBusca.getJson());
                    
                    break;
                case "3":
                    System.out.print("Digite o nome da tabela do registro a ser apagado: ");
                    while (tabela == null) {
                        String nomeTabela = leitor.next();
                        leitor.nextLine();
                        try {
                            tabela = db.getTabela(nomeTabela);
                        } catch (Exception e) {
                            System.out.println("Tabela não encontrada, tente novamente.");
                        }
                    }
                    
                    System.out.print("Digite o indice do registro a ser apagado (caso o registro seja composto por mais de um indice, separe-os por ';'): ");
                    Registro registroApaga = null;
                    while (registroApaga == null){                
                        String indicesDoRegistro = leitor.next();
                        leitor.nextLine();
                        registroApaga = tabela.busca(indicesDoRegistro.split(";"));
                        if(registroApaga == null) {
                            System.out.println("Registro não encontrada, tente novamente.");
                        }else{
                            String msgApaga = registroApaga.getJson();
                            boolean apagou = tabela.remove(indicesDoRegistro.split(";"));
                            if(apagou) 
                                System.out.println("Registro: \n"+msgApaga+"\nApagado com Sucesso!");
                        }
                    }
                    
                    break;
                case "4":
                    String menuJoin = "Escolha uma opção: \n\n"+
                    "1 - Inner Join \n" +
                    "2 - Left Join \n"+
                    "3 - Right Join \n";
                    
                    System.out.println(menuJoin);
                    
                    String chave = null;
                    
                    String opcaoJoin = leitor.next();
                    leitor.nextLine();
                    
                    switch (opcaoJoin){
                        case "1":
                            System.out.println("SELECT * FROM <Tabela com apenas 1 chave primária> \n"
                                    + "INNER JOIN <Tabela com chave estrangeira> \n"
                                    + "ON (tabela1.<Nome da Chave> = tabela2.<Nome da Chave>)\n");
                            
                            System.out.print("Informe o nome da <Tabela que contém apenas 1 chave primária>:");
                            while (tabela == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela = db.getTabela(nomeTabela);
                                    if(tabela.getIndices().tamanho() != 1){
                                        System.out.println("Essa tabela contem mais de um índice. Tente outra.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            
                            System.out.print("Informe o nome da <Tabela que contém a chave estrangeira>:");
                            while (tabela2 == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela2 = db.getTabela(nomeTabela);
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            System.out.print("Informe o <Nome da Chave>:");
                            chave = null;
                            while (chave == null) {
                                chave = leitor.next();
                                leitor.nextLine();
                                try {
                                    if(!tabela2.getIndices().contem(chave)){
                                        chave = null;
                                        throw new IllegalArgumentException();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Chave não encontrada, tente outra:");
                                }
                            }
                            
                            Lista<RegistroAVL[]> inner = db.innerJoin(tabela, tabela2, chave, chave);
                            
                            System.out.println("\n O resultado da consulta apresentou "+ inner.tamanho()+" registros.");
                            
                            break;
                        case "2":
                            System.out.println("SELECT * FROM <Tabela com apenas 1 chave primária> \n"
                                    + "LEFT OUTER JOIN <Tabela com chave estrangeira> \n"
                                    + "ON (tabela1.<Nome da Chave> = tabela2.<Nome da Chave>)\n");
                            
                            System.out.print("Informe o nome da <Tabela que contém apenas 1 chave primária>:");
                            while (tabela == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela = db.getTabela(nomeTabela);
                                    if(tabela.getIndices().tamanho() != 1){
                                        System.out.println("Essa tabela contem mais de um índice. Tente outra.");
                                        tabela = null;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            
                            System.out.print("Informe o nome da <Tabela que contém a chave estrangeira>:");
                            while (tabela2 == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela2 = db.getTabela(nomeTabela);
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            System.out.print("Informe o <Nome da Chave>:");
                            chave = null;
                            while (chave == null) {
                                chave = leitor.next();
                                leitor.nextLine();
                                try {
                                    if(!tabela2.getIndices().contem(chave)){
                                        chave = null;
                                        throw new IllegalArgumentException();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Chave não encontrada, tente outra:");
                                }
                            }
                            
                            Lista<RegistroAVL[]> left = db.leftOuterJoinFlag(tabela, tabela2, chave, chave);
                            
                            System.out.println("\n O resultado da consulta apresentou "+ left.tamanho()+" registros.");
                            
                            break;
                        case "3":
                            System.out.println("SELECT * FROM <Tabela com apenas 1 chave primária> \n"
                                    + "RIGHT OUTER JOIN <Tabela com chave estrangeira> \n"
                                    + "ON (tabela1.<Nome da Chave> = tabela2.<Nome da Chave>)\n");
                            
                            System.out.print("Informe o nome da <Tabela que contém apenas 1 chave primária>:");
                            while (tabela == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela = db.getTabela(nomeTabela);
                                    if(tabela.getIndices().tamanho() != 1){
                                        System.out.println("Essa tabela contem mais de um índice. Tente outra.");
                                        tabela = null;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            
                            System.out.print("Informe o nome da <Tabela que contém a chave estrangeira>:");
                            while (tabela2 == null) {
                                String nomeTabela = leitor.next();
                                leitor.nextLine();
                                try {
                                    tabela2 = db.getTabela(nomeTabela);
                                } catch (Exception e) {
                                    System.out.println("Tabela não encontrada, tente novamente:");
                                }
                            }
                            System.out.print("Informe o <Nome da Chave>:");
                            chave = null;
                            while (chave == null) {
                                chave = leitor.next();
                                leitor.nextLine();
                                try {
                                    if(!tabela2.getIndices().contem(chave)){
                                        chave = null;
                                        throw new IllegalArgumentException();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Chave não encontrada, tente outra:");
                                }
                            }
                            
                            Lista<RegistroAVL[]> right = db.rightOuterJoin(tabela, tabela2, chave, chave);
                            
                            System.out.println("\n O resultado da consulta apresentou "+ right.tamanho()+" registros.");
                            break;
                        default:
                            System.out.println("Ops... Opção inválida, retornando ao menu principal...");
                            break;
                    }
                    
                    break;
                case "5":
                  
                    System.out.println("SELECT COUNT(*) FROM <Tabela> WHERE <campo 1 = valor 1> AND <campo 2 = valor 2> AND ...\n");

                    String restricaoValor = null;
                    
                    System.out.print("Informe o nome da <Tabela>:");
                    while (tabela == null) {
                        String nomeTabela = leitor.next();
                        leitor.nextLine();
                        try {
                            tabela = db.getTabela(nomeTabela);
                        } catch (Exception e) {
                            System.out.println("Tabela não encontrada, tente novamente:");
                        }
                    }

                    System.out.print("Informe as restrições separando-as por \";\" da seguinte forma: campo1 = valor1; campo2 = valor2\n"
                            + "(Caso queira executar a consulta sem restrições informe: \"-\" ): ");
                    while (restricaoValor == null) {
                        restricaoValor = leitor.nextLine();
                        if(!restricaoValor.equals("-")){
                            String[][]restricoes = null;
                            try {
                                String[] aux = restricaoValor.trim().split(";");
                                restricoes = new String[aux.length][2];

                                for (int i=0; i < aux.length; i++) {
                                    String[] aux2 = aux[i].trim().split("=");
                                    restricoes[i][0] = aux2[0].trim();
                                    restricoes[i][1] = aux2[1].trim();
                                }
                                
                                Lista<RegistroAVL> select = db.select(tabela, restricoes);
                                System.out.println("\n O resultado da consulta apresentou " + select.tamanho() + " registros.");
                                
                                if(select.tamanho()==1){
                                    System.out.println(select.primeira.getValor().getJson());
                                }
                                
                            } catch (Exception e) {
                                System.out.println("Ops... parece que não existe algum campo da restrição, tente novamente:");
                                restricaoValor = null;
                            }
                        }else{
                             Lista<RegistroAVL> select = db.select(tabela, null);
                            
                            System.out.println("\n O resultado da consulta apresentou " + select.tamanho() + " registros.");
                        }
                    }
                    
                   

                    break;
                default:
                    System.out.println("Ops... Opção inválida, retornando ao menu principal...");
                    break;
            }
            
            
            
            
            
            //Lista<RegistroAVL[]> uniao = db.rightOuterJoin(db.getTabela("fd_group"), db.getTabela("food_des"), "fdgrp_cd", "fdgrp_cd");
            //Lista<RegistroAVL[]> uniao = db.leftOuterJoin(db.getTabela("fd_group"), db.getTabela("food_des"), "fdgrp_cd", "fdgrp_cd");
            
            //String[] restricao1 = {"year","1980"};
            //String[] restricao2 = {"vol_city","65"};
            
            //Lista<RegistroAVL> select = db.select(db.getTabela("data_src"), restricao1);

//            for (int i = 0; i < select.tamanho(); i++) {
//                System.out.println(select.get(i).getJson());
//            }
//            
//            for (int i = 0; i < uniao.tamanho(); i++) {
//                String primeiro = "null";
//                String segundo = "null";
//                if(uniao.get(i)[0] != null)
//                    primeiro = uniao.get(i)[0].getJson();
//                if(uniao.get(i)[1] != null)
//                    segundo = uniao.get(i)[1].getJson();
//                System.out.println(primeiro+"--->"+segundo);
//            }
            
//            break;
              System.out.println("\n\n\n");
        }
        
                
        
    }
    
}
