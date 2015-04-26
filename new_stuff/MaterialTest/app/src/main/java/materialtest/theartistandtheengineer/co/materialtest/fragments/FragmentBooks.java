package materialtest.theartistandtheengineer.co.materialtest.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.activities.BuyActivity;
import materialtest.theartistandtheengineer.co.materialtest.activities.SellActivity;
import materialtest.theartistandtheengineer.co.materialtest.activities.SingleBookActivity;
import materialtest.theartistandtheengineer.co.materialtest.adapters.AdapterSearch;
import materialtest.theartistandtheengineer.co.materialtest.app.AppController;
import materialtest.theartistandtheengineer.co.materialtest.materialtest.MyApplication;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;
import materialtest.theartistandtheengineer.co.materialtest.pojo.Book;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNotifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBooks extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button button_sell;
    private Button button_buy;


    public static final String URL_BOOK = "https://www.googleapis.com/books/v1/volumes";
    public static final String URL_BOOK_SEARCH = "q=";
    public static final String URL_BOOK_CONTENTS = "francis+chan";
    public static final String URL_BOOK_START_INDEX = "startIndex=";
    public static final String URL_BOOK_MAX_RESULTS = "maxResults=";
    public static final String URL_BOOK_PARAM_API_KEY = "key=";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMPERSAND = "&";
    private static final String STATE_BOOKS = "state_books";

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Book> listBooks = new ArrayList<>();
    private RecyclerView listSearchedBooks;
    private AdapterSearch adapterSearch;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotifications.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBooks newInstance(String param1, String param2) {
        FragmentBooks fragment = new FragmentBooks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getRequestUrl(int startIndex, int maxResults) {

        return URL_BOOK
                + URL_CHAR_QUESTION
                + URL_BOOK_SEARCH
                + URL_BOOK_CONTENTS
                + URL_CHAR_AMPERSAND
                + URL_BOOK_START_INDEX
                + startIndex
                + URL_CHAR_AMPERSAND
                + URL_BOOK_MAX_RESULTS
                + maxResults
                + URL_CHAR_AMPERSAND
                + URL_BOOK_PARAM_API_KEY
                + AppController.API_KEY_GOOGLE_BOOKS;
    }

    public FragmentBooks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //volleySingleton = VolleySingleton.getInstance();
        //requestQueue = volleySingleton.getRequestQueue();
        //sendJsonRequest();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_books, container, false);
        button_buy = (Button) view.findViewById(R.id.button_buy);
        button_buy.setOnClickListener(this);
        button_sell = (Button) view.findViewById(R.id.button_sell);
        button_sell.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    /*
    button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SingleBookActivity.this,
                        "OnClickListener : " +
                                "\nSpinner: " + String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                Log.d("condition = ", String.valueOf(spinner.getSelectedItem()));
                Log.d("asking price = ", sell_amount.getText().toString());
            }
        });
     */


    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Log.d("PRESSED", (String) ((Button)v).getText());

        // Start BUY BOOK Intent
        if(((Button)v).getText().equals("BUY")) {
            Intent intent = new Intent(context, BuyActivity.class);
            startActivity(intent);
        }
        // Start SELL BOOK Intent
        else{
            Intent intent = new Intent(context, SellActivity.class);
            startActivity(intent);
        }



        //context.startActivity(intent);

        //if(((Button)v).getText().equals("SELL"))
            //Log.d("BUTTON", "IS SELL!!!");

        /*Button button_pressed = (Button)v;
        String buttonText = button_pressed.getText().toString();

        Log.d("Clicked ", buttonText);

        if(buttonText.equals("BUY"))
            Log.d("BUTTON", "IS BUY!!!");
        if(buttonText.equals("SELL"))
            Log.d("BUTTON", "IS SELL!!!");
        */

        //Context context = v.getContext();
        //Intent intent = new Intent(context, SingleBookActivity.class);



        /*
        public void onClick(View v) {
    // 1) Possibly check for instance of first
    Button b = (Button)v;
    String buttonText = b.getText().toString();
}
}
         */


    }
}
