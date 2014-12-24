package ryan.nhg.gtg;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.PriorityQueue;


public class MainActivity extends Activity
{

    //  TABS
    private static final int INITIAL_TAB = R.id.tab_location;
    private int currentTab;

    //  LAYOUTS
    private static final int    LAYOUT_LOCATION = 0,
                                LAYOUT_SEARCH = 1,
                                LAYOUT_RECENT = 2,
                                LAYOUT_FAVORITE = 3;
    private LinearLayout[] layouts;
    private LinearLayout layout_main;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayouts();
        initTabs();
    }

    //  LAYOUTS
    private void initLayouts()
    {
        layout_main = (LinearLayout)findViewById(R.id.layout_main);

        layouts = new LinearLayout[4];
        layouts[LAYOUT_LOCATION] = new LocationLayout(this);
        layouts[LAYOUT_SEARCH] = new SearchLayout(this);
    }

    private void openLayout()
    {
        if(currentTab == R.id.tab_search)
            layout_main.addView(layouts[LAYOUT_SEARCH]);
        else
            layout_main.addView(layouts[LAYOUT_LOCATION]);
    }

    private void closeLayout()
    {
        if(currentTab == R.id.tab_search)
            layout_main.removeView(layouts[LAYOUT_SEARCH]);
        else
            layout_main.removeView(layouts[LAYOUT_LOCATION]);
    }

    //  TABS

    private void initTabs()
    {
        currentTab = INITIAL_TAB;
        selectTab();
    }

    public void tabClicked(View view)
    {
        ImageButton clickedTab = (ImageButton)view;

        //  Deselect selected tab
        deselectTab();

        //  Set clicked tab
        currentTab = clickedTab.getId();

        //  Select current tab
        selectTab();

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


}
