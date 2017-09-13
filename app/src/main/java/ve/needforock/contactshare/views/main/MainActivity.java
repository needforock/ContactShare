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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

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
    private Query query;
    private DatabaseReference ref;
    private int filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Todos mis Contactos");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.contactRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);



        ref = new Queries().Contacts();
        query = ref.orderByChild("name");
        adapter = new ContactAdapter(this, query);
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
        startActivity(intent);
    }

    @Override
    public void dataChange() {
        progressDialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_family) {
            filter = 1;
            query = ref.orderByChild("group_name").startAt("Familia");
            adapter = new ContactAdapter(this, query);
            getSupportActionBar().setTitle("Familia");
            recyclerView.setAdapter(adapter);
            return true;
        }
        if (id == R.id.action_friends) {
            filter = 2;
            getSupportActionBar().setTitle("Amigos");
            query = ref.orderByChild("group_name").startAt("Amigo").endAt("F");
            adapter = new ContactAdapter(this, query);
            recyclerView.setAdapter(adapter);
            return true;

        }
        if (id == R.id.action_all) {
            filter = 3;
            getSupportActionBar().setTitle("Todos mis Contactos");
            query = ref.orderByChild("name");
            adapter = new ContactAdapter(this, query);
            recyclerView.setAdapter(adapter);
            return true;

        }


        return super.onOptionsItemSelected(item);
    }


}
