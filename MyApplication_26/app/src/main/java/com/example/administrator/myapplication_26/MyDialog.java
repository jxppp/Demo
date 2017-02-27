package com.example.administrator.myapplication_26;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/28.
 */
public class MyDialog extends Dialog {

    TextView textView;
    private TextView yes, no;

    public MyDialog(Context context) {
        super(context, R.style.Theme_Dialog_Alpha);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_removedev, null);
        textView =(TextView) layout.findViewById(R.id.title);
        setContentView(layout);
        no = (TextView) layout.findViewById(R.id.tv_remove_cancel);
        yes = (TextView) layout.findViewById(R.id.tv_remove_yes);
    }

    public static Dialog builderDialog(Activity activity, String title, String msg, String... buttonStrs){
        final MyDialog dialog = new MyDialog(activity);
        dialog.setTitle(msg);
        switch (buttonStrs.length){
            case 3:
            case 2:
                if(buttonStrs[1] != null){
                    dialog.setNoText(buttonStrs[1]);
                }
            case 1:// 确定
                if (buttonStrs[0] != null) {
                    dialog.setYesText(buttonStrs[0]);
                }
            case 0:
                break;

        }
        return dialog;
    }

    public static Dialog builderSimpleDialog(Activity activity, int title,
                                             int msg, final DialogInterface.OnClickListener listener,
                                             int... buttonStrs) {
        return builderDialog(activity
                , activity.getString(title)
                , activity.getString(msg)
                , resIds2Strings(activity, buttonStrs));
    }

    public static String[] resIds2Strings(Activity activity, int[] buttonStrs) {
        String[] strs = new String[buttonStrs.length];
        for (int i = 0; i < buttonStrs.length; i++) {
            strs[i] = activity.getString(buttonStrs[i]);
        }
        return strs;
    }

    public void setTitle(String title) {
        textView.setText(title);

    }

    public void setNoText(String text) {
        no.setText(text);
    }

    public void setYesText(String text) {
        yes.setText(text);
    }
}
