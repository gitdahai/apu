package com.uphealth.cn.ui.login.eat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.uphealth.cn.support.base.helper.photo.util.PhotoUtil;
import com.uphealth.cn.ui.login.dynamic.ApuHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Bimp {
	
public static int max = 0;
	
	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	public static String getThumbPath(ImageItem imageItem) throws IOException {

		File file = new File(imageItem.imagePath);
		File fileThumb = PhotoUtil.getAlbumDir();

		fileThumb = new File(fileThumb, file.getName());
		FileOutputStream fos = new FileOutputStream(fileThumb);
		imageItem.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fos);
		
//		Bitmap bitmap = ClipImageUtil.lessenUriImage(false,imageItem.imagePath);
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		return fileThumb.getAbsolutePath();

	}

	public static void clear() {

		ApuHelper.excuteThread(new Runnable() {

			@Override
			public void run() {
				tempSelectBitmap.clear();
				PhotoUtil.delAllFile(PhotoUtil
						.getThmubImaePath());
				
			}
		});
	}
}
