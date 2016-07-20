package com.shixi.a;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by z on 2016/6/4.
 */
public  class SelectDialogUtil {

    private static ListView mListview;
    private static List<String> mTexts;

    public static void  showSelectDialogUtil(Context context, List<String> texts, boolean cancelAble, onTextSelectedListener listener) {
        mTexts = texts;
        mListview = (ListView) View.inflate(context, R.layout.dialog_select, null);
        mListview.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1,texts));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setView(mListview).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(cancelAble);//调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        mListview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mOnTextSelectedListener.onTextSelected(mTexts.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mOnTextSelectedListener.onNothingSelected();

            }
        });
    }

    private static onTextSelectedListener mOnTextSelectedListener;

    interface onTextSelectedListener {
        public void onTextSelected(String selectedText);

        public void onNothingSelected();
    }


}
