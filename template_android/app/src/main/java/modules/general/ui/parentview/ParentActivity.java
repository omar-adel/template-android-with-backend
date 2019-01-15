package modules.general.ui.parentview;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.template.R;
import modules.general.ui.custom_views.CustomTitle;
import modules.general.ui.custom_views.NavigationViews.MenuRestApiAndSqlite;
import modules.general.utils.KeyBoardUtil;

import butterknife.BindView;
import modules.general.utils.LanguageUtil;

import com.mvp_base.Base;
import com.mvp_base.BaseAppCompatActivity;

public class ParentActivity<P extends Base.IPresenter> extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.parentview_cs_title)
    CustomTitle csTitle;
    @BindView(R.id.parentview_lnr_content)
    LinearLayout lnrContent;
    @BindView(R.id.parentview_nv_navigationview)
    MenuRestApiAndSqlite menuRestApiAndSqlite;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    public DrawerLayout getDrawer() {
        return drawer;
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.parent_view;
    }

    @Override
    public int getExtraLayout() {
        return 0;
    }


    @Override
    public int getContainerID() {
        return R.id.parentview_lnr_content;
    }

    @Override
    public void configureUI() {
        LanguageUtil.setAppLanguage(getApplicationContext(),  LanguageUtil.ENGLISH_LANGUAGE);
    }

    public void onMenuPress() {
        if (!drawer.isDrawerOpen(GravityCompat.END)) {
            KeyBoardUtil.hideSoftKeyboard(this);
            drawer.openDrawer(GravityCompat.END);
        } else
            closeDrawer();
    }

    public void onBack() {
        finish();
    }

    @Override
    public Base.IPresenter injectDependencies() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void openDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.END)) {
            KeyBoardUtil.hideSoftKeyboard(this);
            drawer.openDrawer(GravityCompat.END);
        }
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
    }

    public LinearLayout getContentView() {
        return lnrContent;
    }

    public int getContentRes() {
        return R.id.parentview_lnr_content;
    }

    public CustomTitle getCsTitle() {
        return csTitle;
    }

    public void disableDrawerSwipe() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeDrawer();
        finish();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        String lang_code = LanguageUtil.getAppLanguage(); //load it from SharedPref
        Context context = LanguageUtil.setAppLanguage(newBase, lang_code);
        super.attachBaseContext(context);
    }
}