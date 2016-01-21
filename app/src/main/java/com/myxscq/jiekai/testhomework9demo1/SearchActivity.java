package com.myxscq.jiekai.testhomework9demo1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.ImageDownloader;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;


public class SearchActivity extends Activity {

//    private final String LOG_TAG = SearchActivity.class.getSimpleName();
    private EditText etKeyword;
    private EditText etPriceFrom;
    private EditText etPriceTo;
    private Spinner spSortBy;

    private String Keyword;
    private String MaxPrice;
    private String MinPrice;
    private String SortBy;
    private TextView testViewHH;
    private ListView ResultDisplay;
    private List<Map<String, String>> jsonListData;
    private ImageView ivImageDisplay0;
    private ImageView ivImageDisplay1;
    private ImageView ivImageDisplay2;
    private ImageView ivImageDisplay3;
    private ImageView ivImageDisplay4;

    private TextView tvItemTitle0;
    private TextView tvItemTitle1;
    private TextView tvItemTitle2;
    private TextView tvItemTitle3;
    private TextView tvItemTitle4;

    private TextView tvItemPrice0;
    private TextView tvItemPrice1;
    private TextView tvItemPrice2;
    private TextView tvItemPrice3;
    private TextView tvItemPrice4;


    public static int index = 0;
    BitmapFactory.Options bmOptions;
    static Bitmap bm;
    String imageUrl = "";
    String itemIndex = "";
    JSONObject ebayJSON;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> galleryURLList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        ResultDisplay = (ListView)findViewById(R.id.lvResultDisplay);
        ivImageDisplay0 = (ImageView) findViewById(R.id.ivImageDisplay0);
        ivImageDisplay1 = (ImageView) findViewById(R.id.ivImageDisplay1);
        ivImageDisplay2 = (ImageView) findViewById(R.id.ivImageDisplay2);
        ivImageDisplay3 = (ImageView) findViewById(R.id.ivImageDisplay3);
        ivImageDisplay4 = (ImageView) findViewById(R.id.ivImageDisplay4);

        tvItemTitle0 = (TextView) findViewById(R.id.tvItemTitle0);
        tvItemTitle1 = (TextView) findViewById(R.id.tvItemTitle1);
        tvItemTitle2 = (TextView) findViewById(R.id.tvItemTitle2);
        tvItemTitle3 = (TextView) findViewById(R.id.tvItemTitle3);
        tvItemTitle4 = (TextView) findViewById(R.id.tvItemTitle4);

        tvItemPrice0 = (TextView) findViewById(R.id.tvItemPrice0);
        tvItemPrice1 = (TextView) findViewById(R.id.tvItemPrice1);
        tvItemPrice2 = (TextView) findViewById(R.id.tvItemPrice2);
        tvItemPrice3 = (TextView) findViewById(R.id.tvItemPrice3);
        tvItemPrice4 = (TextView) findViewById(R.id.tvItemPrice4);



        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//       intent = this.getIntent().getExtras();

//        String ebayResult = intent.getStringExtra("ebayResult");
        String ebayResult = bundle.getString("ebayResult");
        String Keyword = bundle.getString("keyword");
        try {
            ebayResult = ebayResult.replaceAll("\\s","");
            ebayResult = ebayResult.replaceAll("\\n","");
        }catch (Throwable t){
            Toast.makeText(this, "xinyi is big bad egg", Toast.LENGTH_SHORT).show();
        }


//        String Keyword = intent.getStringExtra("Keyword");



//
        try {

            testViewHH = (TextView)findViewById(R.id.tvKeywordTitle);
            testViewHH.setText("Results for '"+Keyword+"'");

            parseJSONData(ebayResult);
//
//            Log.v("My App", ebayJSON.toString());
//            Toast.makeText(this, ebayJSON.toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "xinyi is big bad egg", Toast.LENGTH_SHORT).show();
           Log.v("My App", "xinyi is big bad egg");

        } catch (Throwable t) {
            Log.v("My App", "There is a mistake" );
        }
    }

    private void parseJSONData(String jsonData) throws JSONException {
        ebayJSON = new JSONObject(jsonData);
//        int jsonLength = ebayJSON.length();
//
        for(int i=0; i<=4;i++){

           itemIndex = "item" + i;
//            String item0 = "item0";
//            JSONObject itemContent = ebayJSON.getJSONObject(itemIndex);

        JSONObject itemContent = ebayJSON.getJSONObject(itemIndex);
        JSONObject itemBasicInfo = itemContent.getJSONObject("basicInfo");

        String itemGalleryURL = itemBasicInfo.getString("galleryURL");

        String itemTitle = "";
        itemTitle = itemBasicInfo.getString("title");
//            Toast.makeText(this, itemTitle, Toast.LENGTH_SHORT).show();

        String itemCurrentPrice = "";
        itemCurrentPrice = itemBasicInfo.getString("convertedCurrentPrice");
        String itemShippingServiceCost = "";
        itemShippingServiceCost = itemBasicInfo.getString("shippingServiceCost");
        String itemPrice ="";

        if(itemShippingServiceCost.equals("") || itemShippingServiceCost.equals("0.0")){
            itemPrice = "Price:$" + itemCurrentPrice + "(FREE Shipping)";
        }else{
            itemPrice = "Price:$" + itemCurrentPrice + "(+ $" + itemShippingServiceCost + "Shipping)";
        }
            Log.v("MyAPP",itemPrice);

            if(itemIndex.equals("item0") ){
                if(!itemTitle.equals("")){
                    tvItemTitle0.setText(itemTitle);
                }
                if(!itemPrice.equals("")){
                    tvItemPrice0.setText(itemPrice);
                }
            }else if(itemIndex.equals("item1")){
                if(!itemTitle.equals("")){
                    tvItemTitle1.setText(itemTitle);
                }
                if(!itemPrice.equals("")){
                    tvItemPrice1.setText(itemPrice);
                }
            }else if(itemIndex.equals("item2")){
                if(!itemTitle.equals("")){
                    tvItemTitle2.setText(itemTitle);
                }
                if(!itemPrice.equals("")){
                    tvItemPrice2.setText(itemPrice);
                }
            }else if(itemIndex.equals("item3")){
                if(!itemTitle.equals("")){
                    tvItemTitle3.setText(itemTitle);
                }
                if(!itemPrice.equals("")){
                    tvItemPrice3.setText(itemPrice);
                }
            }else if(itemIndex.equals("item4")){
                if(!itemTitle.equals("")){
                    tvItemTitle4.setText(itemTitle);
                }
                if(!itemPrice.equals("")){
                    tvItemPrice4.setText(itemPrice);
                }
            }else{
                Log.v("app", "xinyi is a big egg");
            }

        Log.v("app",itemGalleryURL);
//        Toast.makeText(this, itemGalleryURL, Toast.LENGTH_SHORT).show();
//        imageUrl = itemGalleryURL;
//        Bitmap bitmap = getHttpBitmap(itemGalleryURL);
//        Bitmap bitmap = getHttpBitmap("http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690");
        Log.v("hhh",itemIndex);
        new ImageDownload().execute();
//        ivImageTest.setImageBitmap(bitmap);

        //ListView Test

//                try {
//                    jsonListData =  getData(ebayJSON);
//
//                    ResultDisplayAdapter adapter = new ResultDisplayAdapter(this);
//                    ResultDisplay.setAdapter(adapter);
//                }catch (Throwable t) {
//                    Log.v("My App", "List mistake" );
//                }




//            HashMap<String, String> goodItem = new HashMap<>();
//            JSONObject itemBasicInfo = itemContent.getJSONObject("basicInfo");
//
//
//            String itemTitle = itemBasicInfo.getString("Title");
//
//            String itemGalleryURL = itemBasicInfo.getString("galleryURL");
//            goodItem.put("title", itemTitle);
//            goodItem.put("galleryURL", itemGalleryURL);
//            titleList.add(itemTitle);
//            galleryURLList.add(itemGalleryURL);

        }
    }



    public class ImageDownload extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {
            String itemIndexInside = "item" + index;

            Log.v("dsds","ddsdsds"+itemIndexInside);
            JSONObject itemContent = null;
            try {
                itemContent = ebayJSON.getJSONObject(itemIndexInside);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("hhhh","1");
            }
            JSONObject itemBasicInfo = null;
            try {
                itemBasicInfo = itemContent.getJSONObject("basicInfo");
                Log.v("hhhh","2");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("hhhh","3");
            }

            try {
                String itemGalleryURL = itemBasicInfo.getString("galleryURL");
                imageUrl = itemGalleryURL;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("hhhh","4");
            }
            // TODO Auto-generated method stub
            bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            loadBitmap(imageUrl, bmOptions);
            index++;
            return imageUrl;
        }

        protected void onPostExecute(String imageUrl) {
//            Log.v("this",index.toString());
//            index--;
//            String itemIndexInside = "item" + index;
            String testIndex = "item"+index;
            Log.v("hahah",testIndex);
//            String imageUrl = itemGalleryURL;
//            pd.dismiss();
            Log.v("MyApp1", imageUrl);
//            if (!imageUrl.equals("")) {
//            Log.v("MyApp2", "11111111"+itemIndexInside);
//                if(itemIndex.equals("item0")){
//                    Log.v("MyApp", imageUrl);
//                    ivImageDisplay0.setImageBitmap(bm);
//                }else if(itemIndex.equals("item1")){
//                    ivImageDisplay1.setImageBitmap(bm);
//                    Log.v("MyApp", imageUrl);
//                }else if(itemIndex.equals("item2")){
//                    ivImageDisplay2.setImageBitmap(bm);
//                    Log.v("MyApp", imageUrl);
//                }else if(itemIndex.equals("item3")){
//                    ivImageDisplay3.setImageBitmap(bm);
//                    Log.v("MyApp", imageUrl);
//                }else if(itemIndex.equals("item4")){
//                    ivImageDisplay4.setImageBitmap(bm);
//                    Log.v("MyApp", imageUrl);
//                }





            if(index == 1){
                Log.v("MyApp", imageUrl);
                ivImageDisplay0.setImageBitmap(bm);
            }else if(index == 2){
                ivImageDisplay1.setImageBitmap(bm);
                Log.v("MyApp", imageUrl);
            }else if(index == 3){
                ivImageDisplay2.setImageBitmap(bm);
                Log.v("MyApp", imageUrl);
            }else if(index == 4){
                ivImageDisplay3.setImageBitmap(bm);
                Log.v("MyApp", imageUrl);
            }else if(index == 5){
                ivImageDisplay4.setImageBitmap(bm);
                Log.v("MyApp", imageUrl);
            }






//            } else {
//                Log.v("app","An mistake happened");
//            }
        }

    }

    public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bm = BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (IOException e1) {
        }
        return bm;
    }

    private static InputStream OpenHttpConnection(String strURL)
            throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {
        }
        return inputStream;
    }
//    public static Bitmap getHttpBitmap(String url){
//        URL myFileURL;
//        Bitmap bitmap=null;
//        try{
//            myFileURL = new URL(url);
//            //获得连接
//            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
//            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
//            conn.setConnectTimeout(6000);
//            //连接设置获得数据流
//            conn.setDoInput(true);
//            //不使用缓存
//            conn.setUseCaches(false);
//            //这句可有可无，没有影响
//            //conn.connect();
//            //得到数据流
//            Log.v("app", "this is a mistake 0");
//            InputStream is = conn.getInputStream();
//            Log.v("app", "this is a mistake 1");
//            //解析得到图片
//            bitmap = BitmapFactory.decodeStream(is);
//            Log.v("app", "this is a mistake 2");
//            //关闭数据流
//            is.close();
//        }catch(Exception e){
//            e.printStackTrace();
//            Log.v("app", "this is a mistake 3");
//        }
//
//        return bitmap;
//
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        index = 0;
        return super.onKeyDown(keyCode, event);


    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }


    private List<Map<String, String>> getData(JSONObject ebayJSONResult) throws JSONException {



        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int i=0; i<4;i++) {

            String itemIndex = "item" + i;
            JSONObject itemContent = ebayJSONResult.getJSONObject(itemIndex);

//            getData(itemContent);

            Map<String, String> map = new HashMap<String, String>();

            JSONObject itemBasicInfo = itemContent.getJSONObject("basicInfo");

            String itemTitle = itemBasicInfo.getString("Title");

            String itemGalleryURL = itemBasicInfo.getString("galleryURL");
            map.put("title", itemTitle);
            map.put("galleryURL", itemGalleryURL);

            list.add(map);

        }
        return list;
    }


    public final class ViewHolder {
        public ImageView ivGalleryImage;
        public TextView tvItemContent;
        public TextView tvItemPrice;
    }

    public class ResultDisplayAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ResultDisplayAdapter(Context context){

            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return jsonListData.size();
//            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_display, null);
                holder.ivGalleryImage = (ImageView)convertView.findViewById(R.id.ivGalleryImage);
                holder.tvItemContent = (TextView)convertView.findViewById(R.id.tvItemContent);
                holder.tvItemPrice = (TextView)convertView.findViewById(R.id.tvItemPrice);

                convertView.setTag(holder);

            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvItemContent.setText((String)jsonListData.get(position).get("title"));

            holder.tvItemPrice.setText((String)jsonListData.get(position).get("galleryURL"));


            return convertView;








        }
    }


    public void titleOnClick0(View view) throws JSONException {

        JSONObject itemContent = ebayJSON.getJSONObject("item0");
//        JSONObject itemBasicInfo = itemContent.getJSONObject("basicInfo");
//
//        String itemGalleryURL = itemBasicInfo.getString("galleryURL");
//        String itemTitle = itemBasicInfo.getString("title");
//        String itemPictureURLSuperSize = itemBasicInfo.getString("pictureURLSuperSize");
        Intent intent = new Intent();
//            intent.putExtra("itemTitle",itemTitle);
//            intent.putExtra("itemGalleryURL",itemGalleryURL);
//            intent.putExtra("itemPictureURLSuperSize",itemPictureURLSuperSize);
        intent.putExtra("itemContent", itemContent.toString());

        intent.setClass(SearchActivity.this, DetailsActivity.class);
        startActivity(intent);

    }


    public void titleOnClick1(View view) throws JSONException {

        JSONObject itemContent = ebayJSON.getJSONObject("item1");
        Intent intent = new Intent();
        intent.putExtra("itemContent", itemContent.toString());
        intent.setClass(SearchActivity.this, DetailsActivity.class);
        startActivity(intent);
    }
    public void titleOnClick2(View view) throws JSONException {

        JSONObject itemContent = ebayJSON.getJSONObject("item2");
        Intent intent = new Intent();
        intent.putExtra("itemContent", itemContent.toString());
        intent.setClass(SearchActivity.this, DetailsActivity.class);
        startActivity(intent);
    }
    public void titleOnClick3(View view) throws JSONException {

        JSONObject itemContent = ebayJSON.getJSONObject("item3");
        Intent intent = new Intent();
        intent.putExtra("itemContent", itemContent.toString());
        intent.setClass(SearchActivity.this, DetailsActivity.class);
        startActivity(intent);
    }
    public void titleOnClick4(View view) throws JSONException {

        JSONObject itemContent = ebayJSON.getJSONObject("item4");
        Intent intent = new Intent();
        intent.putExtra("itemContent", itemContent.toString());
        intent.setClass(SearchActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    public void galleryImageOnclick0(View v){
        String link = null;
        try {
            link = ebayJSON.getJSONObject("item0").getJSONObject("basicInfo").getString("viewItemURL");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void galleryImageOnclick1(View v){
        String link = null;
        try {
            link = ebayJSON.getJSONObject("item1").getJSONObject("basicInfo").getString("viewItemURL");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void galleryImageOnclick2(View v){
        String link = null;
        try {
            link = ebayJSON.getJSONObject("item2").getJSONObject("basicInfo").getString("viewItemURL");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void galleryImageOnclick3(View v){
        String link = null;
        try {
            link = ebayJSON.getJSONObject("item3").getJSONObject("basicInfo").getString("viewItemURL");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void galleryImageOnclick4(View v){
        String link = null;
        try {
            link = ebayJSON.getJSONObject("item4").getJSONObject("basicInfo").getString("viewItemURL");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



