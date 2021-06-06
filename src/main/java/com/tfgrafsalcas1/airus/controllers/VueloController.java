package com.tfgrafsalcas1.airus.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tfgrafsalcas1.airus.api.VueloApi;
import com.tfgrafsalcas1.airus.documents.Vuelo;
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
	public static String saveVuelos(Model model, String begin, String end) throws IOException, ParseException {
		try{
			if(begin!=null && end!=null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
				Date t = dateFormat.parse(begin);
				Date t1 = dateFormat.parse(end);
				if(((t1.getTime()-t.getTime())/1000)<=7200 && t.getTime()<t1.getTime()){
					List<Vuelo> vuelos = new VueloApi().getFlights((int)(t.getTime()/1000),(int)(t1.getTime()/1000));
					System.out.println("Number of flights: " + vuelos.size());
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
				}else{
					model.addAttribute("mensaje", "El intervalo de tiempo no puede ser superior a dos horas. Y la fecha de inicio debe ser anterior a la de final.");
				}
				
			}
			return VIEWS_SEARCH_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}
		
	}
	
	@PostMapping(value = "/vuelos")
	public static String listVuelos(Model model, String dato, String opcion) throws IOException {
		try{
			if(opcion!=null){
				if(opcion.equals("salida")){
					if(dato!=null){
						List<Vuelo> vuelos = flightService.listVuelos().stream().filter(x->x.getEstDepartureAirport()!=null).filter(x->x.getEstDepartureAirport().equals(dato)).collect(Collectors.toList());
						System.out.println("Number of flights: " + vuelos.size());
						model.addAttribute("vuelos", vuelos);
					}
				}else if(opcion.equals("llegada")){
					if(dato!=null){
						List<Vuelo> vuelos = flightService.listVuelos().stream().filter(x->x.getEstArrivalAirport()!=null).filter(x->x.getEstArrivalAirport().equals(dato)).collect(Collectors.toList());
						System.out.println("Number of flights: " + vuelos.size());
						model.addAttribute("vuelos", vuelos);
					}
				}else{
					if(dato!=null){
						List<Vuelo> vuelos = flightService.listVuelos().stream().filter(x->x.getAvion().getIcao24()!=null).filter(x->x.getAvion().getIcao24().equals(dato)).collect(Collectors.toList());
						System.out.println("Number of flights: " + vuelos.size());
						model.addAttribute("vuelos", vuelos);
					}
				}
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
			Vuelo vuelo = flightService.getVuelo(id);
			model.addAttribute("vuelo", vuelo);
			return VIEWS_SHOW_FLIGHTS;
		}
		catch(Exception e) {
			model.addAttribute("mensaje", e.getMessage());
			return ERROR;
		}	
	}

}
