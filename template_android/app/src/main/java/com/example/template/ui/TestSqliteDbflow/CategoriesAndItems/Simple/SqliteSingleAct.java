package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple;

import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single.ISqliteSingleContract;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single.SqliteSinglePresenter;
import modules.general.ui.parentview.ParentActivity;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.SqliteSingleAction;
import static modules.general.utils.Constants.SqliteSingleActionGet;
import static modules.general.utils.Constants.SqliteSingleSource;
import static modules.general.utils.Constants.SqliteSourceItems;

public class SqliteSingleAct extends ParentActivity<SqliteSinglePresenter> implements
        ISqliteSingleContract.ISqliteSingleView {
    String sourceAct;
    String sourceAction;
    long elementId = 0l;
    long elementCategoryId = 0l;


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();

        sourceAct = getIntent().getStringExtra(SqliteSingleSource);
        sourceAction = getIntent().getStringExtra(SqliteSingleAction);
        if (sourceAction.equals(SqliteSingleActionGet)) {
            elementId = getIntent().getLongExtra(INPUT_KEY, 0l);
            if (sourceAct.equals(SqliteSourceItems)) {
                elementCategoryId = getIntent().getLongExtra(INPUT_KEY_CATEGORY_ID, 0l);
            }
        }

        openFragment(sourceAct, sourceAction, elementId, elementCategoryId);
    }


    @Override
    public SqliteSinglePresenter injectDependencies() {
        return new SqliteSinglePresenter(this, this);
    }

    public void openFragment(String sourceAct, String sourceAction, long elementId, long elementCategoryId) {
        getSqliteSinglePresenter().openFragment(sourceAct, sourceAction, elementId, elementCategoryId);
    }

    public SqliteSinglePresenter getSqliteSinglePresenter() {
        return ((SqliteSinglePresenter) getPresenter());
    }

    @Override
    public void updateTitle(String title) {

        getCsTitle().updateTitle(title);
    }
}
