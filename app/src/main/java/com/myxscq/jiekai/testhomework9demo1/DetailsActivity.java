package com.myxscq.jiekai.testhomework9demo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class DetailsActivity extends ActionBarActivity {


    ImageView mImgView1;
    static Bitmap bm;
    ProgressDialog pd;
    String imageUrl = "";
    BitmapFactory.Options bmOptions;
    private String itemPrice = "Price:N/A";
    private String itemLocation = "Location:N/A";
    private String itemGalleryURL = "";
    private String itemTitle = "";
    private String itemURL = "";
    private ImageView ivDetailImageDisplay;
    private TabHost tabHost;
    private TextView tvDetailsTitle;
    private TextView tvDetailsPrice;
    private TextView tvDetailsLocation;
    private TextView tvDetailsCategoryName;
    private TextView tvDetailsConditionContent;
    private TextView tvDetailsBuyingFormatContent;

    private JSONObject itemBasicInfo;
    private JSONObject itemSellerInfo;
    private JSONObject itemShippingInfo;

    private TextView tvDetailsUserNameContent;
    private TextView tvDetailsFeedbackScoreContent;
    private TextView tvDetailsPositiveScoreContent;
    private TextView tvDetailsFeedbackRatingContent;
    private TextView tvStoreContent;
    private ImageView ivDetailsTopRatedContent;

    private TextView tvDetailsShippingTypeContent;
    private TextView tvDetailsHandlingTimeContent;
    private TextView tvDetailsShipingLocationContent;
    private ImageView ivDetailsExpeditedShippingContent;
    private ImageView ivDetailsOneDayShippingContent;
    private ImageView ivDetailsReturnAcceptedContent;

    private View basicInfoLayout;
    private View sellerLayout;
    private View shippingLayout;
    private Button btBaciInfo;
    private Button btSeller;
    private Button btShipping;
    private Button btnBuyNow;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    private ImageView imDetailsTopRated;
    private ImageView imDetailsFacebook;

//    private Object mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_details);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        imDetailsFacebook = (ImageView)findViewById(R.id.imDetailsFacebook);
        imDetailsFacebook.setOnClickListener(new FacebookShareFunction());

        Intent intent = this.getIntent();

        //Get the relativelayout container
        basicInfoLayout = (View)findViewById(R.id.basicInfoLayout);
        sellerLayout = (View)findViewById(R.id.sellerLayout);
        shippingLayout = (View)findViewById(R.id.shippingLayout);

        //Get the Button
        btBaciInfo = (Button)findViewById(R.id.btBaciInfo);
        btSeller = (Button)findViewById(R.id.btSeller);
        btShipping = (Button)findViewById(R.id.btShipping);
        btnBuyNow = (Button)findViewById(R.id.btnBuyNow);

//        String itemTitle = intent.getStringExtra("itemTitle");
//        String itemGalleryURL = intent.getStringExtra("itemGalleryURL");
        ivDetailImageDisplay = (ImageView)findViewById(R.id.ivDetailImageDisplay);
        tvDetailsTitle = (TextView)findViewById(R.id.tvDetailsTitle);
        tvDetailsPrice = (TextView)findViewById(R.id.tvDetailsPrice);
        tvDetailsLocation = (TextView)findViewById(R.id.tvDetailsLocation);
        tvDetailsCategoryName = (TextView)findViewById(R.id.tvDetailsCategoryName);
        tvDetailsConditionContent = (TextView)findViewById(R.id.tvDetailsConditionContent);
        tvDetailsBuyingFormatContent = (TextView)findViewById(R.id.tvDetailsBuyingFormatContent);
        tvDetailsUserNameContent = (TextView)findViewById(R.id.tvDetailsUserNameContent);
        tvDetailsFeedbackScoreContent = (TextView)findViewById(R.id.tvDetailsFeedbackScoreContent);
        tvDetailsPositiveScoreContent = (TextView)findViewById(R.id.tvDetailsPositiveScoreContent);
        tvDetailsFeedbackRatingContent = (TextView)findViewById(R.id.tvDetailsFeedbackRatingContent);
        ivDetailsTopRatedContent = (ImageView)findViewById(R.id.ivDetailsTopRatedContent);
        tvStoreContent = (TextView)findViewById(R.id.tvStoreContent);
        tvDetailsShippingTypeContent = (TextView)findViewById(R.id.tvDetailsShippingTypeContent);
        tvDetailsHandlingTimeContent = (TextView)findViewById(R.id.tvDetailsHandlingTimeContent);
        tvDetailsShipingLocationContent = (TextView)findViewById(R.id.tvDetailsShipingLocationContent);
        ivDetailsExpeditedShippingContent = (ImageView)findViewById(R.id.ivDetailsExpeditedShippingContent);
        ivDetailsOneDayShippingContent = (ImageView)findViewById(R.id.ivDetailsOneDayShippingContent);
        ivDetailsReturnAcceptedContent = (ImageView)findViewById(R.id.ivDetailsReturnAcceptedContent);

        imDetailsTopRated = (ImageView)findViewById(R.id.imDetailsTopRated);

        String itemContent = intent.getStringExtra("itemContent");
        Log.v("Myapp ","This is the mistake1");
        try {
            JSONObject itemJSONContent = new JSONObject(itemContent);
            itemBasicInfo = itemJSONContent.getJSONObject("basicInfo");
            itemSellerInfo = itemJSONContent.getJSONObject("sellerInfo");
            itemShippingInfo = itemJSONContent.getJSONObject("shippingInfo");

            itemGalleryURL = itemBasicInfo.getString("galleryURL");
            itemTitle = itemBasicInfo.getString("title");
            itemURL = itemBasicInfo.getString("viewItemURL");
            String itemCurrentPrice = itemBasicInfo.getString("convertedCurrentPrice");
            String itemShippingServiceCost = itemBasicInfo.getString("shippingServiceCost");
            itemLocation = itemBasicInfo.getString("location");
            String itemPictureURLSuperSize = itemBasicInfo.getString("pictureURLSuperSize");
            String itemCategoryName = itemBasicInfo.getString("categoryName");
            String itemCondition = itemBasicInfo.getString("conditionDisplayName");
            String itemFixedType = itemBasicInfo.getString("listingType");


            String itemUserName = itemSellerInfo.getString("sellerUserName");
            String itemFeedbackScore = itemSellerInfo.getString("feedbackScore");
            String itemPositiveFeedback = itemSellerInfo.getString("positiveFeedbackPercent");
            String itemFeedbackRating = itemSellerInfo.getString("feedbackRatingStar");
            String itemTopRatedSeller = itemSellerInfo.getString("topRatedSeller");

            String itemSellerStoreName = itemSellerInfo.getString("sellerStoreName");

            String itemShippingType = itemShippingInfo.getString("shippingType");
            String itemHandlingTime = itemShippingInfo.getString("handlingTime") + "day";
            String itemShipToLocations = itemShippingInfo.getString("shipToLocations");
            String itemExpeditedShipping = itemShippingInfo.getString("expeditedShipping");
            String itemOneDayShippingAvailable = itemShippingInfo.getString("oneDayShippingAvailable");
            String itemReturnsAccepted = itemShippingInfo.getString("returnsAccepted");


            String itemBuyingFormat = "";
            if(itemFixedType.equals("FixedPrice") || itemFixedType.equals("StoreInventory")){
                itemBuyingFormat = "Buy It Now";
            }else if(itemFixedType.equals("Auction")){
                itemBuyingFormat = "Auction";
            }else if(itemFixedType.equals("Classified")){
                itemBuyingFormat = "Classified Ad";
            }else{
                itemBuyingFormat = "N/A";
            }


            if(itemShippingServiceCost.equals("") || itemShippingServiceCost.equals("0.0")){
                itemPrice = "Price:$" + itemCurrentPrice + "(FREE Shipping)";
            }else{
                itemPrice = "Price:$" + itemCurrentPrice + "(+ $" + itemShippingServiceCost + "Shipping)";
            }
            if(!itemPictureURLSuperSize.equals("")){
                imageUrl = itemPictureURLSuperSize;
            }else{
                imageUrl = itemGalleryURL;
            }
//            Toast.makeText(this, itemTitle, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, itemGalleryURL, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, itemPictureURLSuperSize, Toast.LENGTH_SHORT).show();

            tvDetailsTitle.setText(itemTitle);
            tvDetailsPrice.setText(itemPrice);
            tvDetailsLocation.setText(itemLocation);
            tvDetailsCategoryName.setText(itemCategoryName);
            tvDetailsConditionContent.setText(itemCondition);
            tvDetailsBuyingFormatContent.setText(itemBuyingFormat);

            tvDetailsUserNameContent.setText(itemUserName);
            tvDetailsFeedbackScoreContent.setText(itemFeedbackScore);
            tvDetailsPositiveScoreContent.setText(itemPositiveFeedback);
            tvDetailsFeedbackRatingContent.setText(itemFeedbackRating);
            if(itemTopRatedSeller.equals("true")){
                ivDetailsTopRatedContent.setImageResource(R.drawable.check);
                imDetailsTopRated.setImageResource(R.drawable.itemtoprated);
            }else if(itemTopRatedSeller.equals("false")){
                ivDetailsTopRatedContent.setImageResource(R.drawable.uncheck);
            }


            if(!itemSellerStoreName.equals("")){
                tvStoreContent.setText(itemSellerStoreName);
            }else{
                tvStoreContent.setText("N/A");
            }
            tvDetailsShippingTypeContent.setText(itemShippingType);
            tvDetailsHandlingTimeContent.setText(itemHandlingTime);
            tvDetailsShipingLocationContent.setText(itemShipToLocations);

            if(itemExpeditedShipping.equals("true")){
                ivDetailsExpeditedShippingContent.setImageResource(R.drawable.check);
            }else if(itemExpeditedShipping.equals("false")){
                ivDetailsExpeditedShippingContent.setImageResource(R.drawable.uncheck);
            }

            if(itemOneDayShippingAvailable.equals("true")){
                ivDetailsOneDayShippingContent.setImageResource(R.drawable.check);
            }else if(itemOneDayShippingAvailable.equals("false")){
                ivDetailsOneDayShippingContent.setImageResource(R.drawable.uncheck);
            }

            if(itemReturnsAccepted.equals("true")){
                ivDetailsReturnAcceptedContent.setImageResource(R.drawable.check);
            }else if(itemReturnsAccepted.equals("false")){
                ivDetailsReturnAcceptedContent.setImageResource(R.drawable.uncheck);
            }








        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("app","MistakeCatch");
        }


        btBaciInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicInfoLayout.setVisibility(View.VISIBLE);
                sellerLayout.setVisibility(View.GONE);
                shippingLayout.setVisibility(View.GONE);

                btBaciInfo.setBackgroundColor((Color.parseColor("#7aa8d9")));
                btSeller.setBackgroundColor((Color.parseColor("#e0e0e0")));
                btShipping.setBackgroundColor((Color.parseColor("#e0e0e0")));
            }

        });

        btSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicInfoLayout.setVisibility(View.GONE);
                sellerLayout.setVisibility(View.VISIBLE);
                shippingLayout.setVisibility(View.GONE);

                btSeller.setBackgroundColor((Color.parseColor("#7aa8d9")));
                btBaciInfo.setBackgroundColor((Color.parseColor("#e0e0e0")));
                btShipping.setBackgroundColor((Color.parseColor("#e0e0e0")));
            }

        });

        btShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicInfoLayout.setVisibility(View.GONE);
                sellerLayout.setVisibility(View.GONE);
                shippingLayout.setVisibility(View.VISIBLE);

                btShipping.setBackgroundColor((Color.parseColor("#7aa8d9")));
                btSeller.setBackgroundColor((Color.parseColor("#e0e0e0")));
                btBaciInfo.setBackgroundColor((Color.parseColor("#e0e0e0")));
            }

        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = null;
                try {
                    link = itemBasicInfo.getString("viewItemURL");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });









        //Facebook Share



//        try{

//        Log.v("facebook", shareDialog.toString());
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result Result){
                Log.v("facebook", "success");
                String ID = Result.getPostId();
                if(ID!=null) {
                    Toast.makeText(getApplicationContext(), "Posted Success, Post ID:" + ID, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancel(){
                Log.v("facebook", "cancel");
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error){

            }
        });
            Log.v("app","Facebook MistakeX0");
//        }catch(Exception e){
//            Log.v("app","mistakeX");
//        }


//
//    imDetailsFacebook.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            try {
//                Log.v("app","facebook mistake0");
//                facebookShareFunction();
//            } catch (JSONException e) {
////                e.printStackTrace();
//                Log.v("app","facebook mistake1");
//            }
//        }
//    });












        new ImageDownload().execute("");

    }



//    private void facebookShareFunction() throws JSONException {
//        Log.v("app","facebook mistake3");
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
////            String link = itemBasicInfo.getString("viewItemURL");
//           String facebookDescription = itemPrice + ",location:" + itemLocation;
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                   .setContentDescription("hhhhh")
//                    .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                    .setContentTitle(itemTitle)
//                    .setContentDescription(facebookDescription)
//                    .setContentUrl(Uri.parse(itemURL))
//                    .setImageUrl(Uri.parse(itemGalleryURL))
//                    .build();
//            Log.v("app","facebook mistake4");
//            shareDialog.show(linkContent);
//            Log.v("app","facebook mistake5");
//        }
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }





//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            mImgView1 = (ImageView) findViewById(R.id.mImgView1);
//            pd = ProgressDialog.show(MainActivity.this, "Aguarde...",
//                    "Carregando...");

//        }

        public class ImageDownload extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                loadBitmap(imageUrl, bmOptions);
                return imageUrl;
            }

            protected void onPostExecute(String imageUrl) {
//                pd.dismiss();
                if (!imageUrl.equals("")) {
                    ivDetailImageDisplay.setImageBitmap(bm);
                } else {
                    Log.v("myapp", imageUrl);
                }
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



//
//    public void facebookOnclick(View v){
//        try {
//            Log.v("app","facebook mistake0");
//            facebookShareFunction();
//        } catch (JSONException e) {
////                e.printStackTrace();
//            Log.v("app","facebook mistake1");
//        }
//    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    class FacebookShareFunction implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                String facebookDescription = itemPrice + ",location:" + itemLocation;
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        .setContentDescription("hhhhh")
//                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .setContentTitle(itemTitle)
                        .setContentDescription(facebookDescription)
                        .setContentUrl(Uri.parse(itemURL))
                        .setImageUrl(Uri.parse(itemGalleryURL))
                        .build();

                shareDialog.show(linkContent);
            }
        }
    }


}


