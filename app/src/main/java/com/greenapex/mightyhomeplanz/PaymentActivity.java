package com.greenapex.mightyhomeplanz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.dialog.ErrorDialogFragment;
import com.greenapex.mightyhomeplanz.entities.CreditCard;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.mightyhomeplanz.entities.PaymentForm;
import com.greenapex.mightyhomeplanz.entities.PaymentResponse;
import com.greenapex.mightyhomeplanz.entities.PaymentToken;
import com.greenapex.mightyhomeplanz.entities.TokenList;
import com.greenapex.webservice.AddPaymentWebservice;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PaymentActivity extends BaseFragmentActivity {

    /*
     * Change this to your publishable key.
     *
     * You can get your key here: https://manage.stripe.com/account/apikeys
     */
    public static final String PUBLISHABLE_KEY = "pk_test_VYZxHN7WQvevTdulOUV6ruYP";

    //    private ProgressDialogFragment progressFragment;
    private MileStoneModel selectedMilestone;
    private String jobID;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
//        progressFragment = ProgressDialogFragment.newInstance(R.string.processMessage);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.processMessage));
        Bundle params = getIntent().getExtras();
        if (params != null) {
            jobID = params.getString(Constants.JOBID);
            String selMileStone = params.getString(Constants.SELECTED_MILE_STONE);
            if (jobID != null && selMileStone != null) {
                MileStoneModel mileStoneModel = getGson().fromJson(selMileStone, MileStoneModel.class);
                selectedMilestone = mileStoneModel;
            } else {
                Toast.makeText(PaymentActivity.this, "Please Contact Administrator", Toast.LENGTH_SHORT).show();
                this.setResult(RESULT_CANCELED);
                finish();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Please Contact Administrator", Toast.LENGTH_SHORT).show();
            this.setResult(RESULT_CANCELED);
            finish();
        }
    }

    public void saveCreditCard(PaymentForm form) {

        Card card = new Card(
                form.getCardNumber(),
                form.getExpMonth(),
                form.getExpYear(),
                form.getCvc());
        card.setCurrency(form.getCurrency());

        boolean validation = card.validateCard();
        if (validation) {
            startProgress();
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(final Token token) {
                            getTokenList().addToList(token);

//                            Stripe stripe = new Stripe();
//                            stripe.setDefaultPublishableKey("pk_test_VYZxHN7WQvevTdulOUV6ruYP");
//                            finishProgress();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        int mAmount = (int) Double.parseDouble(selectedMilestone.getAmount());
                                        if (mAmount == 0) {
                                            handleError("We cannot make payment for zero amount.");
                                            finishProgress();
                                            return;
                                        }
                                        com.stripe.Stripe.apiKey = Constants.STRIPE_API_KEY;
                                        Map<String, Object> chargeParams = new HashMap<String, Object>();
                                        chargeParams.put("amount", String.valueOf(mAmount)); // amount in cents, again
                                        chargeParams.put("currency", "usd");
                                        chargeParams.put("source", token.getId());
                                        chargeParams.put("description", selectedMilestone.getTitle());
                                        Charge charge = Charge.create(chargeParams);
                                        charge.getStatus();
                                        if (charge.getStatus().equalsIgnoreCase("succeeded")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Call Add Payment Webservice
                                                    PaymentToken paymentToken = new PaymentToken();
                                                    Card cCard = token.getCard();
                                                    CreditCard ccCard = new CreditCard();
                                                    ccCard.setName(cCard.getName());
                                                    ccCard.setLast4(cCard.getLast4());
                                                    ccCard.setCountry(cCard.getCountry());
                                                    ccCard.setCvc_check(cCard.getCVC());
                                                    ccCard.setExp_month(String.valueOf(cCard.getExpMonth()));
                                                    ccCard.setExp_year(String.valueOf(cCard.getExpYear()));
                                                    ccCard.setBrand(cCard.getType());

                                                    paymentToken.setCard(ccCard);
                                                    paymentToken.setId(token.getId());
                                                    paymentToken.setCreated(String.valueOf(token.getCreated().getTime()));
                                                    if (token.getUsed())
                                                        paymentToken.setUsed("1");
                                                    else
                                                        paymentToken.setUsed("0");

                                                    UpdatePaymentStatusOnServer(paymentToken);
                                                    Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    } catch (AuthenticationException e) {
                                        e.printStackTrace();
                                    } catch (InvalidRequestException e) {
                                        e.printStackTrace();
                                    } catch (APIConnectionException e) {
                                        e.printStackTrace();
                                    } catch (CardException e) {
                                        e.printStackTrace();
                                    } catch (APIException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();


                        }

                        public void onError(Exception error) {
                            handleError(error.getLocalizedMessage());
                            finishProgress();
                        }
                    });
        } else if (!card.validateNumber()) {
            handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            handleError("The CVC code that you entered is invalid");
        } else {
            handleError("The card details that you entered are invalid");
        }
    }

    private void UpdatePaymentStatusOnServer(PaymentToken token) {
        AddPaymentWebservice addPaymentWebservice = new AddPaymentWebservice(new AddPaymentWebservice.AddPaymentWebserviceHandler() {
            @Override
            public void addPaymentWebserviceStart() {
                startProgress();
            }

            @Override
            public void addPaymentWebserviceSucessful(String response, String message) {
                setResult(RESULT_OK);
                finishProgress();
                finish();
            }

            @Override
            public void addPaymentWebserviceFailedWithMessage(String message) {
                handleError(message);
                finishProgress();
            }
        }, this);
        try {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setToken(token);
            paymentResponse.setUserID(getUserGson().getUserID());
            paymentResponse.setMilestoneID(selectedMilestone.getMilestoneID());
            paymentResponse.setAmount(selectedMilestone.getAmount());
            paymentResponse.setJobID(jobID);
            paymentResponse.setMessage("payment_successful");
            JSONObject params = new JSONObject(paymentResponse.toString());
            addPaymentWebservice.callService(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startProgress() {
//        progressFragment.show(getSupportFragmentManager(), "progress");
        progressDialog.show();
    }

    private void finishProgress() {
//        progressFragment.dismiss();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
        fragment.show(getSupportFragmentManager(), "error");
    }

    private TokenList getTokenList() {
        return (TokenList) (getSupportFragmentManager().findFragmentById(R.id.token_list));
    }
}
