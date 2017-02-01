/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testeanalise;

/**
 *
 * @author Ana Gonçalo
 */
//@XStreamAlias("aluno")
public class Aluno {
    private String matricula;
    private String nome;
    
    public Aluno(){
        
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
