package data;

import business.*;
import java.sql.*;
import java.util.*;

/** 
 * Implementação da classe Lista_ReproducaoDAO
 * Grupo 12
 * 2019/2020
 */
public class Lista_ReproducaoDAO {

    /** 
     * calcula quantas listas de reprodução existem
     * @return size
     */
    public int size(){
        Connection c = null;
        try{
            c = Connect.connect();
            String sql = "SELECT COUNT(id_Lista) FROM Lista_Reproducao";
            PreparedStatement stm = c.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            if(rs.next()){
                int r = rs.getInt("COUNT(id_Lista)");
            }
            else{
                throw new Exception("Empty table");
            }
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
        return -1;
    }

    /** 
     * verifica se existem listas de reprodução na base de dados
     * @return true, caso não existam listas de reprodução;
     *         false, caso contrário
     */
    public boolean isEmpty(){
        Connection c = null;
        try{
            c = Connect.connect();
            String s = "SELECT id_Lista FROM Lista_Reproducao";
            PreparedStatement stm = c.prepareStatement(s);
            ResultSet rs = stm.executeQuery();
            
            return !rs.next();
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * verifica se existe a lista de reprodução com a chave especificado
     * @param key
     * @return true, caso exista essa lista de reprodução;
     *         false, caso contrário
     */
    public boolean containsKey(Object key){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT id_Lista FROM Lista_Reproducao WHERE Lista_Reproducao.Lista='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(s);
            
            return rs.next();
        }
        catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    public boolean containsValue(Object value) {
        throw new NullPointerException("public boolean containsValue(Object value) not implemented!");
    }

    /**
     * devolve a lista de reprodução com a chave especificada
     * @param key
     * @return lista
     */
    public Lista_Reproducao get(Object key){
        Lista_Reproducao lr = null;
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT * FROM Lista_Reproducao WHERE id_Lista='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(s);
            
            if(rs.next()){
                lr = new Lista_Reproducao(rs.getString(1),rs.getString(2));
            }
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
        return lr;
    }

    /** 
     * Introduz uma lista de reprodução na base de dados
     * @param key
     * @param value
     * @return lista
     */
    public Lista_Reproducao put(String key,Lista_Reproducao value){
        Connection c = null;
        try{
            c = Connect.connect();
            Lista_Reproducao lr = null;
            
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Lista_Reproducao WHERE id_Lista='"+key+"'");
            String s = "INSERT INTO Lista_Reproducao VALUES('"+key+"','"+value.getIdLista()+"','"+value.getIdUtilizador()+"')";
            int i  = stm.executeUpdate(s);
            
            if(i!=1) System.out.println("Erro na inserção");
            
            lr = new Lista_Reproducao(value.getIdLista(),value.getIdUtilizador());
            
            return lr;
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * remove uma lista de reprodução da base de dados, devolvendo-a
     * @param key
     * @return lista
     */
    public Lista_Reproducao remove(Object key) {
        Connection c = null;
        try{
            c = Connect.connect();
            Lista_Reproducao lr = this.get(key);
            Statement stm = c.createStatement();
            
            String sql = "DELETE * FROM ListaReproducao_Conteudo WHERE id_ListaReproducao='"+key+"'";
            int nI=stm.executeUpdate(sql);
            if(nI!=1) System.out.println("Erro a remover");
            
            sql = "DELETE * FROM Lista_Reproducao WHERE id_Lista='"+key+"'";
            int i  = stm.executeUpdate(sql);
            
            if(i!=1) {
                System.out.println("Erro a remover");
            }
            return lr;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * elimina todas as listas de reprodução da base de dados
     */
    public void clear () {
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Lista_Reproducao");
            
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    public void putAll(Map<? extends String,? extends Lista_Reproducao> t) {
        throw new NullPointerException("Not implemented!");
    }

    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /** 
     * retorna uma coleção com todas as listas de reprodução da base de dados
     * @return col
     */
    public Collection<Lista_Reproducao> values() {
        Connection c = null;
        try{
            c = Connect.connect();
            Collection<Lista_Reproducao> col = new HashSet<Lista_Reproducao>();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Lista_Reproducao");
            
            for (;rs.next();) {
                col.add(new Lista_Reproducao(rs.getString(1),rs.getString(2)));
            }
            return col;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    public Set<Map.Entry<String,Lista_Reproducao>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Lista_Reproducao>> entrySet() not implemented!");
    }

    /** 
     * adiciona um conteúdo a uma lista de reprodução 
     * @param cont
     * @param key 
     */
    public void addConteudo(Conteudo cont, Object key){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM ListaReproducao_Conteudo WHERE id_Conteudo='"+cont.getIdConteudo()+"'");
            String sql = ("SELECT idCategoria FROM Categoria JOIN Colecao ON Categoria.idCategoria = Colecao.idCategoria WHERE id_Conteudo='"+cont.getIdConteudo()+"'");
            ResultSet rs = stm.executeQuery(sql);
            
            sql = ("INSERT INTO ListaReproducao_Conteudo VALUES ('"+cont.getIdConteudo()+"','"+key+"','"+rs.getString(1)+"')");
            ResultSet r = stm.executeQuery(sql);
            
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * coloca numa lista todos os conteúdos que pertencem a uma lista de reprodução 
     * @param key
     * @param emailU
     * @return lista
     */
    public List<Conteudo> getContLista(Object key,String emailU){
        List<Conteudo> listaC = new ArrayList<>();
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT * FROM Conteudo AS C JOIN ListaReproducao_Conteudo AS LRC ON LRC.id_Conteudo=C.id_Conteudo JOIN Lista_Reproducao AS LR ON LRC.id_ListaReproducao=LR.id_Lista WHERE LR.id_Lista='"+(String)key+"' AND LR.id_Utilizador='"+emailU+"' ";
            ResultSet rs = stm.executeQuery(s);
            
            while(rs.next()){
                listaC.add(new Conteudo(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getInt(4),rs.getString(5),rs.getString(9)));
            }
            return listaC;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
}
