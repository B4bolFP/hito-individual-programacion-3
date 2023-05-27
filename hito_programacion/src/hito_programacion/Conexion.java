package hito_programacion;

import java.sql.*;

public class Conexion {
	
	private Connection conexion;
	private Statement statement;
	private ResultSet resultset;
	
	public Conexion() {
		// Intentamos crear la conexion
		try {
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hitop", "root", "");
			
		} catch(Exception e) {
			
			System.out.println("No conecta!");
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql) {

		try {
			
			statement = conexion.createStatement();
			resultset = statement.executeQuery(sql);
			
			
		} catch (SQLException e) {
			
			System.out.println("No Funciona!");
			e.printStackTrace();
		}
		
		return resultset;
		
	}
	
	public void executeUpdate(String sql) {
		
		try {
			
			statement = conexion.createStatement();
			statement.executeUpdate(sql);
			
		} catch(SQLException e) {
			
			System.out.println("No Funciona!");
			e.printStackTrace();
			
		}

	}
	
	public void desconectar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			System.out.println("No se pudo desconectar");
			e.printStackTrace();
		}
	}
}
