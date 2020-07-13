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
	
	//Folder donde se guardaran los documentos
	private static final String UPLOAD_DIR = "C:/pruebas";
		
	public Pedido create(Pedido nuevoPedido, MultipartFile archivo) throws IOException{
		
		// Validando si archivo viene vacio ó si el archivo ya existe en la carpeta        
        if(archivo.isEmpty() || new File(nuevoPedido.getRutaArchivo()).exists()) {
 			return null;
 		}
        
        
	    // Creando el directorio
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) { // crea el directorio si no existe
            fileSaveDir.mkdirs();
        }
 	    
        //Guardando el archivo PDF en directorio 
        FileOutputStream salidaArchivo = new FileOutputStream( nuevoPedido.getRutaArchivo() );
		salidaArchivo.write( archivo.getBytes() );
		salidaArchivo.close();
		
	 	return repositorioPedido.save(nuevoPedido);
        
	}

}
