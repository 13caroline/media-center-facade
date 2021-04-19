package dss.gestsocios;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

/**
 *
 * @author carolina
 */
public class GestModel{
    
    private Map<Integer,Aluno> alunos;
    
    /**
     * guarda através da serialização a hash table
     */
    public void save(){
       try{
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("socios.ser")); 
           oos.writeObject(this.alunos);
           oos.close();
           
       } catch (IOException i) {
            i.printStackTrace();
        } 
    
    }
    
    /**
     * decifra o ficheiro com a informação das hash tables
     * @return 
     */
    public GestModel load(){
       GestModel g = new GestModel();
       
       File dir = new File("socios.ser");
       if (dir.exists()){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("socios.ser")); 
            g = (GestModel) ois.readObject(); 
            ois.close();
        }
        catch(ClassNotFoundException e){
             System.out.println("Class not found exception");
                e.printStackTrace();
        }
        catch(FileNotFoundException e){
        }
        catch(IOException e){
            e.printStackTrace();
        }
       }
       return g;
     }
    
    
    /** 
     * retorna o aluno com o número dado 
     * @param num
     * @return Aluno
     * @throws AlunoInexistenteException 
     */
    public Aluno getAluno(int num) throws AlunoInexistenteException{
        if(!alunos.containsKey(num))
            throw new AlunoInexistenteException();
        
        return alunos.get(num).clone();
    }
    
    /**
     * retorna um map com todos os alunos 
     * @return todos os alunos
     */
    public Map<Integer, Aluno> getAlunos(){
        Map<Integer,Aluno> novo = new HashMap<Integer,Aluno>(); 
        
        for(Map.Entry<Integer,Aluno> entry : alunos.entrySet())
            novo.put(entry.getKey(), entry.getValue());
        
        return novo;
    }
    
    /**
     * adiciona um aluno
     * @param a 
     */
    public void addAluno(Aluno a){
       int num = a.getNumero(); 
       if(!alunos.containsKey(num))
           alunos.put(a.getNumero(),a.clone());
    }
    /**
     * paga a quota mensal de um aluno
     * @param numero
     * @param valor 
     */
    public void pagarQuota(Integer numero, Double valor){
        Aluno a = alunos.get(numero);
        Map<Integer,Double> quotas = a.getQuotas(); 
        quotas.put(Calendar.getInstance().get(Calendar.MONTH), valor);
    }
   
    public Aluno criaAluno(int numero, String nome, String morada, int ano, String curso, boolean valid, Map<Integer,Double> quotas){
        Aluno a = new Aluno(numero, nome, morada, ano, curso, valid, quotas);
        
       return a;
    }
   
}
