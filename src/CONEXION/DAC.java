package CONEXION;

/**
 * @author Administrador
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class DAC {
private Connection cnx;
private CallableStatement cst;

public Connection conectar()
{
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      cnx=DriverManager.getConnection("jdbc:odbc:datos");
      return cnx;
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"Conectar:Error al conectar la BD..!!!" + e.getMessage());
        return null;
    }
}   

public void desconectar()
{
    try
    {
      cnx.close();
    }
    catch(Exception e) {  }
}

public void prepararSP(String nombre)
{
    try {
       cst=conectar().prepareCall(nombre);
    } 
    catch(Exception e){
        JOptionPane.showMessageDialog(null,"PrepararSP:Debe colocar un nombre al SP..!!!" + e.getMessage());
    }
}

public void addParametro(int num,String nombre)
{    
    try
    {
     cst.setString(num,nombre);
    }
    catch(Exception e){e.getMessage();}
}

public void ejecutarSP()
{
  try
  {
   cst.executeUpdate();
   desconectar();
  }
  catch(Exception e){   
      JOptionPane.showMessageDialog(null,"ejecutarSP:Error al ejecutar el SP..!!!" + e.getMessage());
   }
}


public ResultSet ejecutarSQL(String sql) // Variable en memoria
{
   try {   
   java.sql.Statement stmt=conectar().createStatement();
   ResultSet reg=stmt.executeQuery(sql);   
   return reg;
   }
   catch(Exception e){
      JOptionPane.showMessageDialog(null,"obtenerRegistro:Error..!!!" + e.getMessage());
      return null;
   }
  } 

}//end class
