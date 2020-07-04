package mx.uam.tsis.UAMI_PRINT.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;

/**
 * Se encarga de almacenar y recuperar Usuarios
 * @author Jose Luis Salgado M. 
 *
 */
public interface RepositorioUsuario extends CrudRepository <Usuario, Integer>{//Parametros (Entidad usuario, Tipo de la llave primaria)
	

}
