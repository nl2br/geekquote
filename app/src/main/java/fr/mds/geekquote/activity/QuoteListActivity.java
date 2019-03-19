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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myschool.geekquote.R;

import fr.mds.geekquote.dao.SQLiteQuoteDao;
import fr.mds.geekquote.model.Quote;
import fr.mds.geekquote.adapter.QuoteViewAdapter;

import io.fabric.sdk.android.Fabric;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuoteListActivity extends Activity {

    public static final String TAG = "geekquote";

    private ArrayList<Quote> quotes = new ArrayList<>();
    private LinearLayout ll_quote_list_main;
    private Button bt_quote_list_add;
    private EditText et_quote_list_quote_content;
    private ListView lv_quote_list_list;
    private TextView tv_quote_list_list;
    private QuoteViewAdapter quoteViewAdapter;
    private SharedPreferences sharedPreferences;
    private SQLiteQuoteDao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());



        setContentView(R.layout.quote_list);

        // shared preferences
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

//        // get list from shared preferencies
//        String jsonList = sharedPreferences.getString("quotes", "");
//        if(jsonList != null && !jsonList.isEmpty()) {
//            // convert back to list of object
//            Gson gson = new Gson();
//            Type listType = new TypeToken<List<Quote>>() {}.getType();
//
//            quotes = gson.fromJson(jsonList,listType);
//        }

        quotes = (ArrayList<Quote>) db.getAllQuotes();

        ll_quote_list_main = findViewById(R.id.ll_quote_list_main);
        bt_quote_list_add = findViewById(R.id.bt_quote_list_add);
        et_quote_list_quote_content = findViewById(R.id.et_quote_list_quote_content);
        lv_quote_list_list = findViewById(R.id.lv_quote_list_list);

        quoteViewAdapter = new QuoteViewAdapter(this, quotes);
        lv_quote_list_list.setAdapter(quoteViewAdapter);

        bt_quote_list_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Log.d(TAG, "QuotesListActivity - OnClick");
            addQuote(et_quote_list_quote_content.getText().toString());

            //convert list to string
            Gson gson = new Gson();
            String listjson = gson.toJson(QuoteListActivity.this.quotes);
            Log.d(TAG, "listjson " + listjson );

            // add last quote to shared preferencies
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("last_quote", et_quote_list_quote_content.getText().toString());
            editor.putString("quotes", listjson);
            editor.apply();

            et_quote_list_quote_content.setText("");

            quoteViewAdapter.notifyDataSetChanged();
            }
        });

/*        if (savedInstanceState != null) {

        }else {
            Resources resources = getResources();
            String[] myNotes = resources.getStringArray(R.array.quotes_list);
            for (String myNote : myNotes) {
                addQuote(myNote);
            }
        }*/

        lv_quote_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // explicit car on spécifie quelle classe utliser
                Intent intent = new Intent(QuoteListActivity.this, QuoteDetailActivity.class);
                Log.d(TAG, "onClick" + position);
                intent.putExtra("position",position);
                intent.putExtra("quote", quotes.get(position));
                //startActivity(intent);
                startActivityForResult(intent,0);
            }
        });

        Log.d(TAG, quotes.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"onActivityResult " + requestCode +" "+resultCode + " " + data);
        // recuperer les datas envoyées

        if(data != null){
        Bundle extras = data.getExtras();

            Quote newQuote = (Quote) extras.getSerializable("newQuote");
            Integer position = extras.getInt("position");
            Log.d(TAG, "newQuote - OnClick " + position + " " + newQuote.toString() );

            // remplacer la quote dans le array
            quotes.set(position,newQuote);

            // refresh
            quoteViewAdapter.notifyDataSetChanged();
        }
    }

    void addQuote(String strQuote){
        Quote quote = new Quote(strQuote);
        quotes.add(quote);
        db = new SQLiteQuoteDao(this);
        db.addQuote(quote);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG,"Act 1 onRestoreInstanceState " + savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
        quotes.addAll((ArrayList<Quote>) savedInstanceState.getSerializable("quotes"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"Act 1 onSaveInstanceState " + outState);
        super.onSaveInstanceState(outState);
        outState.putSerializable("quotes",quotes);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String lastQuote = sharedPreferences.getString("last_quote","");
        if(lastQuote != null && !lastQuote.isEmpty()){
            Log.d(TAG,"on resume, last quote = " + lastQuote);
            Toast.makeText(this,lastQuote,Toast.LENGTH_SHORT).show();

        }
    }
}
