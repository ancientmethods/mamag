package com.mamags.mamag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamags.mamag.model.MealType;
import com.mamags.mamag.ui.MealTypeListFragment;

import java.util.List;

/**
 * Created by samer on 17/02/2018.
 */

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.ViewHolder> {


        private List<MealType> mealTypes;

        public MealTypeAdapter(List<MealType> menuList) {
            this.mealTypes = menuList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(mealTypes.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return mealTypes.size();
        }

     class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_menu_list_item, parent, false));
            text = itemView.findViewById(R.id.text);

            text.setOnClickListener(v ->{});
        }

    }
}
