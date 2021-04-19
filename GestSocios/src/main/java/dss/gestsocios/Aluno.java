package dss.gestsocios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author carolina
 */
public class Aluno {
    private int numero; 
    private String nome; 
    private String morada; 
    private int ano; 
    private String curso;
    private boolean valid;
    private Map <Integer,Double> quotas;
    
    public Aluno(int numero, String nome, String morada, int ano, String curso, boolean valid, Map<Integer,Double> quotas){
        this.numero = numero; 
        this.nome = nome; 
        this.morada = morada; 
        this.ano = ano;
        this.curso = curso;
        this.valid = valid;
        this.quotas = quotas;
    }
    
    public Aluno(Aluno a){
        this.numero = a.getNumero();
        this.nome = a.getNome();
        this.morada = a.getMorada();
        this.ano = a.getAno();
        this.curso = a.getCurso();
        this.valid = a.getValid();
        this.quotas = a.getQuotas();
    }
    
    public Aluno() {
		this.numero = 0;
		this.nome = "n/a";
		this.morada = "n/a";
		this.curso = "n/a";
                this.valid = false;
		this.quotas = new HashMap<>();
	}
    
    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getMorada() {
        return morada;
    }

    public int getAno() {
        return ano;
    }

    public String getCurso() {
        return curso;
    }
    
    public boolean getValid(){
        return valid;
    }
    
    public Map<Integer,Double> getQuotas(){
        Map<Integer,Double> novo = new HashMap<Integer,Double>(); 
        for(Map.Entry<Integer,Double> entry : quotas.entrySet())
            novo.put(entry.getKey(), entry.getValue());
        
        return novo;
    }
    
    public Aluno clone(){
        return new Aluno(this);
    }
    
    public void payment(){
        this.valid = true;
    }
}

