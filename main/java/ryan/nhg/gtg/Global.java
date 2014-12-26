package ryan.nhg.gtg;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ryan on 12/25/14.
 */
public class Global
{
    //  LOCATION
    public static double latitude;
    public static double longitude;

    //  RECENT STOPS
    private static BusStop[] recentStops;
    private static int recent_index;
    public static final int MAX_RECENT_STOPS = 10;

    //  FAVORITES
    private static BusStop[] favorites;
    private static int num_favorites;
    public static final int MAX_FAVORITE_STOPS = 10;

    //  SAVING/ LOADING
    private static String RECENT_STOPS_FILENAME = "recent.dat",
                            RECENT_INDEX_FILENAME = "index.dat",
                            FAVORITES_FILENAME = "favorites.dat",
                            NUM_FAVORITES_FILENAME = "num_favorites.dat";


    //  RECENT STOPS

    public static void addRecentStop(String stopId, String stopName)
    {
        if(recentStops == null) {
            //  TO-DO: Attempt to load from file
            recentStops = new BusStop[MAX_RECENT_STOPS];
            recent_index = 0;
        }

        recentStops[recent_index] = new BusStop(stopId,stopName);
        recent_index = (recent_index+1)%MAX_RECENT_STOPS;

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
            if(favorites[i] != null && favorites[i].stopId == stopId)
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
                    return;
                }
        }
    }

    public static void removeFavorite(String stopId)
    {
        if(favorites==null) return;

        for(int i = 0; i < favorites.length; i++)
            if(favorites[i] != null && favorites[i].stopId == stopId)
            {
                favorites[i] = null;
                num_favorites--;
            }

    }

    public static void saveStops(Context context)
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStops(Context context)
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
