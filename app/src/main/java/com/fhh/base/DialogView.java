package com.fhh.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fhh.R;
import com.fhh.utils.ScreenUtils;


public class DialogView {
    public static void showSureDialog(final Activity activity, String title, String content, String sureText, String cancelText
            , final SureDialogViewListener sureListener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity, R.style.CallPhoneStyle);
        final AlertDialog SingleAlertDialog = alertBuilder.create();
        View v = View.inflate(activity, R.layout.dialog_confirm, null);
        TextView mTvTitle = v.findViewById(R.id.dialog_confirm_tv_title);
        TextView mTvContent = v.findViewById(R.id.dialog_confirm_tv_content);
        Button mBtnCancel = v.findViewById(R.id.dialog_confirm_btn_cancel);
        final Button mBtnSure = v.findViewById(R.id.dialog_confirm_btn_sure);
        mTvTitle.setText(title);
        mTvContent.setText(content);
        mBtnCancel.setText(cancelText);
        mBtnSure.setText(sureText);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleAlertDialog.dismiss();
            }
        });
        mBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sureListener.OnClickListener(mBtnSure, SingleAlertDialog);
            }
        });
        SingleAlertDialog.setView(v);
        WindowManager.LayoutParams params = SingleAlertDialog.getWindow().getAttributes();
        SingleAlertDialog.getWindow().setAttributes(params);
        SingleAlertDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams wlp = SingleAlertDialog.getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        int Width = ScreenUtils.getScreenWidth(activity);
        int Height = ScreenUtils.getScreenHeight(activity);
        params.width = (int) (Width * 0.9);
        params.height = (int) (Height * 0.7);
        SingleAlertDialog.getWindow().setAttributes(params);
        SingleAlertDialog.getWindow().setAttributes(wlp);
        SingleAlertDialog.show();
//        return SingleAlertDialog;
    }

    public interface SureDialogViewListener {
        void OnClickListener(View view, AlertDialog dialog);
    }
}
