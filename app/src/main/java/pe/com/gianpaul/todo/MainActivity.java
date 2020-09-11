package pe.com.gianpaul.todo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pe.com.gianpaul.todo.ui.completgroup.CompletingGroupFragment;
import pe.com.gianpaul.todo.ui.outstandgroup.OutstandingGroupFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment = new OutstandingGroupFragment();
        switchFragment(fragment);
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.outstandItem:
                    fragment = new OutstandingGroupFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.completItem:
                    fragment = new CompletingGroupFragment();
                    switchFragment(fragment);
                    return true;
            }
            return true;
        }
    };

    public void message(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}