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

            Integer position = null;

            position = extras.getInt("position");
            Log.d(TAG, "Position: " + position );
            Quote quote = (Quote) extras.getSerializable("quote");
            Log.d(TAG, "quote: " + quote);
            Log.d(TAG, "quote.toString: " + quote.toString());

            tv_second.setText(quote.toString());
        }

        // retourner les données modifier
        btn_second_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // créer un new quote
                Quote newQuote = new Quote(tv_second.getText().toString());

                // update dans la base
                db = new SQLiteQuoteDao(QuoteDetailActivity.this);
                int numRow = db.updateQuote(newQuote);
                if(numRow!=0){
                    Log.d(TAG, "UPDATE SUCCESS: " + numRow );
                }else{
                    Log.d(TAG, "UPDATE FAILED");
                }


                getIntent().putExtra("newQuote",newQuote);
                getIntent().putExtra("position",getIntent().getExtras().getInt("position"));
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
