package ryan.nhg.gtg;

import java.io.Serializable;

/**
 * Created by ryan on 12/26/14.
 */

public class BusStop implements Serializable
{
    public String stopId, stopName;

    public BusStop(String stopId, String stopName)
    {
        this.stopId = stopId;
        this.stopName = stopName;
    }
}
