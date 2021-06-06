package com.tfgrafsalcas1.airus.api;

import com.tfgrafsalcas1.airus.deserialize.OpenSkyStatesDeserializer;
import com.tfgrafsalcas1.airus.documents.BoundingBox;
import com.tfgrafsalcas1.airus.documents.StateVectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StateVectorsApi {
	private static final String HOST = "opensky-network.org";
	private static final String API_ROOT = "https://" + HOST + "/api";
	private static final String STATES_URI = API_ROOT + "/states/all";

	private enum REQUEST_TYPE {
		GET_STATES,
		GET_MY_STATES
	}

	private final boolean authenticated;

	private final ObjectMapper mapper;

	private final OkHttpClient okHttpClient;
	private final Map<REQUEST_TYPE, Long> lastRequestTime;


	private static class BasicAuthInterceptor implements Interceptor {
		private final String credentials;

		BasicAuthInterceptor(String username, String password){
			credentials = Credentials.basic(username, password);
		}

		@Override
		public Response intercept(Chain chain) throws IOException {
			Request req = chain.request()
					.newBuilder()
					.header("Authorization", credentials)
					.build();
			return chain.proceed(req);
		}
	}

	/**
	 * Create an instance of the API for anonymous access.
	 */
	public StateVectorsApi() {
		this(null, null);
	}

	public StateVectorsApi(String username, String password) {
		lastRequestTime = new HashMap<>();
		mapper = new ObjectMapper();
		SimpleModule sm = new SimpleModule();
		sm.addDeserializer(StateVectors.class, new OpenSkyStatesDeserializer());
		mapper.registerModule(sm);

		authenticated = username != null && password != null;

		if (authenticated) {
			okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
					.addInterceptor(new BasicAuthInterceptor(username, password))
					.build();
		} else {
            okHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build();
		}
	}

	private StateVectors getResponse(String baseUri, Collection<AbstractMap.Entry<String,String>> nvps) throws IOException {
		HttpUrl parsedUrl = HttpUrl.parse(baseUri);
		if (parsedUrl == null) {
			throw new MalformedURLException("Could not parse uri " + baseUri);
		}

		HttpUrl.Builder urlBuilder = parsedUrl.newBuilder();
		for (AbstractMap.Entry<String,String> nvp : nvps) {
			urlBuilder.addQueryParameter(nvp.getKey(), nvp.getValue());
		}
		Request req = new Request.Builder()
				.url(urlBuilder.build())
				.build();

		Response response = okHttpClient.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Could not get OpenSky Vectors, response " + response);
		}

		String contentType = response.header("Content-Type");
		Charset charset = null;
		if (contentType != null) {
			MediaType mediaType = MediaType.parse(contentType);
			if (mediaType != null) {
				charset = mediaType.charset();
			}
		}
		if (charset != null) {
			return mapper.readValue(new InputStreamReader(response.body().byteStream(), charset), StateVectors.class);
		} else {
			throw new IOException("Could not read charset in response. Content-Type is " + contentType);
		}
	}


	private boolean checkRateLimit(REQUEST_TYPE type, long timeDiffAuth, long timeDiffNoAuth) {
		Long t = lastRequestTime.get(type);
		long now = System.currentTimeMillis();
		lastRequestTime.put(type, now);
		return (t == null || (authenticated && now - t > timeDiffAuth) || (!authenticated && now - t > timeDiffNoAuth));
	}

	private StateVectors getOpenSkyStates(String baseUri, ArrayList<AbstractMap.Entry<String,String>> nvps) throws IOException {
		try {
			return getResponse(baseUri, nvps);
		} catch (MalformedURLException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Invalid URI. Please report a bug");
		} catch (JsonParseException | JsonMappingException e) {
			// this should not happen
			e.printStackTrace();
			throw new RuntimeException("Programming Error in OpenSky API. Could not parse JSON Data. Please report a bug");
		}
	}

	/**
	 * Retrieve state vectors for a given time. If time == 0 the most recent ones are taken.
	 * Optional filters might be applied for ICAO24 addresses.
	 *
	 * @param time Unix time stamp (seconds since epoch).
	 * @param icao24 retrieve only state vectors for the given ICAO24 addresses. If {@code null}, no filter will be applied on the ICAO24 address.
	 * @return {@link StateVectors} if request was successful, {@code null} otherwise or if there's no new data/rate limit reached
	 * @throws IOException if there was an HTTP error
	 */
	public StateVectors getStates(int time, String[] icao24) throws IOException {
		ArrayList<AbstractMap.Entry<String,String>> nvps = new ArrayList<>();
		if (icao24 != null) {
			for (String i : icao24) {
				nvps.add(new AbstractMap.SimpleImmutableEntry<>("icao24", i));
			}
		}
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("time", Integer.toString(time)));
		return checkRateLimit(REQUEST_TYPE.GET_STATES, 4900, 9900) ? getOpenSkyStates(STATES_URI, nvps) : null;
	}

	/**
	 * Retrieve state vectors for a given time. If time == 0 the most recent ones are taken.
	 * Optional filters might be applied for ICAO24 addresses.
	 * Furthermore, data can be retrieved for a certain area by using a bounding box.
	 *
	 * @param time Unix time stamp (seconds since epoch).
	 * @param icao24 retrieve only state vectors for the given ICAO24 addresses. If {@code null}, no filter will be applied on the ICAO24 address.
	 * @param bbox bounding box to retrieve data for a certain area. If {@code null}, no filter will be applied on the position.
	 * @return {@link StateVectors} if request was successful, {@code null} otherwise or if there's no new data/rate limit reached
	 * @throws IOException if there was an HTTP error
	 */
	public StateVectors getStates(int time, String[] icao24, BoundingBox bbox) throws IOException {
		if (bbox == null) return getStates(time, icao24);

		ArrayList<AbstractMap.Entry<String,String>> nvps = new ArrayList<>();
		if (icao24 != null) {
			for (String i : icao24) {
				nvps.add(new AbstractMap.SimpleImmutableEntry<>("icao24", i));
			}
		}
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("time", Integer.toString(time)));
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("lamin", Double.toString(bbox.getMinLatitude())));
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("lamax", Double.toString(bbox.getMaxLatitude())));
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("lomin", Double.toString(bbox.getMinLongitude())));
		nvps.add(new AbstractMap.SimpleImmutableEntry<>("lomax", Double.toString(bbox.getMaxLongitude())));
		return checkRateLimit(REQUEST_TYPE.GET_STATES, 4900, 9900) ? getOpenSkyStates(STATES_URI, nvps) : null;
	}
}
