package ve.needforock.contactshare.data;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Soporte on 13-Sep-17.
 */

public class Offline {

    public void setPersistence (){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        new Nodes().users().keepSynced(true);

    }
}
