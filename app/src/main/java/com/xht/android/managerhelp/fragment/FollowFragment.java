package com.xht.android.managerhelp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.R;

/**
 * Created by Administrator on 2017/1/5.
 */


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link FollowFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 *     我的客户中的跟进记录
 */
public class FollowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView followListView;

    public FollowFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenJinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowFragment newInstance(String param1, String param2) {
        FollowFragment fragment = new FollowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_follow, container, false);
        followListView = (ListView)view. findViewById(R.id.flolowListView);
        final PullRefreshLayout  swipeRefreshLayout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // 刷新3秒完成
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        followListView.setAdapter(null);

        return view;
    }

}

