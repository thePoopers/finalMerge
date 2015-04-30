
package materialtest.theartistandtheengineer.co.materialtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.pojo.MainMenuInformation;


/**
 * Created by joshgenao on 2/22/15.
 * The Menu Adapter allows an activity to start from selecting the item in the Menu
 */
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    private Context context;
    private static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    List<MainMenuInformation> data = Collections.emptyList();
    private HashMap<String, String> userData;
    private String email;
    private String name;

    public MenuAdapter(Context context, List<MainMenuInformation> data, String email, String name)//, HashMap<String, String> userData)
    {
        inflater = LayoutInflater.from(context);
        //this.userData = userData;
        this.email = email;
        this.name = name;
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER)
        {
            // View represents the custom Linear Layout in custom_row.xml
            View view = inflater.inflate(R.layout.drawer_header, parent, false);

            HeaderHolder holder = new HeaderHolder(view);

            return holder;
        }
        else
        {
            // View represents the custom Linear Layout in custom_row.xml
            View view = inflater.inflate(R.layout.custom_row, parent, false);

            ItemHolder holder = new ItemHolder(view);

            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
        {
            return TYPE_HEADER;
        }
        else
        {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HeaderHolder)
        {

            HeaderHolder headerHolder = (HeaderHolder) holder;

            headerHolder.name.setText(name);

            headerHolder.email.setText(email);

            //headerHolder.circularImageView.setImageBitmap(R.layout.);


        }

        else {
            ItemHolder itemHolder = (ItemHolder) holder;

            MainMenuInformation current = data.get(position - 1);

            itemHolder.title.setText(current.title);

            itemHolder.icon.setImageResource(current.iconId);
        }

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    class ItemHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView icon;

        // This is where we find out text view and image in the custom_row.xml
        public ItemHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);

            icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        TextView email;
        //CircularImageView circularImageView;

        // This is where we find out text view and image in the custom_row.xml
        public HeaderHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.user_name);

            email = (TextView) itemView.findViewById(R.id.email_name);

            //    circularImageView = (CircularImageView) itemView.findViewById(R.id.image_profile);
        }
    }


}
