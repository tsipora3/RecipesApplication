package adi.mashmush.recipesapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class RegisterFragment extends Fragment {
    EditText etNameR, etPassR, etPhone, etEmailR;
    HelperDB helperDB;
    User user;
    ImageButton bRegStart;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initcomp(view);

        bRegStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {String stName=etNameR.getText().toString();
                    if (stName.equals(""))  {
                        Toast.makeText(getContext(), "Empty field, please enter name!", Toast.LENGTH_LONG).show();
                        etNameR.setBackgroundColor(Color.RED);
                        return;
                    }
                    String stPassword=etPassR.getText().toString();
                    if (stPassword.equals(""))  {
                        Toast.makeText(getContext(), "Empty field, please enter password!", Toast.LENGTH_LONG).show();
                        etPassR.setBackgroundColor(Color.RED);
                        return;
                    }
                    String stEmail=etEmailR.getText().toString();
                    if (stEmail.equals(""))  {
                        Toast.makeText(getContext(), "Empty field, please enter E-mail!", Toast.LENGTH_LONG).show();
                        etEmailR.setBackgroundColor(Color.RED);
                        return;
                    }
                    String stPhone=etPhone.getText().toString();
                    if (stPhone.equals(""))  {
                        Toast.makeText(getContext(), "Empty field, please enter phone number!", Toast.LENGTH_LONG).show();
                        etPhone.setBackgroundColor(Color.RED);
                        return;
                    }

                    user = new User();
                    user.setUserName(stName);
                    user.setEmail(stEmail);
                    user.setPassword(stPassword);
                    user.setPhone(stPhone);
                    createAlertDialog(user);



                }
            }



    });
        return view;
    }
    private void checkUserName(){
        String stName=etNameR.getText().toString();
       if(helperDB.isUserNameFound(stName)) {
       Toast.makeText(getContext(),"User name already exist", Toast.LENGTH_LONG).show();
           etNameR.setBackgroundColor(Color.RED);
         return;
       }
    }
    private void createAlertDialog(User user) {
        AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
        adb.setTitle("Please check your data.\nSave user?");
        adb.setMessage(user.getUserName()+"\n"+user.getPassword()+"\n"+user.getEmail()+"\n"+user.getPhone());
        adb.setCancelable(false);
        adb.setPositiveButton("Sounds Good", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                helperDB.insertUser(user);
                Intent intent = new Intent(getContext(), Junction.class);
                intent.putExtra("user",user);
                Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
        adb.setNegativeButton("Start again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etNameR.setText("");
                etPassR.setText("");
                etEmailR.setText("");
                etPhone.setText("");
            }
        });
        adb.create().show();
    }


    private void initcomp(View view) {
        etNameR = view.findViewById(R.id.etNameR);
        etPassR = view.findViewById(R.id.etPassR);
        etPhone = view.findViewById(R.id.etPhoneR);
        etEmailR = view.findViewById(R.id.etEmailR);
        bRegStart = view.findViewById(R.id.bRegStart);
        helperDB=new HelperDB(getContext());

    }
    }
