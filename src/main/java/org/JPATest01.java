package org;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ore.ciberfarma.modelo.Usuario;

public class JPATest01 {

	public static void main(String[] args) {
		
		// Crear un objeto de Usuario a grabar
		Usuario u = new Usuario();
		u.setCodigo(10);
		u.setNombre("Eren");
		u.setApellido("Lopez");
		u.setUsuario("usuario3@ciber.com");
		u.setClave("123");
		u.setFnacim("2000/10/15");
		u.setTipo(1);
		u.setEstado(1);
		
		// grabar el objeto
		// 1. fabricar el acceso a los datos
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		// 2. crear el manejador de entidades
		EntityManager em = fabrica.createEntityManager();
		
		// 3. empezar mi transacci�n
		em.getTransaction().begin();
		// proceso a realizar (persistencia)
		//em.persist(u);
		em.merge(u);
		// 4. confirmar la transacci�n
		em.getTransaction().commit();
		System.out.println("Registro agregado");
		em.close();
	}

}
