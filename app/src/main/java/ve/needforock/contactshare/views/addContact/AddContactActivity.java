package ve.needforock.contactshare.views.addContact;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Map;

import ve.needforock.contactshare.R;
import ve.needforock.contactshare.models.Contact;

import static ve.needforock.contactshare.views.details.DetailsActivity.CONTACT_EDIT;
import static ve.needforock.contactshare.views.details.DetailsActivity.EDIT_FLAG;


public class AddContactActivity extends AppCompatActivity implements ContactValidationCallBack {

    private EditText nameEt, phoneEt, mailEt;
    private String name, phone, mail, answer, path, contactKey;
    private RadioGroup group;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 20;
    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private CircularImageView contactPhoto;
    private int editFlag;
    private Contact contactToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editFlag = getIntent().getIntExtra(EDIT_FLAG,0);

        if(editFlag == 1){
            getSupportActionBar().setTitle("Editar Contacto");
        }else {
            getSupportActionBar().setTitle("Agregar Contacto");
        }

        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(AddContactActivity.this, RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);

        contactPhoto = (CircularImageView) findViewById(R.id.contactPhotoCi);

        contactPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPhoto();
            }
        });
        nameEt = (EditText) findViewById(R.id.contactNameEt);
        phoneEt = (EditText) findViewById(R.id.contactPhoneEt);
        mailEt = (EditText) findViewById(R.id.contactMailEt);
        group = (RadioGroup) findViewById(R.id.groupRg);


        if(editFlag ==1){

            contactToEdit = (Contact) getIntent().getSerializableExtra(CONTACT_EDIT);
            contactKey = contactToEdit.getKey();
            nameEt.setText(contactToEdit.getName());
            phoneEt.setText(contactToEdit.getPhone());
            mailEt.setText(contactToEdit.getMail());


        }
        Button saveBtn = (Button) findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEt.getText().toString();
                phone = phoneEt.getText().toString();
                mail = mailEt.getText().toString();
                int id = group.getCheckedRadioButtonId();

                new ContactValidation(AddContactActivity.this).validate(name, mail, phone, id);


            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void contactNoEmpty(int id) {

        RadioButton radioButton = (RadioButton) group.findViewById(id);
        if (id != -1) {
            answer = radioButton.getText().toString();
        } else {
            answer = "Sin Grupo";
        }


       new ContactToFireBase().saveContactPhoto(name, phone, mail, answer, path, contactKey, editFlag);

        finish();
    }

    @Override
    public void contactEmpty() {
        Toast.makeText(this, "Debe Ingresar los datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mailInvalid() {
        Toast.makeText(this, "Mail Incorrecto", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_90);

        if (RESULT_OK == resultCode) {

            Bitmap photo = magicalCamera.getPhoto();

            path = magicalCamera.savePhotoInMemoryDevice(photo, "contactPhoto", "myDirectoryName", MagicalCamera.JPEG, true);

            path = "file://" + path;
            Toast.makeText(this, "result ok", Toast.LENGTH_SHORT).show();
            setPhoto(path);

        } else {
            Toast.makeText(this, "Foto No Tomada", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestPhoto() {
        magicalCamera.takePhoto();
    }

    private void setPhoto(String path){
        Picasso.with(this).load(path).centerCrop().fit().into(contactPhoto);
    }

}
