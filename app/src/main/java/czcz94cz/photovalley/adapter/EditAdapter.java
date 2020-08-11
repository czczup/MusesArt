package czcz94cz.photovalley.adapter;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import czcz94cz.photovalley.R;
import czcz94cz.photovalley.model.Filter;


public class EditAdapter extends BaseQuickAdapter<Filter, BaseViewHolder> {

    public EditAdapter(int layoutResId, List<Filter> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Filter item) {
        if (item.getId() == 0) {
            Glide.with(mContext).load(Uri.parse(item.getCoverImage())).into((ImageView) helper.getView(R.id.cover_image));
        } else {
            Glide.with(mContext).load(item.getCoverImage()).into((ImageView) helper.getView(R.id.cover_image));
        }
        Log.d(TAG, "convert: "+item.getName());
        helper.setText(R.id.filterName, item.getName());
        helper.addOnClickListener(R.id.rootLayout);
    }

}