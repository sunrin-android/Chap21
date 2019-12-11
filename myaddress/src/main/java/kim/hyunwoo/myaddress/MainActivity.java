package kim.hyunwoo.myaddress;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button contactBtn;
    TextView nameView;
    TextView phoneView;
    TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactBtn=findViewById(R.id.lab2_btn);
        nameView=findViewById(R.id.name);
        phoneView=findViewById(R.id.phone);
        emailView=findViewById(R.id.email);

        contactBtn.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }

    }

    @Override
    public void onClick(View v) {
        if(v==contactBtn){
           // 주소록 앱에 인텐트 보내기
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            String id = Uri.parse(data.getDataString()).getLastPathSegment();
            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{
                    ContactsContract.Data.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Email.ADDRESS
            }, "contact_id=?", new String[]{id}, null);

            String name = "";
            String number = "";
            String email = "";
            if(cursor.moveToNext()){
                name = cursor.getString(0);
                number = cursor.getString(1);
            }

            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{
                    ContactsContract.CommonDataKinds.Email.ADDRESS
            }, "contact_id=?", new String[]{id}, null);
            if(cursor.moveToNext()){
                email = cursor.getString(0);
            }
            nameView.setText(name);
            phoneView.setText(number);
            emailView.setText(email);
        }
    }
}

