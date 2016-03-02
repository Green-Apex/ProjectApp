package com.greenapex.Adaptors;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;
import com.greenapex.R;
import com.greenapex.Utils.Constants;

import java.util.ArrayList;

/**
 * Created by admin on 21-Dec-15.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final RequestManager imageLoader;
    private final ArrayList<String> arrImages;
    private final LayoutInflater mLayoutInflater;

    public ImageViewPagerAdapter(Context mContext, ArrayList<String> arrImages, RequestManager imageLoader) {
        this.mContext = mContext;
        this.arrImages = arrImages;
        this.imageLoader = imageLoader;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final String rowItem = arrImages.get(position);
        View itemView = mLayoutInflater.inflate(R.layout.square_image_item, container, false);

//        SquareImageView imgProfile = (SquareImageView) itemView.findViewById(R.id.imgSquareItem);
        ImageView imgProfile = (ImageView) itemView.findViewById(R.id.imgSquareItem);


        imageLoader.load(Constants.BaseImageDomain+rowItem)
                .asBitmap().centerCrop()
                .placeholder(R.drawable.gallery_icon)
                .error(R.drawable.gallery_icon)
                .into(imgProfile);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
