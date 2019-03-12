package fr.mds.geekquote.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myschool.geekquote.R;
import fr.mds.geekquote.model.Quote;

import java.util.ArrayList;

public class QuoteListActivity extends Activity {

    public static final String TAG = "geekquote";

    private ArrayList<Quote> quotes = new ArrayList<>();

    // private TextView tv_quote_list_title;
    // private Spinner sp_quote_list_quotes;
    private LinearLayout ll_quote_list_main;
    private Button bt_quote_list_add;
    private EditText et_quote_list_quote_content;
    private ListView lv_quote_list_list;

    private TextView tv_quote_list_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // tv_quote_list_title = findViewById(R.id.tv_quote_list_title);
        // sp_quote_list_quotes = findViewById(R.id.sp_quote_list_quotes);

        setContentView(R.layout.quote_list);

        ll_quote_list_main = findViewById(R.id.ll_quote_list_main);
        bt_quote_list_add = findViewById(R.id.bt_quote_list_add);
        et_quote_list_quote_content = findViewById(R.id.et_quote_list_quote_content);
        lv_quote_list_list = findViewById(R.id.lv_quote_list_list);

        /* simple adapter to show simple list
        final ArrayAdapter<Quote> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quotes);
        lv_quote_list_list.setAdapter(listViewAdapter);

        lv_quote_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(QuoteListActivity.this, quotes.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        bt_quote_list_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "QuotesListActivity - OnClick");
                addQuote(et_quote_list_quote_content.getText().toString());
                et_quote_list_quote_content.setText("");
                listViewAdapter.notifyDataSetChanged();
            }
        });

        Resources resources = getResources();
        String[] myNotes = resources.getStringArray(R.array.quotes_list);
        for (String myNote : myNotes) {
            addQuote(myNote);

            // TextView textView = new TextView(this);
            // textView.setText(myNote.toString());
            // ll_quote_list_main.addView(textView);
        }



        // tv_quote_list_title.setText(myNotes[0]);

        // setup spinner
        // ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myNotes);
        // sp_quote_list_quotes.setAdapter(spinnerAdapter);

        Log.d(TAG, quotes.toString());
    }

    void addQuote(String strQuote){
        Quote quote = new Quote(strQuote);
        quotes.add(quote);

    }
}
