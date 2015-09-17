package com.hkm.slideselection.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.internal.widget.TintImageView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hkm.slideselection.R;
import com.hkm.slideselection.worker.MessageEvent;
import com.hkm.slideselection.worker.SelectChoice;
import com.hkm.slideselection.V2.TwoLevelPagerAdapter;
import com.hkm.slideselection.worker.Util;
import com.hkm.slideselection.worker.bEZ;
import com.hkm.slideselection.worker.bridgeEZ;
import com.squareup.otto.Subscribe;

/**
 * HB Filter by Heskeyo Kam
 * Created by hesk on 16/9/15.
 */
public class HbSelectionFragment extends selectionBody {
    private TintImageView back, apply, reset;
    private ProgressBar mProgress;
    private TextView title_navigation;
    private boolean initialize = false, isInProgress = false;
    private bridgeEZ mInterface = new bEZ();
    protected TwoLevelPagerAdapter adapter;

    public static HbSelectionFragment newInstance(SelectChoice data) {
        final HbSelectionFragment b = new HbSelectionFragment();
        b.setArguments(Util.stuffs(data));
        return b;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TwoLevelPagerAdapter(getChildFragmentManager(), getArguments());
        mViewPager.setAdapter(adapter);
        initialize = true;
        mViewPager.setCurrentItem(0);
        bindViews(view);
        inProgressDoneSimple();
    }

    @Override
    public boolean onPressBack() {
        boolean re = adapter.levelBack(mViewPager);
        apply_level_to_tools(adapter.getCurreLv());
        return re;
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param activity listener
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof bridgeEZ) {
            mInterface = (bridgeEZ) activity;
        }
        if (getParentFragment() instanceof bridgeEZ) {
            mInterface = (bridgeEZ) getParentFragment();
        }
    }

    @Override
    protected int getXml() {
        return R.layout.hb_controlla;
    }

    public void setNagviationTitle(String mTitle) {
        title_navigation.setText(mTitle);
    }

    private void bindViews(View mv) {
        mProgress = (ProgressBar) mv.findViewById(R.id.sssl_ui_loading_progress_bar_xx);
        title_navigation = (TextView) mv.findViewById(R.id.sssl_title_navigation);
        back = (TintImageView) mv.findViewById(R.id.sssl_b_back);
        apply = (TintImageView) mv.findViewById(R.id.sssl_b_filter_apply);
        reset = (TintImageView) mv.findViewById(R.id.sssl_b_filter_clear);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInProgress) {
                    onPressBack();
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("result_f", "here u go");
                mInterface.request_applied();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("result_f", "remove the filter and reset");
                start_new_filter();
            }
        });
        inProgress();
    }

    private void reveal_apply(boolean enabled) {
        if (enabled) {
            apply.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
        } else {
            apply.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);
        }
    }

    private void apply_level_to_tools(int lv) {
        if (lv == 0) {
            apply.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
        } else {
            apply.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);
        }
    }

    public void inProgress() {
        mProgress.animate().alpha(1f);
        isInProgress = true;
        reveal_apply(false);
    }

    private void inProgressDoneSimple() {
        initialize = false;
        mProgress.animate().alpha(0f);
        reveal_apply(false);
    }

    public void inProgressDone() {
        if (mProgress != null) {
            mProgress.animate().alpha(0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    isInProgress = false;
                    apply_level_to_tools(adapter.getCurreLv());
                }
            });
        }

        initialize = false;
    }

    private void start_new_filter() {
        inProgress();
        initialize = true;
        mInterface.request_new_filter();
    }

    @Override
    public void onStart() {
        mBus.register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        mBus.unregister(this);
        super.onStop();
    }

    @Subscribe
    @Override
    public void onEvent(SelectChoice event_choice) {
        mInterface.SelectNow(mViewPager, adapter, event_choice, this);
    }

    @Subscribe
    @Override
    public void onEvent(MessageEvent event_integer) {
        apply_level_to_tools(1);
        mInterface.HomeSelect(mViewPager, adapter, event_integer.At(), this);
    }

}