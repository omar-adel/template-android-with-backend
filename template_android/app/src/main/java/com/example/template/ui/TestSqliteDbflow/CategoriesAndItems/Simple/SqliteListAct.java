package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple;

import com.example.template.R;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list.ISqliteListContract;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list.SqliteListPresenter;
import modules.general.ui.parentview.ParentActivity;
import modules.general.ui.utils.general_listeners.ITitleListener;

import static modules.general.utils.Constants.SqliteSourceCategories;
import static modules.general.utils.Constants.SqliteSourceItems;

public class SqliteListAct extends ParentActivity<SqliteListPresenter> implements
        ISqliteListContract.ISqliteListView {

    String sourceFragment = "";

    @Override
    public void configureUI() {

        super.configureUI();
        getCsTitle().hideSettings();
        getCsTitle().initalizeView(this, getString(R.string.test_rest_api_categories), new ITitleListener() {
            @Override
            public void onBackPressed() {
                onBack();
            }

            @Override
            public void onMenuPressed() {
                onMenuPress();
            }

            @Override
            public void onSettingsPressed() {
            }
        });
//        openFragment(SqliteSourceCategories);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openFragment(sourceFragment);
    }

    @Override
    public SqliteListPresenter injectDependencies() {
        return new SqliteListPresenter(this, this);
    }

    public SqliteListPresenter getSqliteListPresenter() {
        return ((SqliteListPresenter) getPresenter());
    }

    public void openFragment(String sourceFragment) {
        this.sourceFragment = sourceFragment;
        if (sourceFragment.isEmpty()) {
            sourceFragment = SqliteSourceCategories;
        }
        if (sourceFragment.equals(SqliteSourceCategories)) {
            getCsTitle().updateTitle(getString(R.string.test_rest_api_categories));
        } else if (sourceFragment.equals(SqliteSourceItems))

        {
            getCsTitle().updateTitle(getString(R.string.test_rest_api_items));
        }

        getSqliteListPresenter().openFragment(sourceFragment);
    }
}
