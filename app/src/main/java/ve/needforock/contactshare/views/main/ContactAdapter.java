package ve.needforock.contactshare.views.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import ve.needforock.contactshare.R;
import ve.needforock.contactshare.models.Contact;


/**
 * Created by Soporte on 14-Aug-17.
 */

public class ContactAdapter extends FirebaseRecyclerAdapter<Contact, ContactAdapter.ContactHolder> {

    private ContactListener listener;
    private boolean first = true;
    private Context context;



    public ContactAdapter(ContactListener listener, Query ref) {
        super(Contact.class, R.layout.list_item_contact, ContactHolder.class, ref);
        this.listener = listener;

    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        listener.dataChange();
    }



    @Override
    protected void populateViewHolder(final ContactHolder viewHolder, Contact model, int position) {
        viewHolder.name.setText(model.getName());
        Picasso.with(viewHolder.thumbnailIv.getContext()).invalidate(model.getPhoto());
        viewHolder.thumbnailIv.setImageResource(R.mipmap.ic_account_circle_black_24dp);
        if(model.getPhoto().trim().length()>0) {
            Picasso.with(viewHolder.thumbnailIv.getContext()).load(model.getPhoto()).resize(50,50).into(viewHolder.thumbnailIv);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact auxContact = getItem(viewHolder.getAdapterPosition());
                listener.clicked(auxContact);
            }
        });
    }



    public static class ContactHolder extends RecyclerView.ViewHolder{

        private ImageView thumbnailIv;
        private TextView name;

        public ContactHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nameTv);
            thumbnailIv = (ImageView) itemView.findViewById(R.id.thumbnailIv);
        }


    }
}
