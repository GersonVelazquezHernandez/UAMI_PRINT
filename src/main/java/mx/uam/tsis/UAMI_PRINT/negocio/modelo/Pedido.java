package mx.uam.tsis.UAMI_PRINT.negocio.modelo;
import javax.persistence.Entity;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor//Te genera los constructores gracias a la dependencia de Lombok
@Builder 
@Data // Permite la creacion de geters y seters con la dependencia lombok
@Entity
public class Pedido {
	private String descripcionImpresion;
    private String rutaArchivo;
    private String tipoImpresion;
    private String metodoPago;
	@Override
	public String toString() {
		return "Pedido [descripcionImpresion=" + descripcionImpresion + ", rutaArchivo=" + rutaArchivo
				+ ", tipoImpresion=" + tipoImpresion + ", metodoPago=" + metodoPago + "]";
	}
    
    
}
