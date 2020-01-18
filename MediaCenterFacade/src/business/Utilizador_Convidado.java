package business;

/** 
 * Implementação da classe Utilizador_Convidado
 * Utilizador que apenas tem acesso à biblioteca da aplicação
 * Grupo 12 
 * 2019/2020
 */
public class Utilizador_Convidado extends Utilizador{
    
    /** 
     * Construtor parametrizado
     * @param nEmail 
     */
    public Utilizador_Convidado(String nEmail){
        super(nEmail,1);
    }
}

