package com.example.dou.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dou.App;
import com.example.dou.CommitAdapter;
import com.example.dou.R;
import com.example.dou.RecyclerViewPageChangeListenerHelper;
import com.example.dou.adapter.VideoAdapter;
import com.example.dou.command.MyVideoPlayer;
import com.example.dou.command.PagerLayoutManager;
import com.example.dou.command.SoftKeyBoardListener;
import com.example.dou.command.SoftKeyHideShow;
import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.ui.activity.MainActivity;
import com.example.dou.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class RecommendFragment extends AddMethodFragment {

    private List<String> myData;
    private PagerLayoutManager mLayoutManager;
    private MyVideoPlayer jzVideo;
    private SoftKeyBoardListener softKeyBoardListener;//软键盘监听
    private CommitAdapter commitAdapter;
    private RecyclerView recyclerViewCommit;
    private RelativeLayout rl_bottom;
    private View commit;
    private TextView tv_shape, tv_shape2, tv_send, tv_context;
    private LinearLayout ll_cancel;
    private RelativeLayout rl_all;
    private EditText et_context;
    private Animation showAction;
    /**
     * 默认从第一个开始播放
     */
    private int positionClick = 0;

    /**
     * 是否可以自动滑动
     * 当现实评论列表，说明用户想评论，不可以自动滑动
     */
    private boolean isScroll = true;




    private SwipeRefreshLayout swipeRefresh;
    RecyclerView videoList;
    List<Video> videos = new ArrayList<>();
    LinearLayoutManager layoutManager;
    VideoAdapter adapter;
    private String title;
    int curPosition = 0;
    List<Flag> flags=new ArrayList<>();
    private List<User> users = new ArrayList<>();
    User user;

    public String getTitle() {
        return "推荐";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attention_fragment, null);
        //分割线
        rl_bottom = view.findViewById(R.id.rl_bottom);
        recyclerViewCommit = view.findViewById(R.id.recyclerViewCommit);
        commit = view.findViewById(R.id.commit);
        tv_shape = view.findViewById(R.id.tv_shape);
        tv_shape2 = view.findViewById(R.id.tv_shape2);
        tv_send = view.findViewById(R.id.tv_send);
        tv_context = view.findViewById(R.id.tv_context);
        ll_cancel = view.findViewById(R.id.ll_cancel);
        rl_all = view.findViewById(R.id.rl_all);
        et_context = view.findViewById(R.id.et_context);
        myData = new ArrayList<>();



        //分割线
        videoList = view.findViewById(R.id.videoList);
        swipeRefresh=view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorButtomRed);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                users.clear();
                videos.clear();
                flags.clear();
                getVideo();
            }
        });
        initListener();
        initDate();
        initView();
        initCommand();
        return view;
    }

    private void initCommand() {


    }
    /**
     * 评论布局
     */
    public void showCommitDialog() {
        if (commitAdapter == null) {
            commitAdapter = new CommitAdapter(getContext());
            recyclerViewCommit.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewCommit.setAdapter(commitAdapter);
        } else {
            commitAdapter.notifyDataSetChanged();
        }

        //为布局设置显示的动画
        showAction = AnimationUtils.loadAnimation(getContext(), R.anim.actionsheet_dialog_in);
        commit.startAnimation(showAction);

        //显示布局和阴影
        commit.setVisibility(View.VISIBLE);
        tv_shape.setVisibility(View.VISIBLE);

        //不可以滑动
        isScroll = false;

        //关闭评论
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
                //可以滑动
                isScroll = true;
            }
        });
        //阴影点击,隐藏评论列表和阴影
        tv_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
                //可以滑动
                isScroll = true;
            }
        });
        //输入评论点击
        tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(getContext());//隐藏软键盘
            }
        });
        //第二层阴影点击，隐藏输入评论框和软键盘
        tv_shape2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(getContext());//隐藏软键盘
            }
        });
        //发送评论
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                SoftKeyHideShow.HideShowSoftKey(getContext());//隐藏软键盘
            }
        });
    }

    /**
     * 软键盘监听
     */
    private void setSoftKeyBoardListener() {
        softKeyBoardListener = new SoftKeyBoardListener(getActivity());
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //动态设置控件宽高
                ViewGroup.LayoutParams params = rl_bottom.getLayoutParams();
                rl_bottom.setPadding(0, 0, 0, height);
                rl_bottom.setLayoutParams(params);
                //当软键盘显示的时候
                rl_bottom.setVisibility(View.VISIBLE);
                tv_shape2.setVisibility(View.VISIBLE);

                et_context.setFocusable(true);
                et_context.setFocusableInTouchMode(true);
                et_context.setCursorVisible(true);
                et_context.requestFocus();
            }

            @Override
            public void keyBoardHide(int height) {
                //当软键盘隐藏的时候
                rl_bottom.setVisibility(View.GONE);
                tv_shape2.setVisibility(View.GONE);
                et_context.setFocusable(false);
                et_context.setFocusableInTouchMode(false);
                et_context.setCursorVisible(false);
            }
        });
        //设置点击事件,显示软键盘
        et_context.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        //防止EditText点击两次才获取到焦点
        et_context.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;//不要忘记这句话
                    //处理逻辑
                    et_context.setFocusable(true);
                    et_context.setFocusableInTouchMode(true);
                    et_context.setCursorVisible(true);
                }
                return false;
            }
        });
    }

    //

    private void initListener() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDate() {
        user=((App)getActivity().getApplication()).getUser();

    }

    private void getVideo() {
        String url = HttpUtil.host + "getFiveVideo";
        String userId;
        if(user!=null) {
            userId=user.getUserId();
        }else{
            userId="";
        }
        HttpUtil.getFiveVideoHttp(url,userId, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Map<String, String> map = null;
                try {
                    map = new Gson().fromJson(response.body().string(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                users.addAll(new Gson().fromJson(map.get("userList"), new TypeToken<List<User>>() {
                }.getType()));
                videos.addAll(new Gson().fromJson(map.get("videoList"), new TypeToken<List<Video>>() {
                }.getType()));
                flags.addAll(new Gson().fromJson(map.get("flagList"), new TypeToken<List<Flag>>() {
                }.getType()));
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initView() {
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(videoList);
        layoutManager = new LinearLayoutManager(getContext());
        videoList.setLayoutManager(layoutManager);
        adapter = new VideoAdapter(videos, users,flags, getContext());
        //

        layoutManager.findViewByPosition(curPosition).findViewById(R.id.command).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommitDialog();
            }
        });


        //
        videoList.setAdapter(adapter);
        videoList.getItemAnimator().setChangeDuration(0);
        videoList.setItemAnimator(null);
        getVideo();
        videoList.addOnScrollListener(new RecyclerViewPageChangeListenerHelper(pagerSnapHelper,
                new RecyclerViewPageChangeListenerHelper.OnPageChangeListener() {


                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        adapter.setPlay(position);
                        if (videos.size() - position <= 3) {
                            getVideo();
                        }
                        curPosition = position;
                    }
                }));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

