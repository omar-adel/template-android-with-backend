package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.sqlite.Items;
import com.example.template.model.bean.sqlite.querymodels.CategoriesWithOutItemsQueryModel;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteSingleAct;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list.ISqliteListFragmentContract;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.list.SqliteListFragmentPresenter;
import modules.general.ui.utils.GUI.CustomSpinner;
import modules.general.ui.utils.GUI.RecyclerViewEmptySupport;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import com.mvp_base.BaseSupportFragment;

import static modules.general.ui.utils.adapters.CustomRecyclerViewAdapter.SqliteListCategoriesType;
import static modules.general.ui.utils.adapters.CustomRecyclerViewAdapter.SqliteListItemsType;
import static modules.general.utils.Constants.SqliteListSource;
import static modules.general.utils.Constants.SqliteSingleAction;
import static modules.general.utils.Constants.SqliteSingleActionAdd;
import static modules.general.utils.Constants.SqliteSingleSource;
import static modules.general.utils.Constants.SqliteSourceCategories;
import static modules.general.utils.Constants.SqliteSourceItems;

public class SqliteListFragment extends BaseSupportFragment<SqliteListFragmentPresenter>
        implements ISqliteListFragmentContract.ISqliteListFragmentView, CustomSpinner.SpinnerListener {

    private CustomRecyclerViewAdapter adapter;

    @BindView(R.id.spCategories)
    CustomSpinner spCategories;
    @BindView(R.id.rv)
    RecyclerViewEmptySupport rv;
    @BindView(R.id.txtvEmpty)
    TextView txtvEmpty;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    String sourceFragment;
    ProgressDialog progressDialog;
    long categoryIdForItems = 0l;

    public String getSourceFragment() {
        return sourceFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.frg_test_sqlite;
    }

    @Override
    public void configureUI() {

        sourceFragment = getArguments().getString(SqliteListSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContainerActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        if (sourceFragment.equals(SqliteSourceCategories)) {
            adapter = new CustomRecyclerViewAdapter<CategoriesWithOutItemsQueryModel>(getContainerActivity(), SqliteListCategoriesType);
        } else if (sourceFragment.equals(SqliteSourceItems)) {
            adapter = new CustomRecyclerViewAdapter<Items>(getContainerActivity(), SqliteListItemsType);
            spCategories.setVisibility(View.VISIBLE);
            initSpCategories();
        }

        rv.setEmptyView(txtvEmpty);
        rv.setAdapter(adapter);
        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContainerActivity())
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.divider)
                        .build());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContainerActivity(), SqliteSingleAct.class);
                intent.putExtra(SqliteSingleSource, sourceFragment);
                intent.putExtra(SqliteSingleAction, SqliteSingleActionAdd);
                startActivity(intent);
            }
        });

        getPresenter().loadList(SqliteSourceCategories);

    }


    private void initSpCategories() {
        ArrayList<CategoriesWithOutItemsQueryModel> categorySqlitesSp = new ArrayList<>();
        categorySqlitesSp.add(0, new CategoriesWithOutItemsQueryModel(0, getString(R.string.all)));
        spCategories.setData(spCategories, categorySqlitesSp
                , this
                , R.layout.spinner_item_chosers_white_black_arrow, R.layout.dropdown_spinner,
                1
                , false);
    }

    @Override
    public SqliteListFragmentPresenter injectDependencies() {
        return new SqliteListFragmentPresenter(getContainerActivity(), this);
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
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
    public void showList(final ArrayList arrayList) {
        getContainerActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setAll(arrayList);
            }
        });
    }

    @Override
    public void showSpCategories(ArrayList arrayList) {
        int initialPos = 0;
        ArrayList<CategoriesWithOutItemsQueryModel> categorySqlitesSp = new ArrayList<>();
        categorySqlitesSp.add(0, new CategoriesWithOutItemsQueryModel(0, getString(R.string.all)));
        for (int i = 0; i < arrayList.size(); i++) {
            categorySqlitesSp.add((CategoriesWithOutItemsQueryModel) arrayList.get(i));
            if (initialPos == 0) {
                if (Long.valueOf(categoryIdForItems).compareTo(((CategoriesWithOutItemsQueryModel) arrayList.get(i)).getId()) == 0) {
                    initialPos = i;
                }
            }
        }
        spCategories.setData(spCategories, categorySqlitesSp
                , this
                , R.layout.spinner_item_chosers_white_black_arrow, R.layout.dropdown_spinner,
                1
                , false);
        spCategories.setSelectedObject(initialPos);
        getPresenter().loadList(SqliteSourceItems);
    }

    @Override
    public void onSpinnerItemSelected(String tag, ArrayList<Object> items, int pos) {
        if (tag.equals(String.valueOf(spCategories.getId()))) {
            if (pos == 0) {
                categoryIdForItems = 0L;
                getPresenter().loadList(SqliteSourceItems);
            } else {
                categoryIdForItems = ((CategoriesWithOutItemsQueryModel) spCategories.getSelectedItem()).getId();
                getPresenter().loadItemsByCategory(((CategoriesWithOutItemsQueryModel) spCategories.getSelectedItem()).getId());
            }

        }
    }
}
