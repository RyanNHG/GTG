package ryan.nhg.gtg;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ryan on 12/25/14.
 */
public class LocationGrabber implements LocationListener
{
    private LocationManager lm;
    private Context context;
    private LocationLayout locationLayout;
    private Toast toast;

    public LocationGrabber(Context context, LocationLayout locationLayout)
    {
        this.context = context;
        this.locationLayout = locationLayout;
        startLocationManager();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if(location != null) {
            Global.latitude = location.getLatitude();
            Global.longitude = location.getLongitude();

            locationLayout.locationReady();
            if(toast!=null)toast.cancel();
            stopLocationManager();
        }
        else Toast.makeText(context, "Could not acquire location.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    public void stopLocationManager()
    {
        lm.removeUpdates(this);
    }

    public void startLocationManager()
    {

        lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        String bestProvider = lm.getBestProvider(new Criteria(),false);

        lm.requestLocationUpdates(bestProvider,1000*60,20,this);

        Location location = lm.getLastKnownLocation(bestProvider);

        try
        {
            Global.latitude = location.getLatitude();
            Global.longitude = location.getLongitude();
            locationLayout.locationReady();
        }
        catch (Exception e)
        {
            toast = Toast.makeText(context, "Looking for bus stops...",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
