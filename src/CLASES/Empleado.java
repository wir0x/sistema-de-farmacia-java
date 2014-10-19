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
public class Empleado extends DAC{
    private int cod_emp;
    private String nombre;
    private String paterno;
    private String materno;
    private String direccion;
    private String telefono;


    public Empleado(){
        cod_emp=0;
        nombre=paterno=materno=direccion=telefono="";
    }

    public Empleado (int cod, String Nombre,String Paterno,String Materno, String Direccion, String Telefono){
        this.cod_emp=cod;
        this.nombre=Nombre;
        this.paterno=Paterno;
        this.materno=Materno;
        this.direccion =Direccion;
        this.telefono=Telefono;
    }

    public void Codigo(int cod){
        cod_emp=cod;
    }
    public int Codigo(){
        return cod_emp;
    }

    public void Nombre(String nom){
        nombre=nom;
    }
    public String  Nombre(){
        return nombre;
    }

    public void Paterno(String pat){
        paterno=pat;
    }
    public String Paterno(){
        return paterno;
    }

    public void Materno(String mat){
        materno=mat;
    }
    public String Materno(){
        return materno;
    }

    public void Direccion(String dir){
        direccion =dir;
    }
    public String Direccion (){
        return direccion;
    }

    public void Telefono(String tel){
        telefono =tel;
    }
    public String Telefono(){
        return telefono;
    }

    public void Guardar(){
        prepararSP("{call insertarEmpleado(?,?,?,?,?)}");
        addParametro(1, nombre);
        addParametro(2, paterno);
        addParametro(3, materno);
        addParametro(4, direccion);
        addParametro(5, telefono);
        ejecutarSP();
    }

    public void eliminar(){
        prepararSP("{eliminarEmpleado(?)}");
        addParametro(1, String.valueOf(cod_emp));
        ejecutarSP();
    }

    public void modificar(){
        prepararSP("{modificarEmpleado(?,?,?,?,?,?)}");
        addParametro(1, String.valueOf(cod_emp));
        addParametro(2, nombre);
        addParametro(3, paterno);
        addParametro(4, materno);
        addParametro(5, direccion);
        addParametro(6, telefono);
        ejecutarSP();
    }

    public ResultSet buscarPorNombre(String criterio){
        try{
            ResultSet reg=ejecutarSQL("select *from empleado where name like '"+criterio+"%'");
            return reg;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public ResultSet traerEmpleado(String criterio) {
        String s =
                "SELECT cod_emp 'Codigo',nombree 'Nombre',(RTRIM(paternoe)+' '+RTRIM(maternoe)) 'Apellidos' "
                + "FROM empleado"
                + "WHERE (nombre like '" + criterio + "%' or paterno like '" + criterio + "%')";
        return ejecutarSQL(s);
    }


}
