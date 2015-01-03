package ryan.nhg.gtg;

import android.content.Context;
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
            System.out.println("Location: [" + Global.latitude + ", " + Global.longitude +"]");
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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
    }
}
