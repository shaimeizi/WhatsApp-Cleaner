package com.example.pawan.whatsAppcleaner.adapters.innerAdapeters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pawan.whatsAppcleaner.datas.FileDetails;
import com.example.pawan.whatsAppcleaner.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InnerDetailsAdapter_video extends RecyclerView.Adapter<InnerDetailsAdapter_video.InnerDataViewHolder> {

    private Context ctx;
    ArrayList<FileDetails> innerDataList;

    private OnCheckboxListener onCheckboxlistener;



    private static final int PICKFILE_RESULT_CODE = 8778;

    public InnerDetailsAdapter_video(Context ctx, ArrayList<FileDetails> innerDataList, OnCheckboxListener onCheckboxlistener){
        this.ctx = ctx;
        this.innerDataList = innerDataList;
        this.onCheckboxlistener = onCheckboxlistener;

    }

    @Override
    public InnerDetailsAdapter_video.InnerDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.image_wallpaper_content, parent, false);

        return new InnerDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InnerDetailsAdapter_video.InnerDataViewHolder innerDataViewHolder, int positions) {

        final FileDetails details = innerDataList.get(positions);

        Glide.with(ctx).load(details.getPath()).into(innerDataViewHolder.imageView );

         final int pos = positions;

        innerDataViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File a = new File (details.getPath());
                Uri file = FileProvider.getUriForFile(ctx, ctx.getApplicationContext().getPackageName() +
                        ".my.package.name.provider",a);
               intent.setDataAndType(file, "video/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                ctx.startActivity(intent);

            }
        });

        if (details.isSelected()) {
            innerDataViewHolder.checkBox.setChecked(true);
        } else {
            innerDataViewHolder.checkBox.setChecked(false);
        }

        // FIXME: 1/26/19

        innerDataViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                innerDataList.get(innerDataViewHolder.getAdapterPosition()).setSelected(isChecked);

                if (onCheckboxlistener != null) {
                    onCheckboxlistener.onCheckboxClicked(buttonView,innerDataList);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return innerDataList.size();
    }


    public class InnerDataViewHolder extends RecyclerView.ViewHolder {

        TextView image_name;
        ImageView imageView;
        CardView cardView;
        CheckBox checkBox;
        public InnerDataViewHolder(View itemView) {
            super(itemView);

//            image_name = itemView.findViewById(R.id.img_name);
            imageView  = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.card_view1);
            checkBox = itemView.findViewById(R.id.checkbox);

        }
    }

    public interface OnCheckboxListener {
        void onCheckboxClicked(View view, List<FileDetails> updatedFiles);
    }
}