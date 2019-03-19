package fr.mds.geekquote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myschool.geekquote.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.mds.geekquote.dao.SQLiteQuoteDao;
import fr.mds.geekquote.helper.DatabaseOpenHelper;
import fr.mds.geekquote.model.Quote;

// - create the new activity class extends Activity
// - insert the activity into the manifest
// - create the layout
// - add the onCreate method into the new activity
// - associate the layout with the activity


public class QuoteDetailActivity extends Activity {

    public static final String TAG = "geekquote";
    private Button btn_second_back;
    private Button btn_second_validate;
    private TextView tv_second;
    private ArrayList<Quote> quotes = new ArrayList<>();
    private SQLiteQuoteDao db;
    private Quote currentQuote;
    private Integer currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //
        setContentView(R.layout.second_activity);
        tv_second = findViewById(R.id.tv_second);
        btn_second_validate = findViewById(R.id.btn_second_validate);

        btn_second_back = findViewById(R.id.btn_second_back);
        btn_second_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        // recuperer les datas envoyées
        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            Integer id = extras.getInt("id");
            Quote quote = (Quote) extras.getSerializable("quote");
            currentPosition = extras.getInt("position");

            currentQuote = quote;

            Log.d(TAG, "get Id: " + quote.getId() );
            Log.d(TAG, "get quote: " + quote.getStrQuote());

            tv_second.setText(quote.getStrQuote());
        }

        // retourner les données modifier
        btn_second_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // update de la quote
                currentQuote.setStrQuote(tv_second.getText().toString());

                db = new SQLiteQuoteDao(QuoteDetailActivity.this);

                // update dans la base
                int numRow = db.updateQuote(currentQuote);
                if(numRow!=0){
                    Log.d(TAG, "UPDATE SUCCESS: " + numRow );
                }else{
                    Log.d(TAG, "UPDATE FAILED");
                }

                // renvoyer les infos à quoteListActivity
                getIntent().putExtra("id",currentQuote.getId());
                getIntent().putExtra("updatedQuote",currentQuote);
                getIntent().putExtra("position",currentPosition);
                //getIntent().putExtra("id",getIntent().getExtras().getInt("id"));
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });



    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.d(TAG,"Act 2 onRestoreInstanceState " + savedInstanceState);
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        Log.d(TAG,"Act 2 onSaveInstanceState " + outState);
//        super.onSaveInstanceState(outState);
//    }
}
