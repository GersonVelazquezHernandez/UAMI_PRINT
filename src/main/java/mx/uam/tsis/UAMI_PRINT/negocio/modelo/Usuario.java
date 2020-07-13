package mx.uam.tsis.UAMI_PRINT.negocio.modelo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	
	/*
	//Podriamos decir que esta es una identiada foranea para la base de datos ya que podemos tener multiples pedidos un cliente (cardinalidad multiple)
		@Builder.Default
		@OneToMany(targetEntity = Pedido.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE) //Decimos que es de uno a muchos, con el Lazy le decimos que recorra su lista de alumnos y nos devuelba solo ese alumno y no todos de forma anticipada
		@JoinColumn(name = "idPedido") // No crea tabla intermedia	
		private List <Pedido> alumnos = new ArrayList<>();
	*/

}
