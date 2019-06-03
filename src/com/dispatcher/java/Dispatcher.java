package com.dispatcher.java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.dispatcher.dao.EmpleadoDao;
import com.dispatcher.dao.LlamadaDao;
import com.dispatcher.entity.Empleado;
import com.dispatcher.entity.Llamada;

public class Dispatcher implements Runnable{
	
	private Llamada llamadaEntrante;
	private Random randomGenerator = new Random();
	private LlamadaDao llamadaDao=new LlamadaDao();
	private EmpleadoDao empleadoDao=new EmpleadoDao();
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    
    /**
	 * Este método es el constructor de la clase dispatcher la cual se encarga de lanzar los hilos
	 * @author DeissyCoral
	 * @param llamadaEntrante el cual recibe la llamada en turno
	*/
	public Dispatcher(Llamada llamadaEntrante){
	        this.llamadaEntrante=llamadaEntrante;
	    }
	/**
	 * Este método lanza cada hilo del pool de hilos
	 * @author DeissyCoral
	*/ 
	@Override
	public void run() {
		dispatchCall();
		
	}
	
	/**
	 * Este método contiene la logica del hilo
	 * @author DeissyCoral
	*/
	public void dispatchCall() {
		Empleado empleadoLlamada=new Empleado();
		String horaInicio="";
		/**
		 * Este ciclo está activo mientras no hay empleados libres.  En el momento en que uno se desocupe, es retornado
		 * y el ciclo termina para asignar al empleado a la llamada
		*/
		do {
			empleadoLlamada=asignarEmpleado();
		}while(empleadoLlamada==null);
		
		horaInicio=String.valueOf(format.format(new Date(System.currentTimeMillis())));
		System.out.println("Lanzamiento nuevo hilo --> "+Thread.currentThread().getName()+" Start at "+horaInicio);
		
    	//seteamos los datos de retorno
        llamadaEntrante.setDuracionLlamada(randomGenerator.nextInt(5) + 5);
        llamadaEntrante.setEstadoLlamada("EC");//En espera EE, en curso EC, finalizada FN
        
      //Asignar empleado	
		llamadaEntrante.setEmpleadoLlamada(empleadoLlamada);
    	llamadaDao.editar(llamadaEntrante);
    	
    	//tiempo de espera del thread de acuerdo a la dureacion de llamada
    	try {Thread.sleep(llamadaEntrante.getDuracionLlamada()*1000);}
    	catch(InterruptedException e) {e.printStackTrace();}
    	
      //Impresión de hilo
    	System.out.println("**********");
    	System.out.println(Thread.currentThread().getName()+" End at "+horaInicio);
        System.out.println("LLamada "+llamadaEntrante.getIdLlamada()+" - Duración "+llamadaEntrante.getDuracionLlamada()
        +" - Empleado "+llamadaEntrante.getEmpleadoLlamada().getNombreApellidoEmpleado());
        System.out.println(Thread.currentThread().getName()+" End at "+format.format(new Date(System.currentTimeMillis()))+"\n");
        System.out.println("**********");
        //finaliza la llamada
        llamadaEntrante.setEstadoLlamada("FN");//En espera EE, en curso EC, finalizada FN		
        llamadaDao.editar(llamadaEntrante);
	}
	
	/**
	 * Este método contiene la lógica para agregar el empleado que atenderá la llamada
	 * @author DeissyCoral
	 * @return empleado, el cual será asignado en el objeto llamadaEntrante para persistir en BD
	*/
	public Empleado asignarEmpleado() {
		int randomEmpleado=0;
		Empleado empleado=new Empleado();
		List<Llamada> listaLlamadaEC=new ArrayList<Llamada>();
		
		List<Empleado> listaOperador=new ArrayList<Empleado>();
		List<Empleado> listaSupervisor=new ArrayList<Empleado>();
		List<Empleado> listaDirector=new ArrayList<Empleado>();
		
		//Listado todos los operadores, supervisores y directores
		listaOperador=empleadoDao.findByTipo(new Long(1));//Tipo 1 es Operador
		listaSupervisor=empleadoDao.findByTipo(new Long(2));//Tipo 2 es Supervisor
		listaDirector=empleadoDao.findByTipo(new Long(3));//Tipo 3 es Director
		
		//Lista de llamadas en curso en el momento
		listaLlamadaEC=llamadaDao.findByEstado("EC"); //EC= llamadas en curso
		
		//Quitar de la lista de empleados los que estan ocupados
		for(int i=0;i<listaLlamadaEC.size();i++) {
			//validar si es operador
			if(listaLlamadaEC.get(i).getEmpleadoLlamada().getTipoEmpleado().getIdTipoEmpleado().equals(new Long (1))) {
				listaOperador.remove(listaLlamadaEC.get(i).getEmpleadoLlamada());	
			}
			//validar si es supervisor
			else if(listaLlamadaEC.get(i).getEmpleadoLlamada().getTipoEmpleado().getIdTipoEmpleado().equals(new Long (2))) {
				listaSupervisor.remove(listaLlamadaEC.get(i).getEmpleadoLlamada());	
			}
			//validar si es director
			else if(listaLlamadaEC.get(i).getEmpleadoLlamada().getTipoEmpleado().getIdTipoEmpleado().equals(new Long (3))) {
				listaDirector.remove(listaLlamadaEC.get(i).getEmpleadoLlamada());	
			}
		}	
		
		System.out.println("Operadores libres"+listaOperador.size());
		/**
		 * Asignar inicialmente un operador, sino un supervisor, sino un director
		 * en caso contrario, osea no hay empleados disponibles, retorna null, así el ciclo sigue activo
		*/
		if(listaOperador.size()>0) {
			//Operadores
			randomEmpleado=randomGenerator.nextInt(listaOperador.size()-1);
			empleado=listaOperador.get(randomEmpleado);
		}else if(listaSupervisor.size()>0){
			//Supervisores
			randomEmpleado=randomGenerator.nextInt(listaSupervisor.size()-1);
			empleado=listaSupervisor.get(randomEmpleado);
		}else if(listaDirector.size()>0) {
			//Director
			randomEmpleado=randomGenerator.nextInt(listaDirector.size()-1);
			empleado=listaDirector.get(randomEmpleado);
		}else {
			//Cuando no hay empleados libres retorna null
			/*try {Thread.sleep(1000);}
	    	catch(InterruptedException e) {e.printStackTrace();}*/
			return null;
		}
		return empleado;		
	}

}
