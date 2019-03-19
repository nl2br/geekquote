package fr.mds.geekquote.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.myschool.geekquote.R;

import java.util.ArrayList;

import fr.mds.geekquote.adapter.QuoteViewAdapter;
import fr.mds.geekquote.dao.SQLiteQuoteDao;
import fr.mds.geekquote.model.Quote;
import io.fabric.sdk.android.Fabric;

public class QuoteListActivity extends Activity {

    public static final String TAG = "geekquote";

    private LinearLayout ll_quote_list_main;
    private Button bt_quote_list_add;
    private EditText et_quote_list_quote_content;
    private ListView lv_quote_list_list;
    private TextView tv_quote_list_list;

    private ArrayList<Quote> quotes = new ArrayList<>();
    private QuoteViewAdapter quoteViewAdapter;
    private SharedPreferences sharedPreferences;
    private SQLiteQuoteDao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.quote_list);

        ll_quote_list_main = findViewById(R.id.ll_quote_list_main);
        bt_quote_list_add = findViewById(R.id.bt_quote_list_add);
        et_quote_list_quote_content = findViewById(R.id.et_quote_list_quote_content);
        lv_quote_list_list = findViewById(R.id.lv_quote_list_list);

        // shared preferences
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        db = new SQLiteQuoteDao(this);
        quotes = db.getAllQuotes();

        quoteViewAdapter = new QuoteViewAdapter(this, quotes);
        lv_quote_list_list.setAdapter(quoteViewAdapter);

        bt_quote_list_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "QuotesListActivity - OnClick");
                addQuote(et_quote_list_quote_content.getText().toString());
                et_quote_list_quote_content.setText("");
                quoteViewAdapter.notifyDataSetChanged();
            }
        });

        lv_quote_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // explicit car on spécifie quelle classe utliser
                Intent intent = new Intent(QuoteListActivity.this, QuoteDetailActivity.class);

                Log.d(TAG, "onClick position " + position);
                Log.d(TAG, "onClick quote " + quotes.get(position));
                Log.d(TAG, "onClick id " + quotes.get(position).getId());

                intent.putExtra("id", quotes.get(position).getId());
                intent.putExtra("quote", quotes.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + requestCode + " " + resultCode + " " + data);

        // recuperer les datas envoyées
        if (data != null) {
            Bundle extras = data.getExtras();
            int quotePosition = extras.getInt("position");
            Quote updatedQuote = (Quote) extras.getSerializable("updatedQuote");
            //Integer id = extras.getInt("id");
            Log.d(TAG, "updatedQuote " + updatedQuote.getId() + " " + updatedQuote.getStrQuote());

            // remplacer la quote dans le array
            quotes.set(quotePosition, updatedQuote);

            // refresh
            quoteViewAdapter.notifyDataSetChanged();
        }
    }

    void addQuote(String strQuote) {
        Quote quote = new Quote(strQuote);
        //quotes.add(quote);
        db = new SQLiteQuoteDao(this);
        db.addQuote(quote);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "Act 1 onRestoreInstanceState " + savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
        //quotes.addAll((ArrayList<Quote>) savedInstanceState.getSerializable("quotes"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "Act 1 onSaveInstanceState " + outState);
        super.onSaveInstanceState(outState);
        outState.putSerializable("quotes", quotes);
    }

}
