package ryan.nhg.gtg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.Console;


public class MainActivity extends Activity
{

    //  LAYOUTS
    private LinearLayout[] layouts;
    private LinearLayout layout_main;

    //  TABS
    private int currentTab;
    private int[] tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Global.context = this;

        initLayouts();
        initTabs();
        initLocation();

    }

    //  LAYOUTS
    private void initLayouts()
    {
        layout_main = (LinearLayout)findViewById(R.id.layout_main);

        layouts = new LinearLayout[4];
        layouts[Global.LAYOUT_LOCATION] = new LocationLayout(this);
        layouts[Global.LAYOUT_SEARCH] = new SearchLayout(this);
        layouts[Global.LAYOUT_RECENT] = new RecentLayout(this);
        layouts[Global.LAYOUT_FAVORITE] = new FavoriteLayout(this);
    }

    private void openLayout()
    {
        LinearLayout layout = layouts[currentTab];

        layout_main.addView(layout);
        ((Layout)layout).open();
    }

    private void closeLayout()
    {
        LinearLayout layout = layouts[currentTab];

        layout_main.removeView(layout);
        ((Layout)layout).close();
    }

    //  LOCATION

    private void initLocation()
    {
        Global.locationGrabber = new LocationGrabber(this,(LocationLayout)layouts[Global.LAYOUT_LOCATION]);
    }

    //  TABS

    private void initTabs()
    {
        tabs = new int[]{R.id.tab_location,R.id.tab_search,R.id.tab_recent,R.id.tab_favorite};
    }

    public void tabClicked(View view)
    {
        //  Get clicked tab
        ImageButton clickedTab = (ImageButton)view;

        //  Deselect selected tab
        deselectTab();

        //  Set clicked tab
        int tab_id = clickedTab.getId();

        for(int i = 0; i < tabs.length; i++)
            if(tabs[i] == tab_id)
                currentTab = i;

        //  Select current tab
        selectTab();

    }

    private void selectTab()
    {
        //  Get ImageButton from ID
        ImageButton tab = (ImageButton)findViewById(tabs[currentTab]);

        //  Highlight icon
        tab.setBackground(getResources().getDrawable(R.color.button_material_dark));

        //  Change visible layout
        openLayout();
    }

    private void deselectTab()
    {
        //  Get ImageButton from ID
        ImageButton tab = (ImageButton)findViewById(tabs[currentTab]);

        //  Un-highlight icon
        tab.setBackground(getResources().getDrawable(R.color.button_material_light));

        //  Change visible layout
        closeLayout();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Global.locationGrabber!=null)
            Global.locationGrabber.stopLocationManager();
        Global.save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Global.load();

        if(Global.getLocationOnAppLaunch)
            Global.locationGrabber.startLocationManager();

        deselectTab();
        currentTab = Global.defaultTab;
        selectTab();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
