package ryan.nhg.gtg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;


public class BusActivity extends Activity
{

    //  LAYOUTS
    private BusList list;
    private String stopId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        Intent intent = getIntent();

        initLayouts();
        setTitle(intent.getStringExtra("stop_name"));
        stopId = intent.getStringExtra("stop_id");
        getBuses(stopId);

    }

    private void initLayouts()
    {
        LinearLayout layout_main = (LinearLayout)findViewById(R.id.layout_main);

        //  Initialize bus list
        list = new BusList(this);

        //  Add list to main layout
        layout_main.addView(list);

    }

    private void setTitle(String title)
    {
        ((Button)findViewById(R.id.btn_label)).setText(title);
    }

    private void getBuses(String stopId)
    {
        new DataGrabber(list).execute(""+DataGrabber.GET_BUSES_FOR_STOP,stopId);
    }

    public void backClicked(View v)
    {
        this.finish();
    }

    public void refreshClicked(View v)
    {
        getBuses(stopId);

        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int am_pm = c.get(Calendar.AM_PM);
        String str;

        if(am_pm == Calendar.AM)
            str = "Bus stops loaded at " + hours + ":" + minutes + " AM";
        else str = "Bus stops loaded at " + hours + ":" + minutes + " PM";

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

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
