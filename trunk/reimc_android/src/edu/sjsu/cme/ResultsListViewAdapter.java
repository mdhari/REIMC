package edu.sjsu.cme;

import gash.indexing.Document;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ResultsListViewAdapter extends BaseAdapter implements ListAdapter {

	LayoutInflater mInflater;
	List<Document> resultList = new ArrayList<Document>();
	
	private static class ResultHolder{
		TextView word;
	}
	
	public ResultsListViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
	
    public ResultsListViewAdapter(Context context, List<Document> resultList) {
        mInflater = LayoutInflater.from(context);
        this.resultList = resultList;
    }
    
    public void updateData(List<Document> resultList){
    	this.resultList = resultList;
    }
	
	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public Document getItem(int position) {

		return resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ResultHolder holder;
		
		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.result_item, null);

            holder = new ResultHolder();
            holder.word = (TextView) convertView.findViewById(R.id.word_text);
            

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView and the ImageView.
            holder = (ResultHolder) convertView.getTag();
        }

       holder.word.setText(getItem(position).toString());
        

        return convertView;
	
	}

}
