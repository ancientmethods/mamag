package com.mamags.mamag.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.mamags.mamag.BaseActivity;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.adapters.DefaultListItem;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.ActivityCreateMenuBinding;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Requests.MenuRequest;
import com.mamags.mamag.viewmodel.CRUDViewModel;

public class CreateMenuActivity extends BaseActivity<ActivityCreateMenuBinding, CRUDViewModel> {

    boolean isEdit = false;
    DefaultListItem defaultListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getComponent().inject(this);

        bindView(R.layout.activity_create_menu);
        viewModel = new CRUDViewModel(getApplication(), restAPI);
        viewModel.attach(this);

        if (getIntent() != null && getIntent().getBooleanExtra("edit", false)) {
            isEdit = true;
            binding.toolbar.setTitle(R.string.edit_menu);
            defaultListItem = getIntent().getParcelableExtra("menu");

            if(defaultListItem!=null){
                binding.menuDescription.getEditText().setText(defaultListItem.getMenu().getDescription());
                binding.menuName.getEditText().setText(defaultListItem.getMenu().getName());

            }
        }
        //setSupportActionBar(binding.toolbar);
        binding.toolbar.inflateMenu(R.menu.generic_save);
        binding.toolbar.setOnMenuItemClickListener(menuitem -> validate());


    }

    boolean validate() {
        boolean isValid = false;

        if (TextUtils.isEmpty(binding.menuName.getEditText().getText().toString())) {
            binding.menuName.setError(getString(R.string.name_required));
            return isValid;
        } else {
            isValid = true;

            MenuRequest menuAddRequest = new MenuRequest();
            Menu menu = new Menu();
            menu.setName(binding.menuName.getEditText().getText().toString().trim());
            menu.setDescription(binding.menuDescription.getEditText().getText().toString().trim());
            if (isEdit) {
                menuAddRequest.CrudOption = RequestAction.Update.getValue();
                menu.setId(defaultListItem.getMenu().getId());


            } else {
                menuAddRequest.CrudOption = RequestAction.Add.getValue();

            }
            menuAddRequest.setMenu(menu);

            viewModel.createMenu(menuAddRequest);
        }

        return isValid;
    }


}
