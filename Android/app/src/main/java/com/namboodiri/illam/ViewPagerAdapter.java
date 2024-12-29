package com.namboodiri.illam;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

// ViewPagerAdapter manages multiple fragments in a swipeable interface
public class ViewPagerAdapter extends FragmentPagerAdapter {
    // List to store fragments that will be displayed in the ViewPager
    private final List<Fragment> mFragmentList = new ArrayList<>();
    // List to store titles corresponding to each fragment
    private final List<String> mFragmentTitleList = new ArrayList<>();

    // Constructor that takes a FragmentManager to handle fragment transactions
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    // Returns the fragment at the specified position
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    // Returns the total number of fragments
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    // Method to add a new fragment and its corresponding title
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    // Returns the title of the fragment at the specified position
    // This will be displayed in the TabLayout if used
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
