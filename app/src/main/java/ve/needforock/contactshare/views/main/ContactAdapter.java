package ve.needforock.contactshare.views.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import ve.needforock.contactshare.R;
import ve.needforock.contactshare.models.Contact;



/**
 * Created by Soporte on 14-Aug-17.
 */

public class ContactAdapter extends FirebaseRecyclerAdapter<Contact, ContactAdapter.ContactHolder> {

    private ContactListener listener;
    private boolean first = true;


    public ContactAdapter(ContactListener listener, Query ref) {
        super(Contact.class, R.layout.list_item_contact, ContactHolder.class, ref);
        this.listener = listener;
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        listener.dataChange();
        notifyDataSetChanged();
    }

    @Override
    protected void populateViewHolder(final ContactHolder viewHolder, Contact model, int position) {
        viewHolder.name.setText(model.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact auxContact = getItem(viewHolder.getAdapterPosition());
                listener.clicked(auxContact);
            }
        });

    }

    public static class ContactHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public ContactHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nameTv);
        }


    }
}
