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
public class BusList extends ScrollView
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private LinearLayout list;

    //  BUS STOP CARDS
    private ArrayList<BusCard> buses;
    private int num_buses;

    public BusList(Context context)
    {
        super(context);

        this.context = context;

        setProperties();
        initList();
        initBusCards();
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

    private void initBusCards()
    {
        //  Initialize bus arraylist
        buses = new ArrayList<>();
        num_buses = 0;

    }

    public void addCard(String busName, String eta, String color)
    {

        if(num_buses > 0)
            list.addView(new Space(context));
        buses.add(num_buses, new BusCard(context, color, busName, eta));
        list.addView(buses.get(num_buses));

        num_buses++;
    }

    public void addStops(JSONObject obj)
    {
        removeAll();

        try {
            JSONArray departures = obj.getJSONArray("departures");

            for(int i = 0; i < departures.length(); i++)
            {
                JSONObject bus = departures.getJSONObject(i);
                JSONObject route = bus.getJSONObject("route");

                String eta = bus.getInt("expected_mins") + " mins";
                String color = route.getString("route_color");
                String busName = bus.getString("headsign");

                addCard(busName,eta,color);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeAll()
    {
        list.removeAllViews();
        initBusCards();
    }
}
