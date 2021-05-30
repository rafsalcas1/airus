package com.tfgrafsalcas1.airus.deserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tfgrafsalcas1.airus.documents.Avion;
import com.tfgrafsalcas1.airus.documents.Rastro;
import com.tfgrafsalcas1.airus.documents.WayPoint;

public class OpenSkyRastroDeserializer extends StdDeserializer<Rastro> {
    
    public OpenSkyRastroDeserializer() {
		super(Rastro.class);
	}

	private Collection<WayPoint> deserializePath(JsonParser jp) throws IOException {
		ArrayList<WayPoint> result = new ArrayList<>();

		for (JsonToken next = jp.nextToken(); next != null && next != JsonToken.END_ARRAY; next = jp.nextToken()) {
			if (next == JsonToken.START_ARRAY) {
				continue;
			}
			if (next == JsonToken.END_OBJECT) {
				break;
            }
			WayPoint wp = new WayPoint();
            Date time = new Date((long)(jp.getLongValue())*1000);
			wp.setTime(time);
			wp.setLatitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			wp.setLongitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			wp.setBaroAltitude((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			wp.setTrueTrack((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null));
			wp.setOnGround(jp.nextBooleanValue());

			result.add(wp);
		}
		return result;
	}

    @Override
	public Rastro deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
		if (jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw dc.mappingException(Rastro.class);
		}
		try {
			Rastro res = new Rastro();
			for (jp.nextToken(); jp.getCurrentToken() != null && jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
				if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
					if ("icao24".equalsIgnoreCase(jp.getCurrentName())) {
						String icao24 = ((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getText() : null));
						Avion avion = new Avion(icao24);
						res.setAvion(avion);
                    } else if ("callsign".equalsIgnoreCase(jp.getCurrentName())) {
						String callsign = ((jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getText() : null));
						res.setCallsign(callsign);
					} else if ("startTime".equalsIgnoreCase(jp.getCurrentName())) {
						double t1 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
						Date startTime = new Date((long)(t1)*1000);
						res.setStarTime(startTime);
					} else if ("endTime".equalsIgnoreCase(jp.getCurrentName())) {
						double t2 = (jp.nextToken() != null && jp.getCurrentToken() != JsonToken.VALUE_NULL ? jp.getDoubleValue() : null);
						Date endTime = new Date((long)(t2)*1000);
						res.setEndTime(endTime);
					} else if ("path".equalsIgnoreCase(jp.getCurrentName())) {
						jp.nextToken();
						res.setPath(deserializePath(jp));
					} else {
						jp.nextToken();
					}
				}
			}
			return res;
		} catch (JsonParseException jpe) {
			throw dc.mappingException(Rastro.class);
		}
	}
}
