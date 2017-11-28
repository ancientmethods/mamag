package com.mamags.mamag.ui;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateMealBinding;
import com.mamags.mamag.databinding.ActivityCreateMealtypeBinding;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.MealInfo;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.CreateMenuViewModel;
import com.mamags.mamag.viewmodel.MealTypeViewModel;

public class CreateMealActivity extends BaseActivity<ActivityCreateMealBinding,CreateMenuViewModel> implements MealTypeView {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_meal);
        viewModel = new CreateMenuViewModel(getApplication(),restAPI);
        viewModel.attach(this);

        //setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("New Meal");
        binding.toolbar.inflateMenu(R.menu.generic_save);
        binding.toolbar.setOnMenuItemClickListener(menuitem-> validate());

        binding.group.setVisibility(View.GONE);

        //get the meal types available
        MealTypeRequest request = new MealTypeRequest();
        request.CrudOption = RequestAction.list.getValue();


        MealTypeViewModel mealTypeViewModel =  new MealTypeViewModel(getApplication(),restAPI);
        mealTypeViewModel.getMealTypeList(request);


    }

    boolean validate(){
        boolean isValid=false;

        if(TextUtils.isEmpty(binding.mealDescription.getEditText().getText().toString())){
            binding.mealDescription.setError("Description field is required");
            return  isValid;
        }
        else{
            isValid = true;

            MealRequest createMealRequest = new MealRequest();
            MealInfo mealInfo = new MealInfo();
            mealInfo.setDescription(binding.mealDescription.getEditText().getText().toString().trim());
            mealInfo.setPrice(Float.valueOf(binding.mealDescription.getEditText().getText().toString().trim()));


            createMealRequest.CrudOption = RequestAction.Add.getValue();
            createMealRequest.setMealInfo(mealInfo);

            viewModel.createMealDisposable(createMealRequest);
        }

        return  isValid;
    }

    @Override
    public void loadMealTypeResults(MealTypeListResponse menuListResponse) {
        //add it to the spinner
        if(menuListResponse.getMealTypeList()!=null){

            binding.progressbar.setVisibility(View.GONE);


            //binding.spinner.setAdapter(new MealTypeListFragment.MealTypeAdapter(mealTypeListResponse.getMealTypeList()));
        }
    }
}
