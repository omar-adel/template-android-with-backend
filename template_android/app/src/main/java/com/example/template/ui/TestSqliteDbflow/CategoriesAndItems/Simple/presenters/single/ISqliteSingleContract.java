package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.presenters.single;

import com.mvp_base.Base;

/**
 * Created by Net22 on 11/26/2017.
 */

public interface ISqliteSingleContract {

    public interface ISqliteSingleView {

        void updateTitle(String title);
    }


    public interface ISqliteSinglePresenter extends Base.IPresenter {
        void openFragment(String source, String action, long elementId, long elementCategoryId);

        void updateTitle(String title);
    }
}
