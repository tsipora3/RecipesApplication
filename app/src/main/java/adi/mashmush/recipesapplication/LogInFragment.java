package adi.mashmush.recipesapplication;

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


public class LogInFragment extends Fragment {
    EditText etNameL, etPassL;
    ImageButton bLogInStart;
    HelperDB helperDB;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in_fragmant, container, false);
        initcomp(view);
        bLogInStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loGo();
            }

        });
        return view;

    }
    private void loGo() {
        String stName=etNameL.getText().toString();
        String stPass=etPassL.getText().toString();
        user=helperDB.isUserFound(stName,stPass);
        if (user!=null)
        {
            Toast.makeText(getContext(), "Welcome " + user.getUserName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), Junction.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
        }
        if (stName.equals(""))  {
            Toast.makeText(getContext(), "Empty field, please enter name!", Toast.LENGTH_LONG).show();
            etNameL.setBackgroundColor(Color.RED);
            return;
        }
        String stPassword=etPassL.getText().toString();
        if (stPassword.equals(""))  {
            Toast.makeText(getContext(), "Empty field, please enter password!", Toast.LENGTH_LONG).show();
            etPassL.setBackgroundColor(Color.RED);
            return;
        }
    }



    private void initcomp(View view) {
        etNameL = view.findViewById(R.id.etNameL);
        etPassL = view.findViewById(R.id.etPassL);
        bLogInStart = view.findViewById(R.id.bLogInStart);
        helperDB = new HelperDB(getContext());


    }
}