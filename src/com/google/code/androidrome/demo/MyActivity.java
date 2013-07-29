package com.google.code.androidrome.demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;

public class MyActivity extends Activity
{
    // This is the feed that will be parsed
    String url = "http://www.economist.com/rss/the_world_this_week_rss.xml";

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setTitle( "The Economist - The world this week" );

        initList newsList = new initList();
        newsList.setContext(this);
        newsList.execute();
    }

    private View createList(SyndFeed feed, Activity activity)
    {
        LinearLayout mainPanel = new LinearLayout( activity );
        ListView listView = new ListView( activity );
        final FeedListAdapter feedListAdapter = new FeedListAdapter( activity, feed );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parentView, View childView, int position, long id )
            {
                feedListAdapter.click( position );
            }
        } );
        listView.setAdapter( feedListAdapter );
        mainPanel.addView( listView );
        return mainPanel;
    }

    private class initList extends AsyncTask<String, Integer, String>
    {
        private SyndFeed feed;
        private Activity context;

        @Override
        protected String doInBackground(String... params)
        {
            RssAtomFeedRetriever rssAtomFeedRetriever = new RssAtomFeedRetriever();
            feed = rssAtomFeedRetriever.getMostRecentNews(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            setContentView(createList(feed, getContext()));
        }

        private void setContext(Activity context)
        {
            this.context = context;
        }

        private Activity getContext()
        {
            return context;
        }
    }
}
