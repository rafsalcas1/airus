package com.tfgrafsalcas1.airus.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Vuelo;
import com.tfgrafsalcas1.airus.documents.Vuelos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class OpenSkyVueloDeserializer extends StdDeserializer<Vuelos> {
	
	public OpenSkyVueloDeserializer() {
		super(Vuelos.class);
	}

	private Collection<Vuelo> deserializeFlights(JsonParser jp) throws IOException {
		ArrayList<Vuelo> result = new ArrayList<>();

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.END_ARRAY) {
				break;
			}

			Vuelo sv = new Vuelo();
			System.out.println(jp.currentName());
			Avion a = new Avion(jp.nextTextValue());
			sv.setAvion(a);
			System.out.println(sv.getAvion().getIcao24());
			jp.nextToken();
			sv.setFirstSeen((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null).intValue());
			jp.nextToken();
			sv.setEstDepartureAirport(jp.nextTextValue());
			jp.nextToken();
			sv.setLastSeen((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null).intValue());
			jp.nextToken();
			sv.setEstArrivalAirport(jp.nextTextValue());
			jp.nextToken();
			sv.setCallsign(jp.nextTextValue());
			jp.nextToken();
			sv.setEstDepartureAirportHorizDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstDepartureAirportVertDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstArrivalAirportHorizDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setEstArrivalAirportVertDistance((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setDepartureAirportCandidatesCount((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			sv.setArrivalAirportCandidatesCount((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			jp.nextToken();
			jp.nextToken();

			result.add(sv);
		}

		return result;
	}

	@Override
    public Vuelos deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		System.out.println(jp.getCurrentToken());
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_ARRAY) {
			throw dc.mappingException(Vuelos.class);
		}
		try {
			Vuelos res = new Vuelos();
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_ARRAY; jp.nextToken()) {
				res.setFlights(deserializeFlights(jp));
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(Vuelos.class);
		}
	}

}
