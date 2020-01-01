package com.example.dou.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dou.MainActivity;
import com.example.dou.viewpage.ViewPagerAdapter;
import com.example.dou.viewpage.ViewPagerVideoFragment;
import com.example.dou.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    //viewpager使用！！！
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ViewPagerVideoFragment> viewPagerVideoFragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("作品","neirong1"));
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("动态","neirong2"));
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("喜欢","neirong3"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity mainActivity = ((MainActivity)getActivity());

        drawerLayout = mainActivity.findViewById(R.id.drawer);
        toolbar = mainActivity.findViewById(R.id.toolbar);
        toolbarLayout = mainActivity.findViewById(R.id.collaps);
        tabLayout = mainActivity.findViewById(R.id.tab_layout);
        viewPager = mainActivity.findViewById(R.id.view_pager);

//        设置自定义的toolbar
        mainActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mainActivity.getSupportActionBar();
        toolbarLayout.setTitle("我");

//        设置viewpager适配器，以及添加适配器数据
        ViewPagerAdapter adapter = new ViewPagerAdapter(mainActivity.getSupportFragmentManager(),viewPagerVideoFragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        这个属性在xml中设置是一样的
        System.out.println("XXXXXXXXXXXXXXXX:"+viewPagerVideoFragments.size());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//       使用inflater.inflate()通过menu布局文件，也可以！！！也能使用代码访问，记得前面要加上setHasOptionsMenu
//        menu.add("Menu 1a").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        menu.add("Menu 1b").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        inflater.inflate(R.menu.me_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                break;
            case R.id.setting:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
