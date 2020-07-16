package mx.uam.tsis.UAMI_PRINT.negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.datos.RepositorioPedido;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Pedido;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Usuario;


@Service
@Slf4j
public class PedidoService {
	
	@Autowired
	private RepositorioPedido repositorioPedido;
	
	//Tengo planes para hacer addtopedido
	@Autowired
	private UsuarioService usuarioService;
	
	
	//Folder donde se guardaran los documentos
	private static final String UPLOAD_DIR = "C:/pruebas/";
		
	public Pedido create(Pedido nuevoPedido, MultipartFile archivo, Integer matricula) throws IOException{
		Usuario user = usuarioService.findByMatricula(matricula);
		
		// Validando si archivo viene vacio ó si el archivo ya existe en la carpeta        
        if(archivo.isEmpty() || new File(nuevoPedido.getRutaArchivo()).exists()) {
 			return null;
 		}
        
        
	    // Creando el directorio
        File fileSaveDir = new File(UPLOAD_DIR+user.getMatricula());
        if (!fileSaveDir.exists()) { // crea el directorio si no existe
            fileSaveDir.mkdirs();
        }
 	    
        //Guardando el archivo PDF en directorio 
        FileOutputStream salidaArchivo = new FileOutputStream( nuevoPedido.getRutaArchivo() );
		salidaArchivo.write( archivo.getBytes() );
		salidaArchivo.close();
		System.out.println(nuevoPedido.toString());
		
		//Una vez guardado el archivo, tambien se guarda el pedido al suario que le corresponde		
		Pedido newPedido = repositorioPedido.save(nuevoPedido);
		usuarioService.addPedidoToUsuario(matricula, newPedido);
	 	return newPedido;
        
	}
	
	
	
	
	
	//Funcion para devolver todos los pedidos
	public Iterable <Pedido> retrieveAll (){
		return repositorioPedido.findAll();
	}
	
	//Funcion para devolver un pedido en especifico por ID
	public Pedido findById(Integer idPedido) {
		Optional <Pedido> pedidoOpt = repositorioPedido.findById(idPedido);  
		
		//Validando si existe el pedido y devolverlo como objeto
		if(pedidoOpt.isPresent()) {
			return pedidoOpt.get(); 
		} else {
			return null;
		}
	}
	
	//Funcion que actualiza un pedido
	public Pedido update(Pedido pedido) {
		 return repositorioPedido.save(pedido);
	}
	
	
	//Funcion que elimina un pedido
	public Pedido delete(Integer matricula, Pedido pedido) {
		Optional <Pedido> pedidoOpt = repositorioPedido.findById(pedido.getIdPedido());	
		if(pedidoOpt.isPresent()) {
			try {
				//Borrando el archivo que se guardo de manera local
				 File archivo = new File(pedido.getRutaArchivo());
				 boolean estatus = archivo.delete();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//despues eliminamos al pedido asociado al usuario
			usuarioService.removePedidoToUsuario(matricula, pedido);
			 
			repositorioPedido.delete(pedido);    //Eliminando alumno
			return pedidoOpt.get();              //Enviando objeto copia de alumno eliminado
		} else {
			return null;
		}
	 }

}
