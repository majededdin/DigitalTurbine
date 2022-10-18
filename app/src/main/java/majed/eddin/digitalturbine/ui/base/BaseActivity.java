package majed.eddin.digitalturbine.ui.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import am.networkconnectivity.NetworkConnectivity;
import majed.eddin.digitalturbine.R;
import majed.eddin.digitalturbine.data.model.modified.ErrorHandler;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;
import majed.eddin.digitalturbine.ui.view.fragments.LoadingFragment;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    private V viewModel;
    private RelativeLayout layout;
    private boolean secondRun = false;

    private ProgressBar progress_loading;
    private LinearLayout layout_networkStatus;
    private MaterialTextView txt_networkStatus;

    private final LoadingFragment loadingFragment = new LoadingFragment();

    protected abstract Class<V> getViewModel();

    protected abstract void viewModelInstance(V viewModel);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleNetworkResponse();
        viewModelInstance(initViewModel());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));// set status background white
        }

        super.onCreate(savedInstanceState);
    }

    public abstract void updateView();

    protected abstract void setErrorHandler(ErrorHandler handler);

    protected abstract void viewInit();

    @Override
    protected void onDestroy() {
        if (viewModel != null)
            viewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private V initViewModel() {
        if (getViewModel() != null)
            viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(getViewModel());
        return viewModel;
    }

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        CoordinatorLayout coordinatorLayout = layout.findViewById(R.id.containerBase);
        baseInit();

        coordinatorLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        super.setContentView(layout);
    }

    private void baseInit() {
        progress_loading = layout.findViewById(R.id.progress_loading);
        txt_networkStatus = layout.findViewById(R.id.txt_networkStatus);
        layout_networkStatus = layout.findViewById(R.id.layout_networkStatus);
        loadingFragment.setCancelable(false);
    }

    public void showLoadingFragment(boolean status) {
        try {
            if (!status && !loadingFragment.isDetached())
                loadingFragment.dismiss();
            else if (!loadingFragment.isAdded())
                loadingFragment.show(getSupportFragmentManager(), getClass().getName());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public View getCustomView() {
        return findViewById(android.R.id.content);
    }

    protected void showLoading(LifecycleOwner owner) {
        if (viewModel != null) {
            viewModel.showLoadingResponse().observe(owner, aBoolean -> {
                if (aBoolean == null)
                    return;

                showLoadingFragment(aBoolean);
            });
        }
    }

    public void showMessage(String message) {
        Snackbar.make(getCustomView(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    public void handleApiResponse(ApiResponse apiResponse, View.OnClickListener failureListener) {
        switch (apiResponse.getApiStatus()) {
            case OnError:
                if (apiResponse.getResponseObject() instanceof Result)
                    showMessage(((Result) apiResponse.getResponseObject()).getMessage());
                break;

            case OnBackEndError:
                showMessage(apiResponse.getMessage());
                Log.e("OnBackEndError ", apiResponse.getMessage());
                break;

            case OnFailure:
                showMessage(apiResponse.getMessage());
                Log.e("OnFailure ", apiResponse.getMessage());
                break;

            case OnTimeoutException:
                Snackbar.make(getCustomView(), getString(R.string.request_timeout_please_check_your_connection), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), failureListener).show();
                break;

            case OnConnectException:
            case OnUnknownHost:
                onNetworkConnectionChanged(NetworkConnectivity.NetworkStatus.OnLost);
                break;
        }
    }


    public void handleNetworkResponse() {
        new NetworkConnectivity(this).observe(this, this::onNetworkConnectionChanged);
    }


    private void onNetworkConnectionChanged(NetworkConnectivity.NetworkStatus status) {
        switch (status) {
            case OnConnected:
                if (secondRun) {
                    secondRun = false;
                    txt_networkStatus.setText(getString(R.string.connection_is_back));
                    progress_loading.setVisibility(View.GONE);
                    layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkConnected));

                    updateView();
                    new Handler().postDelayed(() -> layout_networkStatus.setVisibility(View.GONE), 2000);
                }
                break;

            case OnWaiting:
                secondRun = true;
                txt_networkStatus.setText(getString(R.string.waiting_for_connection));
                progress_loading.setVisibility(View.VISIBLE);
                layout_networkStatus.setVisibility(View.VISIBLE);
                layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkWaiting));
                break;

            case OnLost:
                secondRun = true;
                txt_networkStatus.setText(getString(R.string.connection_is_lost));
                progress_loading.setVisibility(View.GONE);
                layout_networkStatus.setVisibility(View.VISIBLE);
                layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkLost));
                break;
        }
    }

}