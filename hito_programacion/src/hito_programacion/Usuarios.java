package hito_programacion;

import java.sql.*;

public class Usuarios {

	private Conexion conexion;
	
	public Usuarios() {
		conexion = new Conexion();
	}
	
	public void listarUsuarios() {
		try {
			
			ResultSet resultset = conexion.executeQuery("SELECT * FROM usuarios");
			// Recorremos el contenido de resultset
			while(resultset.next()) {
				System.out.println(resultset.getString("id") + " " + resultset.getString("nombre") + " " + resultset.getString("plan") + " " + resultset.getString("peso") + " " + resultset.getString("eventos_mes") + " " + resultset.getString("horas_extra") + " " + resultset.getString("gastos_mes") + " " + resultset.getString("comparacion_peso"));
			}
			
		} catch(SQLException e) {
			
			System.out.println("No Funciona!");
			e.printStackTrace();
			
		}
		
	}
	
	public void nuevoUsuario(String nombre, String plan, double peso, int eventosMes, double horasExtra, double gastos_mes, String comparacion_peso) {
		String sql = "INSERT INTO `usuarios` (`id`, `nombre`, `plan`, `peso`, `eventos_mes`, `horas_extra`, `gastos_mes`, `comparacion_peso`) VALUES (NULL, '"+nombre+"', '"+plan+"', '"+peso+"', '"+eventosMes+"', '"+horasExtra+"', '"+gastos_mes+"', '"+comparacion_peso+"');";
		conexion.executeUpdate(sql);
	}
}