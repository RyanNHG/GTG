package ryan.nhg.gtg;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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
    private ImageView colorImageView, favoriteImageView;
    private TextView stopNameTextView, distanceTextView;

    //  VALUES
    private String stopId;
    private boolean favorite, clickIgnore;

    public BusStopCard(Context context, int colorId, String stopName, String distance, String stopId, boolean favorite)
    {
        super(context);

        this.context = context;
        this.stopId = stopId;
        this.favorite = favorite;
        this.clickIgnore = false;

        setProperties();
        initViews(colorId,stopName,distance);
        initOnClick();

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
        favoriteImageView = new ImageView(context);

        //  Set properties of views
        initColor(colorId);
        initFavorite();
        initStopName(stopName);
        initDistance(distance);

        //  Add views
        this.addView(colorImageView);
        this.addView(favoriteImageView);
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

        //  Set color and image
        colorImageView.setBackground(getResources().getDrawable(colorId));

    }

    private void initFavorite()
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, (int) getResources().getDimension(R.dimen.activity_horizontal_margin), 0);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        favoriteImageView.setLayoutParams(params);
        favoriteImageView.setId(2);

        //  Set appropriate icon
        setFavoriteIcon();
    }

        public void setFavoriteIcon()
        {
            if(favorite) favoriteImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_favorite_light_gray));
            else favoriteImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_unfavorite_light_gray));
        }

    private void initStopName(String stopName)
    {
        //  Set layout
        RelativeLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_TOP,colorImageView.getId());
        params.addRule(RelativeLayout.RIGHT_OF,colorImageView.getId());
        params.addRule(RelativeLayout.LEFT_OF,favoriteImageView.getId());
        stopNameTextView.setLayoutParams(params);
        stopNameTextView.setId(3);

        //  Set text
        stopNameTextView.setTextAppearance(context,R.style.TextAppearance_AppCompat_Medium);
        stopNameTextView.setTextColor(getResources().getColorStateList(R.color.black));
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
        distanceTextView.setTextAppearance(context,R.style.Base_TextAppearance_AppCompat_Small);
        distanceTextView.setTextColor(getResources().getColorStateList(R.color.secondary_text_default_material_light));
        distanceTextView.setText(distance);
    }

    private void initOnClick()
    {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickIgnore)
                    clickIgnore = false;
                else if(stopId != null) {

                    //  Add bus stop to recent stops
                    Global.addRecentStop(stopId, stopNameTextView.getText().toString());

                    //  Launch bus activity to display buses for stop
                    Intent intent = new Intent(context, BusActivity.class);

                    intent.putExtra("stop_name",stopNameTextView.getText().toString());
                    intent.putExtra("stop_id",stopId);

                    context.startActivity(intent);
                }
            }
        });

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleFavorite();
                clickIgnore = true;
                return false;
            }
        });
    }

    public String getStopId()
    {
        return stopId;
    }

    public void toggleFavorite()
    {
        favorite = !favorite;
        setFavoriteIcon();

        if(favorite && !Global.isInFavorites(stopId))
        {
            Global.addFavorite(stopId, stopNameTextView.getText().toString());
        }
        else if (!favorite)
        {
            Global.removeFavorite(stopId);
        }

    }

}
