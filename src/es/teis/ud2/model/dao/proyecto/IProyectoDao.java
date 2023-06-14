/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.teis.ud2.model.dao.proyecto;

import java.util.ArrayList;

import es.teis.rud2.exceptions.InstanceNotFoundException;
import es.teis.rud2.model.Proyecto;
import es.teis.ud2.model.dao.IGenericDao;


public interface IProyectoDao extends IGenericDao<Proyecto> {
    
    @Override
    public Proyecto create(Proyecto departamento);
    
    @Override
    public Proyecto read(int id)  throws InstanceNotFoundException;
    
    @Override
    public boolean update(Proyecto departamento);
    
    
    
    @Override
    public boolean delete(int id);
    
    
    public ArrayList<Proyecto> findAll();
    public ArrayList<Proyecto> findAllSpecial();
    
	public boolean transferir(int projno) ;
    
    
}
