package com.dispatcher.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.dispatcher.entity.Llamada;
import com.java.util.EntityManagerUtil;

public class LlamadaDao {
	EntityManager entity = EntityManagerUtil.getEntityManager();
	
	public Llamada guardar(Llamada llamada) {
		entity.getTransaction().begin();
		entity.persist(llamada);
		entity.getTransaction().commit();
		return llamada;
	}
	
	public void editar(Llamada llamada) {
		entity.getTransaction().begin();
		entity.merge(llamada);
		entity.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Llamada> findByEstado(String estado) {
		List<Llamada> listaLlamadas = new ArrayList<>();
		listaLlamadas= entity.createNamedQuery("Llamada.findByEstado").setParameter("estado", estado).getResultList();
		return listaLlamadas;
	}
}
