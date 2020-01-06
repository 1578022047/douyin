package com.example.dou.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.dou.App;
import com.example.dou.adapter.ZuopingAdapter;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.ui.activity.MainActivity;
import com.example.dou.adapter.ViewPagerAdapter;
import com.example.dou.R;
import com.example.dou.utils.HttpUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class MeFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ViewPagerVideoFragment> viewPagerVideoFragments = new ArrayList<>();
    private User user;
    private List<Video> userVideos;
    private List<Video> likeVideos;
    ViewPagerAdapter adapter;

//    给mefragmnet设置个人信息
    private CircleImageView me_image;
    private TextView douyinId;
    private TextView user_info;
    private ImageView image_toolbar;
    private TextView huozan_num;
    private TextView guanzhu_num;
    private TextView fans_num;
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

        //        设置viewpager适配器，以及添加适配器数据
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),viewPagerVideoFragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

//        通过请求服务器获得个人信息等
        initData();
//        初始化mefragment信息
        initInfo();
//        添加监听事件
        initListener();
        return view;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.user_info:
                Intent intent = new Intent();
                startActivity(intent);
                break;
        }
    }

    private void initListener() {
        user_info.setOnClickListener(this);
    }

    private void initInfo() {
        Glide.with(App.sApplication)
                .load(user.getImageUrl())
                .into(me_image);
//        设置meimage的src
        douyinId.setText(user.getUserId());

        user_info.setText(user.getBrief());

        Glide.with(App.sApplication)
                .load(user.getImageUrl())
                .into(image_toolbar);

    }

    private void initData() {
        if(((App)getActivity().getApplication()).getUser()!=null) {
            String url= HttpUtil.host+"getUserVideoAndInfo";
            String userId = ((App) getActivity().getApplication()).getUser().getUserId();
            HttpUtil.getUserVideoAndInfoHttp(url, userId, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    new Handler(getActivity().getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String,String> map=new Gson().fromJson(response.body().string(), Map.class);
                                user=new Gson().fromJson(map.get("user"),User.class);
                                userVideos=new Gson().fromJson(map.get("userVideoList"),new TypeToken<List<Video>>(){}.getType());
                                likeVideos=new Gson().fromJson(map.get("likeVideoList"),new TypeToken<List<Video>>(){}.getType());
                                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewPagerVideoFragments.add(new ViewPagerVideoFragment("作品","neirong1",userVideos));
                                        viewPagerVideoFragments.add(new ViewPagerVideoFragment("动态","neirong2",userVideos));
                                        viewPagerVideoFragments.add(new ViewPagerVideoFragment("喜欢","neirong3",likeVideos));
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
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

}
