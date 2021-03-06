package com.xht.android.managerhelp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.xht.android.managerhelp.util.BitmapUtils;
import com.xht.android.managerhelp.util.LogHelper;


/**
 * Created by Administrator on 2016/11/16.
 */

public class SpaceImageDetailActivity extends Activity {
    private static final String TAG = "SpaceImageDetailActivit";
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_imgshow);

       // intent.putExtra("imgUr",imgUrl1);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String imgUrl = bundle.getString("file");

        LogHelper.i(TAG,"-----------"+imgUrl);

       ImageView imageView= (ImageView) findViewById(R.id.img_show);

        //ImageRequest request = null;
        //BitmapUtils.loadImgageUrl(request, imgUrl,imageView);


        if (!TextUtils.isEmpty(imgUrl)) {
            LogHelper.i(TAG,"----------2");

            BitmapUtils.loadImgageUrl(imgUrl,imageView);
        }else{
            imageView.setImageDrawable(null);
        }

          /*  Bitmap bitmap = imageLoader.loadImage(imageView, imgUrl);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }else{
                imageView.setImageResource(R.mipmap.ic_action_add);
            }*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                if (flag==2){
                    SpaceImageDetailActivity.this.finish();
                }
            }
        });
    }
}
