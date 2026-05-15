package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	// Datos de conexion
	private static final String URL = "jdbc:mysql://localhost:3306/starwars_rpg";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "";

	public static Connection getConnection() {
		Connection con = null;

		try {	
			// Abrimos la conexion
			con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
			e.printStackTrace();
		}

		return con;
	}

	public static List<List<Object>> selectData(String sql, List<Object> parametros) {
		List<List<Object>> datos = new ArrayList<List<Object>>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			// Preparamos la consulta
			ps = con.prepareStatement(sql);
			// Metemos los parametros
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}

			rs = ps.executeQuery();
			int columnas = rs.getMetaData().getColumnCount();

			// Guardamos los datos
			while (rs.next()) {
				List<Object> fila = new ArrayList<Object>();

				for (int i = 1; i <= columnas; i++) {
					fila.add(rs.getObject(i));
				}

				datos.add(fila);
			}
		} catch (SQLException e) {
			System.out.println("Error al hacer la consulta");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la consulta");
				e.printStackTrace();
			}

			cerrarConexion(con);
		}

		return datos;
	}

	public static void insertData(String sql, List<Object> parametros) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConnection();

			// Preparamos la consulta
			ps = con.prepareStatement(sql);

			// Metemos los parametros
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}

			// Ejecutamos el insert
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al hacer el insert");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// Cerramos la consulta
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la consulta");
				e.printStackTrace();
			}

			cerrarConexion(con);
		}
	}

	public static void updateData(String sql, List<Object> parametros) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConnection();

			// Preparamos la consulta
			ps = con.prepareStatement(sql);

			// Metemos los parametros
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}

			// Ejecutamos el update
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al hacer el update");
			e.printStackTrace();
		} finally {
			try {
				
				if (ps != null) {
					// Cerramos la consulta
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la consulta");
				e.printStackTrace();
			}

			cerrarConexion(con);
		}
	}

	public static void deleteData(String sql, List<Object> parametros) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConnection();

			// Preparamos la consulta
			ps = con.prepareStatement(sql);

			// Metemos los parametros
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}

			// Ejecutamos el delete
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al hacer el delete");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// Cerramos la consulta
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar la consulta");
				e.printStackTrace();
			}

			cerrarConexion(con);
		}
	}

	public static void cerrarConexion(Connection con) {
		try {
			// Cerramos la conexion
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
			e.printStackTrace();
		}
	}
}
