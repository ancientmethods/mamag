package com.mamags.mamag.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.adapters.DefaultListItem;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateMealtypeBinding;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.viewmodel.CRUDViewModel;

/**
 * Created by samer on 11/11/2017.
 */

public class CreateMealTypeActivity extends BaseActivity<ActivityCreateMealtypeBinding,CRUDViewModel> {

    boolean isEdit = false;
    DefaultListItem defaultListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_mealtype);
        viewModel = new CRUDViewModel(getApplication(),restAPI);
        viewModel.attach(this);



        if (getIntent() != null && getIntent().getBooleanExtra("edit", false)) {
            isEdit = true;
            binding.toolbar.setTitle(R.string.edit_menu);
            defaultListItem = getIntent().getParcelableExtra("mealtype");

            if(defaultListItem!=null){
                binding.mealtypeDescription.getEditText().setText(defaultListItem.getMenu().getDescription());

            }    //setSupportActionBar(binding.toolbar);
            binding.toolbar.setTitle("Edit Meal Type");
        }
        else{
            binding.toolbar.setTitle("New Meal Type");
        }

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

            if (isEdit) {
                mealTypeRequest.CrudOption = RequestAction.Update.getValue();
                mealType.setId(defaultListItem.getMenu().getId());


            } else {
                mealTypeRequest.CrudOption = RequestAction.Add.getValue();

            }
            mealTypeRequest.CrudOption = RequestAction.Add.getValue();
            mealTypeRequest.setMealType(mealType);

            viewModel.createMealTypeDisposable(mealTypeRequest);
            finish();
        }

        return  isValid;
    }


}
