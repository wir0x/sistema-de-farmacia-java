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
public class DetalleCompra extends DAC{


    private int cod_compra;
    private int cod_med;
    private int cantidad;
    private Double costo;


    public DetalleCompra(){
        cod_compra=0;
        cod_med=0;
        cantidad=0;
        costo=0.0;
    }

    public DetalleCompra(int codC,int codM, int cant,Double cos){
        cod_compra=codC;
        cod_med=codM;
        cantidad =cant;
        costo =cos;
    }

    public void CodigoCompra(int cod){
        cod_compra=cod;
    }
    public int CodigoCompra(){
        return cod_compra;
    }

    public void CodigoMedicamento(int cod){
        cod_med=cod;
    }
    public int CodigoMedicamento(){
        return cod_med;
    }

    public void Cantidad(int cant){
        cantidad =cant;
    }
    public int Cantidad(){
        return cantidad;
    }

    public void Costo(Double cost){
        costo=cost;
    }
    public Double Costo(){
        return costo;
    }
    public void Guardar(){
        prepararSP("{ call insertar_dcompra(?,?,?) }");
        addParametro(1, String.valueOf(cod_med));
        addParametro(2, String.valueOf(cantidad));
        addParametro(3,String.valueOf(costo));
        ejecutarSP();
    }
}
