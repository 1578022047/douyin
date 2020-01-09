package com.example.dou.fragment;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.dou.App;
import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.ui.activity.ChangeUserBriefActivity;
import com.example.dou.ui.activity.ChangeUserInfoActivity;
import com.example.dou.ui.activity.MainActivity;
import com.example.dou.adapter.ViewPagerAdapter;
import com.example.dou.R;
import com.example.dou.utils.HttpUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment implements View.OnClickListener {

    private NavigationView nav_view;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ZuopingViewPagerFragment> zuopingViewPagerFragments = new ArrayList<>();
    private User user;
    private List<Video> userVideos=new ArrayList<>();
    private List<Video> likeVideos=new ArrayList<>();
    private List<Flag> zuopingFlags=new ArrayList<>();
    private List<Flag> likeZuopingFlags=new ArrayList<>();
    ViewPagerAdapter adapter;

//    给mefragmnet设置个人信息
    private CircleImageView me_image;
    private TextView douyinId;
    private TextView user_info;
    private ImageView image_toolbar;
    private Button changeUserInfo;
    private TextView huozan_num;
    private TextView guanzhu_num;
    private TextView fans_num;
    private TextView name;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        me_image=view.findViewById(R.id.me_image);
        douyinId = view.findViewById(R.id.douyin_id);
        user_info = view.findViewById(R.id.user_info);
        image_toolbar = view.findViewById(R.id.image_toolbar);
        huozan_num = view.findViewById(R.id.huozan_num);
        guanzhu_num = view.findViewById(R.id.guanzhu_num);
        fans_num = view.findViewById(R.id.fans_num);
        changeUserInfo = view.findViewById(R.id.changeUserInfo);
        name = view.findViewById(R.id.name);
        nav_view = view.findViewById(R.id.nav_view);
        pref = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = pref.edit();

        //        设置viewpager适配器，以及添加适配器数据
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), zuopingViewPagerFragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

//        通过请求服务器获得个人信息等
        initInfo();
//        初始化mefragment信息
        initData();
//        添加监听事件
        initListener();
        return view;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.user_info:
                Intent intent = new Intent(getActivity(), ChangeUserBriefActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.changeUserInfo:
                Intent intent1 = new Intent(getActivity(), ChangeUserInfoActivity.class);
                startActivityForResult(intent1,2);
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String brief = data.getStringExtra("brief");
                    user_info.setText(brief);
                }
            case 2:
                if (resultCode == RESULT_OK){
//                    不用返回数据应为存在全局app中
                    user = ((App)getActivity().getApplication()).getUser();
                    user_info.setText(user.getBrief());
                    name.setText(user.getName());
                    Glide.with(App.sApplication)
                            .load(user.getImageUrl())
                            .into(image_toolbar);
                    Glide.with(App.sApplication)
                            .load(user.getImageUrl())
                            .into(me_image);
                }
        }
    }

    private void initListener() {
        user_info.setOnClickListener(this);
        changeUserInfo.setOnClickListener(this);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logout:
                        Logout();
                        break;
                }
                return true;
            }

            private void Logout() {
                editor.putString("userId","0");
                editor.apply();

                ((App)getActivity().getApplication()).setUser(null);
                drawerLayout.closeDrawers();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });
    }

    private void initInfo() {
        user = ((App)getActivity().getApplication()).getUser();
        if(user != null){
            Glide.with(App.sApplication)
                    .load(user.getImageUrl())
                    .into(me_image);

            Glide.with(App.sApplication)
                    .load(user.getImageUrl())
                    .into(image_toolbar);
//        设置meimage的src
            douyinId.setText("抖音号:"+user.getUserId().substring(0,12));
            name.setText(user.getName());
            user_info.setText(user.getBrief());

        }
    }

    private void initData() {
                    new Handler(getActivity().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            zuopingViewPagerFragments.add(new ZuopingViewPagerFragment("作品","neirong1",userVideos,zuopingFlags,user));
                            zuopingViewPagerFragments.add(new ZuopingViewPagerFragment("动态","neirong2",userVideos,zuopingFlags,user));
                            zuopingViewPagerFragments.add(new ZuopingViewPagerFragment("喜欢","neirong3",likeVideos,likeZuopingFlags,user));
                            adapter.notifyDataSetChanged();
                        }
                    });
    }



    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity mainActivity = ((MainActivity)getActivity());

        drawerLayout = mainActivity.findViewById(R.id.drawer);
        toolbar = mainActivity.findViewById(R.id.toolbar);
        toolbarLayout = mainActivity.findViewById(R.id.collaps);


//        设置自定义的toolbar
        mainActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mainActivity.getSupportActionBar();
        toolbarLayout.setTitle("我");

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

    @Override
    public void onResume() {
        super.onResume();
    }
}
