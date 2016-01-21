package com.myxscq.jiekai.testhomework9demo1;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.pm.Signature;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
//    private final String LOG_TAG = SearchActivity.class.getSimpleName();
    private Button btnClear;
    private Button btnSearch;
    private EditText etKeyword;
    private EditText etPriceFrom;
    private EditText etPriceTo;
    private Spinner spSortBy;
    private TextView tvShowValidation;
    private String ebayResult;



    String jsonReslut = "";
    private String Keyword;

//    private String keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "do not forgot to your package name", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }



        btnClear = (Button)findViewById(R.id.btnClear);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        etKeyword = (EditText)findViewById(R.id.etKeyword);
        etPriceFrom = (EditText)findViewById(R.id.etPriceFrom);
        etPriceTo = (EditText)findViewById(R.id.etPriceTo);
        spSortBy = (Spinner)findViewById(R.id.spSortBy);
        tvShowValidation = (TextView)findViewById(R.id.tvShowValidation);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etKeyword.setText("");
                etPriceFrom.setText("");
                etPriceTo.setText("");
                spSortBy.setSelection(0);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForm();

            }
        });
    }

    private void checkForm(){
        float minPrice = 0;
        float maxPrice = 0;
        if(!etPriceFrom.getText().toString().equals("")){
            minPrice = Float.valueOf(etPriceFrom.getText().toString());
        }
        if(!etPriceTo.getText().toString().equals("")){
           maxPrice = Float.valueOf(etPriceTo.getText().toString());
        }


        if(etKeyword.getText().toString().equals("")){
            tvShowValidation.setText("Please input keywords");
        }else if(minPrice < 0 || maxPrice <0){
            tvShowValidation.setText("Both max price and min price must be positive");
        }
        else if(maxPrice < minPrice ){
            tvShowValidation.setText("Max Price cannot be smaller than min Price");
        }else {
            excuteSearchAPICall();
        }




    }

    private void excuteSearchAPICall(){
        GetSearchResult getSearchResult = new GetSearchResult();
        getSearchResult.execute();





    }



    public class GetSearchResult extends AsyncTask<String, Void, String> {


        String Keyword = etKeyword.getText().toString();
        String MinPrice = etPriceFrom.getText().toString();
        String MaxPrice = etPriceTo.getText().toString();
        String SortBy = spSortBy.getSelectedItem().toString();
        int SortByIndex = spSortBy.getSelectedItemPosition();


//        String sortOrder;
        String sortOrder = "BestMatch";

//        String SortBy = spSortBy.getSelectedItem().toString();


//        String awsURL = "http://testdemojiekai1-env.elasticbeanstalk.com/";
        String awsURL = "http://home9testdemo1-env.elasticbeanstalk.com/";
//        String awsURL =  "http://xinyi-env.elasticbeanstalk.com/";
        @Override
        protected String doInBackground(String... params) {
;

            if (SortBy.equals("Best Match"))
            {
                sortOrder = "BestMatch";
            }
            else if (SortBy.equals("Price: highest first"))
            {
                sortOrder = "PriceHighestFirst";
            }
            else if (SortBy.equals("Price + Shipping: highest first"))
            {
                sortOrder = "PriceShippingHighestFirst";
            }
            else if (SortBy.equals("Price + Shipping: lowest first"))
            {
                sortOrder = "PriceShippingLowestFirst";
            }
//            if(SortByIndex == 0){
//                sortOrder = "BestMatch";
//            }else if(SortByIndex == 1){
//                sortOrder = "PriceHighestFirst";
//            }else if(SortByIndex == 2){
//                sortOrder = "PriceShippingHighestFirst";
//            }else if(SortByIndex == 3){
//                sortOrder = "PriceShippingLowestFirst";
//            }

            String ebayResult=null;
            //HttpPost连接对象
            try{

                HttpGet httpRequest = new HttpGet(awsURL);
                Log.v("gzg","123");
                //设置参数
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("keywords", Keyword));
                param.add(new BasicNameValuePair("MaxPrice", MaxPrice));
                param.add(new BasicNameValuePair("MinPrice", MinPrice));
                param.add(new BasicNameValuePair("sortOrder", sortOrder));
                param.add(new BasicNameValuePair("entriesPerPage", "5"));
                param.add(new BasicNameValuePair("pageNumberCount", "1"));


//                param.add(new BasicNameValuePair("KeyWords", Keyword));
//                param.add(new BasicNameValuePair("PriceRange", MinPrice));
//                param.add(new BasicNameValuePair("PriceRange2", MaxPrice));
//                param.add(new BasicNameValuePair("SortBy", sortOrder));
//                param.add(new BasicNameValuePair("ResultsPerPage", "5"));
//                param.add(new BasicNameValuePair("pageNumber", "1"));
                URI ebayURI = URIUtils.createURI("http", "home9testdemo1-env.elasticbeanstalk.com", -1, "", URLEncodedUtils.format(param, "UTF-8"), null);
                Log.v("gzg","uri" + ebayURI.toString());
                HttpGet get = new HttpGet(ebayURI);
                //设置字符集,以及编码方式
                //HttpEntity httpEntity = new UrlEncodedFormEntity(param,"utf-8");
                //请求HttpRequest
                //httpRequest.setEntity(httpEntity);
                //取得默认的HttpClient
                HttpClient httpClient = new DefaultHttpClient();
                //取得HttpResponse
                HttpResponse httpResponse = httpClient.execute(get);
                ebayResult= EntityUtils.toString(httpResponse.getEntity());
                //HttpStatus.SC_OK表示连接成功
                Log.v("gzg", "" + httpResponse.getStatusLine().getStatusCode());
                //Log.v("gzg","123" + str);
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
//            Log.v("gzg","" + ebayResult);

            return ebayResult;









//            try {
//
//                String awsURL = "http://testdemojiekai1-env.elasticbeanstalk.com/?";
//
//                Uri preEbaySearchURL = Uri.parse(awsURL).buildUpon()
//                        .appendQueryParameter("Keyword", etKeyword.getText().toString())
//                        .appendQueryParameter("MaxPrice", etPriceTo.getText().toString())
//                        .appendQueryParameter("MinPrice", etPriceFrom.getText().toString())
//                        .appendQueryParameter("SortBy", spSortBy.getSelectedItem().toString())
//                        .appendQueryParameter("results_per_page", "5")
//                        .appendQueryParameter("pageNum", "1").build();
//
//
//                URL ebaySearchURL = new URL(preEbaySearchURL.toString());
//                //make URL connection
//                connectionEbayURL = (HttpURLConnection) ebaySearchURL.openConnection();
//                connectionEbayURL.setRequestMethod("GET");
//                connectionEbayURL.connect();
//
//
//                //Read Response
//                InputStream inputStream = connectionEbayURL.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    return null;
//                }
//
//                jsonResult = buffer.toString();
//                Log.v(LOG_TAG, "result json string: " + jsonResult);
//
//                return jsonResult;
//
//
////                return "hhhhh";
//            }catch (IOException e) {
//                Log.e(LOG_TAG, "Error", e);
//                return null;
//            }  finally {
//                if (connectionEbayURL != null) {
//                    connectionEbayURL.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e(LOG_TAG, "Error closing stream", e);
//                    }
//                }
//            }




        }

        protected void onPostExecute(String result) {
            //异步操作执行完后，在TextView中显示出来，也可以执行别的操作。
//            Log.v("gzg","" + ebayResult);
//            json_data = ebayResult;
//            Intent intent = new Intent();
            try {
                Log.v("test","-1");
                JSONObject ebayJSON = new JSONObject(result);
                if(ebayJSON.getString("ack").equals("Success")){
                    Log.v("test","0");
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("ebayResult",result);
                    bundle.putString("keyword", etKeyword.getText().toString());
//            intent.putExtra("ebayResult",ebayResult);
                    intent.putExtras(bundle);
//                intent.putExtra("keyword", etKeyword.getText().toString());
//                intent.putExtra("minPrice", etPriceFrom.getText().toString());
//                intent.putExtra("maxPrice", etPriceTo.getText().toString());
//        intent.putExtra("sortBy", spSortBy.getSelectedItem().toString());

                    intent.setClass(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    Log.v("test","1");
//            intent.setClass(MainActivity.this, SearchActivity.class);
//            startActivity(intent);
                }else{
                    Log.v("test","2");
                    tvShowValidation.setText("Result not found");
                }
            } catch (JSONException e) {
                Log.v("test","3");
                e.printStackTrace();
            }

        }

    }
}
