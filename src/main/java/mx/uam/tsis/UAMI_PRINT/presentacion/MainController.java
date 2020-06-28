package mx.uam.tsis.UAMI_PRINT.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/*Controlador web de capa de presentación*/
@Controller
@Slf4j
public class MainController {
	
	/*Raíz de la página regresa index.html*/
	@GetMapping("/")
	public String index() {
		log.info("*****En Index******");
		return "index";
	}
	
	@RequestMapping("/ejemplo")
	@ResponseBody
	public String ejemplo() {
		return "Esto es unejemplo";
	}
}
