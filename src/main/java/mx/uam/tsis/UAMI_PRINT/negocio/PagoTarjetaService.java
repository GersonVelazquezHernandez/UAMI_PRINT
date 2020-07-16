package mx.uam.tsis.UAMI_PRINT.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.UAMI_PRINT.datos.RepositorioTarjeta;
import mx.uam.tsis.UAMI_PRINT.negocio.modelo.Tarjeta;

@Slf4j
@Service
public class PagoTarjetaService {
	@Autowired
	private RepositorioTarjeta repositorioTarjeta;

	/**
	 * Creacion de una tarjeta
	 * 
	 * @param nuevoTarjeta ojeto tarjeta a crear
	 * @return devuelve la tarjeta que fue creada
	 */
	public Tarjeta create(Tarjeta nuevaTarjeta) {
		log.info("entre al tarjetaService " + nuevaTarjeta);
		Tarjeta tarjetaP = repositorioTarjeta.save(nuevaTarjeta);
		log.info("entre y voy a regresar a " + tarjetaP);
		return tarjetaP;
	}

	public Iterable<Tarjeta> retrieveAll() {
		return repositorioTarjeta.findAll();
	}

	public Tarjeta retrieve(Integer id) {
		Optional<Tarjeta> tarjetaOp = repositorioTarjeta.findById(id);
		return tarjetaOp.get();

	}
	/////////////////////////////////////////////
	public boolean retrievePago(Integer numeroTarjeta, Integer cvv, String fechaE, double costoCompra){
		
		Optional<Tarjeta> tarjetaOp = repositorioTarjeta.findById(numeroTarjeta);
		log.info("LA TARJETA CONTIENE "+ tarjetaOp);
		
		if(tarjetaOp.get().getCvv().equals(cvv) && tarjetaOp.get().getFechaVencimiento().equals(fechaE)) {
			log.info("entre al el if del retrive pago ");
			
			Tarjeta nuevaTarjeta= new Tarjeta();
			
			double monto =tarjetaOp.get().getSaldoTarjeta()-costoCompra;
			log.info("monto :" + monto);
			
			nuevaTarjeta= tarjetaOp.get();
			
			nuevaTarjeta.setSaldoTarjeta(monto);
			
			repositorioTarjeta.save(nuevaTarjeta);
			return true;
		}
		
		return false;

	}
	/////////////////////////////////////////////
	

	public Tarjeta update(Integer id, Tarjeta tarjetaActualizar) {
		Optional<Tarjeta> tarjeta = repositorioTarjeta.findById(id);
		if (!tarjeta.isPresent()) {
			return repositorioTarjeta.save(tarjetaActualizar);
		} else {
			return null;
		}
	}

	public boolean delete(Integer id) {
		repositorioTarjeta.deleteById(id);
		Optional<Tarjeta> tarjeta = repositorioTarjeta.findById(id);
		return tarjeta.isPresent();
	}

}// fin de tarjetaService
