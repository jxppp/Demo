package com.example.administrator.listviewitemslide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {


    private Context context;
    private LayoutInflater inflater;
    // 数据源，用于存放颜色值的。
    private List<Integer> colors;
    // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
    private int mScreentWidth;
    private View view;

    /**
     * 构造方法
     *
     * @param context
     * @param screenWidth
     */
    public ListViewAdapter(Context context, int screenWidth) {

        // 初始化
        this.context = context;
        mScreentWidth = screenWidth;

        // 填充list的内容模拟数据，否则应该异步执行
        colors = new ArrayList<Integer>();
        for (int i = 0; i < 15; i++) {
            colors.add(R.color.colorAccent);
        }
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
            holder.action = convertView.findViewById(R.id.ll_action);
            holder.btOne = (Button) convertView.findViewById(R.id.button1);
            holder.btTwo = (Button) convertView.findViewById(R.id.button2);
            holder.btThree = (Button) convertView.findViewById(R.id.button3);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv);

            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            holder.content = convertView.findViewById(R.id.ll_content);
            ViewGroup.LayoutParams lp = holder.content.getLayoutParams();
            lp.width = mScreentWidth;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
        holder.btOne.setTag(position);
        holder.btTwo.setTag(position);
        holder.btThree.setTag(position);

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (view != null) {
                            ViewHolder viewHolder1 = (ViewHolder) view.getTag();
                            viewHolder1.hSView.smoothScrollTo(0, 0);
                        }

                    case MotionEvent.ACTION_UP:
                        // 获得ViewHolder
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
                        view = v;
                        // 获得HorizontalScrollView滑动的水平方向值.
                        int scrollX = viewHolder.hSView.getScrollX();
                        // 获得操作区域的长度
                        int actionW = viewHolder.action.getWidth();

                        // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                        // 如果水平方向的移动值<操作区域的长度的一半,就复原
                        if (scrollX < actionW / 2) {
                            viewHolder.hSView.smoothScrollTo(0, 0);
                        } else// 否则的话显示操作区域
                        {
                            viewHolder.hSView.smoothScrollTo(actionW, 0);
                        }

                        return true;
                }
                return false;
            }
        });
        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (holder.hSView.getScrollX() != 0) {
            holder.hSView.scrollTo(0, 0);
        }

        // 设置背景颜色,设置填充内容.
        holder.content.setBackgroundResource(colors.get(position));
        holder.tvContent.setText("" + position);

        // 设置监听事件
        holder.btOne.setOnClickListener(this);
        holder.btTwo.setOnClickListener(this);
        holder.btThree.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.button1:
                colors.add(R.color.colorAccent);
                break;
            case R.id.button2:
                colors.remove(position);
                break;
            case R.id.button3:
                if (colors.get(position) == R.color.colorAccent) {
                    colors.set(position, R.color.colorPrimary);
                } else {
                    colors.set(position, R.color.colorAccent);
                }
                break;

            default:
                break;
        }
        // 刷新ListView内容
        notifyDataSetChanged();
    }

    /**
     * ViewHolder
     *
     * @Title:
     * @Description:主要是避免了不断的view获取初始化.
     * @Author:yzy
     * @Since:2013-10-22
     */
    class ViewHolder {
        public HorizontalScrollView hSView;

        public View content;
        public TextView tvContent;

        public View action;
        public Button btOne;
        public Button btTwo;
        public Button btThree;
    }
}
