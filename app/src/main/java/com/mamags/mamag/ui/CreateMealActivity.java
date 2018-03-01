package com.mamags.mamag.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateMealBinding;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.MealInfo;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.CRUDViewModel;
import com.mamags.mamag.viewmodel.MealTypeViewModel;

public class CreateMealActivity extends BaseActivity<ActivityCreateMealBinding,CRUDViewModel> implements MealTypeView {


    MealTypeViewModel mealTypeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_meal);
        viewModel = new CRUDViewModel(getApplication(),restAPI);
        viewModel.attach(this);

        binding.toolbar.setTitle("New Meal");
        binding.toolbar.inflateMenu(R.menu.generic_save);
        binding.toolbar.setOnMenuItemClickListener(menuitem-> validate());

        binding.group.setVisibility(View.GONE);

        //get the meal types available
        MealTypeRequest request = new MealTypeRequest();
        request.CrudOption = RequestAction.list.getValue();


        mealTypeViewModel =  new MealTypeViewModel(getApplication(),restAPI);
        mealTypeViewModel.attach(this);
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
            mealInfo.setPrice(Float.valueOf(binding.mealPrice.getEditText().getText().toString().trim()));
            mealInfo.setCustomisable(binding.switchCustom.getShowText());

            createMealRequest.CrudOption = RequestAction.Add.getValue();
            createMealRequest.setMealInfo(mealInfo);

            viewModel.createMealDisposable(createMealRequest);
        }

        return  isValid;
    }

    @Override
    public void loadMealTypeResults(MealTypeListResponse mealTypeListResponse) {
        //add it to the spinner
        if(mealTypeListResponse.getMealTypeList()!=null){
            binding.group.setVisibility(View.VISIBLE);
            binding.progressbar.setVisibility(View.GONE);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item);
            for (MealType mealType: mealTypeListResponse.getMealTypeList()){
                arrayAdapter.add(mealType.getDescription());
            }
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            binding.spinner.setAdapter(arrayAdapter);
        }
    }
}
