package ve.needforock.contactshare.data;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Soporte on 03-Sep-17.
 */

public class Queries {

    public DatabaseReference Contacts(){
        CurrentUser currentUser = new CurrentUser();
        String key = currentUser.sanitizedEmail(currentUser.userEmail());
        DatabaseReference ref = new Nodes().contact(key);
        return ref;
    }


}
