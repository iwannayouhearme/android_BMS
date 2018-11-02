package com.fhh.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.fhh.R;
import com.fhh.base.adapter.CommonAdapter;
import com.fhh.base.adapter.lvadapter.ViewHolder;
import com.fhh.utils.NullUtils;
import com.fhh.utils.ScreenUtils;

import java.util.List;

/**
 * @author: kadh
 * @email : 36870855@qq.com
 * @date : 2018/8/7
 * @blog : http://www.nicaicaicai.com
 * @desc :
 */
public class SingleChoiceDialog<T> {

    private Context mContext;
    private String mSelectItem = "";
    private AlertDialog mDialog;
    private List<ISingleChoiceModel> mData;
    private ISingleChoiceDialog mISingleChoiceDialog;
    private CommonAdapter<ISingleChoiceModel> mCommonAdapter;

    /**
     * @param context
     * @param title               标题字段
     * @param selectItem          默认选中字段
     * @param data                数据源
     * @param iSingleChoiceDialog 接口
     */
    public SingleChoiceDialog(Context context, String title, String selectItem, @NonNull List<ISingleChoiceModel> data, @NonNull ISingleChoiceDialog iSingleChoiceDialog) {
        mContext = context;
        mSelectItem = selectItem;
        mData = data;
        mISingleChoiceDialog = iSingleChoiceDialog;
        initDilaog(context, title);
    }

    private void initDilaog(Context context, String title) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        View inflate = View.inflate(context, R.layout.change_single_dialog, null);
        TextView tv = inflate.findViewById(R.id.tv_change);
        ListView listView = inflate.findViewById(R.id.lv_change);
        tv.setText(title);

        mCommonAdapter = new CommonAdapter<ISingleChoiceModel>(context, R.layout.item_change, mData) {
            @Override
            protected void convert(ViewHolder viewHolder, ISingleChoiceModel item, int position) {
                viewHolder.setText(R.id.item_tv_name, NullUtils.filterEmpty(item.getItemTitle()));
                if (mSelectItem.equals(item.getItemKey())) {
                    viewHolder.setChecked(R.id.item_rb, true);
                } else {
                    viewHolder.setChecked(R.id.item_rb, false);
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mISingleChoiceDialog != null) {
                    mISingleChoiceDialog.onItemClickForSingleChoiceDialog(mData.get(position), position);
                    mSelectItem = mData.get(position).getItemKey();
                }
                mDialog.dismiss();
            }
        });
        listView.setAdapter(mCommonAdapter);
        mDialog = alertBuilder.create();
        mDialog.setView(inflate);
    }

    public void show() {
        if (!mDialog.isShowing()) {
            WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = ScreenUtils.dp2px(mContext, 300);
            if (mCommonAdapter.getCount() > 8) {
                params.height = ScreenUtils.dp2px(mContext, 410);
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            mDialog.getWindow().setAttributes(params);
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShow() {
        return mDialog.isShowing();
    }

    public interface ISingleChoiceDialog {
        void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position);
    }

}

