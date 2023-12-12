/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc2023.pkg2.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steven
 */
public class ModeloPersona extends Persona {

    ConexionPg cpg = new ConexionPg();//Conectamos a la base

    public ModeloPersona() {
    }

    public SQLException grabarPersona() {

        String sql;//"INSERT INTO TABLA () VALUES()"
        sql = "INSERT INTO Personas (id_persona,nombres,apellidos,fecha_nacimiento,telefono,sexo,sueldo,cupo,foto) "
                + "VALUES('" + getId_persona() + "','" + getNombres() + "','" + getApellidos() + "','" + getFecha_nacimiento()+ "','" + getTelefono()+ "','" + getSexo() + "','" + getSueldo()+"','" + getCupo()+ "','" + getFoto() + "')";
        return cpg.accionBD(sql);//DEVUELVO NULL SI ES CORRECTO.

    }

    public static List<Persona> listaTodasPersonas() {
        ConexionPg cpg = new ConexionPg();//Conectamos a la base
        List<Persona> listaPersonas = new ArrayList<Persona>();

        String sql;//SELECT * FROM TABLA
        sql = "SELECT id_persona,nombres,apellidos,fecha_nacimiento,telefono,sexo,sueldo,cupo,foto FROM Personas";//
        ResultSet rs = cpg.consultaDB(sql);
        try {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId_persona(rs.getString("id_persona"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setSexo(rs.getString("sexo"));
                persona.setSueldo(rs.getFloat("sueldo"));
                persona.setCupo(rs.getInt("cupo"));
                persona.setFoto(rs.getString("foto"));

                listaPersonas.add(persona);
            }
            rs.close();//CIERRO CONEXION CON LA BASE DE DATOS.
            return listaPersonas;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
            return null;//CUANDO REGRESA NULL, HUBO ERROR EN EL QUERY
        }

    }

    public SQLException editarPersona() {
        String SQL = "UPDATE Personas SET nombres = '" + getNombres() + "', apellidos = '" + getApellidos() + "', fecha_nacimiento = '" + getFecha_nacimiento().toString() + "', telefono = '" + getTelefono()
            + "', sexo = '" + getSexo() + "', sueldo = '" + getSueldo() + "', cupo = '" + getCupo() + "', foto = '" + getFoto() + "'"
            + " WHERE id_persona = '" + getId_persona() + "'";
        
        return cpg.accionBD(SQL);//DEVUELVO NULL SI ES CORRECTO.
    }
    public SQLException eliminarPersona() {
        String SQL = " DELETE FROM Personas WHERE id_persona = '" + getId_persona() + "'";
        
        return cpg.accionBD(SQL);//DEVUELVO NULL SI ES CORRECTO.
    }

    
}
