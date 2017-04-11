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

    public Lista<Tabela> getTabelas() {
        return tabelas;
    }

    public void setTabelas(Lista<Tabela> tabelas) {
        this.tabelas = tabelas;
    }
}
