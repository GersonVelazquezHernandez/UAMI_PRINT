package mx.uam.tsis.UAMI_PRINT.negocio.modelo;

import javax.persistence.Entity;

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
@Entity
public class Usuario {
	@NotNull       //Valida al pasar por controllerRest que esté campo no sea nulo
	@ApiModelProperty(notes="Matricula del usuario", required=true)  //Aparecen en la descripcion de parametros de swagger como un requisito para utilizarlo por metodos CRUD
	@Id            //Indica a JPA(bd) que esté sera su id (llave primaria)
	private Integer matricula;
	
	@NotBlank      //Valida al pasar por controllerRest que este campo no este vacio
	@ApiModelProperty(notes="Nombre del usuario", required=true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes="Apellido del usuario", required=true)
	private String apellido;
	
	@NotBlank
	@ApiModelProperty(notes="Email del usuario", required = true)
	private String email;
	
	@NotBlank
	@ApiModelProperty(notes="Nombre_Cuenta_Usuario del usuario", required=true)
	private String usuario;
	
	@NotBlank
	@ApiModelProperty(notes="Contraseña_Cuenta_Usuario del usuario", required=true)
	private String contrasena;

}
