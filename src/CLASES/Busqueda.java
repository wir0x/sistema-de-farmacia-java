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
public class Busqueda extends DAC {

    private int cod_lab;
    private int cod_med;


    public Busqueda(){
        cod_lab=0;
        cod_med=0;
    }

    public Busqueda(int codl,int codm){
        cod_lab=codl;
        cod_med=codm;
    }


    public void CodigoLaboratorio(int cod){
        cod_lab=cod;
    }
    public int CodigoLaboratorio(){
        return cod_lab;
    }

    public void CodigoMedicamento(int cod){
        cod_med=cod;
    }
    public int CodigoMedicamento(){
        return cod_med;
    }

    
}
