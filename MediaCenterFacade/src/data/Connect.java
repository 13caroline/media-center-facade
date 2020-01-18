package data;

import java.sql.*;

/**
 * Implementação da classe Connect
 * Classe que estabelece a conexão com a base de dados
 * Grupo 12 
 * 2019/2020
 */

public class Connect {
    private static final String host = "localhost";
    private static final String usrName = "root";
    private static final String password = "root";
    private static final String database = "mediacenterfacade";

    /**
     * Estabeler a conexão com a Base de Dados
     * @return c
     */
    public static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver não disponível");
        }

        try{
            return DriverManager.getConnection("jdbc:mysql://"+host+"/"+database+"?autoReconnect=true&useSSL=false",usrName,password);
        } catch (Exception e){}

        return null;
    }

    /**
     * Fechar ligação com a Base de Dados
     * @param c
     */
    public static void close(Connection c){
        try{
            if(c!=null && !c.isClosed())
                c.close();
        } catch(Exception e){}
    }
}
