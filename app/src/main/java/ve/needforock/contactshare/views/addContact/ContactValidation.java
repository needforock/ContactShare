package ve.needforock.contactshare.views.addContact;


/**
 * Created by Soporte on 02-Sep-17.
 */

public class ContactValidation {
    private ContactValidationCallBack validationCallBack;

    public ContactValidation(ContactValidationCallBack validationCallBack) {
        this.validationCallBack = validationCallBack;
    }

    public void validate(String name, String mail, String phone, int id) {


        if (name.trim().length() > 0 && mail.trim().length() > 0 && phone.trim().length() > 0) {
            if(mail.contains("@") && mail.contains(".")) {
                validationCallBack.contactNoEmpty(id);
            }else{
                validationCallBack.mailInvalid();
            }
        } else {
            validationCallBack.contactEmpty();

        }

    }

}
