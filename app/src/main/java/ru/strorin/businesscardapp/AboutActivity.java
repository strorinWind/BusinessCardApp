package ru.strorin.businesscardapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    private static final String[] EXTRA_EMAIL_ADDRESSES = {"strorinw@gmail.com"};
    private static final String EXTRA_EMAIL_SUBJECT = "Feedback";

    private static final String TELEGRAM_URL = "https://t.me/strorin";
    private static final String VK_URL = "https://vk.com/rin_winder";
    private static final String HABR_URL = "https://habr.com/users/strorinwind/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.person_name);

        Button sendBtn = findViewById(R.id.send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = findViewById(R.id.email_text);
                String text = emailField.getText().toString();
                composeEmail(EXTRA_EMAIL_ADDRESSES, EXTRA_EMAIL_SUBJECT, text);
            }
        });

        ImageView tg = findViewById(R.id.tg);
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(TELEGRAM_URL);
            }
        });

        ImageView vk = findViewById(R.id.vk);
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(VK_URL);
            }
        });

        ImageView habr = findViewById(R.id.habr);
        habr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(HABR_URL);
            }
        });
    }

    private void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, R.string.no_email_app_error, Toast.LENGTH_LONG).show();
        }
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, R.string.no_web_browser_error, Toast.LENGTH_LONG).show();
        }
    }
}
