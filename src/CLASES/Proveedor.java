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
public class Proveedor extends DAC {

    private int cod_prov;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String laboratorio;

    public Proveedor(){
        cod_prov=0;
        nombre="";
        nit="";
        direccion="";
        telefono="";
        laboratorio="";
    }

    public void Codigo(int cod){
        cod_prov=cod;
    }
    public int Codigo(){
        return cod_prov;
    }

    public void Nombre(String nom){
        nombre=nom;
    }
    public String Nombre(){
        return nombre;
    }

    public void Nit(String n){
        nit=n;
    }
    public String Nit(){
        return nit;
    }

    public void Direccion(String dir){
        direccion=dir;
    }
    public String Direccion(){
        return direccion;
    }

    public void Telefono(String tel){
        telefono=tel;
    }

    public void CodigoLaboratorio(String cod){
        laboratorio=cod;
    }
    public String CodigoLaboratorio(){
        return laboratorio;
    }

    public void Guardar(){
        prepararSP("{call insertarProveedor(?,?,?,?,?)}");
        addParametro(1, nombre);
        addParametro(2, nit);
        addParametro(3, direccion);
        addParametro(4, telefono);
        addParametro(5, laboratorio);
        ejecutarSP();
    }

    public void Modificar(){
        prepararSP("{call modificarProveedor(?,?,?,?,?,?)}");
        addParametro(1, String.valueOf(cod_prov));
        addParametro(2, nombre);
        addParametro(3, nit);
        addParametro(4, direccion);
        addParametro(5, telefono);
        addParametro(6, laboratorio);
        ejecutarSP();
    }

    public void Eliminar(){
        prepararSP("{call eliminarProveedor(?)}");
        addParametro(1, String.valueOf(cod_prov));
        ejecutarSP();
    }

    public ResultSet BuscarPorNOmbre(String criterio){
        try{
            ResultSet reg=ejecutarSQL("select *from proveedor where nombre like '"+criterio+"%'");
            return reg;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public ResultSet BuscarPorNombre(){
        try{
            String s = "select cod_provee, nombre, nit, telefono, cod_lab "
                    + "from proveedor "
                    + "where proveedor.cod_lab=laboratorio.cod_lab ";
            ResultSet reg=ejecutarSQL(s);
            return reg;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public ResultSet traerProveedor(String criterio) {
        String s =
                "SELECT cod_provee 'Codigo',nombre 'Nombre',nit 'Nit',direccion 'Direccion',telefono 'Telefono',cod_lab 'Cod_Lab' "
                + "FROM proveedor "
                + "WHERE (nombre like '" + criterio + "%')";
        return ejecutarSQL(s);
    }
}
