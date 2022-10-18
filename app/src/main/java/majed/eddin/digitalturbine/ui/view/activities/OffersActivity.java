package majed.eddin.digitalturbine.ui.view.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static majed.eddin.digitalturbine.data.consts.Params.APP_ID;
import static majed.eddin.digitalturbine.data.consts.Params.OFFERS;
import static majed.eddin.digitalturbine.data.consts.Params.Token;
import static majed.eddin.digitalturbine.data.consts.Params.USER_ID;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import majed.eddin.digitalturbine.data.model.modified.ErrorHandler;
import majed.eddin.digitalturbine.data.model.service.Offer;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;
import majed.eddin.digitalturbine.data.remote.ApiStatus;
import majed.eddin.digitalturbine.databinding.ActivityOffersBinding;
import majed.eddin.digitalturbine.ui.base.BaseActivity;
import majed.eddin.digitalturbine.ui.view.adapters.OffersAdapter;
import majed.eddin.digitalturbine.ui.viewModels.OffersVM;
import majed.eddin.digitalturbine.utils.recyclerUtils.OnEndless;

public class OffersActivity extends BaseActivity<OffersVM> {

    private OffersVM offersVM;
    private ActivityOffersBinding binding;
    private OffersAdapter adapter;

    private String applicationId;
    private String userId;
    private String token;
    private Result result;

    @Override
    protected Class<OffersVM> getViewModel() {
        return OffersVM.class;
    }

    @Override
    protected void viewModelInstance(OffersVM viewModel) {
        offersVM = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showLoading(this);
        viewInit();
        updateView();

        offersVM.getOffersIndexResult().observe(this, this::offersIndexResult);
    }

    private void offersIndexResult(ApiResponse<Result> apiResponse) {
        showLoadingFragment(false);
        handleApiResponse(apiResponse, view -> updateView());
        if (apiResponse.getApiStatus() == ApiStatus.OnSuccess) {
            this.result = apiResponse.getResponseObject();
            setResponseResult(apiResponse.getResponseObject().getOffers());
        }
    }


    private void setResponseResult(List<Offer> list) {
        if (list.size() != 0) {
            binding.recyclerOffers.setVisibility(VISIBLE);
            binding.layoutEmptyState.setVisibility(GONE);
            adapter.addAll(list);
        } else {
            binding.recyclerOffers.setVisibility(GONE);
            binding.layoutEmptyState.setVisibility(VISIBLE);
            adapter.clear();
        }
    }

    @Override
    public void setErrorHandler(ErrorHandler handler) {
    }

    @Override
    public void updateView() {
        result.setPages(0);
        adapter.clear();

        OnEndless scrollListener = new OnEndless((LinearLayoutManager) binding.recyclerOffers.getLayoutManager(), 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= result.getPages()) {
                    if (result.getPages() != 0)
                        loadPageData(current_page);
                }
            }
        };

        binding.recyclerOffers.addOnScrollListener(scrollListener);

        loadPageData(1);
    }

    private void loadPageData(int currentPage) {
        offersVM.getOffers(applicationId, userId, token, currentPage);
    }

    @Override
    public void viewInit() {
        applicationId = getIntent().getStringExtra(APP_ID);
        userId = getIntent().getStringExtra(USER_ID);
        token = getIntent().getStringExtra(Token);
        result = getIntent().getParcelableExtra(OFFERS);

        adapter = new OffersAdapter();
        binding.recyclerOffers.setAdapter(adapter);
        binding.recyclerOffers.setLayoutManager(new LinearLayoutManager(this));
    }
}