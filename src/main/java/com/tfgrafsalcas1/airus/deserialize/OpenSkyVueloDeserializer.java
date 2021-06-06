package com.tfgrafsalcas1.airus.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Vuelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OpenSkyVueloDeserializer extends StdDeserializer<List<Vuelo>> {
	
	public OpenSkyVueloDeserializer() {
		super(List.class);
	}

	private Collection<Vuelo> deserializeFlights(JsonParser jp) throws IOException {
		ArrayList<Vuelo> result = new ArrayList<>();

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.END_ARRAY) {
				break;
			}

			Vuelo sv = new Vuelo();
			Avion a = new Avion(jp.nextTextValue());
			sv.setAvion(a);
			jp.nextToken();
			double t1 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
			Date firstSeen = new Date((long)(t1)*1000);
			sv.setFirstSeen(firstSeen);
			jp.nextToken();
			sv.setEstDepartureAirport(jp.nextTextValue());
			jp.nextToken();
			double t2 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
			Date lastSeen = new Date((long)(t2)*1000);
			sv.setLastSeen(lastSeen);
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
    public List<Vuelo> deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		System.out.println(jp.getCurrentToken());
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_ARRAY) {
			throw dc.mappingException(List.class);
		}
		try {
			List<Vuelo> res = new LinkedList();
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_ARRAY; jp.nextToken()) {
				res.addAll(deserializeFlights(jp));
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(List.class);
		}
	}

}
