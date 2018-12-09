package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations;

import android.view.View;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations.presenter.ComplexOperationsContract;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.ComplexOperations.presenter.ComplexOperationsPresenter;
import modules.general.ui.parentview.ParentActivity;
import modules.general.utils.GeneralUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ComplexOperations extends ParentActivity<ComplexOperationsPresenter>

        implements ComplexOperationsContract.IView {


    @BindView(R.id.txtResult)
    TextView txtResult;

    public void onGetCategoriesWithoutItems(View view) {

        getComplexOperationsPresenter().onGetCategoriesWithoutItems();
    }

    public void onGetCategoriesWithItems(View view) {
        getComplexOperationsPresenter().onGetCategoriesWithItems();
    }

    public void onGetItemsByCategoryId(View view) {
        long category_id = 3;
        getComplexOperationsPresenter().onGetItemsByCategoryId(category_id);
    }


    public void onGetItemsByCategoriesIds(View view) {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(3l);
        getComplexOperationsPresenter().onGetItemsByCategoriesIds(ids);
    }


    public void onGetCategoriesWhereIdInIds(View view) {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(2l);
        getComplexOperationsPresenter().onGetCategoriesWhereIdInIds(ids);
    }

    public void onGetItemsWhereIdInCategoriesIds(View view) {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(3l);
        ids.add(4l);
        getComplexOperationsPresenter().onGetItemsWhereIdInCategoriesIds(ids);
    }

    public void onGetCustomListCategoriesItemsJoin(View view) {
        getComplexOperationsPresenter().onGetCustomListCategoriesItemsJoin();
    }

    @Override
    public int getExtraLayout() {
        return R.layout.act_sqlite_complex_operations;
    }

    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.sqlite_complex_operations));

    }

    @Override
    public ComplexOperationsPresenter injectDependencies() {
        return new ComplexOperationsPresenter(this, this);
    }

    @Override
    public void showResult(final String result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtResult.setText(result);
            }
        });
    }

    @Override
    public void showResult(final List arrayList, String className) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtResult.setText(GeneralUtil.convertArrToStr(arrayList));
            }
        });


//        if(className.equals(Categories.class.getName()))
//        {
////            GeneralUtil.convertToClazz(arrayList,Object.class,className);
//         }
//        else
//        if(className.equals(Items.class.getName()))
//        {
////            GeneralUtil.convertToClazz(arrayList,Object.class,className);
//         }
////        else
////        if(className.equals(Categories.class.getName()))
////        {
////
////        }
    }

    public ComplexOperationsPresenter getComplexOperationsPresenter() {
        return ((ComplexOperationsPresenter) this.getPresenter());
    }

}
