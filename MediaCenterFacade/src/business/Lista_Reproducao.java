package business;

import data.ConteudoDAO;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Implementação da classe Lista de Reprodução
 * Grupo 12
 * 2019/2020
 */
public class Lista_Reproducao{
    String id;
    String idUtilizador;
    ConteudoDAO conteudos;
    Map<Integer,String> novaCategoria;

    /** 
     * Construtor parametrizado
     * @param nId
     * @param nIdU
     * @param nNome 
     */
    public Lista_Reproducao(String nId, String nIdU){
        id = nId;
        idUtilizador = nIdU;
        conteudos = new ConteudoDAO();
        novaCategoria = new HashMap<Integer,String>();
    }

    /** 
     * devolve o ID da lista
     * @return id
     */
    public String getIdLista(){
        return id;
    }

    /** 
     * devolve o utilizador a quem pertence a lista
     * @return idUtilizador
     */
    public String getIdUtilizador(){
        return idUtilizador;
    }

    /** 
     * devolve os conteúdos da lista
     * @return conteudoDAO
     */
    public ConteudoDAO getConteudos(){
        return conteudos;
    }

}
