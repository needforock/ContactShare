package ve.needforock.contactshare.views.details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import ve.needforock.contactshare.R;
import ve.needforock.contactshare.models.Contact;
import ve.needforock.contactshare.views.addContact.AddContactActivity;
import ve.needforock.contactshare.views.addContact.ContactToFireBase;

import static ve.needforock.contactshare.views.main.MainActivity.CONTACT;

public class DetailsActivity extends AppCompatActivity {

    public static final String CONTACT_EDIT = "ve.needforock.contactshare.views.details.KEY.CONTACT_EDIT";
    public static final String EDIT_FLAG = "ve.needforock.contactshare.views.details.KEY.EDIT_FLAG";
    private static final int MY_PERMISSIONS_REQUEST_CALL = 123;
    private Contact contact;
    private String phoneT;
    private FloatingActionButton addFab, phoneFab, mailFab;


    // public static DiskCacheStrategy diskCacheStrategy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contact=null;
        contact = (Contact) getIntent().getSerializableExtra(CONTACT);
        getSupportActionBar().setTitle(contact.getName());

        RoundedImageView imageView = (RoundedImageView) findViewById(R.id.detailPictureIm);
        ImageView emptyImage = (ImageView) findViewById(R.id.emptyImageIv);


        TextView name = (TextView) findViewById(R.id.detailNameTv);
        TextView mail = (TextView) findViewById(R.id.detailMailTv);
        TextView phone = (TextView) findViewById(R.id.detailPhoneTv);
        TextView group = (TextView) findViewById(R.id.detailGroupTv);


        name.setText(contact.getName());
        mail.setText(contact.getMail());
        phoneT = contact.getPhone();
        phone.setText(phoneT);
        group.setText(contact.getGroup());


       if(contact.getPhoto().trim().length()>0) {
           emptyImage.setVisibility(View.INVISIBLE);

           Picasso.with(DetailsActivity.this).invalidate(contact.getPhoto());
           Picasso.with(DetailsActivity.this).load(contact.getPhoto()).into(imageView);
        }else{
           emptyImage.setVisibility(View.VISIBLE);
       }



        Button edit = (Button) findViewById(R.id.editBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this , AddContactActivity.class);
                intent.putExtra(CONTACT_EDIT, contact);
                intent.putExtra(EDIT_FLAG, 1);
                startActivity(intent);
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.eraseBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ContactToFireBase().eraseContactFb(contact);
                Toast.makeText(DetailsActivity.this, "Contacto Eliminado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        addFab = (FloatingActionButton) findViewById(R.id.addF);
        mailFab = (FloatingActionButton) findViewById(R.id.mailF);
        phoneFab = (FloatingActionButton) findViewById(R.id.phoneF);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addFab.getRotation()!= 0){
                    hideFabs();
                }else{
                    showFabs();
                }
            }
        });

        phoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionsToCall();
            }
        });

        mailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_SENDTO);
                intent.setType("*/*");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {contact.getMail()});
                startActivity(intent);
            }
        });




    }



    public void permissionsToCall(){

        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,

        };
        ActivityCompat.requestPermissions(DetailsActivity.this,
                permissions,
                MY_PERMISSIONS_REQUEST_CALL);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneT ));
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissionsToCall this app might request
        }
    }

    public static float dpToPixels(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dp * scale + 0.5f);
    }

    private void showFabs(){
        Log.d("SHOW","showfabs");
        addFab.animate().rotation(45).setDuration(400).start();
        mailFab.animate().translationY(dpToPixels(this,-70)).setDuration(300).start();
        phoneFab.animate().translationY(dpToPixels(this,-140)).setDuration(600).start();
    }

    private void hideFabs(){
        addFab.animate().rotation(0).setDuration(400).start();
        mailFab.animate().translationY(dpToPixels(this,0)).setDuration(600).start();
        phoneFab.animate().translationY(dpToPixels(this,0)).setDuration(800).start();
    }
}
