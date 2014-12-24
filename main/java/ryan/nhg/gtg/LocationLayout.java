package ryan.nhg.gtg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by ryan on 12/24/14.
 */
public class LocationLayout extends LinearLayout implements Layout
{
    //  CONTEXT
    private Context context;

    //  LAYOUTS
    private BusStopList busStopList;

    public LocationLayout(Context context)
    {
        super(context);

        this.context = context;

        setProperties();
        initLayouts();
        getBusStops();
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
        //  USE GPS LAT AND LON TO GET BUS STOPS NEAR PERSON
        for(int i = 0; i < 10; i++)
        {
            int color;

            if(i%2==0)color = R.color.uiuc_orange;
            else color = R.color.uiuc_blue;

            busStopList.addCard(color,"Illini Union",i + " mi", "id:"+i,(i%4==0));
        }

    }


    public void close()
    {

    }

    public void open()
    {

    }
}
