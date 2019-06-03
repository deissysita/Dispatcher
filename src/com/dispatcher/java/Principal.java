package com.dispatcher.java;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dispatcher.dao.LlamadaDao;
import com.dispatcher.entity.Llamada;

public class Principal {

	private Llamada llamadaEntrante;
	private LlamadaDao llamadaDao=new LlamadaDao();
	
	/**
	 * Este método se lanza al correr la aplicación y lo uso para realizar las pruebas
	 * @author DeissyCoral
	 * @param pide una entrada por teclado que indica el número de llamadas que entraran al dispatcher
	 * @return invoca al pool de hilos y los resultados se verán en consola
	*/
	public static void main(String[] args) {
		Principal principal=new Principal();
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Ingresar el número de llamadas: ");
	    int numLlamadas=0;
	    try {numLlamadas = myObj.nextInt();}
	    catch(Exception ex) {numLlamadas=0;}
	    myObj.close();
		
		/**
		 * PRUEBA 1: 
		 * --> Cuando hay menos de 10 llamadas
		 * Todas deben ser atentidas al tiempo, ya que el pool está vacio y hay 13 empleados
		 * Prueba realizada con 5 llamadas
		*/
	    
	    /**
		 * PRUEBA 2:
		 * --> Cuando hay exactamente 10 llamadas
		 * Todas deben ser atentidas al tiempo, ya que el pool es de 10, y hay 13 empleados
		 * Prueba realizada con 10 llamadas
		*/
	    
	    /**
		 * PRUEBA3:
		 * --> Cuando hay más de 10 llamadas
		 * Deben atenderse las primeras 10 llamadas, y a medida que se desocupe un lugar en el pool, ingresa el siguiente
		 * Al existir 13 empleados y 13 llamadas, no hay problema de asignación, solo de turno en el pool
		 * Prueba realizada con 13 llamadas
		*/
	    
	    /**
		 * PRUEBA 4:
		 * --> Cuando todos los empleados estan ocupados
		 * Deben atenderse las primeras 10 llamadas y a medida que se desocupe un lugar en el pool, ingresa la siguiente
		 * Solo hay 13 empleados, por lo tanto las llamadas que no tienen un empleado asignado siguen en espera
		 * hasta que uno se desocupe
		 * Prueba realizada con 20 llamadas
		*/
	    
		principal.controlLlamadas(numLlamadas);
				
	}
	
	/**
	 * Este método crea el pool de hilos de acuerdo al numero de llamadas.
	 * La llamada maneja estados, por lo tanto aqui inicia como EE (En espera) y persiste en BD
	 * @author DeissyCoral
	 * @param cantidadLlamadas que indica el número de llamadas que entraran al dispatcher
	*/
	public void controlLlamadas(int cantidadLlamadas) {
		
		//Crear hilos: max 10
		ExecutorService servicio;
		
		/**
		 * El pool maneja 10 llamadas concurrentes, cuando hay más de ese numero, entra a espera, mientras una llamada inicial
		 * termina. Cabe aclarar que ya debe tener asignado un empleado ya que son 13 en total.
		*/
        servicio = Executors.newFixedThreadPool(10);
        
        //Ciclo para crear el hilo de cada llamada
		for(int i=0;i<cantidadLlamadas;i++) {
			llamadaEntrante=new Llamada();
			llamadaEntrante.setEstadoLlamada("EE");//En espera EE, en curso EC, finalizada FN
		    llamadaDao.guardar(llamadaEntrante);
	    	//Lanza el hilo
			servicio.submit(new Dispatcher(llamadaEntrante));
		}
	}
}
