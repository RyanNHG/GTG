package ryan.nhg.gtg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.PriorityQueue;


public class MainActivity extends Activity
{

    //  LAYOUTS
    private static final int    LAYOUT_LOCATION = 0,
                                LAYOUT_SEARCH = 1,
                                LAYOUT_RECENT = 2,
                                LAYOUT_FAVORITE = 3;
    private LinearLayout[] layouts;
    private LinearLayout layout_main;

    //  LOCATION
    private LocationGrabber locationGrabber;

    //  TABS
    private static final int INITIAL_TAB = R.id.tab_location;
    private int currentTab;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayouts();
        //initLocation();
        initTabs();
    }

    //  LAYOUTS
    private void initLayouts()
    {
        layout_main = (LinearLayout)findViewById(R.id.layout_main);

        layouts = new LinearLayout[4];
        layouts[LAYOUT_LOCATION] = new LocationLayout(this);
        layouts[LAYOUT_SEARCH] = new SearchLayout(this);
        layouts[LAYOUT_RECENT] = new RecentLayout(this);
        layouts[LAYOUT_FAVORITE] = new FavoriteLayout(this);
    }

    private void openLayout()
    {
        LinearLayout layout;

        if(currentTab == R.id.tab_search)
            layout=layouts[LAYOUT_SEARCH];
        else if(currentTab == R.id.tab_recent)
            layout=layouts[LAYOUT_RECENT];
        else if(currentTab == R.id.tab_favorite)
            layout=layouts[LAYOUT_FAVORITE];
        else
            layout=layouts[LAYOUT_LOCATION];

        layout_main.addView(layout);
        ((Layout)layout).open();
    }

    private void closeLayout()
    {
        LinearLayout layout;

        if(currentTab == R.id.tab_search)
            layout=layouts[LAYOUT_SEARCH];
        else if(currentTab == R.id.tab_recent)
            layout=layouts[LAYOUT_RECENT];
        else if(currentTab == R.id.tab_favorite)
            layout=layouts[LAYOUT_FAVORITE];
        else
            layout=layouts[LAYOUT_LOCATION];

        layout_main.removeView(layout);
        ((Layout)layout).close();
    }

    //  LOCATION

    private void initLocation()
    {
        locationGrabber = new LocationGrabber(this,(LocationLayout)layouts[LAYOUT_LOCATION]);
    }

    //  TABS

    private void initTabs()
    {
        currentTab = INITIAL_TAB;
        selectTab();
    }

    public void tabClicked(View view)
    {
        Intent intent = new Intent(this, BusActivity.class);
        startActivity(intent);
        /*
        ImageButton clickedTab = (ImageButton)view;

        //  Deselect selected tab
        deselectTab();

        //  Set clicked tab
        currentTab = clickedTab.getId();

        //  Select current tab
        selectTab();
        */

    }

    private void selectTab()
    {
        //  Get ImageButton from ID
        ImageButton tab = (ImageButton)findViewById(currentTab);

        //  Highlight icon
        tab.setBackground(getResources().getDrawable(R.color.button_material_dark));

        //  Change visible layout
        openLayout();
    }

    private void deselectTab()
    {
        //  Get ImageButton from ID
        ImageButton tab = (ImageButton)findViewById(currentTab);

        //  Un-highlight icon
        tab.setBackground(getResources().getDrawable(R.color.button_material_light));

        //  Change visible layout
        closeLayout();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationGrabber!=null)
            locationGrabber.stopLocationManager();
        Global.saveStops(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(locationGrabber!=null)
            locationGrabber.startLocationManager();
        Global.loadStops(this);
    }
}
