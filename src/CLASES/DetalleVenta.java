/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package CLASES;
import CLASES.Empleado;

import CONEXION.DAC;

/**
 *
 * @author Gonzalo
 */
public class DetalleVenta extends DAC {


    private int cod_venta;
    private int cod_med;
    private int cantidad;
    private double precio;



    public DetalleVenta(){
        cod_venta=0;
        cod_med=0;
        cantidad=0;
        precio=0;
    }

    public DetalleVenta(int codv,int codm,int cant,double p){
        cod_venta=codv;
        cod_med=codm;
        cantidad=cant;
        precio=p;
    }

    public void CodigoVenta(int cod){
        cod_venta=cod;
    }
    public int CodigoVenta(){
        return cod_venta;
    }

    public void CodigoMedicamento (int cod){
        cod_med=cod;
    }
    public int CodigoMedicamento(){
        return cod_med;
    }

    public void Cantidad(int c){
        cantidad=c;
    }
    public int Cantidad(){
        return cantidad;
    }

    public void precio(double p){
        precio=p;
    }
    public void Guardar(){
        prepararSP("{ call INSERTA_DV(?,?,?) }");
        addParametro(1, String.valueOf(cod_med));
        addParametro(2, String.valueOf(cantidad));
        addParametro(3,String.valueOf(precio));
        ejecutarSP();
    }
}
