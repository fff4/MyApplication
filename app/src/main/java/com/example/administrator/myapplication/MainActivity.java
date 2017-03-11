package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.myapplication.adapter.MListAdapter;
import com.example.administrator.myapplication.bean.GurilyBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lv)
    ListView mListView;

    private List<GurilyBean> mList = new ArrayList<>();
    private String mTag = "MainActivity";
    private GurilyBean mBean = new GurilyBean();
    private MListAdapter mMListAdapter;
    private List<GurilyBean.ResultsBean> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        //同步方法---只有在网络请求完成，才能继续执行
//        sendSyncRequest();
        sendAsyncRequest();

    }


    private void initData() {

    }

    /**
     * 同步请求
     */
    private void sendSyncRequest() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //OKHttp的使用
                //创建okhttp的实例对象
                OkHttpClient okHttpCline = new OkHttpClient();

                String path = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
                Request request = new Request.Builder().get().url(path).build();
                try {

                    Response response = okHttpCline.newCall(request).execute();

                    //内部有 string()方法的封装，使用tostring 只能得到方法的参数，不能得到数据
                    Log.d(mTag, "request = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Gson gson = new Gson();

    /**
     * 异步请求
    */
    private void sendAsyncRequest() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //创建对象
                OkHttpClient okHttp = new OkHttpClient();

                //创建请求方法和请求参数
                String path = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
                Request request = new Request.Builder().get().url(path).build();

                //异步请求-我执行的时候，并不影响别人
                okHttp.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call cal, Response response) throws IOException {
                        String json = response.body().string();
                        //通过gson的jar包来将一个json字符串，解析成一个bean对象
                        mBean = gson.fromJson(json, GurilyBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("2");
                                mResults = mBean.getResults();
//                                mMListAdapter.notifyDataSetChanged();
                                mMListAdapter = new MListAdapter(mResults,MainActivity.this);
                                mListView.setAdapter(mMListAdapter);
                            }
                        });
                    }
                });
            }
        }).start();
    }

}
