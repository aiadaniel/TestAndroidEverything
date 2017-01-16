package com.vigorous.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vigorous.BitmapEntity;
import com.vigorous.FileDownload.DownloadMgr;
import com.vigorous.testandroideverything.R;
import com.vigorous.widget.LoadingImageView;

import java.util.List;

public class BaseRecycleViewAdapter extends RecyclerView.Adapter {
    public static final int CACHE_FRESCO = 1;
    public static final int CACHE_NONE = 2;

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    private Context mContext;
    private List<BitmapEntity> mBitmapLists = null;
    private int mCacheType = CACHE_FRESCO;

    public BaseRecycleViewAdapter(Context context,List<BitmapEntity> lists,int cacheType) {
        mBitmapLists = lists;
        mCacheType = cacheType;
        mContext = context;
    }

    public void setBitmapLists(List<BitmapEntity> lists) {
        mBitmapLists = lists;
        this.notifyDataSetChanged();
    }

    private int getLayoutId() {
        if (mCacheType == CACHE_FRESCO) return R.layout.item_bitmap_fresco;
        if (mCacheType == CACHE_NONE) return R.layout.item_bitmap_normal;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
                return new ItemViewHolder(view,mCacheType);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        if (mBitmapLists == null) {
            return 0;
        }
        return mBitmapLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mBitmapLists != null) {
            BitmapEntity item = mBitmapLists.get(position);
            ((ItemViewHolder) holder).mTvWho.setText(item._who);
            ((ItemViewHolder) holder).mTvPublish.setText(item._publishedAt);
            ((ItemViewHolder) holder).mTvType.setText(item._type);
            if (mCacheType == CACHE_FRESCO) {
                Uri uri = Uri.parse(item._url);
                ((ItemViewHolder) holder).mDraweeIv.setImageURI(uri);
            } else if (mCacheType == CACHE_NONE) {
                DownloadMgr.getInstance(mContext).addDownloadTask(item._url,((ItemViewHolder) holder).mNormalIv);//TODO  当然不要这样做，重新封装一下~
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mDraweeIv;
        LoadingImageView mNormalIv;
        TextView mTvWho, mTvPublish, mTvType;

        public ItemViewHolder(View view,int cacheType) {
            super(view);
            if (cacheType == CACHE_FRESCO)
                mDraweeIv = (SimpleDraweeView) view.findViewById(R.id.iv);
            if (cacheType ==CACHE_NONE)
                mNormalIv = (LoadingImageView) view.findViewById(R.id.iv);

            mTvWho = (TextView) view.findViewById(R.id.tv_who);
            mTvPublish = (TextView) view.findViewById(R.id.tv_publish);
            mTvType = (TextView) view.findViewById(R.id.tv_type);
        }
    }
}
