/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import CONEXION.DAC;

/**
 *
 * @author Gonzalo
 */
public class DetalleAjuste extends DAC{


    private int cod_ajt;
    private int cod_med;
    private int cantidad;


    public DetalleAjuste(){
        cod_ajt=0;
        cod_med=0;
        cantidad=0;
    }

    public DetalleAjuste(int coda,int codm, int cant){
        cod_ajt=coda;
        cod_med=codm;
        cantidad=cant;
    }

    public void CodigoAjuste(int cod){
        cod_ajt=cod;
    }
    public int CodigoAjuste(){
        return cod_ajt;
    }

    public void CodigoMedicamento(int cod){
        cod_med=cod;
    }
    public int CodigoMedicamento(){
        return cod_med;
    }

    public void Cantidad(int cant){
        cantidad=cant;
    }
    public int Cantidad(){
        return cantidad;
    }
}
