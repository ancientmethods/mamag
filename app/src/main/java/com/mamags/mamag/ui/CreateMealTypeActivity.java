package com.mamags.mamag.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateMealtypeBinding;
import com.mamags.mamag.databinding.ActivityCreateMenuBinding;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.viewmodel.CreateMenuViewModel;

/**
 * Created by samer on 11/11/2017.
 */

public class CreateMealTypeActivity extends BaseActivity<ActivityCreateMealtypeBinding,CreateMenuViewModel> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_mealtype);
        viewModel = new CreateMenuViewModel(getApplication(),restAPI);
        viewModel.attach(this);

        //setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("New Meal Type");
        binding.toolbar.inflateMenu(R.menu.generic_save);
        binding.toolbar.setOnMenuItemClickListener(menuitem-> validate());


    }

    boolean validate(){
        boolean isValid=false;

        if(TextUtils.isEmpty(binding.mealtypeDescription.getEditText().getText().toString())){
            binding.mealtypeDescription.setError("Description field is required");
            return  isValid;
        }
        else{
            isValid = true;

            MealTypeRequest mealTypeRequest = new MealTypeRequest();
            MealType mealType = new MealType();
            mealType.setDescription(binding.mealtypeDescription.getEditText().getText().toString().trim());

            mealTypeRequest.CrudOption = RequestAction.Add.getValue();
            mealTypeRequest.setMealType(mealType);

            viewModel.createMealTypeDisposable(mealTypeRequest);
        }

        return  isValid;
    }


}
