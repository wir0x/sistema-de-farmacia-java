/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;

import CONEXION.DAC;
import java.sql.ResultSet;

/**
 *
 * @author Gonzalo
 */
public class Categoria extends DAC{
 private int codigo;
 private String nombre;
 public Categoria()
 {
 
 }
 public Categoria(int c,String n)
 {
     this.codigo = c;
     this.nombre = n;
 }
 public int Codigo()
 {
     return this.codigo;
 }
 public void Codigo(int c)
 {
     this.codigo = c;
 }
 public String Nombre(){
         return this.nombre;
 }
 public void Nombre(String n){
         this.nombre = n;
 }
 @Override
    public String toString() {
        return this.nombre;
    }
public ResultSet traerCategoria()
    {
       String s = "SELECT *FROM categoria";
       return ejecutarSQL(s);
    }



public void Guardar(){
        prepararSP("{call insertarCategoria(?)}");
        addParametro(1, nombre);
        ejecutarSP();
    }
public void Modificar(){
        prepararSP("{call modificarCategoria(?,?)}");
        addParametro(1, String.valueOf(codigo));
        addParametro(2, nombre);
        ejecutarSP();
    }
    public void Eliminar(){
        prepararSP("{call eliminarCategoria(?)}");
        addParametro(1, String.valueOf(codigo));
        ejecutarSP();
    }
}
