package com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.sqlite.Items;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.SqliteSingleAct;

import butterknife.BindView;
import butterknife.ButterKnife;

import static modules.general.utils.Constants.INPUT_KEY;
import static modules.general.utils.Constants.INPUT_KEY_CATEGORY_ID;
import static modules.general.utils.Constants.SqliteSingleAction;
import static modules.general.utils.Constants.SqliteSingleActionGet;
import static modules.general.utils.Constants.SqliteSingleSource;
import static modules.general.utils.Constants.SqliteSourceItems;

/**
 * Created by Net22 on 11/26/2017.
 */

public class SqliteListFrgItemsVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvHeading)
    TextView heading;
    @BindView(R.id.tvDescription)
    TextView desc;

    View itemView;
    int positionClicked;
    Object itemClicked;
    Context context;

    public SqliteListFrgItemsVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Object item, final int position) {

        final Items itemInfo = (Items) item;
        heading.setText(itemInfo.getName());
        desc.setText(itemInfo.getDescription());

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
        return layoutInflater.inflate(R.layout.row_sqlite_items, viewGroup, false);
    }

    public void onClickItem(View view, Items itemInfo) {

        Intent intent = new Intent(context, SqliteSingleAct.class);
        intent.putExtra(SqliteSingleSource, SqliteSourceItems);
        intent.putExtra(SqliteSingleAction, SqliteSingleActionGet);
        intent.putExtra(INPUT_KEY, itemInfo.getId());
        intent.putExtra(INPUT_KEY_CATEGORY_ID, itemInfo.getCategory_id());
        context.startActivity(intent);

    }
}

