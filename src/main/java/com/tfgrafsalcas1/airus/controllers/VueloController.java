package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tfgrafsalcas1.airus.api.VueloApi;
import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.documents.Vuelos;
import com.tfgrafsalcas1.airus.services.AvionService;
import com.tfgrafsalcas1.airus.services.VueloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VueloController {

	private static final String VIEWS_SEARCH_FLIGHTS = "vuelos/buscar_vuelos";
	private static final String VIEWS_LIST_FLIGHTS = "vuelos/vuelos";
	private static final String VIEWS_SHOW_FLIGHTS = "vuelos/mostrar";
	private static final String ERROR = "error";

	private static VueloService flightService;
	private static AvionService avionService;

	@Autowired
	public VueloController(VueloService flightService, AvionService avionService) {
		this.flightService = flightService;
		this.avionService = avionService;
	}

	@GetMapping(value = "/vuelos/buscar")
	public static String saveVuelos(Model model) throws IOException {
		try{
			return VIEWS_SEARCH_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}
		
	}

	@PostMapping(value = "/vuelos/buscar")
	public static String saveVuelos(Model model, Integer begin, Integer end) throws IOException {
		try{
			if(begin!=null && end!=null){
				Vuelos flights = new VueloApi().getFlights(begin,end);
				System.out.println("Number of flights: " + flights.getFlights().size());
				Collection<Vuelo> vuelos = flights.getFlights();
				for(Vuelo vuelo: vuelos){
					if(avionService.getAvion(vuelo.getAvion().getIcao24())==null){
						avionService.saveAvion(vuelo.getAvion());
						flightService.saveFlight(vuelo);
					}else{
						boolean b = false;
						for(Vuelo v : flightService.getVuelosAvion(vuelo.getAvion())){
							if(v.getLastSeen().compareTo(vuelo.getLastSeen())==0){
								b = true;
								break;
							}
						}
						if(!b){
							flightService.saveFlight(vuelo);
						}
					}
				}
				model.addAttribute("vuelos", vuelos);
			}
			return VIEWS_SEARCH_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}
		
	}
	
	@PostMapping(value = "/vuelos")
	public static String listVuelos(Model model, String estDepartureAirport) throws IOException {
		try{
			if(estDepartureAirport!=null){
				List<Vuelo> vuelos = flightService.listVuelos().stream().filter(x->x.getEstDepartureAirport()!=null).filter(x->x.getEstDepartureAirport().equals(estDepartureAirport)).collect(Collectors.toList());
	        	System.out.println("Number of flights: " + vuelos.size());
				model.addAttribute("vuelos", vuelos);
				System.out.println("Number of flights: " + flightService.listVuelos().size());
			}
			return VIEWS_LIST_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

	@GetMapping(value = "/vuelos")
	public static String listVuelos(Model model) throws IOException {
		try{
			return VIEWS_LIST_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

	@GetMapping(value = "/vuelos/mostrar")
	public static String mostrarVuelo(Model model, String id) throws IOException {
		try{
			Optional<Vuelo> vuelo = flightService.getVuelo(id);
			System.out.println(vuelo.toString());
			model.addAttribute("vuelo", vuelo);
			System.out.println("Vuelo " + id);
			return VIEWS_SHOW_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

}
