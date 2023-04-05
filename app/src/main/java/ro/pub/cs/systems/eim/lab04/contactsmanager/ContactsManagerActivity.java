package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private EditText contact_name;
    private EditText contact_number;
    private EditText contact_address;
    private EditText contact_email_address;
    private EditText contact_job_name;
    private EditText contact_company_name;
    private EditText contact_website;
    private EditText contact_im;
    private LinearLayout additional_fields;

    Button additional_details_button;
    Button save_button;
    Button cancel_button;

    private SaveButtonOnClickListener saveButtonOnClickListener = new SaveButtonOnClickListener();
    private CancelButtonOnClickListener cancelButtonOnClickListener = new CancelButtonOnClickListener();
    private ShowAdditionalDetailsButtonOnClickListener showAdditionalDetailsButtonOnClickListener = new ShowAdditionalDetailsButtonOnClickListener();

    private class SaveButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            Bundle extras = intent.getExtras();
            Uri data = intent.getData();

            System.out.println("getData(): " + data);
            System.out.println("getExtras(): " + extras);

            String name = contact_name.getText().toString();
            String phone_number = contact_number.getText().toString();
            String email = contact_email_address.getText().toString();
            String address = contact_address.getText().toString();
            String jobTitle = contact_job_name.getText().toString();
            String company = contact_company_name.getText().toString();
            String website = contact_website.getText().toString();
            String im = contact_im.getText().toString();

            System.out.println("Differences: " + contact_name.getText().toString() + " and " + contact_name.toString());

            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phone_number != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone_number);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }

            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            startActivity(intent);
        }
    }

    private class CancelButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    private class ShowAdditionalDetailsButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            additional_details_button.setText(getResources().getString(R.string.show_additional_fields));

            if (additional_fields.getVisibility() == View.VISIBLE) {
                additional_fields.setVisibility(View.INVISIBLE);
            } else {
                additional_fields.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 2017) {
            setResult(resultCode, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        additional_fields = findViewById(R.id.additional_fields_layout);

        contact_name = findViewById(R.id.contact_name);
        contact_number = findViewById(R.id.contact_number);
        contact_address = findViewById(R.id.contact_address);
        contact_im = findViewById(R.id.contact_im);
        contact_job_name = findViewById(R.id.contact_job);
        contact_company_name = findViewById(R.id.contact_company);
        contact_website = findViewById(R.id.contact_website);
        contact_email_address = findViewById(R.id.contact_email);

        additional_details_button = findViewById(R.id.show_additional_fields_button);
        save_button = findViewById(R.id.save_button);
        cancel_button = findViewById(R.id.cancel_button);

        save_button.setOnClickListener(saveButtonOnClickListener);
        cancel_button.setOnClickListener(cancelButtonOnClickListener);
        additional_details_button.setOnClickListener(showAdditionalDetailsButtonOnClickListener);

        Intent intent = getIntent();
    }
}