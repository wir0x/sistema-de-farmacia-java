/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import CONEXION.DAC;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author Gonzalo
 */
public class Compra extends DAC{


    private int cod_compra;
    private Date fecha;
    private int cod_provee;
    private int cod_emp;

    public Compra(){

    }
    public Compra(int cod, Date fe){
        cod_compra =cod;
        fecha=fe;
    }
    public int Cod_emp(){
        return this.cod_emp;
    }
    public void Cod_emp(int c){
        this.cod_emp = c;
    }
    public int cod_provee(){
        return this.cod_provee;
    }
    public void cod_provee(int p){
        this.cod_provee = p;
    }
    public void Codigo(int cod){
        cod_compra=cod;
    }
    public int Codigo(){
        return cod_compra;
    }

    public void Fecha(Date f){
        fecha=f;
    }
    public Date Fecha(){
        return fecha;
    }
    public JTextField lblhora;
    public void fecha(){
        int i=1;
        while(i>0){
        Calendar Cal= Calendar.getInstance();

            String fec= Cal.get(Cal.DATE)+"/"+(Cal.get(Cal.MONTH)+1)+"/"+Cal.get(Cal.YEAR)+" "+Cal.get(Cal.HOUR_OF_DAY)+":"+Cal.get(Cal.MINUTE)+":"+Cal.get(Cal.SECOND);
            lblhora.setText(fec);
        }
    }
    public ResultSet traerProveedor(String criterio)
    {
        String s =
  "SELECT cod_provee 'Codigo',nombre 'Nombre',nit 'Nit', direccion 'Direccion', telefono 'Telefono', cod_lab 'labotatorio' "
+ "FROM proveedor"
+ "WHERE (nombre like '"+criterio+"%')";
       return ejecutarSQL(s);
    }
    public void Guardar(){
        prepararSP("{ call insertar_compra(?,?) }");
        addParametro(1, String.valueOf(cod_provee));
        addParametro(2, String.valueOf(cod_emp));
        ejecutarSP();
    }
}
