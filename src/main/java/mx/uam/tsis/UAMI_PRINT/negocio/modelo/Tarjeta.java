package mx.uam.tsis.UAMI_PRINT.negocio.modelo;





import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity//indica que hay que persistir en la BD
public class Tarjeta {
	@NotNull
	@ApiModelProperty(notes = "Numero de Tarjeta", required= true)
	@Id
	private Integer numeroTarjeta;
	@NotNull
	@ApiModelProperty(notes = "cvv de tarjeta", required= true)
	private Integer cvv;
	
	@NotNull
	@ApiModelProperty(notes = "FechaVencimineto", required= true)
	private String fechaVencimiento;
	
	@ApiModelProperty(notes = "Nombre del Encargado", required= false)
	private String nombreEncargado;
	///////////////////////////////////////////
	@NotNull
	@ApiModelProperty(notes = "Saldo de tarjeta", required= true)
	private double saldoTarjeta;
	///////////////////////////

}