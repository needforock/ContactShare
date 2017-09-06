package ve.needforock.contactshare.views.main;

import ve.needforock.contactshare.models.Contact;

/**
 * Created by Soporte on 04-Sep-17.
 */

public interface ContactListener {
    void clicked(Contact contact);
    void dataChange();

}
