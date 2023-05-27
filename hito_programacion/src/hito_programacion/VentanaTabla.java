package hito_programacion;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.table.*;

public class VentanaTabla extends JFrame {
	
	// Objetos de la ventana
    private JTable table;
    private JButton botonCrear;
    private JButton botonRefrescar;
    
    // Instanciamos la conexión con la base de datos.
    Conexion conexion = new Conexion();
    
    public VentanaTabla() {
        // Ajustamos los valores de la ventana a nuestras necesidades
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tabla de datos");
        setSize(950, 250);
        setLocationRelativeTo(null);
        
        // Instanciamos el modelo de la tabla y hacemos que no se pueda editar forzando el método "isCellEditable"
        DefaultTableModel modelo_tabla = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                // Hacer que todos los campos no sean editables
                return false;
            }
        };
        
        try {
            // Nos guardamos la consulta en "resultset"
            ResultSet resultset = conexion.executeQuery("SELECT * FROM usuarios");
            
            // Y obtenemos las características de la tabla
            ResultSetMetaData datos = resultset.getMetaData();
            
            // Obtenemos el número de columnas
            int columnas = datos.getColumnCount();
            
            // Con este bucle vamos creando columnas en base al nombre de las columnas.
            for (int i = 1; i <= columnas; i++) {
                modelo_tabla.addColumn(datos.getColumnName(i));
            }
            
            // Bucle para rellenar las tablas en base a los contenidos de las columnas
            while (resultset.next()) {
                Object[] datosFila = new Object[columnas];
                for (int i = 1; i <= columnas; i++) {
                	datosFila[i - 1] = resultset.getObject(i);
                }
                // despues de guardar todos los datos en "rowData" se los pasamos a la tabla
                modelo_tabla.addRow(datosFila);
            }
            
        // catch por si resulta en un error tener el control sobre la aplicacion
        } catch (SQLException e) {
            System.out.println("Error al montar la tabla");
            e.printStackTrace();
        }
        
        // Crear la tabla y asignar el modelo
        table = new JTable(modelo_tabla);
        
        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Definimos el boton
        botonCrear = new JButton("Agregar Usuarios");
        
        // Añadimos la accion de abrir la ventana al boton
        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaAgregarUsuario ventana = new VentanaAgregarUsuario();
                ventana.setVisible(true);
            }
        });
        
        // Lo mismo que el boton de encima pero con refrescar
        botonRefrescar = new JButton("Refrescar");
        botonRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	
            	// obtenemos las filas u con un bucle lar borramos todas
                int filas = modelo_tabla.getRowCount();
                
                for (int i = filas - 1; i >= 0; i--) {
                    modelo_tabla.removeRow(i);
                }
                
                // Y volvemos a crear la tabla para que se actualice
                try {
                    ResultSet resultset = conexion.executeQuery("SELECT * FROM usuarios");
                    
                    ResultSetMetaData datos = resultset.getMetaData();
                    
                    int columnas = datos.getColumnCount();
                    
                    while (resultset.next()) {
                        Object[] datosFila = new Object[columnas];
                        for (int i = 1; i <= columnas; i++) {
                            datosFila[i - 1] = resultset.getObject(i);
                        }
                        
                        modelo_tabla.addRow(datosFila);
                    }
                } catch (SQLException f) {
                    System.out.println("Error al refrescar la tabla");
                    f.printStackTrace();
                }
            }
        });
        
        // Cambiar el administrador de diseño a BorderLayout
        setLayout(new BorderLayout());
        
        // Agregar el JScrollPane (tabla) al centro de la ventana
        add(scrollPane, BorderLayout.CENTER);
        
        // Crear un panel para los botones y utilizar FlowLayout en el panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // Ajustar el tamaño y la forma de los botones
        botonCrear.setPreferredSize(new Dimension(150, 40));
        botonRefrescar.setPreferredSize(new Dimension(150, 40));
        
        // Agregar los botones al panel
        buttonPanel.add(botonCrear);
        buttonPanel.add(botonRefrescar);
        
        // Agregar el panel de botones al sur de la ventana
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
