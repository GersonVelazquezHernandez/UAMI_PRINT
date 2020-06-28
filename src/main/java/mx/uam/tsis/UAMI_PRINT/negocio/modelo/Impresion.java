package mx.uam.tsis.UAMI_PRINT.negocio.modelo;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class Impresion {
	private String comentario;
    private MultipartFile[] archivo;
}
