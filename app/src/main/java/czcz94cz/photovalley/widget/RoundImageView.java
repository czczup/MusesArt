package czcz94cz.photovalley.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import czcz94cz.photovalley.R;

public class RoundImageView extends AppCompatImageView {
    float width, height;

    public RoundImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= 30 && height > 30) {
            Path path = new Path();
            //四个圆角
            path.moveTo(30, 0);
            path.lineTo(width - 30, 0);
            path.quadTo(width, 0, width, 30);
            path.lineTo(width, height - 30);
            path.quadTo(width, height, width - 30, height);
            path.lineTo(30, height);
            path.quadTo(0, height, 0, height - 30);
            path.lineTo(0, 30);
            path.quadTo(0, 0, 30, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
