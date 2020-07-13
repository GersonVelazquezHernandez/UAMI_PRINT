package mx.uam.tsis.UAMI_PRINT.servicio;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.negocio.PedidoService;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Pedido;


/*Controlador tipo rest de capa de servicio*/
@RestController
@Slf4j
public class PedidoController {
	
	private Pedido pedido = new Pedido();
	
	@Autowired
	private PedidoService pedidoService;
	
	
	//Folder donde se guardaran los documentos
	private static final String UPLOAD_DIR = "C:/pruebas";
	
	
	@PostMapping(path = "/cargar-archivo")
	@ResponseBody
	public ResponseEntity<String> fileUpload(
			@RequestParam("descripcion") String descripcionImpresion,
			@RequestParam("archivo") MultipartFile archivo,
			@RequestParam("tipo") String tipoImpresion,
			@RequestParam("pago") String metodoPago
			
			) throws IOException, ServletException {
		
		// filename completo del archivo
        String filename = UPLOAD_DIR + File.separator + archivo.getOriginalFilename();
        
        pedido.setDescripcionImpresion(descripcionImpresion);
		pedido.setMetodoPago(metodoPago);
		pedido.setTipoImpresion(tipoImpresion);
		pedido.setRutaArchivo(filename);
        
	     // error si el archivo no se subio, viene vacio ó si el archivo ya existe en la carpeta        
        if(archivo.isEmpty() || new File(filename).exists()) {
 			return new ResponseEntity(HttpStatus.BAD_REQUEST);
 		}
        
        
		// Creacion de archivo en la carpeta
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) { // crea el directorio si no existe
            fileSaveDir.mkdirs();
        }         
		        
        FileOutputStream salidaArchivo = new FileOutputStream( filename );
        salidaArchivo.write( archivo.getBytes() );
        salidaArchivo.close();
        
        log.info(pedido.toString());
        return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	
	
	/******************  METODOS ALTERNATIVOS ****************************************/
	
	/**
	 * 
	 * 
	 * @param descripcionImpresion
	 * @param archivo
	 * @param tipoImpresion
	 * @param metodoPago
	 * @return Devuelve Objeto del Pedido creado, en caso contrario devuelve null 
	 * @throws IOException
	 * @throws ServletException
	 */
	@ApiOperation(
			value = "Crear Pedido.",
			notes = "Puedes crear un pedido enviando el archivo PDF y datos complementarios"
			)
	@PostMapping(path = "/pedido", produces = "application/json")
	@ResponseBody
	public ResponseEntity <?>descarga(
			@RequestParam("descripcion") String descripcionImpresion,
			@RequestParam("archivo") MultipartFile archivo,
			@RequestParam("tipo") String tipoImpresion,
			@RequestParam("pago") String metodoPago
			
			) throws IOException, ServletException {
		
		pedido = new Pedido();
		
		// filename completo del archivo
        String filename = UPLOAD_DIR + "/" + archivo.getOriginalFilename();
        log.info("Ruta de archivo almacenado "+filename);
        
        pedido.setDescripcionImpresion(descripcionImpresion);
		pedido.setMetodoPago(metodoPago);
		pedido.setTipoImpresion(tipoImpresion);
		pedido.setRutaArchivo(filename);
       
		Pedido nuevoPedido = pedidoService.create(pedido, archivo);
		//Validando si pedido fue creado o es null
		if(nuevoPedido != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@GetMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Pedido> result = pedidoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	/**
	 * 
	 * 
	 * @param idPedido
	 * @return Devuelve el Objeto Pedido, devuelve null y msje de error
	 */
	@ApiOperation(
			value = "Buscar Pedido",
			notes = "Puedes buscar Pedido por ID"
			)
	@GetMapping(path="/pedido/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable ("idPedido") Integer idPedido) {
		log.info("Buscando Pedido con ID "+ idPedido);
		
		Pedido pedido = pedidoService.findById(idPedido);
		//Validando si Pedido buscado existe
		if(pedido != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pedido);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Pedido con ID en sistema");
		}
	}
	
	
	/**
	 *  
	 * @param nuevoPedido
	 * @param idPedido
	 * @return Devuelve el objeto Pedido actualizado, en caso contrario devuelve null y msje de error
	 */
	@ApiOperation(
			value = "Actualizar Pedido",
			notes = "Puedes actualizar el pedido por ID y enviando un JSON con todos los parametros a actualizar pero sin enviar el archivo"
			)
	@PutMapping(path = "/pedido/{idPedido}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Pedido nuevoPedido, @PathVariable("idPedido") Integer idPedido) {
		
		log.info("Metodo PUT, Pedido es: "+idPedido+" Datos Pedido"+nuevoPedido);
		Pedido pedido = pedidoService.findById(idPedido);
		
		if(pedido != null) {
			pedido.setDescripcionImpresion(nuevoPedido.getDescripcionImpresion());
			pedido.setRutaArchivo(nuevoPedido.getRutaArchivo());
			pedido.setMetodoPago(nuevoPedido.getMetodoPago());
			pedido.setTipoImpresion(nuevoPedido.getTipoImpresion());
			
			nuevoPedido = pedidoService.update(pedido);
			return ResponseEntity.status(HttpStatus.OK).body(nuevoPedido);
			
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No existe el Pedido con ese ID en el sistema");
		}
	}
	
	
	/**
	 * 
	 * @param idPedido
	 * @return Devuelve Objeto Pedido eliminado, en caso contrario devuelve null y msje de error
	 */
	@ApiOperation(
			value = "Eliminar Pedido",
			notes = "Puedes Eliminar el pedido por ID"
			)
	@DeleteMapping(path = "/pedido/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity <?> delete(@PathVariable("idPedido") Integer idPedido ) {
		Pedido pedido = pedidoService.findById(idPedido);
			
		if(pedido != null) {
			pedidoService.delete(pedido);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedido);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe Pedido con el ID en el sistema");
		}
	}
}
