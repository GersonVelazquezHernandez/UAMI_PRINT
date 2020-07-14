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
		Optional<Tarjeta> alumnoOp = repositorioTarjeta.findById(id);
		return alumnoOp.get();

	}

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
