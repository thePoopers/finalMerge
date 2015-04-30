package materialtest.theartistandtheengineer.co.materialtest.adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.activities.SingleBookActivity;
import materialtest.theartistandtheengineer.co.materialtest.activities.SingleBuyBookActivity;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;
import materialtest.theartistandtheengineer.co.materialtest.pojo.Book;


public class AdapterBuy extends RecyclerView.Adapter<AdapterBuy.ViewHolderBookSearch> {
    public static final String URL_UBOOKS = "http://theartistandtheengineer.co/ubooks/";
    private ArrayList<Book> listBooks = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;


    public AdapterBuy(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setBookList(ArrayList<Book> listBooks){
        this.listBooks = listBooks;
        notifyItemRangeChanged(0, listBooks.size());
    }

    @Override
    public ViewHolderBookSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_book_buy, parent, false);
        ViewHolderBookSearch viewHolder = new ViewHolderBookSearch(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolderBookSearch holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Book currentBook = listBooks.get(position);
        String urlThumbnail = currentBook.geturlThumbnail();
        holder.url = urlThumbnail;
        holder.bookTitle.setText(currentBook.getTitle());
//        holder.bookAuthor.setText(currentBook.getAuthors());
//        holder.isbn_13.setText("ISBN: "+currentBook.getISBN_13());
//        holder.price.setText("Price: $"+currentBook.getPrice());
//        holder.bcondition.setText("Condition: "+currentBook.getBcondition());
//        holder.seller_id.setText("Seller ID: "+currentBook.getSellerId());
        //holder.reputation_avg.setText(currentBook.getReputationAvg());
        //holder.reputation_avg.setText(currentBook.getReputationAvg());



        if(urlThumbnail != null){
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener(){
                // if cant load image
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.bookThumbnail.setImageBitmap(response.getBitmap());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    //use implements View.OnCreateContextMenuListener for Context Menu
    static class ViewHolderBookSearch extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView bookThumbnail;
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView isbn_13;
        private String url;
        private TextView bcondition;
        private TextView transaction_status;
        //private TextView reputation_avg;
        private TextView seller_id;
        private TextView price;
        private TextView tid;

        public ViewHolderBookSearch(View itemView) {
            super(itemView);
            //bookThumbnail = (ImageView) itemView.findViewById(R.id.bookThumbnail);
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            /*bookAuthor = (TextView) itemView.findViewById(R.id.bookAuthor);
            isbn_13 = (TextView) itemView.findViewById(R.id.isbn_13);
            price = (TextView) itemView.findViewById(R.id.price);
            bcondition = (TextView) itemView.findViewById(R.id.bcondition);
            seller_id = (TextView) itemView.findViewById(R.id.seller_id);*/
            //reputation_avg = (TextView) itemView.findViewById(R.id.reputation_avg);
            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
//            AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
//            ad.setTitle("Commit to buy?");
//            ad.setMessage("setmessage");
//            ad.setIcon(R.drawable.ic_launcher);
//            ad.setPositiveButton("BUY", null);
//            ad.setNeutralButton("Cancel", null);
//            ad.setCancelable(false);
//            ad.create();
//            ad.show();

            String[] bookDataArray = {
                    (String) bookTitle.getText()
//                    (String) bookAuthor.getText(),
//                    (String) isbn_13.getText(),
//                    (String) price.getText(),
//                    (String) bcondition.getText(),
//                    (String) seller_id.getText(),
//                    url
            };

            Context context = itemView.getContext();
            Intent intent = new Intent(context, SingleBuyBookActivity.class);
//            intent.putExtra("bookTitle", bookDataArray[0]);
//            intent.putExtra("bookAuthor", bookDataArray[1]);
//            intent.putExtra("isbn_13", bookDataArray[2]);
//            intent.putExtra("url", bookDataArray[3]);
//            intent.putExtra("price", bookDataArray[4]);
//            intent.putExtra("bcondition", bookDataArray[5]);
//            intent.putExtra("seller_id", bookDataArray[6]);*/
            context.startActivity(intent);
        }
    }

}