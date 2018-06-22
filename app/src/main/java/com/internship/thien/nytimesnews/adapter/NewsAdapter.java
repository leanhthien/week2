package com.internship.thien.nytimesnews.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.data.model.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> mNews;
    private Context mContext;
    private NewsItemListener mNewsListener;
    //int LENGTH = 150;
    int RADIUS = 10;

    public NewsAdapter(Context context, List<News> News, NewsItemListener itemListener) {
        mNews = News;
        mContext = context;
        mNewsListener = itemListener;
    }

    public NewsAdapter(Context ctx) {

        mNews = new ArrayList<>();;
        this.mContext = ctx;
    }

    @Override
    @NonNull
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View postView;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int orientation = mContext.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (viewType == 0)
                postView = inflater.inflate(R.layout.item_news, parent, false);
            else
                postView = inflater.inflate(R.layout.item_news, parent, false);

        }
        else
            postView = inflater.inflate(R.layout.item_news, parent, false);

        return new ViewHolder(postView, this.mNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        final String link_thumbnail;

        News News = mNews.get(position);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(4f);
        circularProgressDrawable.setCenterRadius(40f);
        circularProgressDrawable.setColorSchemeColors(android.R.color.holo_orange_light);
        circularProgressDrawable.start();

        //int orientation = mContext.getResources().getConfiguration().orientation;
        //if (orientation == Configuration.ORIENTATION_PORTRAIT)

        assert holder.headline != null;
        holder.headline.setText(News.getHeadline().getMain());
        assert holder.snippet != null;
        holder.snippet.setText(News.getSnippet());

        if(News.getMultimedia().size()!= 0) {
            link_thumbnail = createThumbnailLink(News.getMultimedia().get(0).getUrl());
            Glide.with(mContext).load(link_thumbnail)
                    .apply(new RequestOptions()
                            .placeholder(circularProgressDrawable)
                            .fitCenter())
                    .apply(bitmapTransform(new RoundedCornersTransformation(RADIUS, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(holder.thumbnail);
        }
        else {

            Glide.with(mContext).load("")
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.img_nytimes_logo_default)
                            .fitCenter())
                    .apply(bitmapTransform(new RoundedCornersTransformation(RADIUS, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(holder.thumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    //For ........
    @Override
    public int getItemViewType(int position) {
        if (mNews.get(position).getMultimedia().size() != 0)
            return 0;
        else
            return 1;

    }

    public void setData(List<News> data) {
        mNews.clear();
        mNews.addAll(data);
        notifyDataSetChanged();
    }


    public void setListener(NewsItemListener listener) {
        this.mNewsListener = listener;
    }

    public void updateNews(List<News> News) {
        mNews = News;
        notifyDataSetChanged();
    }

    private News getItem(int adapterPosition) {
        return mNews.get(adapterPosition);
    }

    private String createThumbnailLink(String path) {

        final String BASE_IMAGES_URL = "https://www.nytimes.com/";

        if (path == null) return null;
        return BASE_IMAGES_URL + path;
    }

    public interface NewsItemListener {
        void onNewsClick(News News);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.headline_news)
        @Nullable
        TextView headline;
        @BindView(R.id.snippet_news)
        @Nullable
        TextView snippet;
        @BindView(R.id.thumbnail_news)
        ImageView thumbnail;

        NewsItemListener mNewsListener;

        private ViewHolder(View itemView, NewsItemListener NewItemListener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.mNewsListener = NewItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            this.mNewsListener.onNewsClick(getItem(getAdapterPosition()));
            notifyDataSetChanged();
        }
    }
}
