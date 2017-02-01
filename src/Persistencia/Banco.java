/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ana Gonçalo
 */
public class Banco {
    public static Connection conn = null;
    
    public static Connection getConexao() throws SQLException {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/AnaliseXML", "root", "ananne");
            
        } 
        catch (ClassNotFoundException exc) 
        {
            System.out.println("Error ao carregar o Driver");
        }
        catch (SQLException ex) 
        {
            System.out.println("Problemas ao abrir a conexaocom o banco");
        }
        return conn;
    }
    
    public static void closeConexao(Connection conn, ResultSet rs, PreparedStatement pstmt, Statement stmt) 
    {
        if(rs!= null) 
        {
            try
            {
                rs.close();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error ao fechar o ResultSet");
            }
        }
        if(stmt!= null) 
        {
            try
            {
                stmt.close();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error ao fechar o statement");
            }
        }
        if(pstmt!= null) 
        {
            try
            {
                pstmt.close();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error ao fechar o prepared statement");
            }
        }
        if(conn!= null) 
        {
            try
            {
                conn.close();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error ao carregar a conexão");
            }
        }
    }
}
