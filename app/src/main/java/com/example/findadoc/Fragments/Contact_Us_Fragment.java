package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Web_View_Activity;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Contact_Us_Fragment extends Fragment {

    EditText full_name_edit,email_edit,mobile_no,message_text;
    CheckBox checkbox_contact;
    SharedPreferences sharedPreferences;
    String language="en";
    String full_nam_text="",email_edit_text="",mobile_text="",message_text_edit="";
    TextView accept_terms;
    Button  send_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_contact__us_, container, false);
        full_name_edit=v.findViewById(R.id.full_name_edit);
        email_edit=v.findViewById(R.id.email_edit_text);
        mobile_no=v.findViewById(R.id.mobile_no);
        message_text=v.findViewById(R.id.message_text);
        send_btn=v.findViewById(R.id.send_btn);
        checkbox_contact=v.findViewById(R.id.checkbox_contact);
            accept_terms=v.findViewById(R.id.accept_terms);
        sharedPreferences= getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
            }
        });
        checkbox_contact.setSelected(true);
            accept_terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(getActivity(), Web_View_Activity.class);
                    mIntent.putExtra("key","1");
                    mIntent.putExtra("isTermsAndCondition", true);
                    startActivity(mIntent);
                }
            });
      /*  SpannableString SpanString = new SpannableString(
                getString(R.string.i_have_read_and_i_accept_terms_of_use));
        ClickableSpan teremsAndCondition = new ClickableSpan() {
            @Override
            public void onClick(View textView) {


                Intent mIntent = new Intent(getActivity(), Web_View_Activity.class);
                mIntent.putExtra("key","1");
                mIntent.putExtra("isTermsAndCondition", true);
                startActivity(mIntent);
            }
        };

        // Character starting from 32 - 45 is Terms and condition.
        // Character starting from 49 - 63 is privacy policy.

        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {



            }
        };

        SpanString.setSpan(teremsAndCondition, 13, 31, 0);
        SpanString.setSpan(privacy, 40, 57, 0);
        SpanString.setSpan(new ForegroundColorSpan(Color.RED), 13, 31, 0);
        SpanString.setSpan(new ForegroundColorSpan(Color.RED), 40, 57, 0);
        SpanString.setSpan(new UnderlineSpan(), 13, 31, 0);
        SpanString.setSpan(new UnderlineSpan(), 40, 57, 0);

        accept_terms.setMovementMethod(LinkMovementMethod.getInstance());
        accept_terms.setText(SpanString, TextView.BufferType.SPANNABLE);
        accept_terms.setSelected(true);
*/
        return v;
    }

    private void Validation() {
        full_nam_text=full_name_edit.getText().toString();
        email_edit_text=email_edit.getText().toString();
        mobile_text=mobile_no.getText().toString();
        message_text_edit=message_text.getText().toString();

        if(full_nam_text.equals(""))
        {
            full_name_edit.setError(getResources().getString(R.string.enter_name));
            full_name_edit.requestFocus();

        }
        else if(email_edit_text.equals(""))
        {
            email_edit.setError(getResources().getString(R.string.enter_email));
            email_edit.requestFocus();
        }
        else if(!email_edit_text.matches("[A-Z0-9a-z._%+-]{2,}+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"))
        {
            email_edit.setError(getResources().getString(R.string.email_error));
            email_edit.requestFocus();
        }
        else if(mobile_text.equals(""))
        {
            mobile_no.setError(getResources().getString(R.string.mobile_no_enter));
            mobile_no.requestFocus();
        }
        else if(message_text_edit.equals(""))
        {
            message_text.setError(getResources().getString(R.string.enter_msg));
            message_text.requestFocus();
        }
        else if(!checkbox_contact.isChecked())
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.checked_Data), Toast.LENGTH_SHORT).show();

        }

        else {
            get_my_space_contact_API();

        }
    }

    private void get_my_space_contact_API() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Advance_Search_Model> call = Api_client.getClient().my_space_contact("7c538f486ea815187d12c54c3646d71e",language,full_nam_text,email_edit_text,mobile_text,message_text_edit,"1");
        call.enqueue(new Callback<Advance_Search_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Advance_Search_Model> call, Response<Advance_Search_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))
                        {
                            full_name_edit.setText("");
                            email_edit.setText("");
                            mobile_no.setText("");
                            message_text.setText("");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Advance_Search_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
}