package ryan.nhg.gtg;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * Created by ryan on 12/25/14.
 */
public class Global
{
    //  CONTEXT
    public static Context context;

    //  LAYOUTS
    public static final int     LAYOUT_LOCATION = 0,
                                LAYOUT_SEARCH = 1,
                                LAYOUT_RECENT = 2,
                                LAYOUT_FAVORITE = 3;

    //  LOCATION
    public static double latitude;
    public static double longitude;
    public static LocationGrabber locationGrabber;

    //  RECENT STOPS
    private static BusStop[] recentStops;
    private static int recent_index;
    public static final int MAX_RECENT_STOPS = 10;

    //  FAVORITES
    private static BusStop[] favorites;
    private static int num_favorites;
    public static final int MAX_FAVORITE_STOPS = 10;

    //  SETTINGS
    public static int defaultTab;
    public static boolean getLocationOnAppLaunch;

    //  SAVING/ LOADING
    private static String   RECENT_STOPS_FILENAME = "recent.dat",
                            RECENT_INDEX_FILENAME = "index.dat",
                            FAVORITES_FILENAME = "favorites.dat",
                            NUM_FAVORITES_FILENAME = "num_favorites.dat",
                            DEFAULT_TAB = "default_tab.dat",
                            LOCATION_PREF = "location_pref.dat";


    //  RECENT STOPS

    public static void addRecentStop(String stopId, String stopName)
    {
        if(recentStops == null) {
            //  TO-DO: Attempt to load from file
            recentStops = new BusStop[MAX_RECENT_STOPS];
            recent_index = 0;
        }

        recentStops[recent_index] = new BusStop(stopId,stopName);

        removeOtherRecentStops(recent_index,stopId);

        recent_index = (recent_index+1)%MAX_RECENT_STOPS;

    }

    private static void removeOtherRecentStops(int indexToAvoid, String stopid)
    {
        for(int i = 0; i < MAX_RECENT_STOPS; i++)
        {
            BusStop stop = recentStops[i];

            if (i != indexToAvoid && stop != null && (stop.stopId.equals(stopid)))
                recentStops[i] = null;
        }
    }

    public static BusStop[] getRecentStops()
    {

        BusStop[] stops = new BusStop[MAX_RECENT_STOPS];
        int j = 0;
        int i = recent_index;

        if(recentStops!=null)
        {
            do {
                if (recentStops[i] != null)
                {
                    stops[j] = recentStops[i];
                    j++;
                }

                i = (i - 1 + MAX_RECENT_STOPS) % MAX_RECENT_STOPS;
            }
            while (i != (recent_index + MAX_RECENT_STOPS) % MAX_RECENT_STOPS);
        }

        return stops;
    }


    //  FAVORITES

    public static boolean isInFavorites(String stopId)
    {
        if(favorites==null) return false;

        for(int i = 0; i < favorites.length; i++)
            if(favorites[i] != null && favorites[i].stopId.equals(stopId))
                return true;

        return false;
    }

    public static BusStop[] getFavoriteStops()
    {
        return favorites;
    }

    public static void addFavorite(String stopId,String stopName)
    {
        if(favorites == null) {
            //  TO-DO: Load from file
            favorites = new BusStop[MAX_FAVORITE_STOPS];
            num_favorites = 0;
        }

        if(num_favorites < MAX_FAVORITE_STOPS)
        {
            for(int i = 0; i < MAX_FAVORITE_STOPS; i++)
                if(favorites[i]==null)
                {
                    favorites[i] = new BusStop(stopId, stopName);
                    num_favorites++;
                    Toast.makeText(context, stopName + " added to favorites!", Toast.LENGTH_SHORT).show();

                    return;
                }
        }
    }

    public static void removeFavorite(String stopId)
    {
        if(favorites==null) return;

        for(int i = 0; i < favorites.length; i++)
            if(favorites[i] != null && favorites[i].stopId.equals(stopId))
            {
                Toast.makeText(context, favorites[i].stopName + " removed from favorites!", Toast.LENGTH_SHORT).show();
                favorites[i] = null;
                num_favorites--;
            }

    }

    public static void save()
    {
        try {
            FileOutputStream fos = context.openFileOutput(RECENT_STOPS_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(recentStops);
            os.close();

            fos = context.openFileOutput(RECENT_INDEX_FILENAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(recent_index);
            os.close();

            fos = context.openFileOutput(FAVORITES_FILENAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(favorites);
            os.close();

            fos = context.openFileOutput(NUM_FAVORITES_FILENAME, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(num_favorites);
            os.close();

            fos = context.openFileOutput(DEFAULT_TAB, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(defaultTab);
            os.close();

            fos = context.openFileOutput(LOCATION_PREF, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(getLocationOnAppLaunch);
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load()
    {
        try {
            FileInputStream fis = context.openFileInput(RECENT_STOPS_FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            recentStops = (BusStop[]) is.readObject();
            is.close();

            fis = context.openFileInput(RECENT_INDEX_FILENAME);
            is = new ObjectInputStream(fis);
            recent_index = (int) is.readObject();
            is.close();

            fis = context.openFileInput(FAVORITES_FILENAME);
            is = new ObjectInputStream(fis);
            favorites = (BusStop[]) is.readObject();
            is.close();

            fis = context.openFileInput(NUM_FAVORITES_FILENAME);
            is = new ObjectInputStream(fis);
            num_favorites = (int) is.readObject();
            is.close();

            fis = context.openFileInput(DEFAULT_TAB);
            is = new ObjectInputStream(fis);
            defaultTab = (int) is.readObject();
            is.close();

            fis = context.openFileInput(LOCATION_PREF);
            is = new ObjectInputStream(fis);
            getLocationOnAppLaunch = (boolean) is.readObject();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  MISC METHODS
    public static String getTimeString()
    {
        Calendar c = Calendar.getInstance();
        String hours = "" + c.get(Calendar.HOUR);
        String minutes = "" + c.get(Calendar.MINUTE);
        int am_pm = c.get(Calendar.AM_PM);

        if(c.get(Calendar.MINUTE) < 10 )
            minutes = "0" + minutes;

        if(am_pm == Calendar.AM)
            return "" + hours + ":" + minutes + " AM";
        else return "" + hours + ":" + minutes + " PM";
    }

}
