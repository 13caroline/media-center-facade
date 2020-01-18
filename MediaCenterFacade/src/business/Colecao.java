package business;

import data.ConteudoDAO;

/** 
 * Implementação da classe Colecao
 * Classe onde são armazenados os conteúdos de um utilizador residente
 * Grupo 12 
 * 2019/2020
 */

public class Colecao {
    ConteudoDAO conteudos;

    /**
     * Construtor vazio
     */
    public Colecao(){
        conteudos = new ConteudoDAO();
    }

    /** 
     * devolve os conteudos
     * @return conteudoDAO
     */
    public ConteudoDAO getConteudo(){
        return conteudos;
    }
}
