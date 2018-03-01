package com.mamags.mamag.adapters;

import android.view.View;
import android.widget.TextView;

import com.mamags.mamag.R;
import com.mamags.mamag.model.MealType;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Samer on 28/02/2018.
 */

public class MealTypeFlexItem extends AbstractFlexibleItem<MealTypeFlexItem.MyViewHolder> {




    MealType mealType;

    public MealTypeFlexItem(MealType mealType){
        this.mealType = mealType;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_menu_list_item;
    }

    @Override
    public MyViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new MyViewHolder(view, adapter);
    }
    /**
     * When an item is equals to another?
     * Write your own concept of equals, mandatory to implement or use
     * default java implementation (return this == o;) if you don't have unique IDs!
     * This will be explained in the "Item interfaces" Wiki page.
     */
    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof MealType) {
            MealType inItem = (MealType) inObject;
            return this.mealType.getDescription().equals(inItem.getDescription());
        }
        return false;
    }



    @Override
    public void bindViewHolder(FlexibleAdapter adapter, MyViewHolder holder, int position, List<Object> payloads) {
        holder.mTitle.setText(mealType.getDescription());
        // Title appears disabled if item is disabled
        holder.mTitle.setEnabled(isEnabled());
    }



    /**
     * The ViewHolder used by this item.
     * Extending from FlexibleViewHolder is recommended especially when you will use
     * more advanced features.
     */
    public class MyViewHolder extends FlexibleViewHolder {

        public TextView mTitle;

        public MyViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = itemView.findViewById(R.id.text);
        }
    }
}
