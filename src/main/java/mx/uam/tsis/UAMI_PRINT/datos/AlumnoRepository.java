package mx.uam.tsis.UAMI_PRINT.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
/**
 * Se encarga de almacenar y recuperar alumnos
 * @author HP
 *
 */
public interface AlumnoRepository extends CrudRepository <Alumno, Integer>{//Entidad, Tipo de la llave primaria
	

}
