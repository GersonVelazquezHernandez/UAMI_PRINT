package mx.uam.tsis.UAMI_PRINT.servicio;


import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping(path = "/descargar-archivo", produces = "application/json")
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
        /*
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
        //return new ResponseEntity(HttpStatus.ACCEPTED);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
       */
		Pedido nuevoPedido = pedidoService.create(pedido, archivo);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
	}
}
