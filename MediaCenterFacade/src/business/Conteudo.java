package business;

/** 
 * Implementação da classe Conteúdo
 * Grupo 12 
 * 2019/2020
 */
public class Conteudo {
    String id;
    String id_U;
    double duracao;
    int tipo;
    String path;
    String categoria;

    /** 
     * Construtor vazio
     */
    public Conteudo(){
        id = "0";
        id_U = null;
        duracao = 0.0;
        tipo = 5;
        path = "nPath";
        categoria = "nCategoria";
    }
    
    /** 
     * Construtor parametrizado
     * @param nId
     * @param nIdU
     * @param nDur
     * @param nTipo
     * @param nPath
     * @param nCategoria 
     */
    public Conteudo(String nId, String nIdU, double nDur, int nTipo, String nPath,String nCategoria ){
        id = nId;
        id_U = null;
        duracao = nDur;
        tipo = nTipo;
        path = nPath;
        categoria = nCategoria;
    }
    
    /**
     * atribui um valor ao ID do conteúdo 
     * @param nId 
     */
    public void setID(String nId){
        id = nId; 
    }
    
    /**
     * atribui um valor à duração do conteúdo 
     * @param nDuracao 
     */
    public void setDuracao(double nDuracao){
        duracao = nDuracao;
    }
    
    /** 
     * atribui um tipo ao conteúdo 
     * 0, caso se trate de um vídeo
     * 1, caso se trate de uma música
     * @param nTipo 
     */
    public void setTipo(int nTipo){
        tipo = nTipo;
    }
    
    /** 
     * atribui um valor à categoria do conteúdo 
     * @param nCategoria 
     */
    public void setCategoria(String nCategoria){
        categoria = nCategoria;
    }

    /**
     * atribui um valor ao path do conteúdo
     * @param nPath 
     */
    public void setPath(String nPath){
        path = nPath;
    }
    
    /** 
     * devolve o ID do conteúdo 
     * @return id
     */
    public String getIdConteudo(){
        return id;
    }
    
    /**
     * devolve o ID do convidado 
     * @return id_U
     */
    public String getIdConvidado(){
        return id_U;
    }
    
    /**
     * devolve a duração do conteúdo
     * @return duração
     */
    public double getDuracao(){
        return duracao;
    }
    
    /**
     * devolve o tipo do conteúdo 
     * @return tipo
     */
    public int getTipo(){
        return tipo;
    }

    /** 
     * devolve o path do conteúdo
     * @return path
     */
    public String getPath(){
        return path;
    }
    
    /** 
     * devolve a categoria do conteúdo
     * @return categoria
     */
    public String getCategoria(){
        return categoria;
    }
    
}
