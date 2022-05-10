package com.example.cogentcodebucketassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cogentcodebucketassignment.databinding.RvLanguagePreferenceItemBinding;
import com.example.cogentcodebucketassignment.model.LanguageItem;

import java.util.List;

public class LanguagePreferenceRvAdapter extends RecyclerView.Adapter<LanguagePreferenceRvAdapter.ViewHolder> {
    //CLASS LEVEL VARIABLES
    Context mContext;
    int selectedLanguagePosition;
    List<LanguageItem> mList;

    //CONSTRUCTOR
    public LanguagePreferenceRvAdapter(Context context, List<LanguageItem> list) {
        mContext = context;
        mList = list;
        selectedLanguagePosition = 0;
    }

    public int getSelectedLanguagePosition() {
        return selectedLanguagePosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLanguagePreferenceItemBinding binding = RvLanguagePreferenceItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.language.setText(mList.get(position).getLocalName());
        if (selectedLanguagePosition == position) {
            holder.binding.imgSelectedTick.setVisibility(View.VISIBLE);
        } else
            holder.binding.imgSelectedTick.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //CLASS LEVEL VARIABLES
        RvLanguagePreferenceItemBinding binding;

        public ViewHolder(RvLanguagePreferenceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.rootLayout.setOnClickListener(view -> {
                selectedLanguagePosition = getAdapterPosition();
                notifyDataSetChanged();
            });
        }
    }
}
