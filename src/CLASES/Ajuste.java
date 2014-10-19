/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import CONEXION.DAC;
import java.sql.Date;

/**
 *
 * @author Gonzalo
 */
public class Ajuste extends DAC {

    private int cod_ajt;
    private Date fecha;
    private String observacion;

    public Ajuste(){

    }

    public Ajuste(int cod,Date f, String ob){
        cod_ajt=cod;
        fecha=f;
        observacion=ob;
    }

    public void Codigo(int cod){
        cod_ajt=cod;
    }
    public int Codigo(){
        return cod_ajt;
    }

    public void Fecha(Date f){
        fecha=f;
    }
    public Date Fecha(){
        return fecha;
    }

    public void Observacion(String ob){
        observacion=ob;
    }
    public String Observacion(){
        return observacion;
    }
}
