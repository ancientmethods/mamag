package com.mamags.mamag.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.viewmodel.MenuViewModel;

public class CreateMenuActivity extends BaseActivity<ViewDataBinding,BaseViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        setContentView(R.layout.activity_create_menu);

        viewModel = new MenuViewModel(getApplication(),restAPI);
        viewModel.attach(this);
    }
}
