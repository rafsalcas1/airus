package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.tfgrafsalcas1.airus.api.RastroApi;
import com.tfgrafsalcas1.airus.documents.Rastro;
import com.tfgrafsalcas1.airus.documents.WayPoint;
import com.tfgrafsalcas1.airus.services.AvionService;
import com.tfgrafsalcas1.airus.services.RastroService;
import com.tfgrafsalcas1.airus.services.WayPointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RastroController {

	private static final String VIEWS_MOSTRAR_RASTRO = "rastro/mostrar_rastros";
	private static final String VIEWS_PATH = "rastro/path";
	private static final String ERROR = "error";

	private static RastroService rastroService;
	private static AvionService avionService;
	private static WayPointService wayPointService;

	@Autowired
	public RastroController(RastroService rastroService, AvionService avionService, WayPointService wayPointService) {
		this.rastroService = rastroService;
		this.avionService = avionService;
		this.wayPointService = wayPointService;
	}

	@GetMapping(value = "/vuelos/rastro")
	public static String rastroVuelo(Model model, String icao24, String firstSeen) throws IOException, ParseException {
		try{
			DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
			Date t = dateFormat.parse(firstSeen);
			Integer l = (int) (t.getTime() / 1000);
			Long now = new Date().getTime();
			Rastro existe = rastroService.existeRastro(icao24, t);
			if(!(((now - l) / 1000)>2505600)){
				Rastro rastro = new RastroApi().getRastro(l/1000, icao24);
				if(avionService.getAvion(rastro.getAvion().getIcao24())==null){
					avionService.saveAvion(rastro.getAvion());
					rastroService.saveRastro(rastro);
					for(WayPoint wayPoint : rastro.getPath()){
						wayPointService.saveWayPoint(wayPoint);
					}
				}else{
					if(existe!=null){
						if(!(rastro.getPath().equals(existe.getPath()))){
							existe.setPath(rastro.getPath());
						}
					}else {
						rastroService.saveRastro(rastro);
						for(WayPoint wayPoint : rastro.getPath()){
							wayPointService.saveWayPoint(wayPoint);
						}
					}
				}
				model.addAttribute("rastro", rastro);
				return VIEWS_MOSTRAR_RASTRO;
			}else if(existe!=null){
				model.addAttribute("rastro", existe);
				return VIEWS_MOSTRAR_RASTRO;
			}else{
				model.addAttribute("mensaje", "El vuelo es anterior a treinta días y no está guardado en la base de datos.");
				return VIEWS_MOSTRAR_RASTRO;
			}
			
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

	@GetMapping(value = "/vuelos/rastro/path")
	public static String pathRastro(Model model, String rastro_id) throws IOException {
		try{
			System.out.println(rastro_id);
			Rastro rastro = rastroService.getRastro(rastro_id);
			model.addAttribute("wayPoints", rastro.getPath());
			return VIEWS_PATH;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}
}
