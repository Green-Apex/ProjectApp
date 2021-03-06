package com.greenapex.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.AutoCompleteTextView;

import com.greenapex.R;

public class CustomAutoCompleteTextView extends AutoCompleteTextView {
    private Typeface typeface;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.app);
        String customFont = a.getString(R.styleable.app_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    private boolean setCustomFont(Context ctx, String asset) {
        try {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(ctx.getAssets(),
                        getResources().getString(R.string.app_font1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        setTypeface(typeface);
        return true;
    }

    private Animation inAnimation;
    private Animation outAnimation;

    public void setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            if (visibility == VISIBLE) {
                if (inAnimation != null)
                    startAnimation(inAnimation);
            } else if ((visibility == INVISIBLE) || (visibility == GONE)) {
                if (outAnimation != null)
                    startAnimation(outAnimation);
            }
        }

        super.setVisibility(visibility);
    }
}