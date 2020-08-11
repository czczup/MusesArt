package czcz94cz.photovalley.features.albumpicker;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

/**
 * Created by VanXN on 2018/3/8.
 */

public class AlbumPickerUtils {

    public static void showSnackBar(FloatingActionButton fab, CharSequence text) {
        Snackbar.make(fab, text, Snackbar.LENGTH_SHORT).show();
    }
}
