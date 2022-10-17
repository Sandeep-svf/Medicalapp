package com.example.findadoc.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.Model.Comment_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.My_View_Holder> {
    Context context;
    List<Comment_Response> comment_models;
    String dr_id;
    SharedPreferences sharedPreferences;
    String person_id="";
    private String language="en";

    public Comment_Adapter(Context context, List<Comment_Response> comment_models, String dr_idd) {
        this.context = context;
        this.comment_models = comment_models;
        this.dr_id=dr_idd;
    }


    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Comment_Adapter.My_View_Holder holder, int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");
        sharedPreferences = context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
        holder.name.setText(comment_models.get(position).getUserName());
        holder.comment_text_data.setText(comment_models.get(position).getComment());
        String comment_id= String.valueOf(comment_models.get(position).getCommentId());

        holder.menu_vert_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert_Dialog_Option(comment_id,position,dr_id,holder.menu_vert_icon);

            }
        });
    }

    private void Alert_Dialog_Option(String comment_id, int position, String dr_id, ImageView menu_vert_icon) {
//        Context wrapper=new ContextThemeWrapper(context,R.style.PopupMenu_style);
        PopupMenu popupMenu = new PopupMenu(context,menu_vert_icon);
        popupMenu.getMenuInflater().inflate(R.menu.delete_comment,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    // Handle the non group menu items here
                    

                    case R.id.delete:

                        Alert_Dialog_Delete(comment_id,position,dr_id);

                        return true;

                    default:
                        return false;
                }
            }
        });
        popupMenu.show();




/*
            final LayoutInflater inflater =LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.comment_edit, null);
        ImageView cancle_icon=view.findViewById(R.id.cancle_icon);
        RatingBar ratingBar=view.findViewById(R.id.ratingBar1);
        EditText edit_comment=view.findViewById(R.id.comment_edit);
        ImageView cmt_user_profile=view.findViewById(R.id.cmt_user_profile);
            Glide.with(context).load(image).into(cmt_user_profile);
            edit_comment.setText(comment);
            ratingBar.setRating(Float.parseFloat(rating));

            PopupWindow   popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
            popupWindow.setAnimationStyle(R.style.popup_window_animation);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 40, 60);
            cancle_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
*/

    }

    private void Alert_Dialog_Delete(String comment_id, int position, String dr_id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setCancelable(false);
        builder1.setMessage("Are you sure you want to Delete your Comment ?");
        builder1.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Delete_Commmt_API(comment_id,position);
                notifyDataSetChanged();
            }
        });

        builder1.setNegativeButton("Cancle", new DialogInterface.OnClickListener()
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

    private void Delete_Commmt_API(String comment_id, int position)         {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().comment_delete("7c538f486ea815187d12c54c3646d71e",comment_id,language);
        call.enqueue(new Callback<Fav_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Fav_Model> call, Response<Fav_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
                            removeItem(position);
                            notifyDataSetChanged();


                        } else {
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
            public void removeItem(int position)
            {
                try
                {
                    comment_models.remove(position);
                    notifyDataSetChanged();
                }
                catch (IndexOutOfBoundsException index)
                {
                    index.printStackTrace();
                }
                catch (Exception e)
                {e.printStackTrace();}
            }

            @Override
            public void onFailure(Call<Fav_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return comment_models.size();
    }

    public void setComments(List<Comment_Response> comments_list) {
        this.comment_models = comments_list;
        notifyDataSetChanged();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView name,comment_text_data;
        ImageView menu_vert_icon;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            comment_text_data=itemView.findViewById(R.id.comment_text_data);
            menu_vert_icon=itemView.findViewById(R.id.menu_vert_icon);
        }
    }
}
