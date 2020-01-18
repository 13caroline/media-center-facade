package data;

import business.*;
import java.sql.*;
import java.util.*;

/** 
 * Implementação da classe UtilizadorDAO
 * Grupo 12 
 * 2019/2020
 */

public class UtilizadorDAO {

    /** 
     * verifica se existem utilizadores na base de dados
     * @return true, caso não existam utilizadores na base de dados;
     *         false, caso contrário
     */
    public boolean isEmpty(){
        Connection c = null;
        try{
            c = Connect.connect();
            String s = "SELECT email FROM Utilizador";
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
     * calcula o número de utilizadores na base de dados
     * @return size
     */
    public int size(){
        Connection c = null;
        try{
            c = Connect.connect();
            String s = "SELECT COUNT(email) FROM Utilizador";
            PreparedStatement stm = c.prepareStatement(s);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                int r = rs.getInt("COUNT(email)");
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
     * verfica se um determinado utilizador se encontra na base de dados
     * @param key
     * @return true, caso esse utilizador se encontre na base de dados;
     *         false, caso contrário
     */
    public boolean containsKey(String key){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT email FROM Utilizador WHERE Utilizador.email='"+key+"'";
            ResultSet rs = stm.executeQuery(s);
            return rs.next();
        }
        catch (Exception e){ throw new NullPointerException(e.getMessage());}
        finally{
            Connect.close(c);
        }
    }

    public boolean containsValue(Object value) {
        throw new NullPointerException("public boolean containsValue(Object value) not implemented!");
    }

    /** 
     * devolve o utilizador com a chave especificada
     * @param key
     * @return Utilizador
     */
    public Utilizador get(Object key){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = "SELECT * FROM Utilizador WHERE Utilizador.email='"+key+"'";
            ResultSet rs = stm.executeQuery(s);
            Statement newSTM = c.createStatement();
            String newS;
            ResultSet newRS;
            Utilizador u = null;
            
            if(rs.next()){
                int tipo = rs.getInt(2);
                if(tipo == 0){
                    newS="SELECT * FROM Utilizador_Residente WHERE id_Utilizador='"+key+"'";
                    newRS = newSTM.executeQuery(newS);
                    if(newRS.next())
                        u = new Utilizador_Residente(newRS.getString(2),newRS.getString(1),newRS.getString(3));
                }
                else if(tipo == 1){
                    newS="SELECT * FROM Utilizador_Convidado WHERE id_UConvidado='"+key+"'";
                    newRS = newSTM.executeQuery(newS);
                    if(newRS.next())
                        u = new Utilizador_Convidado(newRS.getString(1));
                }
                else {
                    newS="SELECT * FROM Administrador WHERE id_Administrador='"+key+"'";
                    newRS = newSTM.executeQuery(newS);
                    if(newRS.next())
                        u = new Administrador(newRS.getString(2),newRS.getString(1),newRS.getString(3));
                }
            }
            return u;
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * insere um utilizador na base de dados
     * @param key
     * @param value
     * @return Utilizador
     */
    public Utilizador put(String key, Utilizador value) {
        Connection c = null;
        try{
            c = Connect.connect();
            Utilizador u;
            int i;
            int t = value.getTipo();
            
            String sql = "SET FOREIGN_KEY_CHECKS=0";
            Statement stm = c.createStatement();
            stm.execute(sql);
            stm.executeUpdate(sql);

            if (t == 0){
                if(containsKey(key)){
                    //stm.execute(sql);    
                    stm.executeUpdate("UPDATE Lista_Reproducao SET id_Utilizador='"+key+"' WHERE id_Utilizador='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Colecao SET id_Utilizador='"+key+"' WHERE id_Utilizador='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Utilizador_Residente SET id_Utilizador='"+key+"' WHERE id_Utilizador='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Lista_Amigos SET id_Utilizador='"+key+"' WHERE id_Utilizador='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Lista_Amigos SET id_Amigo='"+key+"' WHERE id_Amigo='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Utilizador SET email='"+key+"' WHERE email='"+value.getEmail()+"'");
                    //stm.execute(sql);
                    stm.executeUpdate("UPDATE Utilizador_Residente SET PalavraPasse='"+((Utilizador_Residente)value).getPalavra()+"' WHERE id_Utilizador = '"+key+"'");

                    sql = "SET FOREIGN_KEY_CHECKS=1";
                    stm.execute(sql);
                    stm.executeUpdate(sql);  
                }
                else{
                    sql = "INSERT INTO Utilizador VALUES ('"+key+"','"+value.getTipo()+"')";
                    stm.executeUpdate(sql); 
                    
                    sql = "INSERT INTO Utilizador_Residente VALUES ('"+key+"','"+((Utilizador_Residente)value).getNome()+"',NULL)";
                    stm.executeUpdate(sql);
                }
            
            u = new Utilizador_Residente(key, ((Utilizador_Residente)value).getNome(), ((Utilizador_Residente)value).getPalavra());
            }
            else if (t == 1){
                stm.executeUpdate("DELETE FROM Utilizador_Convidado WHERE Utilizador_Convidado.id_UConvidado='"+key+"'");
                stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+key+"'");
                
                sql = "INSERT INTO Utilizador VALUES ('"+key+"','"+value.getTipo()+"')";
                i = stm.executeUpdate(sql);
                if(i!=1) System.out.println("Erro na inserção em Utilizador");

                sql = "INSERT INTO Utilizador_Convidado VALUES ('"+key+"')";
                i = stm.executeUpdate(sql);
                if (i!=1) System.out.println("Erro na inserção em Utilizador_Convidado");
                
                u = new Utilizador_Convidado(key);
            }
            else{
                stm.executeUpdate("DELETE FROM Administrador WHERE Administrador.id_Administrador='"+key+"'");
                stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+key+"'");
                
                sql = "INSERT INTO Utilizador VALUES ('"+key+"','"+value.getTipo()+"')";
                i = stm.executeUpdate(sql);
                if(i!=1) System.out.println("Erro na inserção em Utilizador");
              
                sql = "INSERT INTO Administrador VALUES ('"+key+"','"+((Utilizador_Residente)value).getNome()+"','"+((Utilizador_Residente)value).getPalavra()+"')";
                i = stm.executeUpdate(sql); 
                if(i!=1) System.out.println("Erro na inserção em Administrador");
                u = new Administrador(key, ((Administrador)value).getNome(), ((Administrador)value).getPalavra());
            }
            
            return u;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * remove todos os utilizadores da base de dados
     */
    public void clear () {
        Connection conn = null;
        try{
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Utilizador");
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(conn);
        }
    }

    /** 
     * devolve uma coleção com todos os utilizadores da base de dados
     * @return col
     */
    public Collection<Utilizador> values() {
        Connection c = null;
        try{
            c = Connect.connect();
            Collection<Utilizador> col = new HashSet<Utilizador>();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Utilizador");
            
            for (;rs.next();) {
                int tipo = rs.getInt(1);
                if(tipo == 0)
                    col.add(new Utilizador_Residente(rs.getString(1),rs.getString(2),rs.getString(3)));
                if(tipo == 1)
                    col.add(new Utilizador_Convidado(rs.getString(1)));
                if(tipo == 2)
                    col.add(new Administrador(rs.getString(1),rs.getString(2),rs.getString(3)));
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

    /** 
     * remove um utilizador da base de dados, devolvendo-o
     * @param key
     * @return Utilizador
     */
    public Utilizador remove(Object key) {
        Connection c = null;
        try{
            c = Connect.connect();
            Utilizador u = this.get(key);
            
            int t = u.getTipo();

            Statement stm = c.createStatement();

            if (t == 0){
                stm.executeUpdate("DELETE FROM Utilizador_Residente WHERE Utilizador_Residente.id_Utilizador='"+key+"'");
                stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+key+"'");
                
                u = new Utilizador_Residente((String)key, ((Utilizador_Residente)u).getNome(), ((Utilizador_Residente)u).getPalavra());
            }
            else if (t == 1){
                stm.executeUpdate("DELETE FROM Utilizador_Convidado WHERE Utilizador_Convidado.id_UConvidado='"+key+"'");
                stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+key+"'");
              
                u = new Utilizador_Convidado((String)key);
            }
            else{
                stm.executeUpdate("DELETE FROM Administrador WHERE Administrador.id_Administrador='"+key+"'");
                stm.executeUpdate("DELETE FROM Utilizador WHERE email='"+key+"'");
               
                u = new Administrador((String)key, ((Administrador)u).getNome(), ((Administrador)u).getPalavra());
            }
            
            return u;    
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    public void putAll(Map<? extends String,? extends Utilizador> t) {
        throw new NullPointerException("Not implemented!");
    }

    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    public Set<Map.Entry<String,Utilizador>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Utilizador>> entrySet() not implemented!");
    }

    /** 
     * adiciona um conteúdo à coleção de um utilizador
     * @param key
     * @param conteudo 
     */
    public void addConteudo(Object key,Conteudo conteudo){
        Connection c = null;
        try{
            c = Connect.connect();
            String sqls = "SET FOREIGN_KEY_CHECKS=0";
            Statement sca = c.createStatement();
            sca.execute(sqls);
            sca.executeUpdate(sqls);
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Colecao WHERE id_Utilizador='"+key+"' AND id_Conteudo='"+conteudo.getIdConteudo()+"'");
            
            ConteudoDAO conteudos = new ConteudoDAO();
            if(!conteudos.containsKey(conteudo.getIdConteudo())){
             Statement stmn = c.createStatement();   
             String sql =("INSERT INTO Conteudo VALUES('"+conteudo.getIdConteudo()+"',null,'"+conteudo.getDuracao()+"','"+conteudo.getTipo()+"','"+conteudo.getPath()+"','"+conteudo.getCategoria()+"')");
             stmn.executeUpdate(sql);
            }
           
            Statement newSTM = c.createStatement();
            String del = ("DELETE FROM Categoria WHERE idCategoria='"+conteudo.getCategoria()+"'");
            newSTM.executeUpdate(del);
            String sql = ("INSERT INTO Categoria VALUES ('"+conteudo.getCategoria()+"')");
            newSTM.executeUpdate(sql);
            
            Statement stmr = c.createStatement();
            sql = ("INSERT INTO Colecao VALUES ('"+key+"','"+conteudo.getIdConteudo()+"','"+conteudo.getCategoria()+"')");
            stmr.executeUpdate(sql);
            
            sqls = "SET FOREIGN_KEY_CHECKS=1";
            sca.execute(sqls);
            sca.executeUpdate(sqls);
            
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
 
    /**
     * remove um conteúdo da coleção de um utilizador
     * @param email
     * @param nome
     */
    public void removeConteudo(String email, String nome){
      Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String sql = "DELETE FROM Colecao WHERE id_Utilizador='"+email+"' AND id_Conteudo='"+nome+"'";
            stm.executeUpdate(sql);
            
        }
        catch(Exception e){
          throw new NullPointerException(e.getMessage());
        } 
        finally{
            Connect.close(c);
        }
 }

    /** 
     * adiciona um amigo 
     * @param key
     * @param keyFriend 
     */
    public void insereAmigo(Object key, Object keyFriend){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            stm.executeUpdate("DELETE FROM Lista_Amigos WHERE Lista_Amigos.id_Utilizador='"+key+"' AND Lista_Amigos.id_Amigo='"+keyFriend+"'");

            String sql = ("INSERT INTO Lista_Amigos VALUES ('"+keyFriend+"') WHERE Lista_Amigos.id_Utilizador='"+key+"'");
            stm.executeQuery(sql);
            
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    
    /** 
     * edita os dados de email de um utilizador
     * @param key
     * @param novoEmail 
     */
    public void editarEmail(Object key, String novoEmail){
        Connection c = null;
            try{
            c = Connect.connect();
            put(novoEmail,(Utilizador_Residente)key);
            
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }

    /** 
     * edita os dados de palavra-passe de um utilizador
     * @param key
     * @param novaPalavra 
     */
    public void editarPalavra(Object key, String novaPalavra){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String sql = ("UPDATE Utilizador_Residente SET PalavraPasse = '"+novaPalavra+"' WHERE id_Utilizador ='" +((Utilizador_Residente)key).getEmail()+"'");
            stm.execute(sql);
            stm.executeUpdate(sql);
            
        }
        catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    
    /** 
     * devolve uma lista com todos os conteúdos da coleção de um utilizador
     * @param emailU
     * @return 
     */
    public List<Conteudo> getConteudos(Object emailU){
        List<Conteudo> listaC = new ArrayList<>();
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = ("SELECT * FROM Colecao JOIN Utilizador_Residente ON Colecao.id_Utilizador = Utilizador_Residente.id_Utilizador WHERE Colecao.id_Utilizador='" +emailU+"'");
            ResultSet rs = stm.executeQuery(s);
            for (;rs.next();){
                String novo = ("SELECT * FROM Conteudo WHERE id_Conteudo ='"+rs.getString(2)+"'");
                Statement novoS = c.createStatement();
                ResultSet nova = novoS.executeQuery(novo);
                while(nova.next())
                    listaC.add(new Conteudo(nova.getString(1),nova.getString(2),nova.getDouble(3),nova.getInt(4),nova.getString(5),rs.getString(3)));
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
    
    /** 
     * retorna o nome de todas as listas de um utilizador
     * @param emailU
     * @return lista
     */
    public List<String> getListas(Object emailU) {
       
        List<String> listaC = new ArrayList<>();
        Connection c = null;
        try{
            c = Connect.connect();
            Statement stm = c.createStatement();
            String s = ("SELECT id_Lista FROM Lista_Reproducao JOIN Utilizador_Residente ON Lista_Reproducao.id_Utilizador = Utilizador_Residente.id_Utilizador WHERE Utilizador_Residente.id_Utilizador = '"+emailU+"'");
            ResultSet rs = stm.executeQuery(s);
            for (;rs.next();){
                String novo = ("SELECT * FROM Lista_Reproducao WHERE id_Lista ='"+rs.getString(1)+"'");
                Statement novoS = c.createStatement();
                ResultSet nova = novoS.executeQuery(novo);
                while(nova.next())
                    listaC.add(nova.getString(1));
            }
            
            return listaC;
        }catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    /** 
     * devolve uma lista de todos os utilizadores residentes da base de dados
     * @return lista
     */
    public List<Utilizador_Residente> getUtilizadoresR(){
        Connection c = null;
        try{
            c = Connect.connect();
            List<Utilizador_Residente> u = new ArrayList<>();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Utilizador_Residente");
            
            for (;rs.next();) {
                    u.add(new Utilizador_Residente(rs.getString(1),rs.getString(2),rs.getString(3))); 
            }
            return u;
        }
        catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    
    /** 
     * retorna todos os amigos de um utilizador residente
     * @param email
     * @return lista
     */
    public List<Utilizador_Residente> getAmigos(String email){
        Connection c = null;
        try{
            c = Connect.connect();
            List<Utilizador_Residente> u = new ArrayList<>();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Lista_Amigos JOIN Utilizador_Residente ON Utilizador_Residente.id_Utilizador = Lista_Amigos.id_Amigo WHERE Lista_Amigos.id_Utilizador = '"+email+"'");
            
            for (;rs.next();) {
                    u.add(new Utilizador_Residente(rs.getString(2),rs.getString(4))); 
            }
            return u;
        }
        catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    
    /** 
     * adiciona um conteúdo a uma lista de um utilizador residente
     * @param idC
     * @param idLista
     * @param emailU 
     */
    public void addToList(String idC ,String idLista,String emailU){
        Connection c = null;
        try{
            c = Connect.connect();
            Statement nSTM = c.createStatement();
            String cat = ("SELECT Categoria FROM Conteudo WHERE Conteudo.id_Conteudo='"+idC+"'");
            ResultSet r = nSTM.executeQuery(cat);
            if(r.next()){
                Statement stm = c.createStatement();
                String sql = ("DELETE FROM ListaReproducao_Conteudo WHERE id_Conteudo='"+idC+"'AND id_ListaReproducao ='"+idLista+"'");
                stm.executeUpdate(sql);
                
                Statement novo = c.createStatement();
                String s = ("INSERT INTO ListaReproducao_Conteudo VALUES('"+idC+"','"+idLista+"','"+r.getString(1)+"')");
                novo.executeUpdate(s);
                
            }
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage());
        }
        finally{
            Connect.close(c);
        }
    }
    
    /** 
     * altera a categoria de um conteúdo da coleção e listas do utilizador
     * @param conteudo
     * @param novaCategoria
     * @param email 
     */
    public void alterarCategoria(String conteudo, String novaCategoria, String email){
        Connection c = null;
        try{
            c = Connect.connect();
            String sql = "SET FOREIGN_KEY_CHECKS=0";
            Statement stm = c.createStatement();
            stm.execute(sql);
            stm.executeUpdate(sql);
            
            Statement del = c.createStatement();
            String sqldel = ("DELETE FROM Categoria WHERE idCategoria='"+novaCategoria+"'");
            del.executeUpdate(sqldel);
            
            Statement ins = c.createStatement();
            String sqlins = ("INSERT INTO Categoria VALUES('"+novaCategoria+"')");            
            ins.executeUpdate(sqlins);        
            
            Statement upd = c.createStatement();
            String sqlupd = ("UPDATE Colecao SET idCategoria ='"+novaCategoria+"' WHERE id_Conteudo ='"+conteudo+"'AND id_Utilizador ='"+email+"'");
            upd.execute(sqlupd);
            upd.executeUpdate(sqlupd);
            
            Statement updL = c.createStatement(); 
            String sqlupdL = ("UPDATE ListaReproducao_Conteudo JOIN Lista_Reproducao ON ListaReproducao_Conteudo.id_ListaReproducao = Lista_Reproducao.id_Lista JOIN Utilizador_Residente ON Lista_Reproducao.id_Utilizador = Utilizador_Residente.id_Utilizador AND Utilizador_Residente.id_Utilizador = '"+ email + "' AND ListaReproducao_Conteudo.id_Conteudo = '"+conteudo+"' SET Categoria = '"+novaCategoria+"'");
            updL.execute(sqlupdL); 
            updL.executeUpdate(sqlupdL);
            
            sql = "SET FOREIGN_KEY_CHECKS=1";
            stm.execute(sql);
            stm.executeUpdate(sql);  
            
            
            }
            catch(Exception e){
                throw new NullPointerException(e.getMessage());
            }
            finally{
                Connect.close(c);
            }
        }
}
