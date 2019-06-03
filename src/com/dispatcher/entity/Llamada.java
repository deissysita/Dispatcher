package com.dispatcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="llamada")
@NamedQueries({@NamedQuery(name="Llamada.findByEstado",query="Select p from Llamada p where p.estadoLlamada=:estado")})
public class Llamada {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idLlamada;
	
	@Column
	private Integer duracionLlamada;
	
	@Column
	private String estadoLlamada;//En espera EE, en curso EC, finalizada FN
	
	@JoinColumn(name="idEmpleado")
	@ManyToOne
	private Empleado empleadoLlamada;
	
	public Long getIdLlamada() {
		return idLlamada;
	}
	public void setIdLlamada(Long idLlamada) {
		this.idLlamada = idLlamada;
	}
	public Integer getDuracionLlamada() {
		return duracionLlamada;
	}
	public void setDuracionLlamada(Integer duracionLlamada) {
		this.duracionLlamada = duracionLlamada;
	}
	public String getEstadoLlamada() {
		return estadoLlamada;
	}
	public void setEstadoLlamada(String estadoLlamada) {
		this.estadoLlamada = estadoLlamada;
	}
	public Empleado getEmpleadoLlamada() {
		return empleadoLlamada;
	}
	public void setEmpleadoLlamada(Empleado empleadoLlamada) {
		this.empleadoLlamada = empleadoLlamada;
	}
	public Llamada() {
		
	}
	public Llamada(Long idLlamada) {
		this.idLlamada = idLlamada;
	}
	public Llamada(Long idLlamada, Integer duracionLlamada, String estadoLlamada, Empleado empleadoLlamada) {
		this.idLlamada = idLlamada;
		this.duracionLlamada = duracionLlamada;
		this.estadoLlamada = estadoLlamada;
		this.empleadoLlamada = empleadoLlamada;
	}
	
}
