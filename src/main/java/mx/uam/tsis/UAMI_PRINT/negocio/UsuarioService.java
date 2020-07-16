package mx.uam.tsis.UAMI_PRINT.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.datos.RepositorioUsuario;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Pedido;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;

@Service
@Slf4j
public class UsuarioService {
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private PedidoService pedidoService;

	/**
	 * Crea un nuevo Usuario
	 * 
	 * @param Usuario nuevo
	 * @return
	 */
	public Usuario create(Usuario nuevoUsuario) {
		// regla de negocio no se puede crear mas de un usuario con el mismo identificador
		Optional<Usuario> usuario = repositorioUsuario.findById(nuevoUsuario.getMatricula());
		// si no esta presente el usuario
		if (!usuario.isPresent()) {
			return repositorioUsuario.save(nuevoUsuario);
		} else {
			return null;
		}
	}

	/**
	 * Regresa todos los usuarios
	 * 
	 * @return
	 */
	public Iterable<Usuario> retrieveAll() {
		return repositorioUsuario.findAll();
	}

	/**
	 * Regresa el usuario de determinada matricula
	 * 
	 * @param matricula
	 * @return
	 */
	public Usuario findByMatricula(Integer matricula) {
		Optional <Usuario> usuarioOp= repositorioUsuario.findById(matricula);
		return usuarioOp.get();

	}

	/**
	 * Actualiza un usuario de determinada matricula
	 * 
	 * @param matricula
	 * @param usuarioActualizar
	 * @return
	 */
	public Usuario udate(Integer matricula, Usuario usuarioActualizar) {
		Optional<Usuario> usuario = repositorioUsuario.findById(matricula);
		if (!usuario.isPresent()) {
			return repositorioUsuario.save(usuarioActualizar);
		} else {
			return null;
		}
	}

	/**
	 * Elimina un Usuario de determinada matricula
	 * 
	 * @param matricula
	 * @return
	 */
	public boolean delete(Integer matricula) {
		repositorioUsuario.deleteById(matricula);
		Optional<Usuario> usuario = repositorioUsuario.findById(matricula);
		return usuario.isPresent();
	}
	
	
	
	/**
	 * Acceso de usuario al sistema validando matricula y contraseña
	 * 
	 * @param matricula
	 * @param contrasena
	 * @return Objeto usuario si datos de acceso son correctos, NULL en caso contrario
	 */
	public Usuario findByMatriculaAndPassword(Integer matricula, String contrasena) {
		//Buscando usuario en BD por matricula
		Optional <Usuario> usuarioOp= repositorioUsuario.findById(matricula);
		
		log.info("Dentro de UsuarioService en metodo findByMatrAndPass recibiendo matricula"+matricula+" y contrasena "+contrasena);
		
		//Validando si usuario existe en BD
		if(usuarioOp.isPresent()) {
			log.info("Contraseña del usuario en BD" +usuarioOp.get().getContrasena()+ "   Contraseña recibida por UsuarioController "+contrasena);
			
			//Validando si contraseña de acceso es igual a la contraseña del sistema 
			if(usuarioOp.get().getContrasena().equals(contrasena)) {
				log.info("Contraseña correcta y devolviendo Usuario " +usuarioOp.get());
				return usuarioOp.get();
			}else {
				log.info("Contraseña incorrecta y devolviendo Usuario NULL");
				return null;
			}
		}else {
			log.info("Dentro de UsuarioService en metodo findByMatrAndPass y devolviendo NULL por que no existe el usuario con matricula "+matricula);
			return null;
		}
		
	}
	
	
	
	
	
	
	public boolean addPedidoToUsuario(Integer matricula, Pedido pedido) {
	         	log.info("HOLAAAAAA ENTRE Entre al add");
				
				// 2.- Recuperamos el usuario
				Optional <Usuario> usuarioOpt = repositorioUsuario.findById(matricula);
				
				// 3.- Verificamos que el pedido y el cliente existan
				if(!usuarioOpt.isPresent() || pedido == null) {
					
					log.info("No se encontró alumno o grupo");
					
					return false;
				}
				
				// 4.- Agrego el pedido al usuario
				Usuario usuario = usuarioOpt.get();
				usuario.addPedido(pedido);
				
				// 5.- Persistir el cambio
				repositorioUsuario.save(usuario);
				
				return true;
			}
	
	public boolean removePedidoToUsuario(Integer matricula, Pedido pedido) {
		// 2.- Recuperamos el usuario
		Optional <Usuario> usuarioOpt = repositorioUsuario.findById(matricula);	
		// 3.- Verificamos que el pedido y el cliente existan
		if(!usuarioOpt.isPresent() || pedido == null) {
			
			log.info("No se encontró alumno o grupo");
			
			return false;
		}
		// 4.- Elimino el pedido al usuario
		Usuario usuario = usuarioOpt.get();
		usuario.removePedido(pedido);
		
		
		// 5.- Persistir el cambio
		repositorioUsuario.save(usuario);
		
		return true;
	}
	
}
