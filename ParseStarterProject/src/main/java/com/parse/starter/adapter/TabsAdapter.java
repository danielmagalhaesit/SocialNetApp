package com.parse.starter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.parse.starter.R;
import com.parse.starter.fragments.FriendsFragment;
import com.parse.starter.fragments.HomeFragment;

import java.util.HashMap;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private int[] icons = new int[]{R.drawable.ic_action_home, R.drawable.ic_people};
    private int iconSize;
    private HashMap<Integer, Fragment> usedFragment;

    public TabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        double scale = this.context.getResources().getDisplayMetrics().density;
        iconSize = (int) (36 * scale);
        this.usedFragment = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new HomeFragment();
                usedFragment.put(position, fragment);
                break;
            case 1:
                fragment = new FriendsFragment();

                break;
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        usedFragment.remove(position);
    }

    public Fragment getFragment(Integer index){
        return usedFragment.get(index);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        // Getting icon position
        Drawable drawable = ContextCompat.getDrawable(this.context, icons[position]);
        drawable.setBounds(0,0,iconSize,iconSize);

        // Put img into the string
        ImageSpan imageSpan = new ImageSpan(drawable);

        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;

    }

    @Override
    public int getCount() {
        return icons.length;
    }
}
