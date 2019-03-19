package fr.mds.geekquote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.mds.geekquote.activity.QuoteListActivity;
import fr.mds.geekquote.helper.DatabaseOpenHelper;
import fr.mds.geekquote.model.Quote;

public class SQLiteQuoteDao {

    private SQLiteDatabase db;

    public SQLiteQuoteDao(Context context){
        db = new DatabaseOpenHelper(context).getWritableDatabase();
    }

    public Quote getQuoteById(Long id){
        Log.d(QuoteListActivity.TAG, "Get quote: " + id);

        String[] columns = {DatabaseOpenHelper.COLUMN_ID, DatabaseOpenHelper.COLUMN_STR_QUOTE, DatabaseOpenHelper.COLUMN_RATING, DatabaseOpenHelper.COLUMN_DATE };
        String[] params = {String.valueOf(id)};

        Cursor result = db.query(DatabaseOpenHelper.DATABASE_TABLE, columns,"id=?",params,null,null,null);

        result.moveToFirst();
        Quote quote = null;
        if(result.getCount() > 0){
            Log.d(QuoteListActivity.TAG, "QUERY OK: " + result);

            quote = new Quote();
            quote.setId(result.getLong(0));
            quote.setStrQuote(result.getString(1));
            quote.setRating(result.getInt(2));
            quote.setCreationDate(new Date(result.getInt(3)));
        }
        result.close();

        return quote;
    }

    public void addQuote(Quote quote){
        // add this quote to db
        Log.d(QuoteListActivity.TAG, "Insert to DB: " + quote);
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.COLUMN_STR_QUOTE,quote.getStrQuote());
        values.put(DatabaseOpenHelper.COLUMN_RATING,quote.getRating());
        values.put(DatabaseOpenHelper.COLUMN_DATE, String.valueOf(quote.getCreationDate()));
        quote.setId(db.insert(DatabaseOpenHelper.DATABASE_TABLE,null,values));
    }

    public int updateQuote(Quote quote){
        // update this quote to db
        Log.d(QuoteListActivity.TAG, "Update to DB quote Id: " + quote.getId());
        ContentValues values = new ContentValues();
        String[] whereArgs = {String.valueOf(quote.getId())};
        values.put(DatabaseOpenHelper.COLUMN_STR_QUOTE,quote.getStrQuote());
        values.put(DatabaseOpenHelper.COLUMN_RATING,quote.getRating());
        values.put(DatabaseOpenHelper.COLUMN_DATE, String.valueOf(quote.getCreationDate()));
        return db.update(DatabaseOpenHelper.DATABASE_TABLE,values,"id=?",whereArgs);

    }

    public ArrayList<Quote> getAllQuotes(){

        Log.d(QuoteListActivity.TAG, "List all quotes");
        String[] columns = {DatabaseOpenHelper.COLUMN_ID, DatabaseOpenHelper.COLUMN_STR_QUOTE, DatabaseOpenHelper.COLUMN_RATING, DatabaseOpenHelper.COLUMN_DATE };
        Cursor result = db.query(DatabaseOpenHelper.DATABASE_TABLE, columns,null,null,null,null,null);
        ArrayList<Quote> quotes = new ArrayList<Quote>();
        result.moveToFirst();
        while(!result.isAfterLast()){
            Quote quote = new Quote(result.getString(1));
            quote.setRating(result.getInt(2));
            quote.setId(result.getLong(0));
            quote.setCreationDate(new Date(result.getInt(3)));
            Log.d(QuoteListActivity.TAG, "quote id: " + quote.getId());
            quotes.add(quote);
            result.moveToNext();
        }
        result.close();
        return quotes;
    }

}
