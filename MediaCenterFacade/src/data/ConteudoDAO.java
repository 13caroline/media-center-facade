package data;

import business.Conteudo;
import java.sql.*;
import java.util.*;

/** 
 * Implementação da classe ConteudoDAO
 * Grupo 12 
 * 2019/2020
 */

public class ConteudoDAO {
    
    /** 
     * verifica se não existem conteúdos na base de dados
     * @return true, se não existirem conteúdos; 
     *         false, caso contrário;
     */
    public boolean isEmpty(){
        Connection c = null;
        try{
            c = Connect.connect();
            String s = "SELECT id_Conteudo FROM Conteudo";
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
     * calcula quantos conteúdos existem na base de dados
     * @return size
     */
    public int size(){
        Connection c = null;
        try{
            c = Connect.connect();
            String s = "SELECT COUNT(id_Conteudo) FROM Conteudo";
            PreparedStatement stm = c.prepareStatement(s);
            ResultSet rs = stm.executeQuery();

            if(rs.next()){
                int r = rs.getInt("COUNT(id_Conteudo)");
                
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
     * verifica se o conteúdo especificado se encontra na base de dados
     * @param key
     * @return true, caso o conteúdo se encontre na base de dados;
     *         false, caso contrário
     */
    public boolean containsKey(Object key){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT id_Conteudo FROM Conteudo WHERE Conteudo.id_Conteudo='"+(String)key+"'";
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
     * devolve o conteúdo cujo nome é especificado 
     * @param key
     * @return Conteudo
     */
    public Conteudo get(Object key){
        Conteudo cont = null;
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT * FROM Conteudo WHERE id_Conteudo='"+(String)key+"'";
            ResultSet rs = stm.executeQuery(s);
            
            if(rs.next()){
                cont = new Conteudo(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getInt(4),rs.getString(5),rs.getString(6));
                return cont;
            }
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
        return cont;
    }

    /** 
     * Coloca um conteúdo na base de dados
     * @param key
     * @param value
     * @return Conteudo
     */
    public Conteudo put(String key,Conteudo value){
        Connection c = null;
        try{
            c = Connect.connect();
            Conteudo cont = null;
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Conteudo WHERE id_Conteudo='"+key+"'");
            String s = "INSERT INTO Conteudo VALUES('"+key+"','"+value.getIdConvidado()+"','"+"','"+value.getDuracao()+"','"+value.getTipo()+"','"+value.getPath()+"','"+value.getCategoria()+"')";
            int i  = stm.executeUpdate(s);
            
            if(i!=1) System.out.println("Erro na inserção");
            cont = new Conteudo(value.getIdConteudo(),value.getIdConvidado(),value.getDuracao(),value.getTipo(),value.getPath(),value.getCategoria());
            return cont;
        }
        catch(Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * remove um conteúdo da base de dados, devolvendo o mesmo
     * @param key
     * @return Conteudo 
     */
    public Conteudo remove(Object key) {
        Connection c = null;
        try{
            c = Connect.connect();
            Conteudo cont = this.get(key);
            Statement stm = c.createStatement();
            String s = "DELETE * FROM Conteudo WHERE id_Conteudo='"+key+"'";
            int i  = stm.executeUpdate(s);
            
            if(i!=1) {
                System.out.println("Erro a remover");
            }
            return cont;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * elimina todos os conteúdos da base de dados
     */
    public void clear () {
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Conteudo");
            
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    public void putAll(Map<? extends String,? extends Conteudo> t) {
        throw new NullPointerException("Not implemented!");
    }

    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /** 
     * coloca numa Collection todos os conteúdos armazenados na base de dados
     * @return col
     */
    public Collection<Conteudo> values() {
        Connection c = null;
        try{
            c = Connect.connect();
            Collection<Conteudo> col = new HashSet<Conteudo>();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Conteudo");
            
            for (;rs.next();) {
                col.add(new Conteudo(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
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

    public Set<Map.Entry<String,Conteudo>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Conteudo>> entrySet() not implemented!");
    }
}

