package modules.general.ui.utils.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public   class GenericRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface AdapterDrawData{
        RecyclerView.ViewHolder getView(ViewGroup viewGroup);
        void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                      RecyclerView.ViewHolder holder, final Object item, final int position);
    }
    public ArrayList<T> mList = new ArrayList<>();
     public Context mContext;
    public AdapterDrawData mAdapterDrawData;

    public GenericRecyclerViewAdapter(Context context, AdapterDrawData adapterDrawData) {
        super();
        mContext = context;
        mAdapterDrawData = adapterDrawData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return   mAdapterDrawData.getView(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mAdapterDrawData.bindView(this,holder,getItem(position)  , position);

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
         int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }



}