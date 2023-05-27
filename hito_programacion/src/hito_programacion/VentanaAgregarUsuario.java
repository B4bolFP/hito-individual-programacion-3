package hito_programacion;

// importamos las librerias que necesitamos
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaAgregarUsuario extends JFrame {
	
	// generamos todas las variables que vamos a utilizar posteriormente
	private Usuarios usuarios = new Usuarios();
    private JTextField nombreField;
    private JComboBox planDesplegable;
    private JTextField pesoField;
    private JTextField eventosField;
    private JTextField horasExtraField;
    private JButton guardarButton;
    private JButton cerrarButton;
    private Double gastos_mes;
    private Double total_horas;
    private int total_eventos;
    private String comparacion_peso;  
    
    private String[] planes = {"","Principiante", "Intermedio", "Elite"};

    public VentanaAgregarUsuario() {
        setTitle("Agregar Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        // Generamos el panel con bordes y un grid para alinear bien los objetos
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Los objetos en cuestion
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();
        
        JLabel planLabel = new JLabel("Planes de trabajo:");
        planDesplegable = new JComboBox(planes); // ComboBox genera un desplegable en vase a un array que le pasemos

        JLabel pesoLabel = new JLabel("Peso:");
        pesoField = new JTextField();

        JLabel eventosLabel = new JLabel("Eventos por mes (Max 2):");
        eventosField = new JTextField();

        JLabel horasExtraLabel = new JLabel("Horas extra (Max 5):");
        horasExtraField = new JTextField();

        
        // Declaramos el boton que va a recibir todos los tados y los va a enviar a la base de datos
        guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores ingresados por el usuario
                String nombre = nombreField.getText();
                String plan = planDesplegable.getSelectedItem().toString();
                double peso = Double.parseDouble(pesoField.getText());
                int eventosMes = Integer.parseInt(eventosField.getText());
                double horasExtra = Double.parseDouble(horasExtraField.getText());
                
                total_horas = 9.50 * horasExtra;
            	total_eventos = 22 * eventosMes;
                
            	// Calculo del precio en base al plan
                if (plan == "Principiante") {
                	gastos_mes = 25 + total_horas + total_eventos;
                } else if (plan == "Intermedio") {
                	gastos_mes = 30 + total_horas + total_eventos;
                } else if (plan == "Elite") {
                	gastos_mes = 35 + total_horas + total_eventos;
                }
                
                // Comparacion de categoria en base al peso
                if (peso <= 66) {
                	comparacion_peso = "Peso pluma";
                } else if (peso <= 73) {
                	comparacion_peso = "Peso ligero";
                } else if (peso <= 81) {
                	comparacion_peso = "Peso ligero-medio";
                } else if (peso <= 90) {
                	comparacion_peso = "Peso medio";
                } else if (peso <= 100) {
                	comparacion_peso = "Peso pesado";
                } else {
                	comparacion_peso = "Peso muy pesado";
                }
                
                // Error en base a las limitaciones
                if (eventosMes > 2 || horasExtra > 5 || plan == null) {
                	
                	JOptionPane.showMessageDialog(null, "Algun dato sobrepasa el limite o no se ha seleccionado plan.", "Error", JOptionPane.ERROR_MESSAGE);
                	
                } else { // Enviamos el insert con los datos calculados a la base de datos
                	
                	usuarios.nuevoUsuario(nombre, plan, peso, eventosMes, horasExtra, gastos_mes, comparacion_peso);
                	dispose();
                	
                }
            }
        });
        
        // Boton para cerrar la ventana
        cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Dispose() libera de la memoria la clase por completo.
                dispose();
            }
        });

        // añadimos todos los objetos al panel(ventana)
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(planLabel);
        panel.add(planDesplegable);
        panel.add(pesoLabel);
        panel.add(pesoField);
        panel.add(eventosLabel);
        panel.add(eventosField);
        panel.add(horasExtraLabel);
        panel.add(horasExtraField);
        panel.add(guardarButton);
        panel.add(cerrarButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}

