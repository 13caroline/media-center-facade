package business;

import data.Lista_ReproducaoDAO;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/** 
 * Implementação da classe Utilizador_Residente
 * Utilizador que tem acesso a todas as funcionalidades da aplicação, com exceção das funcionalidades administrativas
 * Grupo 12
 * 2019/2020
 */
public class Utilizador_Residente extends Utilizador{
    String nome;
    String palavrapasse;
    Lista_ReproducaoDAO listas;
    List<Utilizador_Residente> amigos;
    List<Utilizador_Residente> sugestoes;
    List<Utilizador_Residente> pedidos;
    Colecao colecao;

    /** 
     * Construtor vazio
     */
    public Utilizador_Residente() {
        super(" ", 0);
    }
   
    /** 
     * Construtor parametrizado
     * @param nEmail
     * @param nNome 
     */
    public Utilizador_Residente(String nEmail, String nNome){
        super(nEmail,0);
        nome = nNome;
        palavrapasse = " ";
        listas = new Lista_ReproducaoDAO();
        amigos = new ArrayList<Utilizador_Residente>();
        sugestoes = new ArrayList<Utilizador_Residente>();
        pedidos = new ArrayList<Utilizador_Residente>();
        colecao = new Colecao();
        tipo = 0;
    }

    /** 
     * Construtor parametrizado
     * @param nEmail
     * @param nNome
     * @param nPasse 
     */
    public Utilizador_Residente(String nEmail, String nNome, String nPasse){
        super(nEmail,0);
        nome = nNome;
        palavrapasse = nPasse;
        listas = new Lista_ReproducaoDAO();
        amigos = new ArrayList<Utilizador_Residente>();
        sugestoes = new ArrayList<Utilizador_Residente>();
        pedidos = new ArrayList<Utilizador_Residente>();
        colecao = new Colecao();
        tipo = 0;
    }

    /** 
     * devolve o nome do utilizador
     * @return nome
     */
    public String getNome(){
        return nome;
    }

    /** 
     * devolve a coleção do utilizador
     * @return colecao
     */
    public Colecao getColecao(){
        return colecao;
    }

    /** 
     * devolve a palavra-passe do utilizador
     * @return palavra-passe
     */
    public String getPalavra(){
        return palavrapasse;
    }
 
    /** 
     * devolve as listas do utilizador
     * @return listaDAO
     */
    public Lista_ReproducaoDAO getListas(){
        return listas;
    }
    
    /** 
     * atribui uma nova palavra-passe ao utilizador
     * @param novapasse 
     */
    public void setPalavra(String novapasse){
        palavrapasse = novapasse;
    }

    /** 
     * verifica se a palavra-passe do utilizador é válida
     * @param password
     * @return true, se a palavra-passe é válida;
     *         false, caso contrário
     */
    public boolean validaPalavra(String password){
        return palavrapasse.equals(password);
    }
}

