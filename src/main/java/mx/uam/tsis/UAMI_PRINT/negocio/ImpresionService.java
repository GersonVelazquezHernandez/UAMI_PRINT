package mx.uam.tsis.UAMI_PRINT.negocio;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ImpresionService {
	private String comentario;
    private MultipartFile[] archivo;
}
