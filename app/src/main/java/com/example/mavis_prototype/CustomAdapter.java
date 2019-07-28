package com.example.mavis_prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter {

    String[] spinnerCategories;
    int[] spinnerImages;
    Context mContext;

    public CustomAdapter(@NonNull Context context, String[] categories, int[] images) {
        super(context, R.layout.custom_spinner_row);
        this.spinnerCategories = categories;
        this.spinnerImages = images;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return spinnerCategories.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
            mViewHolder.mImage= (ImageView) convertView.findViewById(R.id.ivCategory);
            mViewHolder.mCategory= (TextView) convertView.findViewById(R.id.tvCategory);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mImage.setImageResource(spinnerImages[position]);
        mViewHolder.mCategory.setText(spinnerCategories[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView mImage;
        TextView mCategory;
    }
}
