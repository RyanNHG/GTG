package ryan.nhg.gtg;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ryan on 12/24/14.
 */
public class BusStopList extends ScrollView
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private LinearLayout list;

    //  BUS STOP CARDS
    private ArrayList<BusStopCard> busStops;
    private int num_stops;

    public BusStopList(Context context)
    {
        super(context);

        this.context = context;

        setProperties();
        initList();
        initBusStopCards();
    }

    private void setProperties()
    {
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.setLayoutParams(params);
    }

    private void initList() {
        //  Initialize list
        list = new LinearLayout(context);

        //  Set list properties
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        list.setLayoutParams(params);

        list.setOrientation(LinearLayout.VERTICAL);

        //  Add list
        this.addView(list);
    }

    private void initBusStopCards()
    {
        //  Initialize busStop arraylist
        busStops = new ArrayList<>();
        num_stops = 0;

    }

    public void addCard(String stopName, String distance, String stopId, boolean favorite)
    {
        int color;

        if(num_stops%2==0)color = R.color.uiuc_orange;
        else color = R.color.uiuc_blue;

        if(num_stops > 0)
            list.addView(new Space(context));

        busStops.add(num_stops, new BusStopCard(context, color, stopName, distance, stopId ,favorite));
        list.addView(busStops.get(num_stops));

        num_stops++;
    }

    public void addStops(JSONObject obj, int source)
    {
        removeAll();

        try {

            if(source == DataGrabber.GET_QUICK_SEARCH_BUS_STOPS)
            {
                JSONArray stops = obj.getJSONArray("stops");

                for(int i = 0; i < stops.length(); i++)
                {
                    JSONObject stop = stops.getJSONObject(i);

                    String stopId = stop.getString("i");
                    String stopName = stop.getString("n");

                    addCard(stopName,"",stopId,Global.isInFavorites(stopId));
                }
            }
            else {
                JSONArray stops = obj.getJSONArray("stops");

                for (int i = 0; i < stops.length(); i++) {
                    JSONObject stop = stops.getJSONObject(i);

                    String stopId = stop.getString("stop_id");
                    String stopName = stop.getString("stop_name");
                    String distance;

                    if (source == DataGrabber.GET_LOCATION_BUS_STOPS) {
                        distance = ("" + stop.getDouble("distance") / 5280);
                        if (distance.length() > 5)
                            distance = distance.substring(0, 5) + " miles";
                    } else distance = "";

                    addCard(stopName, distance, stopId, Global.isInFavorites(stopId));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

        private double calculateDistanceFrom(double lat, double lon)
        {
            int R = 3959;
            double x = Global.longitude-lon * Math.cos((Global.latitude+lat)/2);
            double y = (Global.latitude-lat);
            return Math.sqrt(x*x + y*y) * R;
        }

    public void refreshFavorites()
    {
        for(BusStopCard card : busStops)
            card.setFavoriteIcon();
    }

    public void removeCard(int stopId)
    {
        for(BusStopCard card : busStops)
            if(card.getStopId().equals(stopId)) {
                if(busStops.indexOf(card) > 0)
                    busStops.remove(busStops.indexOf(card)-1);
                busStops.remove(card);
                num_stops--;
            }
    }

    public void removeAll()
    {
        list.removeAllViews();
        initBusStopCards();
    }
}
