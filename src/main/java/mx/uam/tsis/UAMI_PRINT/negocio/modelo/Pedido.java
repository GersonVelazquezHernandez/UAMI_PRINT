package mx.uam.tsis.UAMI_PRINT.negocio.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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
	@Id
	@GeneratedValue // Autogenera un ID unico
	private Integer idPedido;
	
	
	private String descripcionImpresion;
	
	@NotBlank
    private String rutaArchivo;
	
	@NotBlank
    private String tipoImpresion;
	
	@NotBlank
    private String metodoPago;
	
	@NotBlank
    private String precioTotal;
	
	private String nombreArchivo;

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", NombreArchivo=" + nombreArchivo + ", descripcionImpresion=" + descripcionImpresion + ", rutaArchivo="
				+ rutaArchivo + ", tipoImpresion=" + tipoImpresion + ", metodoPago=" + metodoPago + ", precioTotal="
				+ precioTotal + "]";
	}
	
	
	
	
    
    
}
