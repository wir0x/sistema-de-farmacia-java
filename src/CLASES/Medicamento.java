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
public class Medicamento extends DAC{

    private int cod_med;
    private String nombre;
    private Double precio;
    private int stock;
    private int cod_cat;


    public Medicamento(){
        this.cod_med=0;
        this.nombre="";
        this.precio=0.0;
        this.stock=0;
        this.cod_cat=0;
    }

    public Medicamento(int c,String n,Double d,int t,int e){
        this.cod_med=c;
        this.nombre=n;
        this.precio=d;
        this.stock=t;
        this.cod_cat=e;
    }

    public void Codigo(int cod){
        cod_med=cod;
    }
    public int Codigo(){
        return cod_med;
    }

    public void Nombre(String nom){
        nombre=nom;
    }
    public String Nombre(){
        return nombre;
    }

    public void Precio (Double pre){
        precio=pre;
    }
    public Double Precio(){
        return precio;
    }

    public void Stock(int stk){
        stock=stk;
    }
    public int Stock(){
        return stock;
    }

    public void Categoria(int cat){
        cod_cat=cat;
    }
    public int Categoria(){
        return cod_cat;
    }

    public void Guardar(){
        prepararSP("{call insertarMedicamento(?,?,?,?)}");
        addParametro(1, nombre);
        addParametro(2, String.valueOf(precio));
        addParametro(3, String.valueOf(stock));
        addParametro(4, String.valueOf(cod_cat));
        ejecutarSP();
    }
    public ResultSet buscarmedicamento(String criterio)
    {
        String s = "";
        s = "SELECT cod_med 'Codigo',RTRIM(nombrem) 'Nombre' ,preciom 'Precio',stockm 'Stock', cod_cat 'Categoria' "
                + "FROM medicamento "
                + "where nombrem like '" + criterio + "%'";
            return ejecutarSQL(s);
    }
    public void Modificar(){
        prepararSP("{call modificarMedicamento(?,?,?,?,?)}");
        addParametro(1, String.valueOf(cod_med));
        addParametro(2, nombre);
        addParametro(3, String.valueOf(precio));
        addParametro(4, String.valueOf(stock));
        addParametro(5, String.valueOf(cod_cat));
    }
    public void Eliminar(){
        prepararSP("{call eliminarMedicamento(?)}");
        addParametro(1, String.valueOf(cod_med));
        ejecutarSP();
    }





    
    public ResultSet traerMedicamento(String criterio, String categoria, Boolean sw) {

        String s = "";
        if (sw) {
            s = "SELECT cod_med 'Codigo',RTRIM(nombreM) 'Nombre' ,preciom 'Precio',stockm 'Stock', cod_cat 'Categoria' "
                    + "FROM MEDICAMENTO "
                    + "WHERE nombrem like '" + criterio + "%' and cod_cat=" + categoria + "";
            return ejecutarSQL(s);
        } else {
            s = "SELECT cod_med 'Codigo',RTRIM(nombrem) 'Nombre' ,preciom 'Precio',stockm 'Stock', cod_cat 'Categoria' "
                    + "FROM MEDICAMENTO "
                    + "WHERE nombrem like '" + criterio + "%'";
            return ejecutarSQL(s);
        }

    }

    public ResultSet traerMed(String criterio){

       String s = "SELECT cod_med 'Codigo',RTRIM(nombrem) 'Nombre' ,preciom 'Precio',stockm 'Stock', cod_cat 'Categoria'"
                + "FROM MEDICAMENTO "
                + "WHERE nombrem like '" + criterio + "%'";
        return ejecutarSQL(s);
    }
    public boolean StockSuficiente(String codigo,String cantidad)
    {
            try{
            String s =
            "select  *from medicamento where stock >= "+cantidad+" and cod_med = "+codigo;
            ResultSet reg = ejecutarSQL(s);
           if(reg.next())
               return true;
           else
               return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
