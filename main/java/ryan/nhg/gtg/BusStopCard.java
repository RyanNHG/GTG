package ryan.nhg.gtg;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
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
public class BusStopCard extends RelativeLayout
{
    //  CONTEXT
    private Context context;

    //  VIEWS
    private ImageView colorImageView, favoriteImageView;
    private TextView stopNameTextView, distanceTextView;

    //  VALUES
    private String stopId;
    private boolean favorite;

    public BusStopCard(Context context, int colorId, String stopName, String distance, String stopId, boolean favorite)
    {
        super(context);

        this.context = context;
        this.stopId = stopId;
        this.favorite = favorite;

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
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toggleFavorite();
                return false;
            }
        });

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
        initStopName(stopName);
        initDistance(distance);
        initFavorite();

        //  Add views
        this.addView(colorImageView);
        this.addView(stopNameTextView);
        this.addView(distanceTextView);
        this.addView(favoriteImageView);

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
        params.setMargins(0,0,(int)getResources().getDimension(R.dimen.activity_horizontal_margin),0);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        favoriteImageView.setLayoutParams(params);

        //  Set appropriate icon
        setFavoriteIcon();
    }

        private void setFavoriteIcon()
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

    public boolean getFavorite()
    {
        return favorite;
    }

    public void toggleFavorite()
    {
        favorite = !favorite;
        setFavoriteIcon();
    }
}
