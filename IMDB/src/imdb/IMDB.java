
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imdb;

import jdk.nashorn.internal.parser.JSONParser
;
import org.json.JSONObject;
/**
 *
 * @author felip
 */
public class IMDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*
        Inspiração http://lokijs.org/#/
        */
        
        DataBase db = new DataBase();
        
        /**
         * Criando tabela passando parametros para o objeto
         */
        
        Tabela data_src = new Tabela();
        data_src.setNome("data_src");
        data_src.addCampo("datasrc_id");
        data_src.addCampo("authors");
        data_src.addCampo("title");
        data_src.addCampo("year");
        data_src.addCampo("journal");
        data_src.addCampo("vol_city");
        data_src.addCampo("issue_state");
        data_src.addCampo("start_page");
        data_src.addCampo("end_page");
        data_src.addIndice("datasrc_id");
        
        db.addTabela(data_src);

        /**
         * Criando tabelas com json
         */
        
        db.addTabela(new Tabela("{nome: 'datsrcln', campos:['ndb_no','nutr_no','datasrc_id'], indices:['ndb_no','nutr_no','datasrc_id']}"));
        db.addTabela(new Tabela("{nome:'deriv_cd', campos:['deriv_cd','derivcd_desc'], indices:['deriv_cd']}"));
        db.addTabela(new Tabela("{nome:'fd_group', campos:['fdgrp_cd','fddrp_desc'], indices:['fdgrp_cd']}"));
        db.addTabela(new Tabela("{nome:'footnote', campos:['ndb_no','footnt_no','footnt_typ','nutr_no','footnt_txt'], indices:['ndb_no','footnt_no']}"));
        db.addTabela(new Tabela("{nome:'nut_data', campos:['ndb_no','nutr_no','nutr_val','num_data_pts','std_error','src_cd','deriv_cd','ref_ndb_no','add_nutr_mark','num_studies','min','max','df','low_eb','up_eb','stat_cmt','cc'], indices:['ndb_no','nutr_no']}"));
        db.addTabela(new Tabela("{nome:'nutr_def', campos:['nutr_no','units','tagname','nutrdesc','num_dec','sr_order'], indices:['nutr_no']}"));
        db.addTabela(new Tabela("{nome:'src_cd', campos:['src_cd','srccd_desc'], indices:['src_cd']}"));
        db.addTabela(new Tabela("{nome:'weight', campos:['ndb_no','seq','amount','msre_desc','gm_wgt','num_data_pts','std_dev'], indices:['ndb_no']}"));

    
        
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
        

        
        Registro r2 = new Registro();
        r2.addIndice("D1073");
        r2.addValor("J.P. McBride, R.A. Maclead");
        r2.addValor("Sodium and potassium in fish from the Canadian Pacific coast.");
        r2.addValor("1956");
        r2.addValor("Journal of the American Dietetic Association");
        r2.addValor("32");
        r2.addValor("");
        r2.addValor("636");
        r2.addValor("638");
        
        db.addRegistro("data_src", r2);
        
        
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
        

        /**
         * TODO fazer leitura de arquivo contendo dados para carregamento
         */
        
        System.out.println(db.toString());

        
        
    }
    
}
