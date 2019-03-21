package com.example.appganaderosv1;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.Fragments.AppointmentFragment;
import com.example.appganaderosv1.Fragments.HomeFragment;
import com.example.appganaderosv1.Fragments.PurchasesFragment;
import com.example.appganaderosv1.Fragments.SalesFragment;

import static com.example.appganaderosv1.MainActivity.administrator;
import static com.example.appganaderosv1.MainActivity.userName;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView navUsername;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        //Assign user name to the drawer
        navUsername = (TextView) headerView.findViewById(R.id.userName);
        navUsername.setText(userName);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_purchases:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PurchasesFragment()).commit();
                break;
            case R.id.nav_sales:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalesFragment()).commit();
                break;
            case R.id.nav_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AppointmentFragment()).commit();
                break;
            case R.id.nav_logout:
                super.onBackPressed();
                break;
            case R.id.nav_garbage:
                if (administrator == false) {
                    Toast.makeText(getApplicationContext(), "Solo Administrador", Toast.LENGTH_LONG).show();
                }
                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void onClick(View view) {
        AppointmentFragment AF = new AppointmentFragment();
        AF.onClick(view);
    }
}
