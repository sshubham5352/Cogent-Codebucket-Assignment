package com.example.cogentcodebucketassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.databinding.RvNewsTopHeadlinesItemBinding;
import com.example.cogentcodebucketassignment.interfaces.NewsTopHeadlinesRvListener;
import com.example.cogentcodebucketassignment.model.responses.NewsTopHeadlineResponse;
import com.example.cogentcodebucketassignment.utils.Helper;

import java.util.List;

public class NewsTopHeadlinesRvAdapter extends RecyclerView.Adapter<NewsTopHeadlinesRvAdapter.ViewHolder> {
    //CLASS LEVEL VARIABLES
    Context mContext;
    int selectedLanguagePosition;
    List<NewsTopHeadlineResponse.NewsArticle> mList;
    NewsTopHeadlinesRvListener mListener;

    //CONSTRUCTOR
    public NewsTopHeadlinesRvAdapter(Context context, List<NewsTopHeadlineResponse.NewsArticle> list, NewsTopHeadlinesRvListener listener) {
        mContext = context;
        mList = list;
        mListener = listener;
    }

    public int getSelectedLanguagePosition() {
        return selectedLanguagePosition;
    }

    @NonNull
    @Override
    public NewsTopHeadlinesRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvNewsTopHeadlinesItemBinding binding = RvNewsTopHeadlinesItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsTopHeadlineResponse.NewsArticle currentItem = mList.get(position);

        Glide.with(mContext)
                .load(currentItem.getUrlToImage())
                .apply(new RequestOptions().placeholder(R.drawable.img_loading_place_holder).error(R.drawable.img_loading_place_holder))
                .into(holder.binding.imgContentPreview);

        holder.binding.newsSource.setText(currentItem.getSource().getName());
        holder.binding.newsOrigin.setText("India");
        holder.binding.newsTimestamp.setText(getTimeLabel(currentItem.getPublishedAt()));
        if (currentItem.getContent() != null)
            holder.binding.newsContent.setText(currentItem.getContent());
        else if (currentItem.getTitle() != null) {
            holder.binding.newsContent.setText(currentItem.getTitle());
        }
    }

    private String getTimeLabel(String publishedAt) {
        String timeLabel = "";
        String timeStamp = Helper.changeDateFormat(publishedAt, Helper.RAW_DATE_FORMAT, Helper.STANDARD_DATE_FORMAT);
        if (Helper.isThisToday(timeStamp)) {
            timeLabel = "Today " + Helper.getOnlyTime(timeStamp);
        } else
            timeLabel = timeStamp;

        return ", " + timeLabel;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //CLASS LEVEL VARIABLES
        RvNewsTopHeadlinesItemBinding binding;

        public ViewHolder(RvNewsTopHeadlinesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.share.setOnClickListener(view -> {
                mListener.openShareBottomSheet();
            });
        }
    }
}
