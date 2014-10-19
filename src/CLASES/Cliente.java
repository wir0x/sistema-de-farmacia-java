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
public class Cliente extends  DAC{
    private int cod_clt;
    private String nombre;
    private String paterno;
    private String materno;
    private String direccion;
    private String telefono;
    private String correo;


    public Cliente(){
        cod_clt=0;
        nombre="";
        paterno="";
        materno="";
        direccion="";
        telefono="";
        correo="";
    }

    public Cliente (int cod, String n, String p,String m, String d, String t, String c){
        this.cod_clt=cod;
        this.nombre=n;
        this.paterno=p;
        this.materno=m;
        this.direccion=d;
        this.telefono=t;
        this.correo=c;
    }


    public void Codigo(int cod){
        cod_clt=cod;
    }
    public int Codigo(){
        return cod_clt;
    }

    public void Nombre(String n){
        nombre=n;
    }
    public String Nombre(){
        return nombre;
    }

    public void Paterno(String p){
        paterno=p;
    }
    public String Paterno(){
        return paterno;
    }

    public void Materno(String p){
        materno=p;
    }
    public String Materno(){
        return materno;
    }

    public void Direccion(String p){
        direccion=p;
    }
    public String Direccion(){
        return direccion;
    }

    public void Telefono(String p){
        telefono=p;
    }
    public String Telefono(){
        return telefono;
    }

    public void Correo(String p){
        correo=p;
    }
    public String Correo(){
        return correo;
    }

    public void Guardar(){
        prepararSP("{call insertarCliente(?,?,?,?,?,?)}");
        addParametro(1, nombre);
        addParametro(2, paterno);
        addParametro(3, materno);
        addParametro(4, direccion);
        addParametro(5, telefono);
        addParametro(6, correo);
        ejecutarSP();
    }

    public void Modificar(){
        prepararSP("{call modificarCliente(?,?,?,?,?,?,?)}");
        addParametro(1, String.valueOf(cod_clt));
        addParametro(2, nombre);
        addParametro(3, paterno);
        addParametro(4, materno);
        addParametro(5, direccion);
        addParametro(6, telefono);
        addParametro(7, correo);
        ejecutarSP();
    }

    public void Eliminar(){
        prepararSP("{call eliminarCliente(?)}");
        addParametro(1, String.valueOf(cod_clt));
        ejecutarSP();
    }

    public ResultSet BuscarPorNOmbre(String criterio){
        try{
            ResultSet reg=ejecutarSQL("select *from cliente where nombrec like '"+criterio+"%'");
            return reg;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
   

    

    public ResultSet traerCliente(String criterio) {
        String s =
                "SELECT cod_clt 'Codigo',nombrec 'Nombre',(RTRIM(paternoc)+' '+RTRIM(maternoc)) 'Apellidos' "
                + "FROM cliente "
                + "WHERE (nombrec like '" + criterio + "%' or paternoc like '" + criterio + "%')";
        return ejecutarSQL(s);
    }
}
