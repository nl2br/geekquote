package fr.mds.geekquote.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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

import com.myschool.geekquote.R;
import fr.mds.geekquote.model.Quote;
import fr.mds.geekquote.adapter.QuoteViewAdapter;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class QuoteListActivity extends Activity {

    public static final String TAG = "geekquote";

    private ArrayList<Quote> quotes = new ArrayList<>();
    private LinearLayout ll_quote_list_main;
    private Button bt_quote_list_add;
    private EditText et_quote_list_quote_content;
    private ListView lv_quote_list_list;
    private TextView tv_quote_list_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quote_list);

        ll_quote_list_main = findViewById(R.id.ll_quote_list_main);
        bt_quote_list_add = findViewById(R.id.bt_quote_list_add);
        et_quote_list_quote_content = findViewById(R.id.et_quote_list_quote_content);
        lv_quote_list_list = findViewById(R.id.lv_quote_list_list);

        final QuoteViewAdapter quoteViewAdapter = new QuoteViewAdapter(this, quotes);
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

        Resources resources = getResources();
        String[] myNotes = resources.getStringArray(R.array.quotes_list);
        for (String myNote : myNotes) {
            addQuote(myNote);
        }

        lv_quote_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuoteListActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, quotes.toString());
    }

    void addQuote(String strQuote){
        Quote quote = new Quote(strQuote);
        quotes.add(quote);
    }
}
