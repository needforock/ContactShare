package ve.needforock.contactshare.views.main;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Soporte on 23-Sep-17.
 */

public class Presenter {

    private CountCallback callback;

    public Presenter(CountCallback callback) {
        this.callback = callback;
    }

    public void contactCount(Query query){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Log.d("count", String.valueOf(count));
                callback.count(String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
