package com.example.template.ui.custom_views.SpinnerViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.CategoryRestApi;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Net22 on 9/25/2017.
 */

public class CategoryRestApiSPView extends LinearLayout {

    @Nullable
    @BindView(R.id.spinner_txtv_item)
    TextView spinner_txtv_item;
    @Nullable
    @BindView(R.id.spinner_drop_txtv_item)
    TextView spinner_drop_txtv_item;

    public CategoryRestApiSPView(Context context, Object o, int type) {
        super(context);
        // Inflating the layout for the custom Spinner
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (type == 0)
            inflater.inflate(R.layout.spinner_item_chosers_white_black_arrow, this);
        else
            inflater.inflate(R.layout.dropdown_spinner, this);
        ButterKnife.bind(this);
        drawData(o, context, type);
    }

    private void drawData(Object o, Context context, int type) {
        CategoryRestApi categoryRestApi = (CategoryRestApi) o;
        if (type == 0)
            spinner_txtv_item.setText(categoryRestApi.getName());
        else
            spinner_drop_txtv_item.setText(categoryRestApi.getName());
    }


}
