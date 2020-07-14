package mx.uam.tsis.UAMI_PRINT.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Tarjeta;


/**
 * Permite crear, eliminar obtener tarjetas para la simulacion de pago con tarjeta
 * @author UAMI-PRINT
 *
 */
public interface RepositorioTarjeta extends CrudRepository <Tarjeta, Integer>{//Parametros (Entidad usuario, Tipo de la llave primaria)
	
}

