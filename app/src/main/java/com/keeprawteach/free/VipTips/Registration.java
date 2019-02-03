package com.keeprawteach.free.VipTips;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keeprawteach.free.Model.UserList;
import com.keeprawteach.free.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registration extends AppCompatActivity {
    FloatingActionButton fab;
    CardView cvAdd;
    Button Daily, Weekly, Monthly;
    String phone, amount;
    EditText PhoneNumber, AmountSelected;
    SweetAlertDialog sweetAlertDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Daily = (Button) findViewById(R.id.aaaa);
        Weekly = (Button) findViewById(R.id.bbbb);
        Monthly = (Button) findViewById(R.id.cccc);

        PhoneNumber = (EditText) findViewById(R.id.phone);
        AmountSelected = (EditText) findViewById(R.id.amount);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        sweetAlertDialog = new SweetAlertDialog(Registration.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Connecting");
        sweetAlertDialog.setContentText("Please wait......");
        sweetAlertDialog.setCancelable(false);


        cvAdd = (CardView) findViewById(R.id.cv_add);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                animateRevealClose();

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_signup);
                Registration.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void startMpesa(View view) {
        phone = PhoneNumber.getText().toString();
        amount = AmountSelected.getText().toString();
        if (amount.isEmpty()) {
            Toast.makeText(this, "Select your package", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            PhoneNumber.setError("Enter Phone Number");
        } else {
            seeUserinFirebase(phone, amount);
        }
    }

    private void seeUserinFirebase(final String phone, final String amount) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        final String a = dateFormat.format(date);
        Random random = new Random();
        final int b = random.nextInt(10000) + 158;
        sweetAlertDialog.show();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/All Users");
        reference.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                        messageSnapshot.getRef().child("date").setValue(a);
                        messageSnapshot.getRef().child("password").setValue("J" + b + "K");
                        messageSnapshot.getRef().child("amount").setValue(amount);
                        messageSnapshot.getRef().child("status").setValue("Pending");

                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Transaction started");
                        sweetAlertDialog.setContentText("Paste the Till Number and Enter your selected amount");
                        sweetAlertDialog.setConfirmButton("Proceed", new SweetAlertDialog.OnSweetClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                openMpesa();
                                sweetAlertDialog.dismissWithAnimation();
                                Registration.this.onBackPressed();
                            }
                        });

                    }
                } else {
                    addUsertoFirebase(phone, amount);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                sweetAlertDialog.dismissWithAnimation();
                Toast.makeText(Registration.this, "Error..!!" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void addUsertoFirebase(String phone, String amount) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String a = dateFormat.format(date);
        Random random = new Random();
        int b = random.nextInt(10000) + 158;
        UserList userList = new UserList(phone, "J" + b + "K", a, amount,"Pending");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/All Users");
        String jeff = reference.push().getKey();
        reference.child(jeff).setValue(userList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("Transaction started");
                sweetAlertDialog.setContentText("Paste the Till Number and Enter your selected amount");
                sweetAlertDialog.setConfirmButton("Proceed", new SweetAlertDialog.OnSweetClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        openMpesa();
                        sweetAlertDialog.dismissWithAnimation();
                        Registration.this.onBackPressed();
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Registration.this, "Error..!!" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                sweetAlertDialog.dismiss();

            }
        });

    }

    private void openMpesa() {
        /*copy the number and open stk*/

        String share = "272950";

        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if (sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(share);
            reloadMpesa();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", share);
            clipboard.setPrimaryClip(clip);
            reloadMpesa();
        }
    }

    private void reloadMpesa() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.stk");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "SIM Toolkit have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void oneday(View view) {
        AmountSelected.setText("50");
        Daily.setBackgroundResource(R.drawable.freebutclicked);
        Weekly.setBackgroundResource(R.drawable.freebut);
        Monthly.setBackgroundResource(R.drawable.freebut);
    }

    public void oneweek(View view) {
        AmountSelected.setText("300");
        Daily.setBackgroundResource(R.drawable.freebut);
        Weekly.setBackgroundResource(R.drawable.freebutclicked);
        Monthly.setBackgroundResource(R.drawable.freebut);
    }

    public void onemonth(View view) {
        AmountSelected.setText("800");
        Daily.setBackgroundResource(R.drawable.freebut);
        Weekly.setBackgroundResource(R.drawable.freebut);
        Monthly.setBackgroundResource(R.drawable.freebutclicked);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

}
