package com.yzkj.utils;



import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.yzkj.application.XlwApplication;
import com.yzkj.dwgc1.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class PublicUtil {

	/**
	 * Toast鎻愰啋
	 * 
	 * @param content
	 *            鍐呭
	 */
	public static void ShowToast(String content) {
		Toast.makeText(XlwApplication.getInstance(), content, Toast.LENGTH_SHORT)
				.show();
	}
	
	/**
	 * 鑾峰彇璁惧瀹藉害
	 * @return
	 */
	public static int getDeviceWidth() {
		return XlwApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 鑾峰彇璁惧楂樺害
	 * @return
	 */
	public static int getDeviceHeight() {
		return XlwApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int getResColor(int resid)
	{
		Resources rs = XlwApplication.getInstance().getResources();
		return rs.getColor(resid);
	}
	
	/**
     * 鍒ゆ柇杈撳叆鍚堟硶鎬�
     * @param name
     * @param addr
     * @param phone
     * @return
     */
    public static boolean isLegal(String name ,String addr,String phone)
    {
        if("".equals(name))
        {
            PublicUtil.ShowToast("璇疯緭鍏ヨ仈绯讳汉濮撳悕");
            return false;
        }
        if("".equals(addr))
        {
            PublicUtil.ShowToast("璇疯緭鍏ヨ仈绯讳汉鍦板潃");
            return false;
        }
        if("".equals(phone) || !RegexUtil.isPhone(phone))
        {
            PublicUtil.ShowToast("璇疯緭鍏ユ纭殑鑱旂郴浜虹數璇�");
            return false;
        }
        return true;
    }



    private static DisplayImageOptions adsOption;//杩欎釜鏄灦鍖呴噷鐨勭被
    public static DisplayImageOptions getAdsOption()
    {
        if(null == adsOption)
        {
            adsOption = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnFail(R.drawable.camera1).showImageOnLoading(R.drawable.camera1).imageScaleType(ImageScaleType.EXACTLY).cacheOnDisk(true).build();
        }
        return  adsOption;
    }
    private static DisplayImageOptions goodsOption;

    public static DisplayImageOptions getGoodsOption()
    {
        if(null == goodsOption)
        {
            goodsOption = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnFail(R.drawable.camera1).showImageOnLoading(R.drawable.camera1).imageScaleType(ImageScaleType.EXACTLY).cacheOnDisk(true).build();
        }
        return  goodsOption;
    }

    private static DisplayImageOptions headOption;

    public static DisplayImageOptions getHeadOption()
    {
        if(null == headOption)
        {
            headOption = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnFail(R.drawable.camera1).showImageOnLoading(R.drawable.camera1).imageScaleType(ImageScaleType.EXACTLY).cacheOnDisk(true).build();
        }
        return  headOption;
    }


    private static ImageLoader mImageLoader;

    public static ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(XlwApplication.getInstance()).threadPoolSize(3).memoryCache(new WeakMemoryCache()).build();
            mImageLoader.init(configuration);
        }

        return mImageLoader;
    }
    
    public static void closeKeyBoard(Context context)
    {
        /**闅愯棌杞敭鐩�**/
        View view = ((Activity)context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) ((Activity)context).getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 灏嗗瓧绗︿覆浠锋牸 杞崲涓� 鏄剧ず2浣嶅皬鏁扮殑瀛楃涓�
     *
     * @param str
     * @return
     */
    public static String priceFormat(String str)
    {
        float price = Float.valueOf(str);
        return String.format("%.2f ", price);
    }
}
