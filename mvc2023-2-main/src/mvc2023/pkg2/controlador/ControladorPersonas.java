/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc2023.pkg2.controlador;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mvc2023.pkg2.modelo.ModeloPersona;
import mvc2023.pkg2.modelo.Persona;
import mvc2023.pkg2.vista.VistaPersona;

/**
 *
 * @author Patricio
 */
public class ControladorPersonas {

    private ModeloPersona modelo;
    private VistaPersona vista;

    public ControladorPersonas(ModeloPersona modelo, VistaPersona vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
    }

    public void iniciaControl() {

        vista.getBtnActualizar().addActionListener(l -> listarPersonas());
        vista.getBntEliminar().addActionListener(l -> eliminarPersonas());
        vista.getBtnNuevo().addActionListener(l -> abrirDialogo(true));
        vista.getBtnEditar().addActionListener(l -> abrirDialogo(false));
        vista.getBtnAceptar().addActionListener(l -> grabarEditarPersona());
        vista.getBtnCancelar().addActionListener(l -> cerrarVentana());
        vista.getBntEliminar().addActionListener(l -> eliminarPersonas());

    }

    private void cerrarVentana() {
        vista.getDlgPersona().setVisible(false);
    }

    private void grabarEditarPersona() {

        if (vista.getDlgPersona().getTitle().contentEquals("CREAR NUEVA PERSONA")) {
            //LOGICA PARA GRABAR
            //VALIDAR ANTES
            String id_persona = vista.getTxtCedula().getText();
            String nombre = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellido().getText();
            String fecha_nacimiento = vista.getTxtFecha_nac().getText();
            String telefono = vista.getTxtTelefono().getText();
            String sexo = vista.getTxtSexo().getText();
            float sueldo = Float.valueOf(vista.getTxtSueldo().getText());
            int cupo = Integer.valueOf(vista.getTxtCupo().getText());
//            String foto = vista.getTxtFoto.getText();
            ModeloPersona persona = new ModeloPersona();
            persona.setId_persona(id_persona);
            persona.setNombres(nombre);
            persona.setApellidos(apellidos);
            persona.setFecha_nacimiento(Date.valueOf(fecha_nacimiento));
            persona.setTelefono(telefono);
            persona.setSexo(sexo);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);

            if (persona.grabarPersona() == null) {
                vista.getDlgPersona().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Persona Creada Satisfacoriamente");
                listarPersonas();
            } else {
                JOptionPane.showMessageDialog(vista, "ERROR");
            }

        } else {

            //logica para Editar
            String nombre = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellido().getText();
            String fecha_nacimiento = vista.getTxtFecha_nac().getText();
            String telefono = vista.getTxtTelefono().getText();
            String sexo = vista.getTxtSexo().getText();
            float sueldo = Float.valueOf(vista.getTxtSueldo().getText());
            int cupo = Integer.valueOf(vista.getTxtCupo().getText());
//            String foto = vista.getTxtFoto.getText();

            ModeloPersona persona = new ModeloPersona();

            persona.setNombres(nombre);
            persona.setApellidos(apellidos);
            persona.setFecha_nacimiento(Date.valueOf(fecha_nacimiento));
            persona.setTelefono(telefono);
            persona.setSexo(sexo);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);

            if (persona.editarPersona() == null) {
                vista.getDlgPersona().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Persona Editada Satisfacoriamente");
                listarPersonas();
            } else {
                JOptionPane.showMessageDialog(vista, "ERROR");
            }
        }
    }

    private void abrirDialogo(boolean nuevo) {
        if (nuevo) {
            vista.getDlgPersona().setTitle("CREAR NUEVA PERSONA");

        } else {
            vista.getDlgPersona().setTitle("EDITAR PERSONA");
        }

        vista.getDlgPersona().setLocationRelativeTo(vista);
        vista.getDlgPersona().setSize(580, 466);
        vista.getDlgPersona().setVisible(true);

    }

    private void listarPersonas() {
        ///Logica cargar personas
        List<Persona> listap = ModeloPersona.listaTodasPersonas();
        DefaultTableModel mTabla;
        mTabla = (DefaultTableModel) vista.getTblPersonas().getModel();
        mTabla.setNumRows(0);//limpio la tabla
        listap.stream().forEach(per -> {
            String[] rowData = {per.getId_persona(), per.getNombres(), per.getApellidos(), per.getFecha_nacimiento().toString(), per.getTelefono(), per.getSexo(), String.valueOf(per.getSueldo()), String.valueOf(per.getCupo()), per.getFoto()};
            mTabla.addRow(rowData);
        });

    }

    private void eliminarPersonas() {
        int numFila = vista.getTblPersonas().getSelectedRow();
        if (numFila >= 0) {
            String id_persona = vista.getTblPersonas().getValueAt(numFila, 0).toString();
            ModeloPersona mod = new ModeloPersona();
            mod.setId_persona(id_persona);
            if (mod.eliminarPersona() == null) {
                JOptionPane.showMessageDialog(null, "Persona Eliminada");
                listarPersonas();
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una persona");
        }
    }

}
