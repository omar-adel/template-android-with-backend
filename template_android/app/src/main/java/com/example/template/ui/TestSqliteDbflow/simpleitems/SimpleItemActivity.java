package com.example.template.ui.TestSqliteDbflow.simpleitems;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.ui.TestSqliteDbflow.simpleitems.presenter.DBFlowItemContract;
import com.example.template.ui.TestSqliteDbflow.simpleitems.presenter.DBFlowItemPresenter;
import modules.general.ui.parentview.ParentActivity;

import butterknife.BindView;

public class SimpleItemActivity extends ParentActivity<DBFlowItemPresenter>
        implements DBFlowItemContract.IView {


    @BindView(R.id.add_item_button)
    Button mAddItem;
    @BindView(R.id.item_count)
    TextView mItemCount;

    @Override
    public int getExtraLayout() {
        return R.layout.act_sqlite_items;
    }


    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.sqlite_item_operations));

        mAddItem.setOnClickListener(onAddClicked);
        getDBFlowItemPresenter().getItemsCount();

    }

    private View.OnClickListener onAddClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            getDBFlowItemPresenter().addItem();

        }
    };

    @Override
    public DBFlowItemPresenter injectDependencies() {
        return new DBFlowItemPresenter(this, this);
    }

    @Override
    public void showItemsCount(long count) {

        mItemCount.setText(String.valueOf(count));

    }


    public DBFlowItemPresenter getDBFlowItemPresenter() {
        return ((DBFlowItemPresenter) this.getPresenter());
    }

}
