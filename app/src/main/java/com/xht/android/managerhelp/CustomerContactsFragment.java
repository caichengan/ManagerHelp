package com.xht.android.managerhelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.xht.android.managerhelp.mode.ContactsAdapter;
import com.xht.android.managerhelp.mode.ContactsMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link CustomerContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 *     我的客户中的联系人
 */
public class CustomerContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView addCustomer;
    private ListView contactsListView;

    private List<ContactsMode> contactsList;


    public CustomerContactsFragment() {
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
    public static CustomerContactsFragment newInstance(String param1, String param2) {
        CustomerContactsFragment fragment = new CustomerContactsFragment();
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


        contactsList=new ArrayList<>();

        //获取联系人名单
        getContactsList();

    }

    private void getContactsList() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);


        addCustomer = (ImageView) view.findViewById(R.id.addCustomer);
        contactsListView = (ListView) view.findViewById(R.id.contactsListView);

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        ContactsAdapter adapter=new ContactsAdapter(contactsList,getActivity());
        contactsListView.setAdapter(adapter);

        return view;
    }


}

