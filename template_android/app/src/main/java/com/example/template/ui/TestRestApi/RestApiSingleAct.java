package com.example.template.ui.TestRestApi;

import com.example.template.ui.TestRestApi.presenter.single.IRestApiSingleContract;
import com.example.template.ui.TestRestApi.presenter.single.RestApiSinglePresenter;
import modules.general.ui.parentview.ParentActivity;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.RestApiSingleAction;
import static modules.general.utils.Constants.RestApiSingleActionGet;
import static modules.general.utils.Constants.RestApiSingleSource;
import static modules.general.utils.Constants.RestApiSourceItems;

public class RestApiSingleAct extends ParentActivity<RestApiSinglePresenter> implements
        IRestApiSingleContract.IRestApiSingleView {
    String sourceAct;
    String sourceAction;
    long elementId = 0l;
    long elementCategoryId = 0l;


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();

        sourceAct = getIntent().getStringExtra(RestApiSingleSource);
        sourceAction = getIntent().getStringExtra(RestApiSingleAction);
        if (sourceAction.equals(RestApiSingleActionGet)) {
            elementId = getIntent().getLongExtra(INPUT_KEY, 0l);
            if (sourceAct.equals(RestApiSourceItems)) {
                elementCategoryId = getIntent().getLongExtra(INPUT_KEY_CATEGORY_ID, 0l);
            }
        }

        openFragment(sourceAct, sourceAction, elementId, elementCategoryId);
    }


    @Override
    public RestApiSinglePresenter injectDependencies() {
        return new RestApiSinglePresenter(this, this);
    }

    public void openFragment(String sourceAct, String sourceAction, long elementId, long elementCategoryId) {
        getRestApiSinglePresenter().openFragment(sourceAct, sourceAction, elementId, elementCategoryId);
    }

    public RestApiSinglePresenter getRestApiSinglePresenter() {
        return ((RestApiSinglePresenter) getPresenter());
    }

    @Override
    public void updateTitle(String title) {

        getCsTitle().updateTitle(title);
    }
}
