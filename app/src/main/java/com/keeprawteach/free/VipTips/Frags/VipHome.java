package com.keeprawteach.free.VipTips.Frags;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keeprawteach.free.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipHome extends Fragment {
    private TabLayout tabLayout;

    private ViewPager viewPager;


    public VipHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vip_home, container, false);

        int number = getArguments().getInt("number");

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(number);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new MultiOne(), "Slip One ");

        adapter.addFragment(new MultiTwo(), "Slip Two");

        adapter.addFragment(new MultiThree(), "Slip Three");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);

            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

}
