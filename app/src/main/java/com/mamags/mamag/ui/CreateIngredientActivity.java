package com.mamags.mamag.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.adapters.DefaultListItem;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateIngredientBinding;
import com.mamags.mamag.databinding.ActivityCreateMenuBinding;
import com.mamags.mamag.model.Ingredient;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Requests.IngredientsRequest;
import com.mamags.mamag.model.Requests.MenuRequest;
import com.mamags.mamag.viewmodel.CRUDViewModel;

/**
 * Created by samer on 19/03/2018.
 */

public class CreateIngredientActivity extends BaseActivity<ActivityCreateIngredientBinding, CRUDViewModel> {

    boolean isEdit = false;
    DefaultListItem defaultListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_ingredient);
        viewModel = new CRUDViewModel(getApplication(), restAPI);
        viewModel.attach(this);

        if (getIntent() != null && getIntent().getBooleanExtra("edit", false)) {
            isEdit = true;
            binding.toolbar.setTitle(R.string.edit_menu);
            defaultListItem = getIntent().getParcelableExtra("ingredient");

            if(defaultListItem!=null){
                binding.ingredientName.getEditText().setText(defaultListItem.getIngredient().getDescription());
                binding.ingredientPrice.getEditText().setText(defaultListItem.getIngredient().getPrice());

            }
            binding.toolbar.setTitle("Edit Ingredient");

        }
        else{
            binding.toolbar.setTitle("New Ingredient");

        }
        //setSupportActionBar(binding.toolbar);
        binding.toolbar.inflateMenu(R.menu.generic_save);
        binding.toolbar.setOnMenuItemClickListener(menuitem -> validate());


    }

    boolean validate() {
        boolean isValid = false;

        if (TextUtils.isEmpty(binding.ingredientName.getEditText().getText().toString())) {
            binding.ingredientName.setError(getString(R.string.name_required));
            return isValid;
        } else {
            isValid = true;

            IngredientsRequest ingredientsRequest = new IngredientsRequest();
            Ingredient ingredient = new Ingredient();
            ingredient.setDescription(binding.ingredientName.getEditText().getText().toString().trim());
            ingredient.setPrice(binding.ingredientPrice.getEditText().getText().toString().trim());
            if (isEdit) {
                ingredientsRequest.CrudOption = RequestAction.Update.getValue();
                ingredient.setId(defaultListItem.getIngredient().getId());


            } else {
                ingredientsRequest.CrudOption = RequestAction.Add.getValue();

            }
            ingredientsRequest.setIngredient(ingredient);

            viewModel.createIngredientDisposable(ingredientsRequest);
        }

        return isValid;
    }
}
