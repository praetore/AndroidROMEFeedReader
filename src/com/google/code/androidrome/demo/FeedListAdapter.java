package com.google.code.androidrome.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

public class FeedListAdapter
    extends BaseAdapter
{
    private SyndFeed feed;

    private Activity context;

    public FeedListAdapter(Activity context, SyndFeed feed)
    {
        this.feed = feed;
        this.context = context;
    }

    public int getCount()
    {
        return feed.getEntries().size();
    }

    public SyndEntry getItem( int index )
    {
        return (SyndEntry) feed.getEntries().get( index );
    }

    public long getItemId( int index )
    {
        return index;
    }

    public View getView( int index, View cellRenderer, ViewGroup viewGroup )
    {
        NewsEntryCellView newsEntryCellView = (NewsEntryCellView) cellRenderer;

        if ( cellRenderer == null )
        {
            newsEntryCellView = new NewsEntryCellView();
        }

        newsEntryCellView.display( index );
        return newsEntryCellView;
    }

    public void click( int position )
    {
        String uri = getItem( position ).getUri();
        Intent webIntent = new Intent( "android.intent.action.VIEW", Uri.parse( uri ) );
        context.startActivity( webIntent );
    }

    private class NewsEntryCellView
        extends TableLayout
    {
        private TextView titleTextView;

        public NewsEntryCellView()
        {
            super( context );
            createUI();
        }

        private void createUI()
        {
            setColumnShrinkable( 0, false );
            setColumnStretchable( 0, false );

            setPadding(10, 2, 10, 2);

            titleTextView = new TextView( context );
            titleTextView.setPadding( 10, 2, 10, 2 );
            addView( titleTextView );
        }


        public void display( int index )
        {
            SyndEntry entry = getItem( index );
            titleTextView.setText( entry.getTitle() );
            titleTextView.setTextColor(Color.BLUE);
        }
    }
}
