package mx.uam.tsis.UAMI_PRINT.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import mx.uam.tsis.UAMI_PRINT.datos.RepositorioUsuario;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;


public class UsuarioService {
	@Autowired
	private RepositorioUsuario repositorioUsuario;

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
	
	
	
}
