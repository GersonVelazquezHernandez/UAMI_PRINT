package mx.uam.tsis.UAMI_PRINT.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.negocio.PagoTarjetaService;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Tarjeta;

/**
 * Controlador del Pago con tarjeta
 * 
 * @author equipo UAMI_PRINT
 *
 */
@RestController
@Slf4j

public class PagoTarjetaController {
	@Autowired
	private PagoTarjetaService tarjetaService;
	@ApiOperation(value = "Insertar una Tarjeta", notes = "Permite Insertar una nueva Tarjeta ")
	@PostMapping(path = "/tarjeta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Tarjeta nuevaTarjeta) { 

		Tarjeta tarjeta = tarjetaService.create(nuevaTarjeta);

		if (tarjeta != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(tarjeta);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede añadir la tarjeta");
		}

	}

	@ApiOperation(value = "Consultar Tarjetas", notes = "Permite Consultar todos las tarjetas  existentes")

	@GetMapping(path = "/tarjeta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll() {

		Iterable<Tarjeta> result = tarjetaService.retrieveAll();

		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
	@ApiOperation(value = "Consultar Tarjetas por identificador", notes = "Permite Consultar todos las tarjetas con identificador especifico")
	@GetMapping(path = "/tarjeta/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("id") @Valid Integer id) {

		Tarjeta tarjeta = tarjetaService.retrieve(id);
		if (tarjeta != null) {
			return ResponseEntity.status(HttpStatus.OK).body(tarjeta);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}
	//////////////////////////////////////////
	@ApiOperation(value = "Consultar Saldo y realizar el pago", notes = "Permite Consultar todos las tarjetas con identificador especifico")
	@GetMapping(path = "/pagoTarjeta/{numeroTarjeta}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveSaldo(@PathVariable("numeroTarjeta") @Valid Integer numeroTarjeta,
											@RequestParam("cvv") @Valid Integer cvv,
											@RequestParam("fechaE") @Valid String fechaE,
											@RequestParam("CostoCompra") @Valid Double costoCompra){ 

		log.info(numeroTarjeta.toString());
		log.info(costoCompra.toString());
		boolean tarjeta = tarjetaService.retrievePago(numeroTarjeta, cvv, fechaE, costoCompra);
		log.info("pasepor tarjetaController");
		if (tarjeta ==true) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}
	//////////////////////////////////////////

	@ApiOperation(value = "Actualiza datos de una Tarjeta", notes = "Actualiza los datos de tarjeta existente")
	@PutMapping(path = "/tarjeta/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizar(@PathVariable @Valid int id, @RequestBody @Valid Tarjeta tarjeta) {

		tarjetaService.update(id, tarjeta);
		return ResponseEntity.status(HttpStatus.CREATED).body(tarjeta);

	}

	@ApiOperation(value = "Eliminar tarjeta", notes = "Permite Eliminar un tarjeta existente")
	@DeleteMapping(path = "/tarjeta/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") @Valid Integer id) {
		log.info("Buscando al tarjeta con identificador para eliminarlo" + id);
		Tarjeta tarjeta = tarjetaService.retrieve(id);
		if (tarjeta != null) {
			tarjetaService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(id);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

}
