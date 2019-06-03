package com.dispatcher.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.dispatcher.entity.Empleado;
import com.java.util.EntityManagerUtil;

public class EmpleadoDao {
	EntityManager entity = EntityManagerUtil.getEntityManager();

	@SuppressWarnings("unchecked")
	public List<Empleado> findAll() {
		List<Empleado> listaEmpleados = new ArrayList<>();
		listaEmpleados= entity.createNamedQuery("Empleado.findAll").getResultList();
		return listaEmpleados;
	}
	
	@SuppressWarnings("unchecked")
	public List<Empleado> findByTipo(Long idTipo) {
		List<Empleado> listaEmpleados = new ArrayList<>();
		listaEmpleados= entity.createNamedQuery("Empleado.findByTipo").setParameter("idTipo", idTipo).getResultList();
		return listaEmpleados;
	}
}
