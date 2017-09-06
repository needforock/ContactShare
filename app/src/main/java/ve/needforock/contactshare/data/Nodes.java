package ve.needforock.contactshare.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Soporte on 01-Sep-17.
 */

public class Nodes {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference users(){
        return root.child("users");
    }

    public DatabaseReference user(String key){
        return root.child(key);
    }

    public DatabaseReference contact(String key){
        return user(key).child("contacts");
    }
}
