package ryan.nhg.gtg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by ryan on 12/24/14.
 */
public class LocationLayout extends LinearLayout implements Layout
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private BusStopList busStopList;

    public LocationLayout(Context context)
    {
        super(context);

        this.context = context;

        setProperties();
        initLayouts();
    }

    private void setProperties()
    {
        LinearLayout.LayoutParams params = new LayoutParams(
                             ViewGroup.LayoutParams.MATCH_PARENT,
                             ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    private void initLayouts()
    {
        //  Initialize BusStopList
        busStopList = new BusStopList(context);

        //  Add BusStopList
        this.addView(busStopList);
    }

    private void getBusStops()
    {
        //  USE GPS LAT AND LON TO GET BUS STOPS NEAR PERSON
        new DataGrabber(busStopList).execute(""+DataGrabber.GET_LOCATION_BUS_STOPS,""+Global.latitude,""+Global.longitude);
    }

    public void locationReady()
    {
        getBusStops();
    }

    public void close()
    {
    }

    public void open()
    {
        if(!Global.getLocationOnAppLaunch) {
            if (Global.locationGrabber == null)
                Global.locationGrabber = new LocationGrabber(context, this);
            else
            {
                Global.locationGrabber.stopLocationManager();
                Global.locationGrabber.startLocationManager();
            }
        }

        //  CHECK IF FAVORITES HAVE CHANGED
        busStopList.refreshFavorites();
    }
}
