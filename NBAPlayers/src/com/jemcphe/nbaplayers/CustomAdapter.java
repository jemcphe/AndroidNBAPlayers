package com.jemcphe.nbaplayers;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	// Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Athlete> athleteList = null;
    private ArrayList<Athlete> arraylist;
 
    /*
     * CustomAdapter Class is used to enhance the UI.
     * This gives me a more robust visual and a more meaningful
     * UI.
     */
    public CustomAdapter(Context context,
            List<Athlete> athleteList) {
        mContext = context;
        this.athleteList = athleteList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Athlete>();
        this.arraylist.addAll(athleteList);
    }
 
    // Define TextViews
    public class ViewHolder {
        TextView name;
        TextView team;
        TextView jerseyNumber;
        TextView height;
        TextView weight;
    }
 
    /*********** Helper Methods ****************/
    @Override
    public int getCount() {  
        return athleteList.size();
    }
 
    @Override
    public Athlete getItem(int position) {
        return athleteList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
        	// Create view holder
            holder = new ViewHolder();
            // inflate that view with custom list_row layout
            view = inflater.inflate(R.layout.list_row, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.list_name);
            holder.team = (TextView) view.findViewById(R.id.list_team);
            holder.jerseyNumber = (TextView) view.findViewById(R.id.list_jerseyNumber);
            holder.height = (TextView) view.findViewById(R.id.list_height);
            holder.weight = (TextView) view.findViewById(R.id.list_weight);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(athleteList.get(position).getDisplayName());
        holder.team.setText(athleteList.get(position).getTeam());
        holder.jerseyNumber.setText(athleteList.get(position).getJerseyNumber());
        holder.height.setText(athleteList.get(position).getHeight());
        holder.weight.setText(athleteList.get(position).getWeight());
 
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, ViewAthleteActivity.class);
                // Pass objectId data
                intent.putExtra("objectId", athleteList.get(position).getId());
                // Pass displayName data
                intent.putExtra("displayName", (athleteList.get(position).getDisplayName()));
                // Pass team data
                intent.putExtra("team", (athleteList.get(position).getTeam()));
                // Pass jerseyNumber data
                intent.putExtra("jerseyNumber", (athleteList.get(position).getJerseyNumber()));
             // Pass height data
                intent.putExtra("height", (athleteList.get(position).getHeight()));
             // Pass weight data
                intent.putExtra("weight", (athleteList.get(position).getWeight()));
                // Start ViewAthleteActivity Class
                mContext.startActivity(intent);
            }
        });
 
        return view;
    }
}
