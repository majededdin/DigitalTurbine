package majed.eddin.digitalturbine.ui.view.activities;

import static majed.eddin.digitalturbine.data.consts.Params.APP_ID;
import static majed.eddin.digitalturbine.data.consts.Params.ERROR_INVALID_APPID;
import static majed.eddin.digitalturbine.data.consts.Params.ERROR_INVALID_UID;
import static majed.eddin.digitalturbine.data.consts.Params.OFFERS;
import static majed.eddin.digitalturbine.data.consts.Params.Token;
import static majed.eddin.digitalturbine.data.consts.Params.USER_ID;
import static majed.eddin.digitalturbine.utils.Utils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import majed.eddin.digitalturbine.R;
import majed.eddin.digitalturbine.data.model.modified.ErrorHandler;
import majed.eddin.digitalturbine.data.model.service.Result;
import majed.eddin.digitalturbine.data.remote.ApiResponse;
import majed.eddin.digitalturbine.data.remote.ApiStatus;
import majed.eddin.digitalturbine.databinding.ActivitySettingsBinding;
import majed.eddin.digitalturbine.ui.base.BaseActivity;
import majed.eddin.digitalturbine.ui.viewModels.OffersVM;

public class SettingsActivity extends BaseActivity<OffersVM> implements View.OnClickListener, TextWatcher {

    private OffersVM offersVM;
    private ActivitySettingsBinding binding;

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
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showLoading(this);
        viewInit();
        updateButtonStatus(false);
        updateView();

        offersVM.getFormState().observe(this, errorHandler -> {
            if (errorHandler == null)
                return;

            setErrorHandler(errorHandler);
            updateButtonStatus(errorHandler.isDataValid());
        });

        offersVM.getOffersIndexResult().observe(this, this::offersIndexResult);
    }


    private void updateButtonStatus(boolean status) {
        if (status) {
            binding.btnFetchOffers.setEnabled(true);
            binding.btnFetchOffers.setAlpha(1);
        } else {
            binding.btnFetchOffers.setEnabled(false);
            binding.btnFetchOffers.setAlpha(.5f);
        }
    }

    private void offersIndexResult(ApiResponse<Result> apiResponse) {
        handleApiResponse(apiResponse, v -> binding.btnFetchOffers.performClick());
        if (apiResponse.getApiStatus() == ApiStatus.OnSuccess) {
            if (apiResponse.getResponseObject().getCode().equals("OK")) {
                Intent intent = new Intent(this, OffersActivity.class);
                intent.putExtra(APP_ID, binding.inputApplicationId.getText().toString().trim());
                intent.putExtra(USER_ID, binding.inputUserId.getText().toString().trim());
                intent.putExtra(Token, binding.inputToken.getText().toString().trim());
                intent.putExtra(OFFERS, apiResponse.getResponseObject());
                startActivity(intent);
                finish();
            } else {
                showMessage(apiResponse.getResponseObject().getMessage());
            }
        }
    }

    @Override
    public void setErrorHandler(ErrorHandler handler) {
        if (handler.getKey() == null)
            return;

        switch (handler.getKey()) {
            case ERROR_INVALID_APPID:
                binding.inputApplicationId.setError(handler.getValue());
                break;
            case ERROR_INVALID_UID:
                binding.inputUserId.setError(handler.getValue());
                break;
            case Token:
                binding.inputToken.setError(handler.getValue());
                break;
            default:
                showMessage(handler.getValue());
                break;
        }
    }

    @Override
    public void updateView() {
    }

    @Override
    public void viewInit() {
        binding.inputApplicationId.addTextChangedListener(this);
        binding.inputUserId.addTextChangedListener(this);
        binding.inputToken.addTextChangedListener(this);

        binding.inputApplicationId.setText("1246");
        binding.inputUserId.setText("superman");
        binding.inputToken.setText("82085b8b7b31b3e80beefdc0430e2315f67cd3e1");

        binding.layoutSettings.setOnClickListener(this);
        binding.btnFetchOffers.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_settings:
                hideKeyboard(getCustomView());
                break;

            case R.id.btn_fetchOffers:
                offersVM.getOffers(binding.inputApplicationId.getText().toString().trim(),
                        binding.inputUserId.getText().toString().trim()
                        , binding.inputToken.getText().toString().trim()
                        , 1);
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }


    @Override
    public void afterTextChanged(Editable editable) {
        offersVM.validateInputForm(binding.inputApplicationId.getText().toString().trim(), binding.inputUserId.getText().toString().trim(),
                binding.inputToken.getText().toString().trim());
    }
}