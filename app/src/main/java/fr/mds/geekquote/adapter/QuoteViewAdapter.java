package fr.mds.geekquote.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myschool.geekquote.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.mds.geekquote.activity.QuoteListActivity;
import fr.mds.geekquote.dao.SQLiteQuoteDao;
import fr.mds.geekquote.model.Quote;

public class QuoteViewAdapter extends ArrayAdapter<Quote> {

    public QuoteViewAdapter(Context context, ArrayList<Quote> quotes) {
        super(context, 0, quotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        Quote quote = getItem(position);
        Log.d(QuoteListActivity.TAG, "quote from view : " + quote);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quote_list_item_quote, parent, false);
        }

        // Lookup view for data population
        TextView lv_quote_list_item_quote = (TextView) convertView.findViewById(R.id.lv_quote_list_item_quote);
        TextView lv_quote_list_item_date = (TextView) convertView.findViewById(R.id.lv_quote_list_item_date);
        TextView lv_quote_list_item_id = (TextView) convertView.findViewById(R.id.lv_quote_list_item_id);

        // Populate the data into the template view using the data object
        lv_quote_list_item_quote.setText(quote.getStrQuote());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        //lv_quote_list_item_date.setText(sdf.format(quote.getCreationDate()));
        lv_quote_list_item_date.setText(sdf.format(quote.getCreationDate().getTime()));
        //lv_quote_list_item_id.setText(quote.getId());

        // Return the completed view to render on screen
        return convertView;
    }
}
