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
public class Laboratorio extends DAC{

    private int cod_lab;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String web;


    public Laboratorio(){
        this.cod_lab=0;
        this.nombre="";
        this.direccion="";
        this.telefono="";
        this.email="";
        this.web="";
    }

    public Laboratorio(int c,String n,String d,String t,String e,String w){
        this.cod_lab=c;
        this.nombre=n;
        this.direccion=d;
        this.telefono=t;
        this.email=e;
        this.web=w;
    }

    public void Codigo(int cod){
        cod_lab=cod;
    }
    public int Codigo(){
        return cod_lab;
    }

    public void Nombre(String nom){
        nombre=nom;
    }
    public String Nombre(){
        return nombre;
    }

    public void Direccion (String dir){
        direccion=dir;
    }
    public String Direccion(){
        return direccion;
    }

    public void Telefono(String tel){
        telefono=tel;
    }
    public String Telefono(){
        return telefono;
    }

    public void Email(String em){
        email=em;
    }
    public String Email(){
        return email;
    }

    public void Web(String we){
        web=we;
    }
    public String Web(){
        return web;
    }
    public ResultSet traerlaboratorio(String criterio) {
        String s =
                "SELECT nombrel 'Nombre' "
                + "FROM laboratorio "
                + "WHERE (nombrel like '" + criterio + "%')";
        return ejecutarSQL(s);
    }
}
