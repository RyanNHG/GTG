package ryan.nhg.gtg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ryan on 12/24/14.
 */
public class Space extends LinearLayout
{
    public Space(Context context)
    {
        super(context);

        this.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)getResources().getDimension(R.dimen.activity_vertical_margin)
        ));
    }
}
