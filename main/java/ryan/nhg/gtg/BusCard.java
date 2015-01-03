package ryan.nhg.gtg;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ryan on 12/24/14.
 */
public class BusCard extends RelativeLayout
{
    //  CONTEXT
    private Context context;

    //  VIEWS
    private ImageView colorImageView;
    private TextView busNameTextView, etaTextView;


    public BusCard(Context context, String color, String busName, String eta)
    {
        super(context);

        this.context = context;

        setProperties();
        initViews(color,busName,eta);

        //  Should a bus stop do something on click?
        //     Details of bus location on map? Pro version?
        //initOnClick();

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

    private void initViews(String color, String busName, String eta)
    {
        //  Initialize views
        colorImageView = new ImageView(context);
        busNameTextView = new TextView(context);
        etaTextView = new TextView(context);

        //  Set properties of views
        initColor(color);
        initBusName(busName);
        initEta(eta);

        //  Add views
        this.addView(colorImageView);
        this.addView(busNameTextView);
        this.addView(etaTextView);

    }

    private void initColor(String color)
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
        colorImageView.setId(3);

        //  Set color and image
        colorImageView.setBackgroundColor(Color.parseColor("#"+color));

    }

    private void initBusName(String busName)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_TOP,colorImageView.getId());
        params.addRule(RelativeLayout.RIGHT_OF,colorImageView.getId());
        busNameTextView.setLayoutParams(params);
        busNameTextView.setId(4);

        //  Set text
        busNameTextView.setTextAppearance(context,R.style.Base_TextAppearance_AppCompat_Large);
        busNameTextView.setText(busName);
    }

    private void initEta(String eta)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, busNameTextView.getId());
        params.addRule(RelativeLayout.ALIGN_START,busNameTextView.getId());
        params.addRule(RelativeLayout.BELOW,busNameTextView.getId());
        etaTextView.setLayoutParams(params);

        //  Set text
        etaTextView.setTextColor(getResources().getColorStateList(R.color.abc_secondary_text_material_light));
        etaTextView.setTextAppearance(context,R.style.Base_TextAppearance_AppCompat_Large);
        etaTextView.setText(eta);
    }

}
