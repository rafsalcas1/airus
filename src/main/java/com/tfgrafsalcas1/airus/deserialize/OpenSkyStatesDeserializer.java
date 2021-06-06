package com.tfgrafsalcas1.airus.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.StateVector;
import com.tfgrafsalcas1.airus.documents.StateVectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class OpenSkyStatesDeserializer extends StdDeserializer<StateVectors> {
	
	public OpenSkyStatesDeserializer() {
		super(StateVectors.class);
	}

	private Collection<StateVector> deserializeStates(JsonParser jp) throws IOException {
		ArrayList<StateVector> result = new ArrayList<>();

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.START_ARRAY) {
				continue;
			}
			if (next == JsonToken.END_OBJECT) {
				break;
			}
			String icao24 = jp.getText();
			if ("null".equals(icao24)) {
				throw new JsonParseException("Got 'null' icao24", jp.getCurrentLocation());
			}
			Avion a = new Avion(icao24);
			StateVector sv = new StateVector(a);
			sv.setCallsign(jp.nextTextValue());
			sv.setOriginCountry(jp.nextTextValue());
			sv.setTimePosition((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setLastContact((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setLongitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setLatitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setBaroAltitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setOnGround(jp.nextBooleanValue());
			sv.setVelocity((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setTrueTrack((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setVerticalRate((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));

			// sensor serials if present
			next = jp.nextToken();
			if (next == JsonToken.START_ARRAY) {
				for (next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
					sv.addSensor(jp.getIntValue());
				}
			}

			sv.setGeoAltitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			sv.setSquawk(jp.nextTextValue());
			sv.setSpi(jp.nextBooleanValue());

			int psi = jp.nextIntValue(0);
			StateVector.PositionSource ps = psi <= StateVector.PositionSource.values().length ?
					StateVector.PositionSource.values()[psi] : StateVector.PositionSource.UNKNOWN;

			sv.setPositionSource(ps);
			next = jp.nextToken();
			while (next != null && next != JsonToken.END_ARRAY) {
				next = jp.nextToken();
			}
			jp.nextToken();

			result.add(sv);
		}

		return result;
	}

	@Override
	public StateVectors deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw dc.mappingException(StateVectors.class);
		}
		try {
			StateVectors res = new StateVectors();
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
				if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
					if ("time".equalsIgnoreCase(jp.getCurrentName())) {
						int t = jp.nextIntValue(0);
						Date time = new Date((long)(t)*1000);
						res.setTime(time);
					} else if ("states".equalsIgnoreCase(jp.getCurrentName())) {
						jp.nextToken();
						res.setStateVector(deserializeStates(jp));
					} else {
						jp.nextToken();
					}
				}
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(StateVectors.class);
		}
	}
}
