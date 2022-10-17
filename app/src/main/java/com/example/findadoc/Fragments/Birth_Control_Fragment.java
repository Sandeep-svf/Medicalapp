package com.example.findadoc.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.findadoc.R;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;



public class Birth_Control_Fragment extends Fragment  {


    TextView date_pick,end_date_pick;
    TextView time_pick;
    RelativeLayout date_layout,time_layout;
    int RC_SIGN_IN=2851;
    GoogleSignInAccount acct;
    GoogleSignInClient mGoogleSignInClient;
    String time_;


    public static final String[] SCOPES = {CalendarScopes.CALENDAR};
    SharedPreferences sharedPreferences;
    String user_email="";
    Button compute_btn;
    private GoogleAccountCredential mCredentialResultsFromApi;
    private String str_start_date,str_end_date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_birth__control_, container, false);
        time_pick=v.findViewById(R.id.time_pick);
        date_pick=v.findViewById(R.id.date_pick);
        date_layout=v.findViewById(R.id.date_layout);
        time_layout=v.findViewById(R.id.time_layout);
        compute_btn=v.findViewById(R.id.compute_btn);
        end_date_pick=v.findViewById(R.id.end_date_pick);
        date_pick.setInputType(InputType.TYPE_NULL);///hide keyboard after click on  EditText
        date_pick.requestFocus();
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_email=sharedPreferences.getString("user_email","");


        date_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                set_Date(date_pick);
            }
        });
        end_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_Date(end_date_pick);
            }
        });
        compute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_date = date_pick.getText().toString();
                String end_Date = end_date_pick.getText().toString();
                 time_ = time_pick.getText().toString();
                SimpleDateFormat pickupDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date date2=new Date();
                try {
                    date=new SimpleDateFormat("dd MMM yyyy").parse(start_date);
                    date2=new SimpleDateFormat("dd MMM yyyy").parse(end_Date);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sft2 = new SimpleDateFormat("yyyy-MM-dd");
                str_start_date=sft2.format(date);
                str_end_date=sft2.format(date2);
//                Toast.makeText(getActivity(), time_, Toast.LENGTH_SHORT).show();

                if (start_date.equals("")) {
                    date_pick.requestFocus();
                    date_pick.setError(getResources().getString(R.string.schosse_medicne));

                    Date pickupdate = null;

                } else if (end_Date.equals("")){
                    end_date_pick.requestFocus();
                    end_date_pick.setError(getResources().getString(R.string.end_date_medicie));
                }
                else if(time_.equals(""))
                {  time_pick.requestFocus();
                    time_pick.setError(getResources().getString(R.string.time_taken));

                }
                else
                {
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
                    if(acct!=null)
                    {
                        if(check_data())
                        {
                            new MakeRequestTask(mCredentialResultsFromApi).execute();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    GoogleAccountCredential mCredentialResultsFromApi = GoogleAccountCredential.usingOAuth2(getActivity(), Arrays.asList(SCOPES))
                                            .setSelectedAccountName(acct.getEmail())
                                            .setBackOff(new ExponentialBackOff());

                                    NetHttpTransport.Builder transport = new NetHttpTransport.Builder();
                                    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                                    com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                                            transport.build(), jsonFactory, mCredentialResultsFromApi)
                                            .setApplicationName(getActivity().getResources().getString(R.string.app_name))
                                            .build();
                                    Event event = new Event()
                                            .setSummary(getResources().getString(R.string.med))
                                            .setLocation("Noida, India 201301")
                                            .setDescription(getResources().getString(R.string.take_med));

                                    DateTime startDateTime = new DateTime(str_start_date+"T"+time_+"+05:30");
                                    EventDateTime start = new EventDateTime()
                                            .setDateTime(startDateTime)
                                            .setTimeZone("Asia/Kolkata");
                                    event.setStart(start);

                                    DateTime endDateTime = new DateTime(str_end_date+"T"+time_+"+05:30");
                                    EventDateTime end = new EventDateTime()
                                            .setDateTime(endDateTime)
                                            .setTimeZone("Asia/Kolkata");
                                    event.setEnd(end);

                                    String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
                                    event.setRecurrence(Arrays.asList(recurrence));

       /* EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("lpage@example.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));*/

                                    EventReminder[] reminderOverrides = new EventReminder[] {
                                            new EventReminder().setMethod("email").setMinutes(24 * 60),
                                            new EventReminder().setMethod("popup").setMinutes(1),
                                    };
                                    Event.Reminders reminders = new Event.Reminders()
                                            .setUseDefault(false)
                                            .setOverrides(Arrays.asList(reminderOverrides));
                                    event.setReminders(reminders);

                                    String calendarId = "primary";
                                    try {
                                        Event events = service.events().insert(calendarId, event).execute();
                                       Toast.makeText(getActivity(), getResources().getString(R.string.add_med_successfully),Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("DATA",e.getMessage()+"sam");
//                                    startActivityForResult(new Intent(getContext(), UserRecoverableAuthIOException.class), 205);
                                    }
                                }
                            }).start();
                            Toast.makeText(getActivity(), getResources().getString(R.string.add_med_successfully),Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {

                        // Add google Sign in code here
                        openGoogleLogin();
                       // Toast.makeText(getActivity(), getResources().getString(R.string.log),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int second=mcurrentTime.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date date = Calendar.getInstance().getTime();

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
                        String output = sdf.format(date).toString();
                        String out2 = String.format("%02d:%02d", selectedHour, selectedMinute);

//                        time_pick.setText(selectedHour + ":" + selectedMinute+":00");
                        time_pick.setText(out2+":00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.select_time));
                mTimePicker.show();
            }
        });
        return v;
    }

    private void openGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("code",requestCode+":"+RC_SIGN_IN);
        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null)
            {

            }
            //Login_API();

            // Google even code should be here ....
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if(acct!=null)
            {
                if(check_data())
                {
                    new MakeRequestTask(mCredentialResultsFromApi).execute();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GoogleAccountCredential mCredentialResultsFromApi = GoogleAccountCredential.usingOAuth2(getActivity(), Arrays.asList(SCOPES))
                                    .setSelectedAccountName(acct.getEmail())
                                    .setBackOff(new ExponentialBackOff());

                            NetHttpTransport.Builder transport = new NetHttpTransport.Builder();
                            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                            com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                                    transport.build(), jsonFactory, mCredentialResultsFromApi)
                                    .setApplicationName(getActivity().getResources().getString(R.string.app_name))
                                    .build();
                            Event event = new Event()
                                    .setSummary(getResources().getString(R.string.med))
                                    .setLocation("Noida, India 201301")
                                    .setDescription(getResources().getString(R.string.take_med));

                            DateTime startDateTime = new DateTime(str_start_date+"T"+time_+"+05:30");
                            EventDateTime start = new EventDateTime()
                                    .setDateTime(startDateTime)
                                    .setTimeZone("Asia/Kolkata");
                            event.setStart(start);

                            DateTime endDateTime = new DateTime(str_end_date+"T"+time_+"+05:30");
                            EventDateTime end = new EventDateTime()
                                    .setDateTime(endDateTime)
                                    .setTimeZone("Asia/Kolkata");
                            event.setEnd(end);

                            String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
                            event.setRecurrence(Arrays.asList(recurrence));

       /* EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("lpage@example.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));*/

                            EventReminder[] reminderOverrides = new EventReminder[] {
                                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                                    new EventReminder().setMethod("popup").setMinutes(1),
                            };
                            Event.Reminders reminders = new Event.Reminders()
                                    .setUseDefault(false)
                                    .setOverrides(Arrays.asList(reminderOverrides));
                            event.setReminders(reminders);

                            String calendarId = "primary";
                            try {
                                Event events = service.events().insert(calendarId, event).execute();
                                Toast.makeText(getActivity(), getResources().getString(R.string.add_med_successfully),Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("DATA",e.getMessage()+"sam");
//                                    startActivityForResult(new Intent(getContext(), UserRecoverableAuthIOException.class), 205);
                            }
                        }
                    }).start();
                    Toast.makeText(getActivity(), getResources().getString(R.string.add_med_successfully),Toast.LENGTH_LONG).show();

                }
            }


        }
        catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("Google Sign in error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getActivity(),"failed",Toast.LENGTH_SHORT).show();
        }

    }

    private void set_Date(TextView end_date_pick) {
        final Calendar calendar = Calendar.getInstance();
        int yy = 1997;
        int mm = 10;
        int dd = 20;

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
//                         String date1 = String.valueOf(dayOfMonth)  + "-" + String.valueOf(monthOfYear+1) + "-" +String.valueOf(year) ;
//                          et_Date.setText(date1);
                int month=monthOfYear+1;

                if(dayOfMonth<10 && month<10)
                {
                    String  date_picks=year + "-" +"0"+ (monthOfYear + 1) + "-" + "0"+dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" +"0"+ (monthOfYear + 1) + "-" + "0"+dayOfMonth;
                }
                else if(month<10 && dayOfMonth>10)
                {
//                            date_pick.setText(year + "-" +"0"+ (monthOfYear + 1) + "-" + dayOfMonth);
                    String  date_picks=year + "-" +"0"+ (monthOfYear + 1) + "-" + dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" +"0"+ (monthOfYear + 1) + "-" + "0"+dayOfMonth;

                }
                else if (dayOfMonth>=10 && month>=10)
                {
//                            date_pick.setText( year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth);
                    String date_picks= year + "-" + (monthOfYear + 1) + "-" +  dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" +"0"+ (monthOfYear + 1) + "-" + "0"+dayOfMonth;

                }
                else if (dayOfMonth>=10 && month<=10)
                {
//                            date_pick.setText( year + "-" +"0" + (monthOfYear + 1) + "-" +  dayOfMonth);
                    String  date_picks= year + "-" +"0" + (monthOfYear + 1) + "-" +  dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" +"0"+ (monthOfYear + 1) + "-" + "0"+dayOfMonth;

                }
                else if (dayOfMonth<10 && month>=10)
                {
//                            date_pick.setText(year + "-" +(monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    String  date_picks=year + "-" +(monthOfYear + 1) + "-" + "0" + dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" +(monthOfYear + 1) + "-" + "0" + dayOfMonth;

                }
                else
                {
//                            date_pick.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    String  date_picks=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    setDatze(date_picks,end_date_pick);
//                            selected_age=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                }

            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis());

        datePicker.show();
    }

    private boolean check_data() {
        try {

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            String str_person_email = acct.getEmail();
            
            mCredentialResultsFromApi = GoogleAccountCredential.usingOAuth2(
                    getActivity(), Arrays.asList(SCOPES))
                    .setSelectedAccountName(str_person_email)
                    .setBackOff(new ExponentialBackOff());

            boolean results = getResultsFromApi();

            Log.e("DATA",results+"");
            if (results) {

                return true;
            }
        } catch (Exception e) {
            Log.e("DATA",e.getMessage()+"");
        }
        return false;
    }
    

    private void setDatze(String date_picks, TextView end_date_pick) {
        SimpleDateFormat pickupDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date pickupdate = null;
        try {
            pickupdate = pickupDateFormat.parse(date_picks);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pickupDateFormat = new SimpleDateFormat("dd MMM YYYY");
        }
        String strPickupDate = pickupDateFormat.format(pickupdate);
        end_date_pick.setText(strPickupDate);
    }

    public boolean getResultsFromApi() {
        boolean isGooglePlayServicesAvailable = isGooglePlayServicesAvailable();
        boolean SelectedAccountName = mCredentialResultsFromApi.getSelectedAccountName() == null;
        boolean isDeviceOnline = isDeviceOnline();

        if (!isGooglePlayServicesAvailable) {
            Log.e("DATA","Pis");
            acquireGooglePlayServices();
        } else if (SelectedAccountName) {
            if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.GET_ACCOUNTS)) {
                Log.e("DATA","Qis");
                // Start a dialog  from which the user can choose an account
                startActivityForResult(mCredentialResultsFromApi.newChooseAccountIntent(), 201);
            } else {
                Log.e("DATA","Ris");
                // Request the GET_ACCOUNTS permission via a user dialog
                EasyPermissions.requestPermissions(this, "This is Demo", 202, Manifest.permission.GET_ACCOUNTS);
            }
        } else if (!isDeviceOnline) {
            Log.e("DATA","Sis");
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(getString(R.string.internet_connection));
            alertBuilder.setMessage(getString(R.string.internet_connection));
            alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            Log.e("DATA","Tis");
            return true;
        }
        return false;
    }
    private void acquireGooglePlayServices() {
        Log.e("DATA","Uis");
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }
    public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        Log.e("DATA","Vis");
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(getActivity(), connectionStatusCode, 203);
        dialog.show();
    }
    private boolean isGooglePlayServicesAvailable() {
        Log.e("DATA","Wis");
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private boolean isDeviceOnline() {
        Log.e("DATA","Xis");
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private final ProgressDialog progressDialog;
        private com.google.api.services.calendar.Calendar mService;
        private Exception mLastError = null;
        private String pageToken = "";

        public MakeRequestTask(GoogleAccountCredential credential) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setMessage(getString(R.string.load));
            progressDialog.getWindow().setGravity(Gravity.CENTER);

            WindowManager.LayoutParams WMLP = progressDialog.getWindow().getAttributes();
            WMLP.x = 50;
            WMLP.y = 30;
            progressDialog.getWindow().setAttributes(WMLP);
            if (!getActivity().isFinishing()) {
                progressDialog.show();
            }

            NetHttpTransport.Builder transport = new NetHttpTransport.Builder();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport.build(), jsonFactory, credential)
                    .setApplicationName(getContext().getResources().getString(R.string.app_name))
                    .build();


        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Log.e("DATA","DEE");
                // List the next 10 events from the primary calendar.

                DateTime now = new DateTime(System.currentTimeMillis());
                List<String> eventStrings = new ArrayList<String>();
                Events events = mService.events().list("primary")
                        .setMaxResults(20)
                        .setTimeMin(now)
                        .setSingleEvents(true)
                        .execute();
                for (Event s:events.getItems()
                     ) {
                    Log.e("DATA",s.getSummary());

                }
                //CalendarActivity.this.all_events_items = mService.events().list("primary").execute().getItems();
                return eventStrings;
            } catch (Exception e) {
                Log.e("DATA","FEE"+e.getMessage());
                mLastError = e;
                cancel(true);
                return null;
            }
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<String> output) {
            progressDialog.dismiss();


        }

        @Override
        protected void onCancelled() {
            progressDialog.dismiss();

            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    Log.e("DATA","AEE");
                    showGooglePlayServicesAvailabilityErrorDialog(((GooglePlayServicesAvailabilityIOException) mLastError).getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    Log.e("DATA","WEE");
                    startActivityForResult(((UserRecoverableAuthIOException) mLastError).getIntent(), 301);
                } else {
                    Log.e("DATA",mLastError+"BEE");
                }
            } else {
                Log.e("DATA",mLastError+"CEE");
            }
        }

    }

}