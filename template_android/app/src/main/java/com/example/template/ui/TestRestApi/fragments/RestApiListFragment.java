package com.example.template.ui.TestRestApi.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.CategoryRestApi;
import com.example.template.ui.TestRestApi.RestApiSingleAct;
import com.example.template.ui.TestRestApi.ViewHolders.RestApiListFrgCategoriesVH;
import com.example.template.ui.TestRestApi.ViewHolders.RestApiListFrgItemsVH;
import com.example.template.ui.TestRestApi.presenter.list.IRestApiListFragmentContract;
import com.example.template.ui.TestRestApi.presenter.list.RestApiListFragmentPresenter;
import modules.general.ui.utils.GUI.CustomSpinner;
import modules.general.ui.utils.GUI.RecyclerViewEmptySupport;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import com.mvp_base.BaseSupportFragment;
import modules.general.ui.utils.adapters.GenericRecyclerViewAdapter;
import static modules.general.utils.Constants.RestApiListSource;
import static modules.general.utils.Constants.RestApiSingleAction;
import static modules.general.utils.Constants.RestApiSingleActionAdd;
import static modules.general.utils.Constants.RestApiSingleSource;
import static modules.general.utils.Constants.RestApiSourceCategories;
import static modules.general.utils.Constants.RestApiSourceItems;

public class RestApiListFragment extends BaseSupportFragment<RestApiListFragmentPresenter>
        implements IRestApiListFragmentContract.IRestApiListFragmentView, CustomSpinner.SpinnerListener {

     private GenericRecyclerViewAdapter adapter;

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
        return R.layout.frg_test_rest_api;
    }

    @Override
    public void configureUI() {

        sourceFragment = getArguments().getString(RestApiListSource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContainerActivity(),
                RecyclerView.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        if (sourceFragment.equals(RestApiSourceCategories)) {

           adapter = new GenericRecyclerViewAdapter(getContainerActivity(), new GenericRecyclerViewAdapter.AdapterDrawData() {
                @Override
                public RecyclerView.ViewHolder getView(ViewGroup parent, int viewType) {

                    return new RestApiListFrgCategoriesVH( getContainerActivity(),
                            RestApiListFrgCategoriesVH.getView(getContainerActivity(), parent));
                }

                @Override
                public void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                                     RecyclerView.ViewHolder holder, Object item, int position) {
                    ((RestApiListFrgCategoriesVH) holder).bindData(
                            genericRecyclerViewAdapter.getItem(position), position);
                }
            });
           } else if (sourceFragment.equals(RestApiSourceItems)) {

            adapter = new GenericRecyclerViewAdapter(getContainerActivity(), new GenericRecyclerViewAdapter.AdapterDrawData() {
                @Override
                public RecyclerView.ViewHolder getView(ViewGroup parent, int viewType) {

                    return new RestApiListFrgItemsVH( getContainerActivity(),
                            RestApiListFrgItemsVH.getView(getContainerActivity(), parent));
                }

                @Override
                public void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                                     RecyclerView.ViewHolder holder, Object item, int position) {
                    ((RestApiListFrgItemsVH) holder).bindData(
                            genericRecyclerViewAdapter.getItem(position), position);
                }
            });


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
                Intent intent = new Intent(getContainerActivity(), RestApiSingleAct.class);
                intent.putExtra(RestApiSingleSource, sourceFragment);
                intent.putExtra(RestApiSingleAction, RestApiSingleActionAdd);
                startActivity(intent);
            }
        });

        getPresenter().loadList(RestApiSourceCategories);

    }


    private void initSpCategories() {
        ArrayList<CategoryRestApi> categoryRestApisSp = new ArrayList<>();
        categoryRestApisSp.add(0, new CategoryRestApi(0, getString(R.string.all)));
        spCategories.setData(spCategories, categoryRestApisSp
                , this
                , R.layout.spinner_item_chosers_white_black_arrow, R.layout.dropdown_spinner,
                0
                , false);
    }

    @Override
    public RestApiListFragmentPresenter injectDependencies() {
        return new RestApiListFragmentPresenter(getContainerActivity(), this);
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
    public void showList(ArrayList arrayList) {
        adapter.setAll(arrayList);
    }

    @Override
    public void showSpCategories(ArrayList arrayList) {
        int initialPos = 0;
        ArrayList<CategoryRestApi> categoryRestApisSp = new ArrayList<>();
        categoryRestApisSp.add(0, new CategoryRestApi(0, getString(R.string.all)));
        for (int i = 0; i < arrayList.size(); i++) {
            categoryRestApisSp.add((CategoryRestApi) arrayList.get(i));
            if (initialPos == 0) {
                if (Long.valueOf(categoryIdForItems).compareTo(((CategoryRestApi) arrayList.get(i)).getId()) == 0) {
                    initialPos = i;
                }
            }
        }
        spCategories.setData(spCategories, categoryRestApisSp
                , this
                , R.layout.spinner_item_chosers_white_black_arrow, R.layout.dropdown_spinner,
                0
                , false);
        spCategories.setSelectedObject(initialPos);
        getPresenter().loadList(RestApiSourceItems);
    }

    @Override
    public void onSpinnerItemSelected(String tag, ArrayList<Object> items, int pos) {
        if (tag.equals(String.valueOf(spCategories.getId()))) {
            if (pos == 0) {
                categoryIdForItems = 0L;
                getPresenter().loadList(RestApiSourceItems);
            } else {
                categoryIdForItems = ((CategoryRestApi) spCategories.getSelectedItem()).getId();
                getPresenter().loadItemsByCategory(((CategoryRestApi) spCategories.getSelectedItem()).getId());
            }

        }
    }
}
