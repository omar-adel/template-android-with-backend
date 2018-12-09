package com.example.template.ui.TestSqliteDbflow.simpleitems.presenter;


import com.mvp_base.Base;

/**
 * Created by Net22 on 9/13/2017.
 */

public interface DBFlowItemContract {

    interface IView {

        void showItemsCount(long count);

    }


    interface IPresenter extends Base.IPresenter {


    }

}
