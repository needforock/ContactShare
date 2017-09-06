package ve.needforock.contactshare.views.drawer;

import android.net.Uri;

import ve.needforock.contactshare.data.CurrentUser;
import ve.needforock.contactshare.data.Nodes;
import ve.needforock.contactshare.models.Contact;
import ve.needforock.contactshare.models.LocalUser;

/**
 * Created by Soporte on 01-Sep-17.
 */

public class UserToFireBase {

    public void SaveUserToFireBase(Uri userImageUri){
        CurrentUser currentUser = new CurrentUser();
        LocalUser user = new LocalUser();
        user.setEmail(currentUser.userEmail());
        user.setName(currentUser.getCurrentUser().getDisplayName());
        user.setPhoto(String.valueOf(userImageUri));
        user.setUid(currentUser.getUid());
        String key = currentUser.sanitizedEmail(currentUser.userEmail());
        Contact contact = new Contact();
        user.setContact(contact);
        new Nodes().user(key).child("details").setValue(user);

    }
}
