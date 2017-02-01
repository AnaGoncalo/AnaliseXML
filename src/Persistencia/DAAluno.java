package Persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import testeanalise.Aluno;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Gonçalo
 */
public class DAAluno {
    
    public static String CadastrarAluno(Aluno aluno) throws SQLException
    {
        System.out.println("aluno dao");
        Connection conn = Banco.getConexao();
        PreparedStatement pstmt = null;
        String comandoSql = "INSERT INTO Aluno(nome, matricula) values(?, ?)";
        try
        {
            pstmt = conn.prepareStatement(comandoSql);
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getMatricula());
            pstmt.executeUpdate();
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DAAluno.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally
        {
            Banco.closeConexao(conn, null, pstmt, null);
        }
        return "OK!";
    }
}
