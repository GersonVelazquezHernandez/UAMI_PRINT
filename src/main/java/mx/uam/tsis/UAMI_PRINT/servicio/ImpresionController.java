package mx.uam.tsis.UAMI_PRINT.servicio;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Impresion;
/*Controlador tipo rest de capa de servicio*/
@RestController
@Slf4j
public class ImpresionController {
	
	
	private Map <Integer,MultipartFile> listaImpresion = new HashMap<>();
	
	@PostMapping("/cargar-archivo")
	@ResponseBody
	public ResponseEntity<String> fileUpload(/*Impresion impresion*/String comentario,MultipartFile archivo) {
	    try {
	    	/*log.info(impresion.getComentario());
	    	log.info(impresion.getArchivo().toString());*/
	        listaImpresion.put(1, archivo);
	        log.info(comentario);
	        log.info(archivo.getOriginalFilename());
	        log.info("Holi desde el backend mandame un zing"+listaImpresion.get(1).toString());
	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	        return new ResponseEntity<>("Formato Invalido!!", HttpStatus.BAD_REQUEST);
	    }

	    return new ResponseEntity<>("Archivo Cargado!!", HttpStatus.CREATED);
	}
}
