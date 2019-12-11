package com.sylvester.ams.controller.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {
    private int pageCount;
    private int mCurrentPosition = -1;

    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount; // 페이지 개수
    }

    // 기본 디폴트 아이템 설정
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            CustomViewPager pager = (CustomViewPager) container;

            // fragment 와 view 가 있다면 현재 페이지를 기본 페이지로 바꾸어 나타낸다.
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }

    // position 에 해당하는 Fragment 를 반환한다.
    @Override
    public Fragment getItem(int position) {
        // 현재 탭과 맞는 페이지를 반환한다.
        switch (position) {
            case 0:
                DetailBasicInfo basicInfoFragment = new DetailBasicInfo();
                return basicInfoFragment;
            case 1:
                DetailFeeding feedingFragment = new DetailFeeding();
                return feedingFragment;
            case 2:
                DetailHabitatInfo habitatFragment = new DetailHabitatInfo();
                return habitatFragment;
            default:
                return null;
        }
    }

    // page 의 개수를 반환한다.
    @Override
    public int getCount() {
        return pageCount;
    }
}
