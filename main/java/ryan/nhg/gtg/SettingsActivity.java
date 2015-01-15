package ryan.nhg.gtg;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;


public class SettingsActivity extends Activity
{

    //  DEFAULT TAB SETTING
    private int currentTab;
    private int[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Global.load();

        initTabs();
        initRadioButtons();
    }

    private void initTabs()
    {
        tabs = new int[]{R.id.tab_location,R.id.tab_search,R.id.tab_recent,R.id.tab_favorite};
        currentTab = Global.defaultTab;

        selectTab();
    }

    private void initRadioButtons()
    {
        RadioButton app = ((RadioButton)findViewById(R.id.radio_loc_onAppLaunch));
        RadioButton loc = ((RadioButton)findViewById(R.id.radio_loc_onLocationClick));

        if(Global.getLocationOnAppLaunch)
            app.setChecked(true);
        else loc.setChecked(true);
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
        setDefaultTab(tab.getId());
    }

    private void deselectTab()
    {
        //  Get ImageButton from ID
        ImageButton tab = (ImageButton)findViewById(tabs[currentTab]);

        //  Un-highlight icon
        tab.setBackground(getResources().getDrawable(R.color.button_material_light));

    }

    public void setDefaultTab(int id)
    {
        switch(id)
        {
            case R.id.tab_location:
                Global.defaultTab = Global.LAYOUT_LOCATION;
                break;
            case R.id.tab_search:
                Global.defaultTab = Global.LAYOUT_SEARCH;
                break;
            case R.id.tab_recent:
                Global.defaultTab = Global.LAYOUT_RECENT;
                break;
            case R.id.tab_favorite:
                Global.defaultTab = Global.LAYOUT_FAVORITE;
                break;
            default:
                return;
        }

        Global.save();
    }

    //  LOCATION SETTING
    public void onLocationRadioClick(View v)
    {
        switch(v.getId())
        {
            case R.id.radio_loc_onAppLaunch:
                Global.getLocationOnAppLaunch = true;
                break;
            case R.id.radio_loc_onLocationClick:
                Global.getLocationOnAppLaunch = false;
                break;
        }

        Global.save();
    }

    public void backClicked(View v)
    {
        this.finish();
    }
}
