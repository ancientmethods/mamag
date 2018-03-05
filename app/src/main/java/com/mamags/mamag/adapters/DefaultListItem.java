package com.mamags.mamag.adapters;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.mamags.mamag.R;
import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Menu;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flipview.FlipView;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Samer on 28/02/2018.
 */

public class DefaultListItem extends AbstractFlexibleItem<DefaultListItem.MyViewHolder> implements Parcelable {


    MealType mealType;

    Menu menu;

    int listType;

    public DefaultListItem(MealType mealType, int listType) {
        this.mealType = mealType;
        this.listType = listType;
    }

    public DefaultListItem(Menu menu,  int listType) {
        this.menu = menu;
        this.listType = listType;

    }

    public MealType getMealType() {
        return mealType;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_simple_item;
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

        if (inObject instanceof Menu) {
            MealType inItem = (MealType) inObject;
            return this.menu.getDescription().equals(inItem.getDescription());
        }

        return false;
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, MyViewHolder holder, int position, List<Object> payloads) {

        switch (listType) {
            case 0:
                holder.mTitle.setText(menu.getDescription());

                break;
            case 1:
                holder.mTitle.setText(mealType.getDescription());

                break;
        }
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
        FlipView mFlipView;

        public MyViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = view.findViewById(R.id.title);
            this.mFlipView = view.findViewById(R.id.image);
            this.mFlipView.setOnClickListener(
                    v -> {
                        mAdapter.mItemLongClickListener.onItemLongClick(getAdapterPosition());
                        toggleActivation();
                    });
        }

        @Override
        public void toggleActivation() {
            super.toggleActivation();
            // Here we use a custom Animation inside the ItemView
            mFlipView.flip(mAdapter.isSelected(getAdapterPosition()));
        }

        @Override
        public float getActivationElevation() {
            return DisplayUtils.pxFromDp(itemView.getContext(), 4f);
        }


    }


    protected DefaultListItem(Parcel in) {
        mealType = (MealType) in.readValue(MealType.class.getClassLoader());
        menu = (Menu) in.readValue(Menu.class.getClassLoader());
        listType = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mealType);
        dest.writeValue(menu);
        dest.writeInt(listType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DefaultListItem> CREATOR = new Parcelable.Creator<DefaultListItem>() {
        @Override
        public DefaultListItem createFromParcel(Parcel in) {
            return new DefaultListItem(in);
        }

        @Override
        public DefaultListItem[] newArray(int size) {
            return new DefaultListItem[size];
        }
    };
}
