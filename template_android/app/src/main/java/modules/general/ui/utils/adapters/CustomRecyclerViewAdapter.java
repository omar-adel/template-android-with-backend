package modules.general.ui.utils.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.template.model.backend.APIService;
import modules.general.model.backend.ApiUtils;
import com.example.template.ui.TestRestApi.ViewHolders.RestApiListFrgCategoriesVH;
import com.example.template.ui.TestRestApi.ViewHolders.RestApiListFrgItemsVH;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.ViewHolders.SqliteListFrgCategoriesVH;
import com.example.template.ui.TestSqliteDbflow.CategoriesAndItems.Simple.ViewHolders.SqliteListFrgItemsVH;
import com.example.template.ui.TestSqliteDbflow.notes.NoteVH;
import modules.general.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class CustomRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<T> mList = new ArrayList<>();
    int mAdapterType = 0;
    public Context mContext;
    public Fragment mFragment;
    public T currentObject;
    public int mainpos;
    public APIService mAPIService;

    public static final int NotesType = 8;
     public static final int SqliteListCategoriesType = 11;
    public static final int SqliteListItemsType = 12;

    //pagination
    public PaginationScrollListener.PaginationAdapterCallback mCallback;
    private boolean isLoadingAdded = false;
    public boolean retryPageLoad = false;
    public String errorMsg;
    ////////////////////////


    public CustomRecyclerViewAdapter(Context context, Fragment fragment, int adapterType) {
        super();
        mContext = context;
        mAdapterType = adapterType;
        mFragment = fragment;
        mAPIService = ApiUtils.getAPIService();

        if (mFragment != null) {
            if (mFragment instanceof PaginationScrollListener.PaginationAdapterCallback) {
                this.mCallback = (PaginationScrollListener.PaginationAdapterCallback) mFragment;

            }


        } else {
            if (context instanceof PaginationScrollListener.PaginationAdapterCallback) {
                this.mCallback = (PaginationScrollListener.PaginationAdapterCallback) context;

            }

        }
    }

    public CustomRecyclerViewAdapter(Context context, int adapterType) {
        super();
        mContext = context;
        mAdapterType = adapterType;
        mAPIService = ApiUtils.getAPIService();

        if (context instanceof PaginationScrollListener.PaginationAdapterCallback) {
            this.mCallback = (PaginationScrollListener.PaginationAdapterCallback) context;

        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (mAdapterType) {
            case NotesType:
                return NotesType;
             case SqliteListCategoriesType:
                return SqliteListCategoriesType;
            case SqliteListItemsType:
                return SqliteListItemsType;
            default:
                return SqliteListCategoriesType;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NotesType:
                return new NoteVH(parent.getContext(), NoteVH.getView(mContext, parent), this);
              case SqliteListCategoriesType:
                return new SqliteListFrgCategoriesVH(parent.getContext(), SqliteListFrgCategoriesVH.getView(mContext, parent));
            case SqliteListItemsType:
                return new SqliteListFrgItemsVH(parent.getContext(), SqliteListFrgItemsVH.getView(mContext, parent));
            default:
                return new RestApiListFrgCategoriesVH(parent.getContext(), RestApiListFrgCategoriesVH.getView(mContext, parent));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case NotesType:
                ((NoteVH) holder).bindData(getItem(position), position);
                  break;
            case SqliteListCategoriesType:
                ((SqliteListFrgCategoriesVH) holder).bindData(getItem(position), position);
                break;
            case SqliteListItemsType:
                ((SqliteListFrgItemsVH) holder).bindData(getItem(position), position);
                break;
            default:
                ((RestApiListFrgCategoriesVH) holder).bindData(getItem(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItem(int index) {
        return ((mList != null && index < mList.size()) ? mList.get(index) : null);
    }

    public Context getContext() {
        return mContext;
    }

    public void setList(ArrayList<T> list) {
        mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, T data) {
        mList.add(position, data);
        notifyDataSetChanged();
    }

    public void setAll(List<T> mItems) {
        mList.clear();
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }


    public void setAll(ArrayList<T> mItems) {
        mList.clear();
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
       notifyDataSetChanged();
     }

    public void addAll(List<T> mItems) {
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<T> mItems) {
         for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        isLoadingAdded = false;
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
             this.notifyItemRangeRemoved(0, size);
        }
    }

//    public void addLoadingFooter() {
//        isLoadingAdded = true;
//        T data=null;
//        addItem(data);
//    }
//
//
//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//        int position = mList.size() - 1;
//        T data = getItem(position);
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }


}