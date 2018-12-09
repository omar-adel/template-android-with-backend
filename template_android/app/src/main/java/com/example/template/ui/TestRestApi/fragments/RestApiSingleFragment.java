package com.example.template.ui.TestRestApi.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.model.IsEmptyRule;
import com.basgeekball.awesomevalidation.model.Rule;
import com.example.template.R;
import com.example.template.model.bean.CategoryRestApi;
import com.example.template.model.bean.CategoryRestApiResponse;
import com.example.template.model.bean.EditRestApiResponse;
import com.example.template.model.bean.ItemRestApi;
import com.example.template.model.bean.ItemRestApiResponse;
import com.example.template.ui.TestRestApi.RestApiSingleAct;
import com.example.template.ui.TestRestApi.presenter.single.IRestApiSingleFragmentContract;
import com.example.template.ui.TestRestApi.presenter.single.RestApiSingleFragmentPresenter;
import modules.general.ui.utils.GUI.CustomSpinner;
import modules.general.utils.validation.awesome.ValidationUtilAwesome;
import modules.general.utils.validation.listeners.OnValidationCallBack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.mvp_base.BaseSupportFragment;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.RestApiSingleAction;
import static modules.general.utils.Constants.RestApiSingleActionAdd;
import static modules.general.utils.Constants.RestApiSingleActionGet;
import static modules.general.utils.Constants.RestApiSingleSource;
import static modules.general.utils.Constants.RestApiSourceCategories;
import static modules.general.utils.Constants.RestApiSourceItems;

public class RestApiSingleFragment extends BaseSupportFragment<RestApiSingleFragmentPresenter>
        implements IRestApiSingleFragmentContract.IRestApiSingleFragmentView
        , CustomSpinner.SpinnerListener, OnValidationCallBack {

    CategoryRestApiResponse categoryRestApiResponse;
    ItemRestApiResponse itemRestApiResponse;
    String sourceFragment;
    String sourceAction;
    long elementId = 0l;
    long elementCategoryId = 0l;
    @BindView(R.id.spCategories)
    CustomSpinner spCategories;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.edDescription)
    EditText edDescription;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.rlBottomBtns)
    RelativeLayout rlBottomBtns;
    @BindView(R.id.llEditDelete)
    LinearLayout llEditDelete;
    ProgressDialog progressDialog;

    public String getSourceFragment() {
        return sourceFragment;
    }

    ValidationUtilAwesome validationUtilAwesome;

    @Override
    public int getLayoutResource() {
        return R.layout.frg_test_rest_api_single;
    }

    @Override
    public void configureUI() {

        sourceFragment = getArguments().getString(RestApiSingleSource);
        sourceAction = getArguments().getString(RestApiSingleAction);
        elementId = getArguments().getLong(INPUT_KEY);

        validationUtilAwesome = new ValidationUtilAwesome(getContainerActivity(), this);
        addValidationForEditText();

        if (sourceAction.equals(RestApiSingleActionGet)) {

            btnAdd.setVisibility(View.GONE);
            llEditDelete.setVisibility(View.VISIBLE);

        } else if (sourceAction.equals(RestApiSingleActionAdd))

        {
            llEditDelete.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        }


        if (sourceFragment.equals(RestApiSourceCategories)) {

            ((RestApiSingleAct) getContainerActivity()).getRestApiSinglePresenter().updateTitle(getString(R.string.category_details));
            if (sourceAction.equals(RestApiSingleActionGet)) {

                getRestApiSingleFragmentPresenter().loadElementById(sourceFragment, elementId);
            }

        } else if (sourceFragment.equals(RestApiSourceItems))

        {

            ((RestApiSingleAct) getContainerActivity()).getRestApiSinglePresenter().updateTitle(getString(R.string.item_details));
            elementCategoryId = getArguments().getLong(INPUT_KEY_CATEGORY_ID);
            spCategories.setVisibility(View.VISIBLE);
            edDescription.setVisibility(View.VISIBLE);
            getPresenter().loadCategories();
        }


    }

    @Override
    public RestApiSingleFragmentPresenter injectDependencies() {
        return new RestApiSingleFragmentPresenter(getContainerActivity(), this);
    }

    private void addValidationForEditText() {
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(new IsEmptyRule(getString(R.string.empty_field)).build());
        validationUtilAwesome.getAwesomeValidation().addValidation(edName, rules);
        if (sourceFragment.equals(RestApiSourceItems)) {
            validationUtilAwesome.getAwesomeValidation().addValidation(edDescription, rules);
        }

        validationUtilAwesome.addOnFocusChangeListeners();
    }


    @OnClick(R.id.btnAdd)
    public void onBtnAddClicked() {
        validationUtilAwesome.validateAllFirstError();
    }

    @OnClick(R.id.btnSave)
    public void onBtnSaveClicked() {
        validationUtilAwesome.validateAllFirstError();
    }

    @OnClick(R.id.btnRemove)
    public void onBtnRemoveClicked() {

        if (sourceFragment.equals(RestApiSourceCategories)) {
            if (categoryRestApiResponse != null) {
                getRestApiSingleFragmentPresenter().deleteCategory(categoryRestApiResponse.getCategoryRestApi());
            }
        } else if (sourceFragment.equals(RestApiSourceItems)) {
            if (itemRestApiResponse != null) {
                getRestApiSingleFragmentPresenter().deleteItem(itemRestApiResponse.getItemRestApi());
            }
        }

    }

    @Override
    public void onSpinnerItemSelected(String tag, ArrayList<Object> items, int pos) {

    }

    public RestApiSingleFragmentPresenter getRestApiSingleFragmentPresenter() {
        return ((RestApiSingleFragmentPresenter) getPresenter());
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContainerActivity());
        }

        if (!getContainerActivity().isFinishing()) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

            }
        }

    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            } else {
                progressDialog = null;
            }
        } else {
            progressDialog = null;
        }
    }


    @Override
    public void showSpCategories(ArrayList arrayList) {
        int initialPos = 0;
        ArrayList<CategoryRestApi> categoryRestApisSp = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            categoryRestApisSp.add((CategoryRestApi) arrayList.get(i));
            if (sourceAction.equals(RestApiSingleActionGet)) {
                if (initialPos == 0) {
                    if (Long.valueOf(elementCategoryId).compareTo(((CategoryRestApi) arrayList.get(i)).getId()) == 0) {
                        initialPos = i;
                    }
                }
            }
        }
        spCategories.setData(spCategories, categoryRestApisSp
                , this
                , R.layout.spinner_item_chosers_white_black_arrow, R.layout.dropdown_spinner,
                0
                , false);
        spCategories.setSelectedObject(initialPos);

        if (sourceAction.equals(RestApiSingleActionGet)) {

            getRestApiSingleFragmentPresenter().loadElementById(sourceFragment, elementId);

        }

    }

    @Override
    public void showCategory(CategoryRestApiResponse categoryRestApiResponse) {

        this.categoryRestApiResponse = categoryRestApiResponse;
        edName.setText(categoryRestApiResponse.getCategoryRestApi().getName());
    }

    @Override
    public void showItem(ItemRestApiResponse itemRestApiResponse) {

        this.itemRestApiResponse = itemRestApiResponse;
        edName.setText(itemRestApiResponse.getItemRestApi().getName());
        edDescription.setText(itemRestApiResponse.getItemRestApi().getDescription());
    }

    @Override
    public void showEditRestApiResponse(EditRestApiResponse editRestApiResponse) {

        if (editRestApiResponse.getSuccess().equals("1")) {
            Toast.makeText(getContainerActivity(), getString(R.string.done_successfully), Toast.LENGTH_LONG).show();

        } else if (editRestApiResponse.getSuccess().equals("0"))

        {
            Toast.makeText(getContainerActivity(), getString(R.string.error_in_webservice), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onValidationSucceeded() {

        if (sourceAction.equals(RestApiSingleActionGet)) {

            if (sourceFragment.equals(RestApiSourceCategories)) {


                if (categoryRestApiResponse != null) {
                    categoryRestApiResponse.getCategoryRestApi().setName(edName.getText().toString());
                    getRestApiSingleFragmentPresenter().editCategory(categoryRestApiResponse.getCategoryRestApi());
                }

            } else if (sourceFragment.equals(RestApiSourceItems))

            {
                if (itemRestApiResponse != null) {
                    if(spCategories.getSelectedItem()!=null)
                    {
                        itemRestApiResponse.getItemRestApi().setName(edName.getText().toString());
                        itemRestApiResponse.getItemRestApi().setDescription(edDescription.getText().toString());
                        itemRestApiResponse.getItemRestApi().setCategory_id(((CategoryRestApi) spCategories.getSelectedItem()).getId());
                        getRestApiSingleFragmentPresenter().editItem(itemRestApiResponse.getItemRestApi());
                    }
                }

            }

        } else if (sourceAction.equals(RestApiSingleActionAdd))

        {
            if (sourceFragment.equals(RestApiSourceCategories)) {

                CategoryRestApi categoryRestApi = new CategoryRestApi();
                categoryRestApi.setName(edName.getText().toString());
                getRestApiSingleFragmentPresenter().addCategory(categoryRestApi);

            } else if (sourceFragment.equals(RestApiSourceItems))

            {
                if(spCategories.getSelectedItem()!=null)

                {
                    ItemRestApi itemRestApi = new ItemRestApi();
                    itemRestApi.setName(edName.getText().toString());
                    itemRestApi.setDescription(edDescription.getText().toString());
                    itemRestApi.setCategory_id(((CategoryRestApi) spCategories.getSelectedItem()).getId());
                    getRestApiSingleFragmentPresenter().addItem(itemRestApi);
                }

            }
        }

    }


}
