package materialtest.theartistandtheengineer.co.materialtest.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request.Method;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import materialtest.theartistandtheengineer.co.materialtest.R;
import materialtest.theartistandtheengineer.co.materialtest.activities.BuyActivity;
import materialtest.theartistandtheengineer.co.materialtest.activities.SellActivity;
import materialtest.theartistandtheengineer.co.materialtest.adapters.AdapterBuy;
import materialtest.theartistandtheengineer.co.materialtest.app.AppController;
import materialtest.theartistandtheengineer.co.materialtest.network.VolleySingleton;
import materialtest.theartistandtheengineer.co.materialtest.pojo.Book;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSell.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSell#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuy extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String URL_BOOK = "https://www.googleapis.com/books/v1/volumes";

    public static final String URL_UBOOKS = "http://theartistandtheengineer.co/ubooks/";

    public static final String URL_BOOK_SEARCH = "q=";
    private static String URL_BOOK_CONTENTS = "";
    public static final String URL_BOOK_START_INDEX = "startIndex=";
    public static final String URL_BOOK_MAX_RESULTS = "maxResults=";
    public static final String URL_BOOK_PARAM_API_KEY = "key=";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMPERSAND = "&";
    private static final String STATE_BOOKS = "state_books";



    private Spinner spinner1;
    private Spinner spinner2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private VolleySingleton volleySingleton;
    private ProgressDialog pDialog;
    private ArrayList<Book> listBooks = new ArrayList<>();
    private RecyclerView listSearchedBooks;
    private AdapterBuy adapterBuy;
    private EditText search_book;
    private Button button_search;

    // TODO: Rename and change types and number of parameters
    public static FragmentBuy newInstance(String param1, String param2) {
        FragmentBuy fragment = new FragmentBuy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_BOOKS, listBooks);
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

    public static String getRequestUrl(String author) {
        return URL_UBOOKS + "?author="+author;
    }



    public FragmentBuy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        //sendJsonRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_buy, container, false);

        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        search_book = (EditText) view.findViewById(R.id.search_book);
        button_search = (Button) view.findViewById(R.id.button_search);
        button_search.setOnClickListener(this);

        listSearchedBooks = (RecyclerView) view.findViewById(R.id.listSearchedBooks);
        listSearchedBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBuy = new AdapterBuy(getActivity());
        listSearchedBooks.setAdapter(adapterBuy);

        if(savedInstanceState != null){
            listBooks = savedInstanceState.getParcelableArrayList(STATE_BOOKS);
            adapterBuy.setBookList(listBooks);
        }else if(listBooks != null) {

        }
        else {
            //sendJsonRequest();
        }
        /*
        listSearchedBooks.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), listSearchedBooks, new ClickListener() {
            // Here you can start a new activity or adding to the list of selected list that you want
            @Override
            public void onClick(View view, int position) {
                //startActivity(new Intent(getActivity(), SingleBookActivity.class));
                Toast.makeText(getActivity(), "onClick " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "onLongClick " + position, Toast.LENGTH_SHORT).show();
            }
        }));*/


        return view;
    }

    private void sendJsonRequest(final String author) {
        StringRequest strReq = new StringRequest(Method.POST,
                URL_UBOOKS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONArray returnData = jObj.getJSONArray("data");
                        for ( int i = 0 ; i < returnData.length(); i++ ){
                            String author = returnData.getJSONObject(i).getString("author");
                            String title = returnData.getJSONObject(i).getString("title");
                            String image_url = returnData.getJSONObject(i).getString("image_url");
                            String bcondition = returnData.getJSONObject(i).getString("bcondition");
                            String price = returnData.getJSONObject(i).getString("price");
                            String isbn = returnData.getJSONObject(i).getString("isbn");
                            String transaction_status = returnData.getJSONObject(i).getString("transaction_status");
                            String seller_id = returnData.getJSONObject(i).getString("seller_id");
                            int tid = (int)returnData.getJSONObject(i).getInt("tid");

                            Book book = new Book();
                            book.setAuthors(author);
                            book.setImageLinks(image_url);
                            book.setTitle(title);
                            book.setCondition(bcondition);
                            book.setISBN_13(bcondition);
                            book.setPrice(price);
                            book.setTransactionStatus(transaction_status);
                            book.seturlThumbnail(image_url);
                            book.setSellerId(seller_id);
                            book.setTid(tid);

                            listBooks.add(book);

                        }

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "buysearch");
                params.put("author", author);
                //params.put("bcondition", bcondition);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "response");
    }




    private ArrayList<Book> parseJSONResponse(JSONObject response) {
        ArrayList<Book> listBooks = new ArrayList<>();

        try {
            StringBuilder data = new StringBuilder();
            // If there are results
            if (response.has("items")) {
                // store all of the results in an JSON array
                JSONArray arrayBooks = response.getJSONArray("items");
                // loop through each of the results(array)
                for (int i = 0; i < arrayBooks.length(); i++) {

                    JSONObject currentBook = arrayBooks.getJSONObject(i);
                    String id = currentBook.getString("id");
                    // make the volumeInfo JSON Object
                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                    // title
                    String volumeTitle = volumeInfo.getString("title");
                    // author
                    JSONArray volumeAuthor = volumeInfo.getJSONArray("authors");
                    String author = volumeAuthor.getString(0);
                    // isbn's
                    JSONArray volumeIndustryIdentifier = volumeInfo.getJSONArray("industryIdentifiers");
                    JSONObject isbn_type1 = volumeIndustryIdentifier.getJSONObject(0);
                    JSONObject isbn_type2 = volumeIndustryIdentifier.getJSONObject(1);

                    String isbn1 = isbn_type1.getString("identifier");
                    String isbn2 = isbn_type2.getString("identifier");
                    String isbn = null;

                    if (isbn1.length() > 10) {
                        isbn = isbn1;
                    } else {
                        isbn = isbn2;
                    }

                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    String urlThumbnail = imageLinks.getString("thumbnail");
                    Book book = new Book();
                    book.setTitle(volumeTitle);
                    book.setAuthors(author);
                    book.setISBN_13(isbn);
                    book.seturlThumbnail(urlThumbnail);

                    listBooks.add(book);
                    //date stuff at end of video 37
                    //data.append(id + "\n" + volumeTitle + "\n" + author + "\n" + identifier + "\n");
                }
                //L.T(getActivity(), listBooks.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listBooks;
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        //searchBook(search_book.getText().toString(), String.valueOf(spinner1.getSelectedItem()), String.valueOf(spinner2.getSelectedItem()));
        //String.valueOf(spinner1.getSelectedItem());
        //Log.d("BUTTON!!! ", String.valueOf(v));
        Log.d("BOOK INFO", search_book.getText().toString());
        Log.d("SEARCH TYPE", String.valueOf(spinner1.getSelectedItem()));
        Log.d("CONDITION TYPE", String.valueOf(spinner2.getSelectedItem()));
        URL_BOOK_CONTENTS = search_book.getText().toString();
        sendJsonRequest(search_book.getText().toString());
    }

//    private void searchBook(final String search_book, final String search_type, final String condition){
//        String tag_string_req = "req_search_book";
//        //pDialog.setMessage("Searching School DB ...");
//        //showDialog();
//
//        StringRequest strReq = new StringRequest(Method.POST,
//                getRequestUrl(search_book), new Response.Listener<String>(){
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("RESPONSE", response);
//                Toast.makeText(getActivity(),
//                        response, Toast.LENGTH_LONG).show();
//                //hideDialog();
//
//                try{
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    if(!error){
//
//                    }
//                }
//                catch(JSONException e){
//
//                }
//            }
//
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getActivity(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("tag", "buysearch");
//                params.put("author", search_book);
//                //params.put("password", password);
//
//                return params;
//            }
//
//        };
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    private class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show();*/

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }*/

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
