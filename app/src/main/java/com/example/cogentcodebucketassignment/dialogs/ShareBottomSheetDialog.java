package com.example.cogentcodebucketassignment.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.databinding.BottomSheetShareBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShareBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    //class-level variables
    BottomSheetShareBinding binding;
    BottomSheetDialog bottomSheetDialog;

    //CONSTRUCTOR
    public ShareBottomSheetDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
            bottomSheetDialog.setOnShowListener(dialogInterface -> {
                View bottomSheetRootView = (bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet));
                bottomSheetRootView.setBackgroundColor(Color.TRANSPARENT);
            });
        } else
            bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_COLLAPSED);

        return bottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null)
            binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_share, container, false);
        else
            onDetach();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setting onClickListeners
        binding.shareLink.setOnClickListener(this);
        binding.telegram.setOnClickListener(this);
        binding.whatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.share_link) {
            Toast.makeText(getContext(), "Share Linked Triggered", Toast.LENGTH_LONG).show();
        } else if (id == R.id.telegram) {
            Toast.makeText(getContext(), "Telegram share Triggered", Toast.LENGTH_LONG).show();
        } else if (id == R.id.whatsapp) {
            Toast.makeText(getContext(), "Whatsapp share Triggered", Toast.LENGTH_LONG).show();
        }
        dismiss();
    }
}
