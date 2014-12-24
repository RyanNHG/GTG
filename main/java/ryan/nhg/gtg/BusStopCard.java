package ryan.nhg.gtg;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ryan on 12/24/14.
 */
public class BusStopCard extends RelativeLayout
{
    //  CONTEXT
    private Context context;

    //  VIEWS
    private ImageView colorImageView;
    private TextView stopNameTextView, distanceTextView;

    //  VALUES
    private String stopId;

    public BusStopCard(Context context, int colorId, String stopName, String distance, String stopId)
    {
        super(context);

        this.context = context;
        this.stopId = stopId;

        setProperties();
        initViews(colorId,stopName,distance);

    }

    private void setProperties()
    {
        //  Set width, height, and margins
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);

        //  Set color and elevation
        this.setBackground(getResources().getDrawable(R.color.card_bg_color));

    }

    private void initViews(int colorId, String stopName, String distance)
    {
        //  Initialize views
        colorImageView = new ImageView(context);
        stopNameTextView = new TextView(context);
        distanceTextView = new TextView(context);

        //  Set properties of views
        initColor(colorId);
        initStopName(stopName);
        initDistance(distance);

        //  Add views
        this.addView(colorImageView);
        this.addView(stopNameTextView);
        this.addView(distanceTextView);


    }

    private void initColor(int colorId)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int)getResources().getDimension(R.dimen.standard_size),
                (int)getResources().getDimension(R.dimen.standard_size));
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        int margin = (int)getResources().getDimension(R.dimen.activity_vertical_margin);
        params.setMargins(margin,margin,margin,margin);
        colorImageView.setLayoutParams(params);
        colorImageView.setId(1);

        //  Set color
        colorImageView.setBackground(getResources().getDrawable(colorId));

    }

    private void initStopName(String stopName)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_TOP,colorImageView.getId());
        params.addRule(RelativeLayout.RIGHT_OF,colorImageView.getId());
        stopNameTextView.setLayoutParams(params);
        stopNameTextView.setId(2);

        //  Set text
        stopNameTextView.setTextAppearance(context,R.style.Base_TextAppearance_AppCompat_Large);
        stopNameTextView.setText(stopName);
    }

    private void initDistance(String distance)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, stopNameTextView.getId());
        params.addRule(RelativeLayout.ALIGN_START,stopNameTextView.getId());
        params.addRule(RelativeLayout.BELOW,stopNameTextView.getId());
        distanceTextView.setLayoutParams(params);

        //  Set text
        distanceTextView.setTextColor(getResources().getColorStateList(R.color.abc_secondary_text_material_light));
        distanceTextView.setTextAppearance(context,R.style.Base_TextAppearance_AppCompat_Large);
        distanceTextView.setText(distance);
    }

    public String getStopId()
    {
        return stopId;
    }
}
