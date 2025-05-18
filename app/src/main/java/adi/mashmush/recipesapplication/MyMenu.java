package adi.mashmush.recipesapplication;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public abstract class MyMenu  extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
       User user = (User) getIntent().getSerializableExtra("user");
        int itemID = item.getItemId();
        if (itemID == R.id.back)
        finish();
        if (itemID == R.id.guide) {
            Intent intent = new Intent(MyMenu.this, Guide.class);
            intent.putExtra("user",user);
            startActivity(intent);

        }
        if (itemID == R.id.contactMe) {
            Intent intent = new Intent(MyMenu.this, ContactMe.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        if (itemID == R.id.aboutMe) {
            Intent intent = new Intent(MyMenu.this, AboutMe.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        if (itemID == R.id.backJunction) {
            Intent intent = new Intent(MyMenu.this, Junction.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        if (itemID == R.id.logOut) {
            Intent intent = new Intent(MyMenu.this, LogRegHello.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}