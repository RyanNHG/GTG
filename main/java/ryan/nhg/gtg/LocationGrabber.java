package ryan.nhg.gtg;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ryan on 12/25/14.
 */
public class LocationGrabber implements LocationListener
{
    private LocationManager locationManager;
    private Context context;
    private LocationLayout locationLayout;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    public LocationGrabber(Context context, LocationLayout locationLayout)
    {
        this.context = context;
        this.locationLayout = locationLayout;
    }

    public void getLocation()
    {
        Location location = null;

        try
        {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled)
            {
                Toast.makeText(context, "Please enable location on your device.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)
                        {
                            Global.latitude = location.getLatitude();
                            Global.longitude = location.getLongitude();

                            Toast.makeText(context, "Location loaded at " + Global.getTimeString(), Toast.LENGTH_SHORT).show();
                            locationLayout.locationReady();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null)
                            {
                                Global.latitude = location.getLatitude();
                                Global.longitude = location.getLongitude();

                                Toast.makeText(context, "Location loaded at " + Global.getTimeString(), Toast.LENGTH_SHORT).show();
                                locationLayout.locationReady();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {

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
        if(locationManager != null){
            locationManager.removeUpdates(this);
        }
    }

    public void startLocationManager()
    {
        getLocation();
    }
}
