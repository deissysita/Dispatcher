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
@Table(name="empleado")
@NamedQueries({@NamedQuery(name="Empleado.findAll",query="Select p from Empleado p"),
			   @NamedQuery(name="Empleado.findByTipo",query="Select p from Empleado p where p.tipoEmpleado.idTipoEmpleado=:idTipo")})
public class Empleado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idEmpleado;
	
	@Column
	private String nombreApellidoEmpleado;
	
	@ManyToOne
	@JoinColumn(name="idTipoEmpleado")
	private TipoEmpleado tipoEmpleado;
	
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombreApellidoEmpleado() {
		return nombreApellidoEmpleado;
	}
	public void setNombreApellidoEmpleado(String nombreApellidoEmpleado) {
		this.nombreApellidoEmpleado = nombreApellidoEmpleado;
	}
	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}
	public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}
	public Empleado() {
		
	}	
	public Empleado(Long idEmpleado, String nombreApellidoEmpleado, TipoEmpleado tipoEmpleado) {
		this.idEmpleado = idEmpleado;
		this.nombreApellidoEmpleado = nombreApellidoEmpleado;
		this.tipoEmpleado = tipoEmpleado;
	}
}
