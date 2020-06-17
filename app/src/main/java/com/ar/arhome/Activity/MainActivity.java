package com.ar.arhome.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ar.arhome.Adapter.SectionsPagerAdapter;
import com.ar.arhome.Fragment.FragmentOne;
import com.ar.arhome.Fragment.FragmentThree;
import com.ar.arhome.Fragment.FragmentTwo;
import com.ar.arhome.R;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;

    //退出时的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.my_view_pager);
        bottomNavigationBar = findViewById(R.id.my_bottom);

        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        initViewPager();
        initBottomNavigation();
    }

    private void initBottomNavigation() {

//        配置导航栏
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setBarBackgroundColor(R.color.background_light_gray);
        bottomNavigationBar.setBackgroundColor(ContextCompat.getColor(this,R.color.background_light_gray));
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//自适应大小
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);//默认
        bottomNavigationBar.setActiveColor(R.color.pure_white);//激活态
        bottomNavigationBar.setInActiveColor(R.color.background_dark_gray);//非激活态

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.index, "首页"))
                .setInActiveColor(R.color.normal_color)
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.camera, "预览"))
                .setInActiveColor(R.color.normal_color)
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.me, "我的"))
                .setInActiveColor(R.color.normal_color)
                .setFirstSelectedPosition(0)
                .initialise();
    }

    private void initViewPager() {
        // 配置ViewPager 用于设置左右滑动的页面
        viewPager.setOffscreenPageLimit(3);

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());

//       向View Pager内添加适配器 用于与数据的绑定
//       Set a PagerAdapter that will supply views for this pager as needed.
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
//    绑定了底部和navibar
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
