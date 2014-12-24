package ryan.nhg.gtg;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

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

    public void addCard(int colorId, String stopName, String distance, String stopId, boolean favorite)
    {
        if(num_stops > 0)
            list.addView(new Space(context));
        busStops.add(num_stops, new BusStopCard(context, colorId, stopName, distance, stopId ,favorite));
        list.addView(busStops.get(num_stops));

        num_stops++;
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
