package com.tfgrafsalcas1.airus.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wayPoints")
public class WayPoint {
    
    @Id
    private String id;
    private Date time;
    private Double latitude;
    private Double longitude;
    private Double baroAltitude;
    private Double trueTrack;
    private boolean onGround;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getBaroAltitude() {
        return baroAltitude;
    }
    public void setBaroAltitude(Double baroAltitude) {
        this.baroAltitude = baroAltitude;
    }
    public Double getTrueTrack() {
        return trueTrack;
    }
    public void setTrueTrack(Double trueTrack) {
        this.trueTrack = trueTrack;
    }
    public boolean isOnGround() {
        return onGround;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baroAltitude == null) ? 0 : baroAltitude.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + (onGround ? 1231 : 1237);
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((trueTrack == null) ? 0 : trueTrack.hashCode());
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
        WayPoint other = (WayPoint) obj;
        if (baroAltitude == null) {
            if (other.baroAltitude != null)
                return false;
        } else if (!baroAltitude.equals(other.baroAltitude))
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
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (trueTrack == null) {
            if (other.trueTrack != null)
                return false;
        } else if (!trueTrack.equals(other.trueTrack))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "WayPoints [baroAltitude=" + baroAltitude + ", latitude=" + latitude + ", longitude=" + longitude
                + ", onGround=" + onGround + ", time=" + time + ", trueTrack=" + trueTrack + "]";
    }
    
    

}
