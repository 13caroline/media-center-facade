package business;

/** 
 * Implementação da classe Utilizador
 * Grupo 12
 * 2019/2020
 */
public class Utilizador {
    String email;
    int tipo;

    /**
     * Construtor parametrizado
     * @param nEmail
     * @param nTipo 
     */
    public Utilizador(String nEmail, int nTipo){
        email = nEmail;
        tipo = nTipo;
    }
    
    /** 
     * devolve o email do utilizador
     * @return email
     */
    public String getEmail(){
        return this.email;
    }

    /** 
     * atribui um valor ao email do utilizador
     * @param novoEmail 
     */
    public void setEmail(String novoEmail){
        this.email = novoEmail;
    }

    /** 
     * devolve o tipo do utilizador 
     * 0, se residente 
     * 1, se convidado 
     * 2, se administrador
     * @return tipo
     */
    public int getTipo(){
        return this.tipo;
    }

    /** 
     * atribui um tipo a um utilizador
     * @param novoTipo 
     */
    public void setTipo(int novoTipo){
        this.tipo = novoTipo;
    }
}
