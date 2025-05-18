package adi.mashmush.recipesapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LogRegHello extends MyMenu {
    Context context;
    LogInFragment logInFragment;
    RegisterFragment registerFragment;
    LogInRegisterFragment logInRegister;
    ImageButton bLogIn, bReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg_hello);
        initcomponents();
        bLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogInFragment logInFragment = new LogInFragment();
                showFragment(logInFragment);

            }
        });
        bReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                showFragment(registerFragment);
            }
        });
    }
    public void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutLogReg, fragment);
        fragmentTransaction.commit();
    }
    private void initcomponents() {
        context = getApplicationContext();
        bReg= findViewById(R.id.bReg);
        bLogIn = findViewById(R.id.bLogIn);
        logInRegister = new LogInRegisterFragment();
        showFragment(logInRegister);
    }
}