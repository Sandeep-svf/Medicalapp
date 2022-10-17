package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.MainActivity;
import com.example.findadoc.Model.Count_Model;
import com.example.findadoc.Model.NotificationRS;
import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.My_View_Holder> {
    Context context;
    List<NotificationRS> notificationRS;

    SharedPreferences sharedPreferences;
    String user_id="";
    String languages;
    public Notification_Adapter(Context context, List<NotificationRS> notificationRS,String languages) {
        this.notificationRS=notificationRS;
        this.context = context;
        this.languages = languages;
    }



    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, @SuppressLint("RecyclerView") int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("person_id","");
    holder.title_name.setText(notificationRS.get(position).getNotiTitle());
    holder.message_date.setText(notificationRS.get(position).getNotiMessage());
    holder.date_o.setText(notificationRS.get(position).getCreatedAt());
    String notification_read_st= String.valueOf(notificationRS.get(position).getNotiReadStatus());
    if (notification_read_st.equals("0"))
    {
        holder.layout_click.setBackground(context.getResources().getDrawable(R.drawable.unread_noti_design));
    }
    else
    {
        holder.layout_click.setBackground(context.getResources().getDrawable(R.drawable.background_noti));
    }
    holder.cancle_icon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String noti_id= String.valueOf(notificationRS.get(position).getNotiId());
            AlertDialogLayout(noti_id,position);

        }
    });
    holder.layout_click.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String notification_id= String.valueOf(notificationRS.get(position).getNotiId());
            read_noti_API(notification_id,position,holder.layout_click);
        }
    });
    }

    private void read_noti_API(String notification_id, int position, LinearLayout layout_click) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Count_Model> call = Api_client.getClient().notification_read("7c538f486ea815187d12c54c3646d71e",
                user_id,
                notification_id);
        call.enqueue(new Callback<Count_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Count_Model> call, Response<Count_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            layout_click.setBackground(context.getResources().getDrawable(R.drawable.background_noti));
                            notificauion_coun();
                        }
                        else {

                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Count_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationRS.size();
    }
    private void AlertDialogLayout(String id, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setCancelable(false);
        builder1.setMessage(R.string.dete_single_noti);
        builder1.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                notification_delete_api(id,position);
                notifyDataSetChanged();


            }
        });

        builder1.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder1.create();
        dialog.show();
        dialog.setCancelable(false);
    }
    private void removeItem(int position) {
        try
        {
            notificationRS.remove(position);
            notifyDataSetChanged();
        }
        catch (IndexOutOfBoundsException index)
        {
            index.printStackTrace();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    private void notification_delete_api(String id, int position) {

        Log.e("languages",languages);

        Call<Pres_Model> call = Api_client.getClient().notification_delete("7c538f486ea815187d12c54c3646d71e",
                user_id,
                id,languages);
        call.enqueue(new Callback<Pres_Model>() {
            @Override
            public void onResponse(Call<Pres_Model> call, Response<Pres_Model> response) {

                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {





                            removeItem(position);
                            notificauion_coun();



                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();




                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Pres_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    private void notificauion_coun() {



        Call<Count_Model> call = Api_client.getClient().count_data("7c538f486ea815187d12c54c3646d71e",user_id);
        call.enqueue(new Callback<Count_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Count_Model> call, Response<Count_Model> response) {

                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            String count= String.valueOf(response.body().getData());
//                            Toast.makeText(getApplicationContext(), count+"A", Toast.LENGTH_LONG).show();
                            MainActivity.text_notification.setText(count);


                        }

                        else {

//                            Toast.makeText(getApplicationContext(), message+"B", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Count_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }
            }
        });


    }


    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView title_name,date_o,message_date;
        ImageView cancle_icon;
        LinearLayout layout_click;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            date_o=itemView.findViewById(R.id.date_o);
            title_name=itemView.findViewById(R.id.title_name);
            cancle_icon=itemView.findViewById(R.id.cancle_icon);
            message_date=itemView.findViewById(R.id.message_date);
            layout_click=itemView.findViewById(R.id.layout_click);
        }
    }
}
