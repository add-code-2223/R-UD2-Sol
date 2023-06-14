/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2.model.dao.proyecto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import es.teis.rud2.data.DBCPDataSourceFactory;
import es.teis.rud2.exceptions.InstanceNotFoundException;
import es.teis.rud2.model.Proyecto;
import es.teis.ud2.model.dao.AbstractGenericDao;

/**
 *
 * @author mfernandez
 */
public class ProyectoSQLServerDao extends AbstractGenericDao<Proyecto> implements IProyectoDao {

	private DataSource dataSource;

	public ProyectoSQLServerDao() {
		this.dataSource = DBCPDataSourceFactory.getDataSource();
	}

	@Override
	public Proyecto create(Proyecto proyecto) {

		try (Connection conexion = this.dataSource.getConnection();
				PreparedStatement pstmt = conexion.prepareStatement(
						"INSERT INTO [dbo].[PROJECT]( PROJNAME,  START_DATE) VALUES( ?, ?);",
						Statement.RETURN_GENERATED_KEYS);) {

			pstmt.setString(1, proyecto.getNombre());
			pstmt.setDate(2, proyecto.getFechaComienzo());

			// Devolverá 0 para las sentencias SQL que no devuelven nada o el número de
			// filas afectadas
			int result = pstmt.executeUpdate();

			ResultSet clavesResultado = pstmt.getGeneratedKeys();

			if (clavesResultado.next()) {
				Integer id = Integer.valueOf(clavesResultado.getInt(1));
				proyecto.setId(id);
			} else {
				proyecto = null;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			proyecto = null;
		}
		return proyecto;
	}

	@Override
	public Proyecto read(int id) throws InstanceNotFoundException {

		String nombre;
		Date fecha;

		int contador;
		Proyecto proyecto = null;

		try (Connection conexion = this.dataSource.getConnection();
				PreparedStatement sentencia = conexion.prepareStatement("SELECT [PROJNO]\n" + "      ,[PROJNAME]\n"
						+ "      ,[START_DATE] FROM dbo.project \n" + "WHERE PROJNO=?");) {
			sentencia.setInt(1, id);

			ResultSet result = sentencia.executeQuery();
			if (result.next()) {
				contador = 0;

				id = result.getInt(++contador);
				nombre = result.getString(++contador);
				fecha = result.getDate(++contador);

				proyecto = new Proyecto();

				proyecto.setId(id);
				proyecto.setNombre(nombre);
				proyecto.setFechaComienzo(fecha);

			} else {
				throw new InstanceNotFoundException(id, getEntityClass());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

		}
		return proyecto;
	}

	@Override
	public boolean update(Proyecto proyecto) {

		boolean actualizado = false;
		try (Connection conexion = this.dataSource.getConnection();
				PreparedStatement pstmt = conexion.prepareStatement(
						"UPDATE [dbo].[PROJECT] " + " SET PROJNAME=?,  START_DATE=? WHERE PROJNO = ?")) {

			pstmt.setString(1, proyecto.getNombre());
			pstmt.setDate(2, proyecto.getFechaComienzo());
			pstmt.setInt(3, proyecto.getId());

			int result = pstmt.executeUpdate();
			actualizado = (result == 1);

			// Devolverá 0 para las sentencias SQL que no devuelven nada o el número de
			// filas afectadas
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

		}
		return actualizado;
	}

	@Override
	public boolean delete(int id) {
		int result = 0;
		try (Connection conexion = this.dataSource.getConnection();
				PreparedStatement pstmt = conexion.prepareStatement("DELETE FROM project WHERE PROJNO=?");) {

			pstmt.setInt(1, id);

			result = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

		}
		return (result == 1);
	}

	@Override
	public ArrayList<Proyecto> findAll() {
	 return findAllProjectsByTable("project");

	
	}

	public boolean transferir(int projno) {
		int contador = 0;
		//int id;
		String nombre = "";
		Date date=null;
		boolean exito = false;
		Connection conexion = null;
		try {
			conexion = this.dataSource.getConnection();
			PreparedStatement pstmtSelect = conexion
					.prepareStatement("SELECT projno, projname, start_date " + "FROM project WHERE projno = ?");
			PreparedStatement sentenciaInsert = conexion.prepareStatement(
					"INSERT INTO project_special( projname, start_date) values (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			PreparedStatement sentenciaDelete = conexion
					.prepareStatement("delete from project where projno =?");

			pstmtSelect.setInt(1, projno);

			ResultSet result = pstmtSelect.executeQuery();

			while (result.next()) {
				contador = 1;

				//id = result.getInt(++contador);
				nombre = result.getString(++contador);
				date = result.getDate(++contador);

			}

			if (contador > 0) {

				conexion.setAutoCommit(false);

				sentenciaInsert.setString(1, nombre);
				sentenciaInsert.setDate(2, date);

				sentenciaInsert.executeUpdate();

				ResultSet clavesResultado = sentenciaInsert.getGeneratedKeys();

				if (clavesResultado.next()) {
					Integer clave = Integer.valueOf(clavesResultado.getInt(1));
					System.out.println("Se ha insertado un registro en project_special con id =" + clave);
				}

				sentenciaDelete.setInt(1, projno);
				sentenciaDelete.executeUpdate();

				conexion.commit();
				exito = true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			if (conexion != null) {

				try {
					conexion.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return exito;
	}

	@Override
	public ArrayList<Proyecto> findAllSpecial() {
		return findAllProjectsByTable("project_special");
	}
	
	private ArrayList<Proyecto> findAllProjectsByTable(String tableName){
		   String nombre;
	        Date date;
	        int projno;
	        int contador;
	        Proyecto proyecto = null;
	        ArrayList<Proyecto> proyectos = new ArrayList<>();

	        try (
	                 Connection conexion = this.dataSource.getConnection();  PreparedStatement pstmt = conexion.prepareStatement("SELECT PROJNO, PROJNAME, START_DATE "
	                + "FROM " + tableName+" ORDER BY PROJNAME");  ResultSet result = pstmt.executeQuery();) {

	            while (result.next()) {
	                contador = 0;

	                projno = result.getInt(++contador);
	                nombre = result.getString(++contador);
	                date = result.getDate(++contador);

	                proyecto = new Proyecto();
	                proyecto.setId(projno);
	                proyecto.setNombre(nombre);
	                proyecto.setFechaComienzo(date);

	                proyectos.add(proyecto);
	            }

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

	        }

	        return proyectos;
	}
}
