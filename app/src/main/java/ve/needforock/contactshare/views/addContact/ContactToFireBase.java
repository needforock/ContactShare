package ve.needforock.contactshare.views.addContact;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ve.needforock.contactshare.data.CurrentUser;
import ve.needforock.contactshare.data.Nodes;
import ve.needforock.contactshare.models.Contact;

/**
 * Created by Soporte on 01-Sep-17.
 */

public class ContactToFireBase {

    private ProgressDialog progressDialog;



    public void saveContactToFireBase(String name, String phone, String mail, String group, String photo) {


        CurrentUser currentUser = new CurrentUser();
        String key = currentUser.sanitizedEmail(currentUser.userEmail());


        Contact contact = new Contact();
        contact.setName(name);
        contact.setGroup(group);
        contact.setMail(mail);
        contact.setPhone(phone);
        contact.setPhoto(photo);
        //user.setContact(contact);

        @SuppressWarnings("VisibleForTests") String[] fullUrl = new Nodes().user(key).child("contacts").push().toString().split("contacts/");
       String newUid = fullUrl[1];
        contact.setUid(newUid);

        new Nodes().user(key).child("contacts").child(newUid).setValue(contact);

        //new Nodes().contact(key,name).setValue(contact);

    }

    public void saveContactPhoto(final String name, final String phone, final String mail, final String group, String path, final String uid , final int editFlag){

        if (path != null) {
            final CurrentUser currentUser = new CurrentUser();
            String folder = currentUser.sanitizedEmail(currentUser.userEmail() + "/");

            String photoName = name + ".png";



            String baseUrl = "gs://contactshare-ff372.appspot.com/users/" + folder;
            String refUrl = baseUrl + "contacts_photos/" + name + "/" + photoName;
           // Log.d("upload", refUrl);
            Log.d("FLAG", String.valueOf(editFlag));

            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);



            storageRef.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                    String photoUrl = fullUrl[0];
                    if (editFlag == 1) {
                        editedToFireBase(name, phone, mail, group, photoUrl, uid, editFlag);

                    } else {
                        saveContactToFireBase(name, phone, mail, group, photoUrl);
                        Log.d("upload", photoUrl);

                    }
                }
            });
        }else{
            Log.d("FLAG", String.valueOf(editFlag));
            if (editFlag == 1) {
                editedToFireBase(name, phone, mail, group, "", uid, editFlag);
            }else{
                saveContactToFireBase(name, phone, mail, group, "");
            }
        }

    }

    public void editedToFireBase(String name, String phone, String mail, String group, String photo, String uid, int editFlag) {


        CurrentUser currentUser = new CurrentUser();
        String key = currentUser.sanitizedEmail(currentUser.userEmail());
        Contact contact = new Contact();
        contact.setName(name);
        contact.setGroup(group);
        contact.setMail(mail);
        contact.setPhone(phone);
        contact.setPhoto(photo);
        contact.setUid(uid);
        Log.d("FLAG", String.valueOf(editFlag));
        if(editFlag==1) {
            new Nodes().user(key).child("contacts").child(uid).setValue(contact);
        }


    }

    public void eraseContactFb(Contact contact){
        CurrentUser currentUser = new CurrentUser();
        String key = currentUser.sanitizedEmail(currentUser.userEmail());
        String uidToRemove = contact.getUid();
        new Nodes().contact(key).child(uidToRemove).removeValue();


    }
}
