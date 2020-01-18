package business;

import data.UtilizadorDAO;
import java.io.IOException;
import java.util.*;
import presentation.*;

/** 
 * Implementação do MediaCenterFacade
 * Grupo 12 
 * 2019/2020
 */

public class MediaCenterFacade {
    private static transient Scanner input = new Scanner(System.in);
    private static UtilizadorDAO utilizadores;
    private static Biblioteca biblioteca;
    private static String defaultPath;
    private static Process runner;

    public MediaCenterFacade(){
        utilizadores = new UtilizadorDAO();
        biblioteca = new Biblioteca();
        defaultPath = "";
    }

    public void setPath(String nPath){
        defaultPath = nPath;
    }

    public UtilizadorDAO getUsers(){
        return utilizadores;
    }

    /**
     * valida os dados de um utilizador residente aquando da sua autenticação
     * @param email
     * @param password
     * @return true se os dados são válidos;
     *         false caso contrário
     */
    public static boolean validaDados(String email, String password){
        if (utilizadores.containsKey(email) == true){
            Utilizador_Residente u;
            u = (Utilizador_Residente)utilizadores.get(email);
            if (u.getPalavra() == null){
                u.setPalavra(password);
                utilizadores.put(email,u);
            }
            else if(u.validaPalavra(password) == false) 
                    return false;
            
            return true;
        }
        else return false;
    }

    /**
     * valida os dados do administrador aquando da sua autenticação
     * @param email
     * @param password
     * @return true se os dados são válidos;
     *         false caso contrário
     */
    public boolean validaDadosAdmin(String email, String password){
        return ((Administrador)utilizadores.get(email)).validaPalavra(password);
    }

    /** 
     * gera um utilizador convidado aquando da sua entrada na aplicação
     * @return utilizador convidado
     */
    public Utilizador_Convidado geraConvidado(){
        Utilizador_Convidado convidado = new Utilizador_Convidado("convidado@tmp.com");
        utilizadores.put(convidado.getEmail(),convidado);
        
       return convidado;
    }

    /** 
     * destrói o utilizador convidado após término de sessão
     * @param convidado 
     */
    public void destroiConvidado(Utilizador_Convidado convidado){
        utilizadores.remove(convidado.getEmail());
    }

    /** 
     * adiciona um utilizador residente ao sistema
     * @param nome
     * @param email
     * @return true se foi possível a inserção do novo utilizador;
     *         false caso contrário
     */
    public boolean addUtilizador(String nome, String email){
        if(!utilizadores.containsKey(email)){
            Utilizador_Residente user = new Utilizador_Residente(email, nome);
            utilizadores.put(email,user);
            return true;
        }
        
        return false;
    }
    
    /** 
     * remove um utilizador residente
     * @param email
     * @return true se foi possível a remoção do utilizador;
     *         false caso contrário
     */
    public boolean removeUtilizador(String email){
        if(utilizadores.containsKey(email)){
            utilizadores.remove(email);
            return true;
        }
       
        return false;
    }

    /** 
     * altera a palavra-passe de um utilizador residente
     * @param u
     * @param novaPal 
     */
    public static void alterarPal(Utilizador u, String novaPal){
        utilizadores.editarPalavra(u,novaPal);
    }

    /** 
     * altera o email de um utilizador residente
     * @param u
     * @param novoEmail 
     */
    public void alterarEmail(Utilizador u, String novoEmail){
        utilizadores.editarEmail(u,novoEmail);
    }

    /**
     * adiciona um novo conteúdo à coleção de um utilizador e, consequentemente, à biblioteca
     * @param email
     * @param conteudo 
     */
    public void upload(String email, Conteudo conteudo){
        utilizadores.addConteudo(email, conteudo);
    }
    
    /**
     * remove um conteúdo de uma coleção de um utilizador residente
     * @param u
     * @param nome 
     */
    public void removerConteudo(Utilizador u, String nome){
        utilizadores.removeConteudo(u.getEmail(),nome);
       
    }
    
    /** 
     * divide a informação do conteúdo em parâmetros 
     * @param info
     * @return Conteudo
     */
    public Conteudo infoCont(String info){
        Conteudo cont = new Conteudo(); 
        cont.setPath(info);
        String[] splited = info.split("/"); 
        String[] splited1 = splited[(splited.length)-1].split(",");
        cont.setID(splited1[3]);
        cont.setDuracao(Double.parseDouble(splited1[0]));
        cont.setTipo(Integer.parseInt(splited1[1])); 
        cont.setCategoria(splited1[2]); 
        
        return cont;
    }
    
    /** 
     * adiciona um amigo ao perfil de um utilizador residente
     * @param email
     * @param emailA 
     */
    public void insereAmigo(String email, String emailA){
        if(utilizadores.containsKey(email) && utilizadores.containsKey(emailA))
            utilizadores.insereAmigo(email,emailA);
    }
   
    public void alterarCategoria(String conteudo, String novaCategoria, String email){
        utilizadores.alterarCategoria(conteudo, novaCategoria, email);
    }
    
    /**
     * reproduz um conteúdo 
     * @param conteudo
     * @return
     * @throws IOException 
     */
    public static Process playConteudo(Conteudo conteudo) throws IOException{
        if(runner != null && runner.isAlive()) runner.destroy();
        String pathVLC;
        String cmd;
        String op;
        ProcessBuilder pb;
        if (System.getProperty("os.name").contains("Windows")){
            cmd = "cmd.exe";
            op = "/c";
            pathVLC = "\"C:\\Program Files\\VideoLAN\\VLC\\vlc.exe\"";
            pb = new ProcessBuilder(cmd, op, '"' + pathVLC + " --play-and-exit " + conteudo.getPath() + '"');
        }
        else if(System.getProperty("os.name").contains("Mac")){
            cmd = "bash";
            op = "-c";
            pathVLC = "/Applications/VLC.app/Contents/MacOS/VLC";
            pb = new ProcessBuilder(cmd, op, pathVLC + " --play-and-exit --no-loop --no-random --no-video-on-top " + conteudo.getPath());
        }
        else{
            cmd = "bash";
            op = "-c";
            pathVLC = "vlc";
            pb = new ProcessBuilder(cmd, op, pathVLC + " --play-and-exit " + conteudo.getPath());
        }
        return pb.start();
    }

    /** 
     * reproduz uma lista de reprodução, coleção ou biblioteca
     * @param email
     * @param id
     * @param tipo
     * @return
     * @throws IOException 
     */
    public static Process play(String email, String id, int tipo) throws IOException{
        if(runner != null && runner.isAlive()) runner.destroy();
        String pathVLC;
        ProcessBuilder pb = new ProcessBuilder();
        List<Conteudo> conteudos = new ArrayList<>();
        switch (id) {
            case "Colecao":
                conteudos = utilizadores.getConteudos(email);
                break;
            case "Biblioteca":
                conteudos = biblioteca.getConteudo();
                break;
            default:
                conteudos = ((Utilizador_Residente)utilizadores.get(email)).getListas().getContLista(id,email);
                break;
        }
        String tmp = conteudos.get(0).getPath();
        int i;
        for (i=1;i<conteudos.size();i++){
            tmp = tmp + " " + conteudos.get(i).getPath();
        }
        String cmd;
        String op;
        String modo;
        switch (tipo) {
            case 1:
                modo = " --play-and-exit --random --no-loop --no-video-on-top ";
                break;
            case 2:
                modo = " --loop --no-random --no-video-on-top";
                break;
            default:
                modo = " --play-and-exit --no-loop --no-random --no-video-on-top ";
                break;
        }
        if (System.getProperty("os.name").contains("Windows")){
            cmd = "cmd.exe";
            op = "/c";
            pathVLC = "\"C:\\Program Files\\VideoLAN\\VLC\\vlc.exe\"";
            pb.command(cmd, op, '"' + pathVLC + modo + tmp + '"');
        }
        else if(System.getProperty("os.name").contains("Mac")){
            cmd = "bash";
            op = "-c";
            pathVLC = "/Applications/VLC.app/Contents/MacOS/VLC";
            pb.command(cmd, op, pathVLC + modo + tmp);
        }
        else{
            cmd = "bash";
            op = "-c";
            pathVLC = "vlc";
            pb = new ProcessBuilder(cmd, op, pathVLC + modo + tmp);
        }
        return pb.start();
    }
    
    /**
     * obtém os conteúdos da coleção de um utilizador residente
     * @param emailU
     * @return lista de conteúdos
     */
    public List<Conteudo> getConteudos(Object emailU){
        List<Conteudo> l = utilizadores.getConteudos(emailU);
        
        return l;
    }
    
    /**
     * obtém as listas de reprodução de um utilizador residente
     * @param emailU
     * @return lista de nomes das listas de reprodução
     */
    public List<String> getListas(Object emailU){
        List<String> l = utilizadores.getListas(emailU);
        
        return l;
    }
    
    /**
     * obtém os utilizadores residentes registados na aplicação 
     * @return lista de utilizadores
     */
    public List<Utilizador_Residente> getUtilizadoresR(){
        List<Utilizador_Residente> l = utilizadores.getUtilizadoresR();
        
        return l;
    }
    
    /**
     * obtém os conteúdos da biblioteca 
     * @return lista de conteúdos
     */
    public List<Conteudo> getBiblioteca(){
        List<Conteudo> l = biblioteca.getConteudo();
        
        return l;
    }
    
    /**
     * obtém o conteúdo da biblioteca com a chave especificada
     * @param key
     * @return conteudo
     */
    public Conteudo getConteudo(String key){
        return biblioteca.getCont(key);
    }
    
    /**
     * obtém o tipo de uma reprodução
     * @param tipo
     * @return int
     */
    public int getTipo(int tipo){
        return tipo;
    }
    
    /**
     * obtém os utilizadores residentes amigos de um outro utilizador residente
     * @param email
     * @return lista de utilizadores
     */
    public static List<Utilizador_Residente> getAmigos(String email){
        List<Utilizador_Residente> l = utilizadores.getAmigos(email);
        
        return l;
    }

    /**
     * adiciona um conteúdo a uma determinada lista de reprodução de um utilizador residente
     * @param emailU
     * @param idc
     * @param idLista
     */
     public void addToList(String idC ,String idLista,String emailU){
         utilizadores.addToList(idC,idLista,emailU);
     }
    
    /*public void createList(String email, List<Conteudo> l) throws IOException{
        Lista_Reproducao lista_nova = new Lista_Reproducao();
        System.out.println("Escreva o nome da lista");
        String nome = input.nextLine();
        lista_nova.setNome(nome);
        for(Conteudo conteudo : l){
            lista_nova.getConteudo().add(conteudo);
        }
        utilizadores.get(email).addList(lista_nova);
    }*/

    public static void main(String args[]){
       
        MediaCenterFacade m = new MediaCenterFacade();
        Menu menu = new Menu();
        menu.setVisible(true);
        
        
    }
}