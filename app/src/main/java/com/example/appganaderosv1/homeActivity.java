package com.example.appganaderosv1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.Fragments.AppointmentFragment;
import com.example.appganaderosv1.Fragments.HomeFragment;
import com.example.appganaderosv1.Fragments.PeopleFragment;
import com.example.appganaderosv1.Fragments.PersonalDataFragment;
import com.example.appganaderosv1.Fragments.PurchasesFragment;
import com.example.appganaderosv1.Fragments.SalesFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.appganaderosv1.MainActivity.userName;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView navUsername;

    private DrawerLayout drawer;

    ConexionSQLiteHelper conn;

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
            case R.id.nav_people:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PeopleFragment()).commit();
                break;
            case R.id.nav_personal_data:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalDataFragment()).commit();
                break;
            case R.id.nav_BackUp:
                try {

                    boolean backUp = Backups();

                    if (backUp) {
                        Toast.makeText(getApplicationContext(), "Respaldo Exitoso", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Respaldo Fallido", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_Import:
                try {
                    boolean importDb = restoreDB();

                    if (importDb) {
                        Toast.makeText(getApplicationContext(), "Importación Exitosa", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Importación Fallida", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_logout:
                Intent miIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(miIntent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void onClick(View view) {
        AppointmentFragment AF = new AppointmentFragment();
        AF.onClick(view);
    }

    public void newPurchase(View view) {
        PurchasesFragment PF = new PurchasesFragment();
        PF.newPurchase(view);
    }

    public void newSale(View view) {
        SalesFragment SF = new SalesFragment();
        SF.newSale(view);
    }

    public boolean Backups() throws IOException {
        //So if the folders don't exist, then the appp will create them.
        File folder = new File("/storage/emulated/0/Databases/AppGanaderos");

        if(!folder.exists()){
            folder.mkdirs();
        }

        String DB_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado";
        //String dbPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP";
        String dbPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado";

        String SHM_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado-shm";
        //String shmPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP-shm";
        String shmPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado-shm";

        String WAL_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado-wal";
        //String walPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP-wal";
        String walPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado-wal";

        File backUpDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);

        File backUpSHM = new File(shmPath);
        File oldSHM = new File(SHM_FILEPATH);

        File backUpWAL = new File(walPath);
        File oldWAL = new File(WAL_FILEPATH);

        if (backUpDb.exists()) {
            //If the database exists then I delete it and create a new one then I insert the data again
            SQLiteDatabase.deleteDatabase(new File(dbPath));
            backUpSHM.delete();
            backUpWAL.delete();

            if(!oldSHM.exists()){
                OutputStream  shm1 = new FileOutputStream(SHM_FILEPATH);
                shm1.flush();
                shm1.close();
            }
            if(!oldWAL.exists()){
                OutputStream wal1 = new FileOutputStream(WAL_FILEPATH);
                wal1.flush();
                wal1.close();
            }

            // Open the empty db as the output stream
            OutputStream db = new FileOutputStream(dbPath);
            OutputStream  shm = new FileOutputStream(shmPath);
            OutputStream wal = new FileOutputStream(walPath);

            backUpDb = new File(dbPath);
            backUpSHM = new File(shmPath);
            backUpWAL = new File(walPath);

            FileUtils.copyFile(new FileInputStream(oldDb), new FileOutputStream(backUpDb));
            FileUtils.copyFile(new FileInputStream(oldSHM), new FileOutputStream(backUpSHM));
            FileUtils.copyFile(new FileInputStream(oldWAL), new FileOutputStream(backUpWAL));

            db.flush();
            db.close();
            shm.flush();
            shm.close();
            wal.flush();
            wal.close();

            return true;

        }else if (!backUpDb.exists()){
            //If the database doesn't exists, then I'll just create a new one and insert the data
            // Open the empty db as the output stream
            OutputStream db = new FileOutputStream(dbPath);
            OutputStream  shm = new FileOutputStream(shmPath);
            OutputStream wal = new FileOutputStream(walPath);

            backUpDb = new File(dbPath);
            backUpSHM = new File(shmPath);
            backUpWAL = new File(walPath);

            FileUtils.copyFile(new FileInputStream(oldDb), new FileOutputStream(backUpDb));
            FileUtils.copyFile(new FileInputStream(oldSHM), new FileOutputStream(backUpSHM));
            FileUtils.copyFile(new FileInputStream(oldWAL), new FileOutputStream(backUpWAL));

            db.flush();
            db.close();
            shm.flush();
            shm.close();
            wal.flush();
            wal.close();

            return true;

        }

        return false;
    }

    public boolean restoreDB() throws IOException {

        File folder = new File("/storage/emulated/0/Databases/AppGanaderos");

        if(folder.exists()){

            String DB_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado";
            //String dbPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP";
            String dbPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado";

            String SHM_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado-shm";
            //String shmPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP-shm";
            String shmPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado-shm";

            String WAL_FILEPATH = "/data/data/com.example.appganaderosv1/databases/bd_ganado-wal";
            //String walPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado_BackUP-wal";
            String walPath = "/storage/emulated/0/Databases/AppGanaderos/bd_ganado-wal";

            File backUpDb = new File(dbPath);
            File oldDb = new File(DB_FILEPATH);

            File backUpSHM = new File(shmPath);
            File oldSHM = new File(SHM_FILEPATH);

            File backUpWAL = new File(walPath);
            File oldWAL = new File(WAL_FILEPATH);


            if (backUpDb.exists() && backUpSHM.exists() && backUpWAL.exists()) {

                SQLiteDatabase.deleteDatabase(new File(DB_FILEPATH));

                conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_ganado", null, 2);
                OutputStream shm = new FileOutputStream(SHM_FILEPATH);
                OutputStream wal = new FileOutputStream(WAL_FILEPATH);

                FileUtils.copyFile(new FileInputStream(backUpDb), new FileOutputStream(oldDb));
                FileUtils.copyFile(new FileInputStream(backUpSHM), new FileOutputStream(oldSHM));
                FileUtils.copyFile(new FileInputStream(backUpWAL), new FileOutputStream(oldWAL));

                conn.close();

                shm.flush();
                shm.close();
                wal.flush();
                wal.close();

                return true;
            }
        }
        return false;
    }

}
