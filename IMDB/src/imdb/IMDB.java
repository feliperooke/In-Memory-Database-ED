
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import imdb.utils.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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
        db.addTabela(new Tabela("{nome:'food_des', campos:['long_desc','shrt_desc','comname','manufacname','survey','ref_desc','refuse','sciname','n_factor','pro_factor','fat_factor','cho_factor'], indices:['ndb_no','fdgrp_cd']}"));
        db.addTabela(new Tabela("{nome:'footnote', campos:['footnt_typ','nutr_no','footnt_txt'], indices:['ndb_no','footnt_no']}"));
        db.addTabela(new Tabela("{nome:'nut_data', campos:['nutr_val','num_data_pts','std_error','src_cd','deriv_cd','ref_ndb_no','add_nutr_mark','num_studies','min','max','df','low_eb','up_eb','stat_cmt','cc'], indices:['ndb_no','nutr_no']}"));
        db.addTabela(new Tabela("{nome:'nutr_def', campos:['units','tagname','nutrdesc','num_dec','sr_order'], indices:['nutr_no']}"));
        db.addTabela(new Tabela("{nome:'src_cd', campos:['srccd_desc'], indices:['src_cd']}"));
        db.addTabela(new Tabela("{nome:'weight', campos:['seq','amount','msre_desc','gm_wgt','num_data_pts','std_dev'], indices:['ndb_no']}"));

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

        String busca = "______                            \n" +
                      "| ___ \\                         _ \n" +
                      "| |_/ / _   _  ___   ___  __ _ (_)\n" +
                      "| ___ \\| | | |/ __| / __|/ _` |   \n" +
                      "| |_/ /| |_| |\\__ \\| (__| (_| | _ \n" +
                      "\\____/  \\__,_||___/ \\___|\\__,_|(_)\n" +
                      "                                  " ;
        
        System.out.println(busca);
        
        Scanner leitor = new Scanner(System.in);

        while (true) {            
            System.out.print("Digite o nome da tabela do registro a ser buscado: ");
            Tabela tabela = null;
            while (tabela == null) {
                String nomeTabela = leitor.next();
                try {
                    tabela = db.getTabela(nomeTabela);
                } catch (Exception e) {
                    System.out.println("Tabela não encontrada, tente novamente.");
                }
            }
            
            System.out.print("Digite o indice do registro a ser buscado (caso o registro seja composto por mais de um indice, separe-os por ';'): ");
            Registro registro = null;
            while (registro == null) {                
                    String indicesDoRegistro = leitor.next();
                    registro = tabela.busca(indicesDoRegistro.split(";"));
                    if(registro == null) {
                        System.out.println("Registro não encontrada, tente novamente.");
                        TreePrinter.print(tabela.arvore.raiz);
                    }
            }
            
            
            
            System.out.println("O Registro buscado foi:");
            System.out.println(registro.getJson());
            
            Lista<RegistroAVL[]> uniao = db.join(db.getTabela("data_src"), db.getTabela("datsrcln"), "datasrc_id", "datasrc_id");
            
        }
        
                
        
    }
    
}
