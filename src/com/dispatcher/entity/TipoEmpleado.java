package com.dispatcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipoempleado")
public class TipoEmpleado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTipoEmpleado;
	
	@Column
	private String nombreTipoEmpleado;
	
	public Long getIdTipoEmpleado() {
		return idTipoEmpleado;
	}
	public void setIdTipoEmpleado(Long idTipoEmpleado) {
		this.idTipoEmpleado = idTipoEmpleado;
	}
	public String getNombreTipoEmpleado() {
		return nombreTipoEmpleado;
	}
	public void setNombreTipoEmpleado(String nombreTipoEmpleado) {
		this.nombreTipoEmpleado = nombreTipoEmpleado;
	}
	
	public TipoEmpleado() {
		
	}
	
	public TipoEmpleado(Long idTipoEmpleado, String nombreTipoEmpleado) {
		this.idTipoEmpleado = idTipoEmpleado;
		this.nombreTipoEmpleado = nombreTipoEmpleado;
	}	
}
