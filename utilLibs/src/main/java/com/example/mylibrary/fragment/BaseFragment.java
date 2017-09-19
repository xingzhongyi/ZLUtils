package com.example.mylibrary.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibrary.R;
import com.example.mylibrary.view.CustomDialog;

import org.xutils.x;

/**
 * Created by xingzy on 2016/4/13.
 */
public class BaseFragment extends Fragment {
    protected String TAG=getClass().getSimpleName();
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
    public CustomDialog pd;

    public void showPd() {
        if (pd == null) {
//            pd = new ProgressDialog(getActivity());
            pd = new CustomDialog(getActivity(), R.style.CustomDialog);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        } else if (!pd.isShowing()) {
            pd.show();
        }
    }

    public void hidePd() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
