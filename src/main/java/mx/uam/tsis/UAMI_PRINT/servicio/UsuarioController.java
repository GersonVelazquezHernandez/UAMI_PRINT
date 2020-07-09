package mx.uam.tsis.UAMI_PRINT.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.negocio.UsuarioService;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;

/**
* Controlador para el API rest Usuario
* 
* @author humbertocervantes
*
*/
@RestController
@Slf4j
//@RequestMapping("/ejemplo")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * PostMapping Le dice al metodo que va a recibir un JSON y mapea la ruta base a un verbo 
	 * @param nuevoAlumno Toma el JSON y en automatico lo combierte a una variable Alumno
	 * @return Devuelve un estatus si se pudo hacer o no 
	 */
	
	//El ApiOperation Sirve para la documentacion de nuestra api, Swagger se encargara de realizarlo
	@ApiOperation(
			value = "Crear un usuario",
			notes = "Permite crear un nuevo usuario"
			
			)
	@PostMapping(path = "/usuario", consumes ="application/json", produces = "application/json")
	public ResponseEntity <?> create(@RequestBody @Valid Usuario nuevoUsuario) {
		log.info("Recibí llamada a create con "+nuevoUsuario);
		Usuario usuario = usuarioService.create(nuevoUsuario);
		if(usuario !=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el alumno, chequelo bien o cumuniquese con el admin");
		}
		
		
	}
	
	
	
	
	
	
	
	@ApiOperation(
			value = "Obtener usuarios",
			notes = "Permite Obtener todos los usuarios almacenados hasta el momento"
		)
	@GetMapping(path = "/usuarios", produces = "application/json" )
	public ResponseEntity <?> retriveAll(){
		Iterable <Usuario> result = usuarioService.retrieveAll();
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	
	
	
	
	
	
	@ApiOperation(
			value = "Regresa un usuario",
			notes = "Regresa a un usuarios mediante la matricula (ID), la matricula debe de ser unica"
			
			)
	/**
	 * El mismo verbo GET de arriba pero esta vez solo regresara al alumno con esa matricula
	 * @param matricula Obtiene el valor "matricula" del JSON
	 * @return
	 */
	@GetMapping(path = "/usuario/{matricula}", produces = "application/json")
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al usuario con matricula "+matricula);
		
		Usuario usuario = usuarioService.findByMatricula(matricula);		
		if(usuario != null) {
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	
	
	
	
	
	
	@ApiOperation(
			value = "Elimna un ausuario",
			notes = "Elimina un usuario base a su matricula (ID)"
			
			)
	@DeleteMapping(path = "/usuario/{matricula}", produces = "application/json")
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		Usuario usuario = usuarioService.findByMatricula(matricula);
		if(usuario!=null) {
			usuarioService.delete(matricula);
			log.info("El alumno fue eliminado con matricula: "+ matricula);
			return ResponseEntity.status(HttpStatus.OK).body("El usuario fue eliminado");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el alumno a eliminar");
		}
				
	}
	
	
	
	
	
	/**
	 * Valida datos del usuario (matricula y contraseña) para acceso a sistema 
	 * 
	 * @param usuario
	 * @param contrasena
	 * @return Si usuario y contraseña son correcto devuelve el Usuario, en caso contrario devuelve null
	 */
	@ApiOperation(
			value = "Acceso Usuario al sistema",
			notes = "Validad la cuenta de usuario (matricula y contraseña) para dar acceso"		
			)
	@GetMapping(path ="/usuario/{matricula}/{contrasena}", produces = "application/json")
	public ResponseEntity <?> access(@PathVariable("matricula") Integer matricula,
									@PathVariable("contrasena") String contrasena){
		
		log.info("Dentro de UsuarioController en metodo ACCES con matricula "+matricula+" y contrasena "+contrasena);
		
		//Validando variables matricula y contraseña no esten vacias
		if(matricula == null || contrasena.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La matricula o contraseña estan vacios");
		}else {
			
			Usuario usuario = usuarioService.findByMatriculaAndPassword(matricula, contrasena);
			
			//Validando si existe el usuario y devuelve estado Http correspondiente
			if(usuario != null) {
				return ResponseEntity.status(HttpStatus.OK).body(usuario);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}		
		}
	}
}
