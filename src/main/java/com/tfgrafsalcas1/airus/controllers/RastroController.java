package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RastroController {

	private static final String VIEWS_SAVE_RASTRO = "rastro/rastros";
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
	public static String rastroVuelo(Model model, String icao24, Integer lastSeen) throws IOException {
		try{
			Rastro rastro = new RastroApi().getRastro(lastSeen, icao24);
			if(avionService.getAvion(rastro.getAvion().getIcao24())==null){
				avionService.saveAvion(rastro.getAvion());
				rastroService.saveRastro(rastro);
				for(WayPoint wayPoint : rastro.getPath()){
					wayPointService.saveWayPoint(wayPoint);
				}
			}else{
				boolean b = false;
				for(Rastro r : rastroService.getRastroAvion(rastro.getAvion())){
					if(r.equals(rastro)){
						if(!(rastro.getPath().equals(r.getPath()))){
							r.setPath(rastro.getPath());
						}
						b = true;
						break;
					}
				}
				if(!b){
					rastroService.saveRastro(rastro);
					for(WayPoint wayPoint : rastro.getPath()){
						wayPointService.saveWayPoint(wayPoint);
					}
				}
			}
			model.addAttribute("rastro", rastro);
			return VIEWS_SAVE_RASTRO;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

	@PostMapping(value = "/vuelos/rastro/path")
	public static String listVuelos(Model model, WayPoint path) throws IOException {
		try{
			System.out.println(path);
			return VIEWS_PATH;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}
}
