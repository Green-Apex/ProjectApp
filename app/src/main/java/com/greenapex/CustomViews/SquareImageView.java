package com.greenapex.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

    private final Context mContext;
//    private Drawable drawable;
//    private int borderSize =5;

    public SquareImageView(Context context) {
        super(context);
        mContext = context;
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//
//        super.onDraw(canvas);
//        try{
//            drawable = getDrawable();
//           Bitmap bmp = ((BitmapDrawable)drawable).getBitmap() ;
//           Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2,
//                   bmp.getHeight() + borderSize * 2, bmp.getConfig());
//            canvas.drawBitmap(bmpWithBorder, 0 , 0 , null);
//            canvas.drawColor(Color.WHITE);
//            Resources res = mContext.getResources();
//            canvas.drawBitmap(bmp, borderSize, borderSize, null);
//        }
//        catch (NullPointerException e) {
//
//            e.printStackTrace();
//        }
//    }
}