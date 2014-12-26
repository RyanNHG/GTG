package ryan.nhg.gtg;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ryan on 12/24/14.
 */
public class SearchLayout extends LinearLayout implements Layout
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private SearchView searchbar;
    private BusStopList busStopList;

    public SearchLayout(Context context)
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
        //  Initialize layouts
        searchbar = new SearchView(context);
        busStopList = new BusStopList(context);

        //  Set properties for searchbar
        initSearchbar();

        //  Add BusStopList
        this.addView(searchbar);
        this.addView(new Space(context));
        this.addView(busStopList);
    }

    private void initSearchbar()
    {
        searchbar.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        searchbar.setBackground(getResources().getDrawable(R.color.card_bg_color));
        searchbar.setQueryHint(getResources().getString(R.string.query_tooltip));
        searchbar.setIconified(false);

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            public boolean onQueryTextSubmit(String query)
            {
                searchbar.clearFocus();
                return false;
            }

            public boolean onQueryTextChange(String query)
            {
                getQuickBusStops(query);
                return false;
            }
        });
    }

    private void getBusStops(String str)
    {
        //  USE SEARCHBAR TEXT TO GET BUS STOPS
        new DataGrabber(busStopList).execute("" + DataGrabber.GET_SEARCH_BUS_STOPS, str);
    }

    private void getQuickBusStops(String str)
    {
        new DataGrabber(busStopList).execute(""+DataGrabber.GET_QUICK_SEARCH_BUS_STOPS,str);
    }

    public void close()
    {

    }

    public void open()
    {
        //  CHECK IF FAVORITES HAVE CHANGED
        busStopList.refreshFavorites();
    }
}
