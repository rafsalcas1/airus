package com.tfgrafsalcas1.airus.documents;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class StateVector {
    
    private String icao24;
    private String callsign;
    private String originCountry;
    private Double timePosition;
    private Double lastContact;
    private Double longitude;
    private Double latitude;
    private Double baroAltitude;
    private boolean onGround;
    private Double velocity;
    private Double trueTrack;
    private Double verticalRate;
    private Double geoAltitude;
    private String squawk;
    private boolean spi;
    private PositionSource positionSource;
    
    private Set<Integer> sensors;

    public StateVector(String icao24) {
		if (icao24 == null) throw new RuntimeException("Invalid icao24. Must not be null");
		this.icao24 = icao24;
		this.sensors = null;
	}

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public Double getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(Double timePosition) {
        this.timePosition = timePosition;
    }

    public Double getLastContact() {
        return lastContact;
    }

    public void setLastContact(Double lastContact) {
        this.lastContact = lastContact;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getBaroAltitude() {
        return baroAltitude;
    }

    public void setBaroAltitude(Double baroAltitude) {
        this.baroAltitude = baroAltitude;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public Double getTrueTrack() {
        return trueTrack;
    }

    public void setTrueTrack(Double trueTrack) {
        this.trueTrack = trueTrack;
    }

    public Double getVerticalRate() {
        return verticalRate;
    }

    public void setVerticalRate(Double verticalRate) {
        this.verticalRate = verticalRate;
    }

    public Double getGeoAltitude() {
        return geoAltitude;
    }

    public void setGeoAltitude(Double geoAltitude) {
        this.geoAltitude = geoAltitude;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public boolean isSpi() {
        return spi;
    }

    public void setSpi(boolean spi) {
        this.spi = spi;
    }

    public PositionSource getPositionSource() {
        return positionSource;
    }

    public void setPositionSource(PositionSource positionSource) {
        this.positionSource = positionSource;
    }

	public void addSensor(int sensor) {
		if (this.sensors == null) {
			this.sensors = new HashSet<>();
		}
		this.sensors.add(sensor);
	}

    public Set<Integer> getSensors() {
        return sensors;
    }

    @Override
    public String toString() {
        return "StateVector [baroAltitude=" + baroAltitude + ", callsign=" + callsign + ", geoAltitude=" + geoAltitude
                + ", icao24=" + icao24 + ", lastContact=" + lastContact + ", latitude=" + latitude + ", longitude="
                + longitude + ", onGround=" + onGround + ", originCountry=" + originCountry + ", positionSource="
                + positionSource + ", sensors=" + sensors + ", spi=" + spi + ", squawk=" + squawk + ", timePosition="
                + timePosition + ", trueTrack=" + trueTrack + ", velocity=" + velocity + ", verticalRate="
                + verticalRate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baroAltitude == null) ? 0 : baroAltitude.hashCode());
        result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
        result = prime * result + ((geoAltitude == null) ? 0 : geoAltitude.hashCode());
        result = prime * result + ((icao24 == null) ? 0 : icao24.hashCode());
        result = prime * result + ((lastContact == null) ? 0 : lastContact.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + (onGround ? 1231 : 1237);
        result = prime * result + ((originCountry == null) ? 0 : originCountry.hashCode());
        result = prime * result + ((positionSource == null) ? 0 : positionSource.hashCode());
        result = prime * result + ((sensors == null) ? 0 : sensors.hashCode());
        result = prime * result + (spi ? 1231 : 1237);
        result = prime * result + ((squawk == null) ? 0 : squawk.hashCode());
        result = prime * result + ((timePosition == null) ? 0 : timePosition.hashCode());
        result = prime * result + ((trueTrack == null) ? 0 : trueTrack.hashCode());
        result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
        result = prime * result + ((verticalRate == null) ? 0 : verticalRate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StateVector other = (StateVector) obj;
        if (baroAltitude == null) {
            if (other.baroAltitude != null)
                return false;
        } else if (!baroAltitude.equals(other.baroAltitude))
            return false;
        if (callsign == null) {
            if (other.callsign != null)
                return false;
        } else if (!callsign.equals(other.callsign))
            return false;
        if (geoAltitude == null) {
            if (other.geoAltitude != null)
                return false;
        } else if (!geoAltitude.equals(other.geoAltitude))
            return false;
        if (icao24 == null) {
            if (other.icao24 != null)
                return false;
        } else if (!icao24.equals(other.icao24))
            return false;
        if (lastContact == null) {
            if (other.lastContact != null)
                return false;
        } else if (!lastContact.equals(other.lastContact))
            return false;
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
            return false;
        if (onGround != other.onGround)
            return false;
        if (originCountry == null) {
            if (other.originCountry != null)
                return false;
        } else if (!originCountry.equals(other.originCountry))
            return false;
        if (positionSource == null) {
            if (other.positionSource != null)
                return false;
        } else if (!positionSource.equals(other.positionSource))
            return false;
        if (sensors == null) {
            if (other.sensors != null)
                return false;
        } else if (!sensors.equals(other.sensors))
            return false;
        if (spi != other.spi)
            return false;
        if (squawk == null) {
            if (other.squawk != null)
                return false;
        } else if (!squawk.equals(other.squawk))
            return false;
        if (timePosition == null) {
            if (other.timePosition != null)
                return false;
        } else if (!timePosition.equals(other.timePosition))
            return false;
        if (trueTrack == null) {
            if (other.trueTrack != null)
                return false;
        } else if (!trueTrack.equals(other.trueTrack))
            return false;
        if (velocity == null) {
            if (other.velocity != null)
                return false;
        } else if (!velocity.equals(other.velocity))
            return false;
        if (verticalRate == null) {
            if (other.verticalRate != null)
                return false;
        } else if (!verticalRate.equals(other.verticalRate))
            return false;
        return true;
    }

	public enum PositionSource {
		ADS_B,
		ASTERIX,
		MLAT,
		FLARM,
		UNKNOWN
	}

}
