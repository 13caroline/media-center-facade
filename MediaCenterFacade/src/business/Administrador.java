package business;

/** 
 * Implementação da classe Administrador
 * Grupo 12 
 * 2019/2020
 */
public class Administrador extends Utilizador{
    String nome;
    String palavrapasse;

    /** 
     * Construtor parametrizado 
     * @param nNome
     * @param nEmail
     * @param nPasseword 
     */
    public Administrador(String nNome, String nEmail, String nPasseword){
        super(nEmail,2);
        nome = nNome;
        email = nEmail;
        palavrapasse = nPasseword;
    }

    /** 
     * valida a palavra-passe de um administrador 
     * @param password
     * @return true se a palavra-passe estiver correta;
     *         false caso contrário
     */
    public boolean validaPalavra(String password){
        return palavrapasse.equals(password);
    }

    /** 
     * devolve o nome do administrador
     * @return nome
     */
    public String getNome(){
        return nome;
    }

    /** 
     * devolve a palavra-passe do administrador
     * @return palavra-passe
     */
    public String getPalavra(){
        return palavrapasse;
    }
}