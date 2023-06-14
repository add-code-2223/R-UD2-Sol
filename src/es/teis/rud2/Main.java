/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.rud2;

import java.util.ArrayList;
import java.util.List;

import es.teis.rud2.model.Proyecto;
import es.teis.ud2.model.dao.proyecto.IProyectoDao;
import es.teis.ud2.model.dao.proyecto.ProyectoSQLServerDao;

/**
 *
 * @author maria
 */
public class Main {

	private static IProyectoDao proyectoDao = new ProyectoSQLServerDao();

	public static void main(String[] args) {

		ArrayList<Proyecto> proyectos = proyectoDao.findAll();	

		mostrarProyectos("Proyectos en project ANTES : ", proyectos);
		
		ArrayList<Proyecto> proyectosSpecial = proyectoDao.findAllSpecial();
		
		mostrarProyectos("Proyectos en project_special ANTES: ", proyectosSpecial);
		
		proyectoDao.transferir(2);
		
		
		 proyectos = proyectoDao.findAll();	

		mostrarProyectos("Proyectos en project DESPUÉS : ", proyectos);
		
		 proyectosSpecial = proyectoDao.findAllSpecial();
		
		mostrarProyectos("Proyectos en project_special DESPUÉS: ", proyectosSpecial);
		
	}

	private static void mostrarProyectos(String msg, List<Proyecto> proyectos) {

		
	
		System.out.println("------------------------- " + msg);
		for (Proyecto proyecto : proyectos) {
			System.out.println(proyecto);
		}
		System.out.println("-------------------------");
		System.out.println("-------------------------");
	}
//
//    private static void verDetalleDepartamento(int id) {
//
//        IDepartamentoDao departamentoDao = new DepartamentoSQLServerDao();
//        IDepartamentoService departamentoServicio = new DepartamentoService(departamentoDao);
//
//        DepartamentoController controlador = new DepartamentoController(departamentoServicio);
//
//        String mensaje = controlador.verDetalles(id);
//        System.out.println(mensaje);
//
//    }
//
//    private static void getDepartmentNamesByLoc(String ubicacion) {
//
//        IDepartamentoDao departamentoDao = new DepartamentoSQLServerDao();
//        IDepartamentoService departamentoServicio = new DepartamentoService(departamentoDao);
//
//        DepartamentoController controlador = new DepartamentoController(departamentoServicio);
//
//        ArrayList<String> nombres = controlador.getDepartamentNamesByLoc(ubicacion);
//        for (String nombre : nombres) {
//            System.out.println("Nombre dept: " + nombre);
//        }
//
//    }
//
//    private static void create() {
//
//   
//           Proyecto pr= new Proyecto();
//           pr.setNombre("prueba");
//           pr.setFechaComienzo(new Date(2023, 5, 05));
//
//           pr = proyectoDao.create(pr);
//
//           System.out.println("Se ha creado el empleado: " + pr);
//
//       
//    }
    


}
