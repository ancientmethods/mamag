package com.mamags.mamag.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.Utils.TT_Tabs;
import com.mamags.mamag.Utils.TabsList;
import com.mamags.mamag.api.TabsAdapter;
import com.mamags.mamag.databinding.ActivityMainBinding;
import com.mamags.mamag.viewmodel.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MenuViewModel> implements TabsAdapter.Listener {


    BottomSheetBehavior mBottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);
        bindView(R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.title_main);
        binding.toolbar.setOnMenuItemClickListener(onMenuOpenClickListener);

        loadTabs();
        loadBottomSheet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tt, menu);
        return true;
    }

    Toolbar.OnMenuItemClickListener onMenuOpenClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.design_bottom_shee:
                    if (mBottomSheetBehavior != null) {
                        if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                        } else {

                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }


                    return true;
            }
            return false;
        }
    };



    void loadBottomSheet() {


        TT_Tabs.getInstance().setDashboard(getString(R.string.title_dashboard));
        onTabsClicked(TT_Tabs.getInstance().getDashboard());

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);

        mBottomSheetBehavior.setPeekHeight(150);
        mBottomSheetBehavior.setHideable(true);
        //this will make sure the sheet will close
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                    //showLoadedState();
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });


    }


    /**
     * this will determine which fragment we should produce
     *
     * @param name
     */
    @Override
    public void onTabsClicked(String name) {

        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (!name.equals(TT_Tabs.getInstance().getDashboard())) {
            binding.toolbar.setTitle(name);
        }

        if (name.equals(TT_Tabs.getInstance().getDashboard())) {


        } else if (name.equals(TT_Tabs.getInstance().getMenus())) {


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, new MenusListFragment()).commitAllowingStateLoss();

        } else if (name.equals(TT_Tabs.getInstance().getOrders())) {


        } else if (name.equals(TT_Tabs.getInstance().getMealtypes())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, new MealTypeListFragment()).commit();

        }

    }

    /**
     * load the tabs and their count
     */

    void loadTabs() {

        TT_Tabs.getInstance().setDashboard(getString(R.string.title_dashboard));
        TT_Tabs.getInstance().setOrders(getString(R.string.orders));
        TT_Tabs.getInstance().setMenus(getString(R.string.menus));
        TT_Tabs.getInstance().setMeals(getString(R.string.menu_meals));
        TT_Tabs.getInstance().setMealtypes(getString(R.string.menu_meal_types));
        TT_Tabs.getInstance().setIngredients(getString(R.string.menu_ingredients));


        List<TabsList> compoundstabs_list = new ArrayList<>();
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getDashboard(), "ic_dashboard_black_24dp"));
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getOrders(), "ic_featured_play_list_black_24dp"));
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getMenus(), "ic_restaurant_menu_black_24dp"));
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getMeals(), "ic_dashboard_black_24dp"));
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getMealtypes(), "ic_featured_play_list_black_24dp"));
        compoundstabs_list.add(new TabsList(TT_Tabs.getInstance().getIngredients(), "ic_restaurant_menu_black_24dp"));

        binding.list.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        binding.list.setAdapter(new TabsAdapter(MainActivity.this, compoundstabs_list));


    }

}
