/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */

package com.dqr.www.textimagepager.textimagepager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.dqr.www.textimagepager.R;
import com.dqr.www.textimagepager.photoview.PhotoView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private View view;
    private List<ImagePagerBean> mImages;
    private Context mContext;
    private OnViewPagerAdapterListener mListener;

    public void setListener(OnViewPagerAdapterListener listener) {
        mListener = listener;
    }

    public ViewPagerAdapter(List<ImagePagerBean> urls, Context context) {
        mImages = urls;
        mContext = context;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        view = (View) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public View getPrimaryItem() {
        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImagePagerBean bean = mImages.get(position);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.text_image_view_pager_item, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.pv_image);
        photoView.enable();

        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(view.getContext())
                .load(bean.getSmallUrl())
                .thumbnail(0.1f);//加载缩略图;

        Glide.with(view.getContext())
                .load(bean.getUrl())
                .thumbnail(0.1f)//加载缩略图
                .fitCenter()
                .error(R.mipmap.ic_launcher)
                .into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mListener != null) {
                    mListener.onClickListener(arg0, position);
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view1) {
                if (mListener != null) {
                    mListener.onLongClickListener(view1, position);
                }
                return false;
            }
        });
        container.addView(view);
        view.setId(position);
        return view;
    }

    public interface OnViewPagerAdapterListener {
        void onClickListener(View view, int position);

        void onLongClickListener(View view, int position);
    }


}