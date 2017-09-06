package ve.needforock.contactshare.views.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import ve.needforock.contactshare.R;
import ve.needforock.contactshare.data.Queries;
import ve.needforock.contactshare.models.Contact;
import ve.needforock.contactshare.views.addContact.AddContactActivity;
import ve.needforock.contactshare.views.details.DetailsActivity;

public class MainActivity extends AppCompatActivity implements ContactListener {

    public static final String CONTACT = "ve.needforock.contactshare.views.main.KEY.CONTACT";
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle("Mis Contactos");
        //new Queries().Contacts();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();


        recyclerView = (RecyclerView) findViewById(R.id.contactRv);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference ref = new Queries().Contacts();
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        adapter = new ContactAdapter(this,ref);

        recyclerView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void clicked(Contact contact) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(CONTACT, contact);
        Log.d("foto", contact.getPhoto());

        startActivity(intent);


    }

    @Override
    public void dataChange() {
        progressDialog.dismiss();
    }


}
