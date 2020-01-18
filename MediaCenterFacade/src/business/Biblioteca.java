package business;

import data.ConteudoDAO;
import java.util.*;
import static java.util.stream.Collectors.toList;

/** 
 * Implementação da classe Biblioteca
 * Classe onde são armazenados os conteúdos da aplicação 
 * Grupo 12 
 * 2019/2020
 */
public class Biblioteca {
    ConteudoDAO conteudos;

    /** 
     * Construtor vazio
     */
    public Biblioteca(){
        conteudos = new ConteudoDAO();
    }

    /**
     * verifica se um dado conteúdo se encontra na base de dados
     * @param conteudo
     * @return true se o conteúdo se encontra na base de dados;
     *         false caso contrário
     */
    public boolean contains(Conteudo conteudo){
        return conteudos.containsKey(conteudo.getIdConteudo());
    }

    /** 
     * adiciona um conteúdo à base de dados
     * @param conteudo 
     */
    public void addConteudo(Conteudo conteudo){
        conteudos.put(conteudo.getIdConteudo(),conteudo);
    }
    
    /** 
     * devolve uma lista com os conteúdos da base de dados
     * @return lista de conteúdos
     */
    public List<Conteudo> getConteudo(){
        List<Conteudo> l = conteudos.values().stream().collect(toList());
        
        return l;
    }
    
    /** 
     * devolve o conteúdo com o nome especificado 
     * @param key
     * @return conteudo
     */
    public Conteudo getCont(String key){
        return conteudos.get(key);
    }
    
}
