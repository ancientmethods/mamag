package com.mamags.mamag.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mamags.mamag.R;
import com.mamags.mamag.Utils.TabsList;

import java.util.List;

/**
 * Created by Samer on 20/02/2018.
 */

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHoldeR> {

    List<TabsList> compoundstabs_list;

    private FragmentActivity activity;
    private Listener listener;

    public TabsAdapter(FragmentActivity act,List<TabsList> tabslist_) {
        compoundstabs_list = tabslist_;
        this.activity = act;
        listener = (Listener)act;
    }

    @Override
    public TabsAdapter.ViewHoldeR onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TabsAdapter.ViewHoldeR(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(TabsAdapter.ViewHoldeR holder, int position) {
        holder.text.setText(compoundstabs_list.get(position).getName());


        int resourceid = activity.getResources().getIdentifier(compoundstabs_list.get(position).getIcon(), "drawable", activity.getPackageName());
        if (resourceid != -1) {
            holder.iconimage.setImageResource(resourceid);

        }
        //tab on click listener
        holder.tab_linear.setOnClickListener(new OnTabClicked(compoundstabs_list.get(holder.getAdapterPosition()).getName()));

    }



    @Override
    public int getItemCount() {
        return compoundstabs_list.size();
    }

    public class ViewHoldeR extends RecyclerView.ViewHolder {

        final TextView text;
        final ImageView iconimage;
        final RelativeLayout tab_linear;


        ViewHoldeR(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_tabs_list_dialog_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
            iconimage = itemView.findViewById(R.id.iconimage);
            tab_linear = itemView.findViewById(R.id.tab_linear);

        }

    }

    private class OnTabClicked implements View.OnClickListener{

        String tabname;
        public OnTabClicked(String tabname){
            this.tabname =tabname;

        }
        @Override
        public void onClick(View v) {
            listener.onTabsClicked(tabname);
        }
    }

    public interface Listener {
        void onTabsClicked(String name);
    }

}