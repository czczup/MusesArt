package czcz94cz.photovalley.features.albumpicker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import czcz94cz.photovalley.R;

/**
 * Created by VanXN on 2018/3/8.
 */

public class PhotoVH extends RecyclerView.ViewHolder {
    ImageView ivPhoto;
    CheckBox checkbox;

    public PhotoVH(View itemView) {
        super(itemView);
        ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

}