package ve.needforock.contactshare.views.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Contact contact;



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
        TextView name = (TextView) findViewById(R.id.detailNameTv);
        TextView mail = (TextView) findViewById(R.id.detailMailTv);
        TextView phone = (TextView) findViewById(R.id.detailPhoneTv);
        TextView group = (TextView) findViewById(R.id.detailGroupTv);

        name.setText(contact.getName());
        mail.setText(contact.getMail());
        phone.setText(contact.getPhone());
        group.setText(contact.getGroup());


       if(contact.getPhoto().trim().length()>0) {

           Picasso.with(DetailsActivity.this).invalidate(contact.getPhoto());
           Picasso.with(DetailsActivity.this).load(contact.getPhoto()).into(imageView);
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
                finish();
            }
        });
    }


}
