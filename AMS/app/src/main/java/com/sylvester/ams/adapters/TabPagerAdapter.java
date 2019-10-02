package com.sylvester.ams.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.sylvester.ams.funtion.CustomViewPager;
import com.sylvester.ams.ui.DetailBasicInfo;
import com.sylvester.ams.ui.DetailBreedingInfo;
import com.sylvester.ams.ui.DetailFeeding;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;
    private int mCurrentPosition = -1;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            CustomViewPager pager = (CustomViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                DetailBasicInfo tabFragment1 = new DetailBasicInfo();
                return tabFragment1;
            case 1:
                DetailFeeding tabFragment2 = new DetailFeeding();
                return tabFragment2;
            case 2:
                DetailBreedingInfo tabFragment3 = new DetailBreedingInfo();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
