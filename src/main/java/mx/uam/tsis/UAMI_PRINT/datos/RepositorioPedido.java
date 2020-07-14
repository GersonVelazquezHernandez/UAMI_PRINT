package mx.uam.tsis.UAMI_PRINT.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Pedido;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;

public interface RepositorioPedido extends CrudRepository <Pedido, Integer>{

}
