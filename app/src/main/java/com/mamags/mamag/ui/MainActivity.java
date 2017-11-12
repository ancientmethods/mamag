package com.mamags.mamag.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.databinding.ActivityMainBinding;
import com.mamags.mamag.viewmodel.MenuViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding,MenuViewModel> implements MenusListFragment.Listener{






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);
        bindView(R.layout.activity_main);

        binding.navigation.setOnNavigationItemSelectedListener(item -> onNavigationItemSelected(item));

    }

    boolean onNavigationItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                binding.message.setText(R.string.title_dashboard);
                return true;
            case R.id.navigation_menus:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, new MenusListFragment()).commit();
                return true;


        }
        return false;
    }


    @Override
    public void onMenuClicked(int position) {
        switch (position){
            case 1:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, new MealTypeListFragment()).commit();
                break;
        }
    }
}
