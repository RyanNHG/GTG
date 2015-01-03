package ryan.nhg.gtg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ryan on 12/24/14.
 */
public class FavoriteLayout extends LinearLayout implements Layout
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private BusStopList busStopList;

    public FavoriteLayout(Context context)
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
        BusStop[] stops = Global.getFavoriteStops();

        //  TO-DO: Add card that tells user how to add favorites.
        if(stops==null || stops.length < 1) return;

        for(BusStop stop : stops)
        {
            if (stop != null)
                busStopList.addCard(stop.stopName, "", stop.stopId, Global.isInFavorites(stop.stopId));
        }
    }

    public void close()
    {

    }

    public void open()
    {
        busStopList.removeAll();
        getBusStops();
    }
}

