package com.example.template.ui.TestRestApi.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.CategoryRestApi;
import com.example.template.ui.TestRestApi.RestApiSingleAct;

import butterknife.BindView;
import butterknife.ButterKnife;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.RestApiSingleAction;
import static modules.general.utils.Constants.RestApiSingleActionGet;
import static modules.general.utils.Constants.RestApiSingleSource;
import static modules.general.utils.Constants.RestApiSourceCategories;

/**
 * Created by Net22 on 11/26/2017.
 */

public class RestApiListFrgCategoriesVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvHeading)
    TextView heading;

    View itemView;
    int positionClicked;
    Object itemClicked;
    Context context;

    public RestApiListFrgCategoriesVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Object item, final int position) {

        final CategoryRestApi itemInfo = (CategoryRestApi) item;
        heading.setText(itemInfo.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionClicked = position;
                itemClicked = item;
                onClickItem(view, itemInfo);
            }
        });

    }

    public static View getView(Context context, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.row_rest_api_categories, viewGroup, false);
    }


    public void onClickItem(View view, CategoryRestApi itemInfo) {

        Intent intent = new Intent(context, RestApiSingleAct.class);
        intent.putExtra(RestApiSingleSource, RestApiSourceCategories);
        intent.putExtra(RestApiSingleAction, RestApiSingleActionGet);
        intent.putExtra(INPUT_KEY, itemInfo.getId());
        context.startActivity(intent);
    }
}

