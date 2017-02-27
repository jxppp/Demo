package com.example.administrator.listviewandviewpage.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.listviewandviewpage.R;
import com.example.administrator.listviewandviewpage.application.BaseApplication;
import com.example.administrator.listviewandviewpage.view.SmartViewPager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */
public class ViewHolder implements View.OnTouchListener {

    //返回的view
    private View viewToReturn;

    //所有图片资源文件的id集合
    private List<Integer> mDatas;
    //viewpager, 使用自定义控件，重写onMesure方法，自适应图片宽高
    private SmartViewPager viewPager;

    //ViewPager的布局
    private RelativeLayout.LayoutParams rlp;
    //自动播放的任务
    private AutoPlayTask autoPlayTask;
    private final int AUTO_PLAY_TIME = 2000;

    public ViewHolder(List<Integer> data){
        viewToReturn = initView();
        viewToReturn.setTag(this);
        setmDatas(data);
    }

    /** 初始化view的方法 ，返回一个view */
    protected View initView() {

        Context context = BaseApplication.getmContext();
        Log.d("rd96", "context 是否为空" + context);
        //初始化头布局
        RelativeLayout mHeaderView = new RelativeLayout(BaseApplication.getmContext());

        // 设置轮播图的宽高
        // 设置LayoutParams的时候，一定要带一个父容器的限制，这里使用AbsListView的原因是因为，Listview没有.LayoutParams选项
        // 此处让宽度是EXACTLY，而高度不是，所以测量时会以宽度为准
        mHeaderView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        viewPager = new SmartViewPager(BaseApplication.getmContext());

        viewPager =(SmartViewPager) View.inflate(BaseApplication.getmContext(), R.layout.list_header, null);

        // 改为在xml布局文件中设置自定义属性
        // viewPager.setRatio(16 / 9.0f);
        rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        // 给SmartViewPager设置宽高的模式，宽为MATCH_PARENT/EXACTLY，高为WRAP_CONTENT/非EXACTLY
        // 便于测量
        viewPager.setLayoutParams(rlp);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        //设置adapter
        viewPager.setAdapter(adapter);

        autoPlayTask = new AutoPlayTask();

        //这一句start要加，否则要按一下才开始跳
        autoPlayTask.start();

        viewPager.setOnTouchListener(this);

        //把轮播图添加到header中去，添加也要按照次序，先添加的在下面
        mHeaderView.addView(viewPager);
        return  mHeaderView;
    }

    private class ViewPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;// 总共能够滑动的次数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;// 返回的view就是这个object
        }

        private HashMap<Integer, Drawable> drawables = new HashMap<Integer, Drawable>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(BaseApplication.getmContext());

            //加载图片， %以下，防止数组越界
            Integer id = mDatas.get(position % mDatas.size());
            Drawable drawable = BaseApplication.getmContext().getResources().getDrawable(id);
            imageView.setImageDrawable(drawable);

            // 如果是从网络获取图片的话，必须增加这个属性，缩放，填充控件
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 每个imageview都可以设置两个源：backGround是imageview本身的大小，而src是设置的图片的大小
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }
    }

    public List<Integer> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<Integer> mDatas) {
        this.mDatas = mDatas;
        //刷新界面
        refreshView();
    }

    /** 先设置从setData传入的数据，然后刷新界面的方法 */
    public void refreshView() {
        mDatas = getmDatas();
        // 让轮播图从0处开始滚动
        viewPager.setCurrentItem(0);
    }

    // 把准备好的View返回去
    public View getRootView() {
        return viewToReturn;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_CANCEL){
            // down事件和cancel事件 停止轮播
            // cancel表示屏幕被挡住之后
            autoPlayTask.stop();
        } else if (action == MotionEvent.ACTION_UP){
            //up事件 恢复轮播
            autoPlayTask.start();
        }
        return false;
    }

    /**
     * 自动播放的Runnable
     */
    private class AutoPlayTask implements Runnable{

        private boolean IS_PLAYING = false;

        @Override
        public void run() {
            if (IS_PLAYING){
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                BaseApplication.getMainThreadHandler().postDelayed(this, AUTO_PLAY_TIME);
            }
        }

        public void stop(){
            BaseApplication.getMainThreadHandler().removeCallbacks(this);
            IS_PLAYING = false;// 不置为false,下次就start不起来
        }

        public void start(){
            if (!IS_PLAYING){
                IS_PLAYING = true;
                // UIUtils.postDelayed(this, AUOT_PLAY_TIME);
                // 移除任何在消息队列中的对应参数中的runnable对象
                BaseApplication.getMainThreadHandler().removeCallbacks(this);// 这句清空的操作很有必要，防止越跳越快的情况
                BaseApplication.getMainThreadHandler().postDelayed(this, AUTO_PLAY_TIME);
            }
        }
    }

}
