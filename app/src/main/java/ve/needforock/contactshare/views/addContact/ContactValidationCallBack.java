package ve.needforock.contactshare.views.addContact;

/**
 * Created by Soporte on 02-Sep-17.
 */

public interface ContactValidationCallBack {

    void contactNoEmpty(int id);
    void contactEmpty();
    void mailInvalid();


}
