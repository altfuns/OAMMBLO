package com.fr4gus.android.oammblo.ui;


import java.util.Date;
import java.util.List;

import twitter4j.TwitterException;

import com.fr4gus.android.oammblo.R;
import com.fr4gus.android.oammblo.bo.Tweet;
import com.fr4gus.android.oammblo.data.DummyTwitterService;
import com.fr4gus.android.oammblo.data.TwitterService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TimelineActivity extends OammbloActivity {

	private ListView timeline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		initComponents();
		
	}
	
	private void initComponents(){
		timeline = (ListView) findViewById(R.id.timeline_list);
		
		TwitterService service = new DummyTwitterService();
		
		try {
			timeline.setAdapter(new TweetAdapter(service.getTimeline()));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class TweetAdapter extends BaseAdapter{

		List<Tweet> tweets;
		public TweetAdapter(List<Tweet> tweets){
			this.tweets = tweets;
		}
		@Override
		public int getCount() {
			return tweets.size();
		}

		@Override
		public Object getItem(int index) {
			return tweets.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TweetViewHolder holder = null;
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(convertView == null){
				// Instance the view with a tweet layout.
				convertView = inflater.inflate(R.layout.timeline_tweet, null);
				// Init Views with timeline_tweet widgets.
				holder = new TweetViewHolder();
				holder.message = (TextView) convertView.findViewById(R.id.timeline_tweet_message);
				holder.author = (TextView) convertView.findViewById(R.id.timeline_tweet_author);
				holder.timestamp = (TextView) convertView.findViewById(R.id.timeline_tweet_timestamp);
				
				// Set tag object to the result view.
				convertView.setTag(holder);
			}else{
				// Gets the instance of older view objects.
				// Reuse object instance to save RAM
				holder = (TweetViewHolder) convertView.getTag();
			}
			
			Tweet tweet = tweets.get(position);
			
			holder.message.setText(tweet.getMessage());
			holder.author.setText(tweet.getAuthor());
			holder.timestamp.setText( (new Date(tweet.getTimestamp())).toString() );
			
			return convertView;
		}
		
	}
	private class TweetViewHolder {
        TextView message;
        TextView author;
        TextView timestamp;        
    }
}
