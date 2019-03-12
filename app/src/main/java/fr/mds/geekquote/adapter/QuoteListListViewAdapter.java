package fr.mds.geekquote.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myschool.geekquote.R;

import java.util.ArrayList;
import fr.mds.geekquote.model.Quote;

public class QuoteListListViewAdapter extends ArrayAdapter<Quote> {

    public QuoteListListViewAdapter(Context context, ArrayList<Quote> quotes) {
        super(context, 0, quotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Quote quote = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quote_list_item_quote, parent, false);
        }

        // Lookup view for data population
        TextView lv_quote_list_item_quote = (TextView) convertView.findViewById(R.id.lv_quote_list_item_quote);
        TextView lv_quote_list_item_date = (TextView) convertView.findViewById(R.id.lv_quote_list_item_date);

        // Populate the data into the template view using the data object
        lv_quote_list_item_quote.setText(quote.getStrQuote());
        lv_quote_list_item_date.setText((CharSequence) quote.getCreationDate());

        // Return the completed view to render on screen
        return convertView;
    }
}
