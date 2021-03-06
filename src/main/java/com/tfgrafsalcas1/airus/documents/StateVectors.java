package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;

import lombok.Data;

@Data
public class StateVectors {
    
    private Integer time;
    private Collection<StateVector> stateVector;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Collection<StateVector> getStateVector() {
        return stateVector;
    }

    public void setStateVector(Collection<StateVector> stateVector) {
        this.stateVector = stateVector;
    }

    @Override
    public String toString() {
        return "StateVectors [stateVector=" + stateVector + ", time=" + time + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stateVector == null) ? 0 : stateVector.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
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
        StateVectors other = (StateVectors) obj;
        if (stateVector == null) {
            if (other.stateVector != null)
                return false;
        } else if (!stateVector.equals(other.stateVector))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }
    
}
