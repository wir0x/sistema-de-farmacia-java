package CLASES;


import java.sql.Date;
import java.sql.ResultSet;
import CONEXION.DAC;
import java.util.Calendar;
import javax.swing.JTextField;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gonzalo
 */
public class Venta extends DAC{
    private int cod_venta;
    private Date fecha;
    private int ci;
    private int cod_clt;
    public Venta()
    {
        
    }
    public int Cod_venta(){
        return this.cod_venta;
    }
    public void Cod_venta(int c){
        this.cod_venta = c;
    }
    public Date Fecha(){
        return this.fecha;
    }
    public void Fecha(Date c){
        this.fecha = c;
    }
    public int Cod_emp(){
        return this.ci;
    }
    public void Cod_emp(int c){
        this.ci = c;
    }
    public int Cod_clt(){
        return this.cod_clt;
    }
    public void Cod_clt(int c){
        this.cod_clt = c;
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
    public ResultSet traerCliente(String criterio)
    {
        String s = 
  "SELECT cod_clt 'Codigo',nombrec 'Nombre',(RTRIM(paternoc)+' '+RTRIM(maternoc)) 'Apellidos' "
+ "FROM cliente"
+ "WHERE (nombrec like '"+criterio+"%' or paternoc like '"+criterio+"%')";
       return ejecutarSQL(s);
    }
    public void Guardar(){
        prepararSP("{ call INSERTAR_VENTA(?,?) }");
        addParametro(1, String.valueOf(cod_clt));
        addParametro(2, String.valueOf(ci));
        ejecutarSP();
    }
}
