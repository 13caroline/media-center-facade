package dss.gestsocios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carolina
 */
public class AlunoInexistenteException extends Exception{
    public AlunoInexistenteException(){
        super("O aluno não existe");
    }
}
