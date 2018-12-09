package modules.general.ui.custom_views.NavigationViews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.template.R;
import com.example.template.ui.TestRestApi.RestApiListAct;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteListAct;
import modules.general.ui.parentview.ParentActivity;
import modules.general.utils.KeyBoardUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static modules.general.utils.Constants.RestApiSourceCategories;
import static modules.general.utils.Constants.RestApiSourceItems;
import static modules.general.utils.Constants.SqliteSourceCategories;
import static modules.general.utils.Constants.SqliteSourceItems;

/**
 * Created by Net22 on 9/14/2017.
 */

public class MenuRestApiAndSqlite extends LinearLayout {

    @BindView(R.id.btnCategories)
    Button btnCategories;
    @BindView(R.id.btnItems)
    Button btnItems;

    public MenuRestApiAndSqlite(Context context) {
        super(context);
        initView(context);
        doWork();
    }


    public MenuRestApiAndSqlite(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        doWork();
    }

    public MenuRestApiAndSqlite(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        doWork();

    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.menu_rest_api, this, true);
        ButterKnife.bind(this);  //true also


    }

    private void doWork() {

        btnCategories.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtil.hideSoftKeyboard((Activity) getContext());
                ((ParentActivity) getContext()).closeDrawer();
//                Toast.makeText(getContext(),"btn1 clicked",Toast.LENGTH_SHORT).show();
                ((ParentActivity) getContext()).closeDrawer();
                if (getContext() instanceof RestApiListAct) {
                    ((RestApiListAct) getContext()).openFragment(RestApiSourceCategories);
                } else if (getContext() instanceof SqliteListAct) {
                    ((SqliteListAct) getContext()).openFragment(SqliteSourceCategories);
                }


            }
        });

        btnItems.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtil.hideSoftKeyboard((Activity) getContext());
                ((ParentActivity) getContext()).closeDrawer();
                if (getContext() instanceof RestApiListAct) {
                    ((RestApiListAct) getContext()).openFragment(RestApiSourceItems);
                } else if (getContext() instanceof SqliteListAct) {
                    ((SqliteListAct) getContext()).openFragment(SqliteSourceItems);
                }


            }
        });


    }

}
