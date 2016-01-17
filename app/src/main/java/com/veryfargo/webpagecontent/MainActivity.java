package com.veryfargo.webpagecontent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGetPageContent;
    TextView tvPgaeContentResult;
    EditText etUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setUpView();
    }

    private void setUpView() {
        etUrl = (EditText) findViewById(R.id.etUrl);
        tvPgaeContentResult = (TextView) findViewById(R.id.tvPageResult);
        btnGetPageContent = (Button) findViewById(R.id.btnGetPageContent);

        // set onClick listener
        btnGetPageContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String siteUrl = etUrl.getText().toString();
        // must perform get web page content in background thread
        GetUrlContentSyncTask getContentSyncTask = new  GetUrlContentSyncTask();
        getContentSyncTask.execute(new String[]{siteUrl});

        //( new  GetUrlContentSyncTask() ).execute(new String[]{siteUrl});
    }


    // GetUrlContent.java inner class
    private class GetUrlContentSyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = new String();
            try {
                Log.d("JSwa", "Connecting to [" + strings[0] + "]");
                Document doc = Jsoup.connect(strings[0]).get();
                result = doc.toString();

            } catch (Throwable t) {
                t.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result );
            // back to UI thread to update screen field
            tvPgaeContentResult.setText(result);
        }
    }
}
