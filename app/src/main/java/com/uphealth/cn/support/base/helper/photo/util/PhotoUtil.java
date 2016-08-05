package com.uphealth.cn.support.base.helper.photo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

public class PhotoUtil {

	protected static final String BASEPATH = "/Apu/service/";
	protected static final String IAMGE_PATH = "images";
	protected static final String IAMGE_THUMB_PATH = "thumbnail";

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();

		return Base64.encodeToString(b, Base64.DEFAULT);

	}

	/**
	 * 计算图片的缩放�??
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static void deleteTempFilePath(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 根据路径删除图片
	 * 
	 * @param path
	 */
	public static boolean delAllFile(String path) {

		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文�?
				delFolder(path + "/" + tempList[i]);// 再删除空文件�?
				flag = true;
			}
		}
		return flag;
	}

	/** param folderPath 文件夹完整绝对路�? **/
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内�?
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			return myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加到图�?
	 */
	public static void galleryAddPic(Context context, String path) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(path);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 获取保存图片的目�?
	 * 
	 * @return
	 */
	public static File getAlbumDir() {
		return getThumbImageFile();
	}

	/** 缩略图片路径 */
	public static File getThumbImageFile() {

		File parent = getBaseDir();
		File desc = new File(parent, IAMGE_THUMB_PATH);
		if (!desc.exists())
			desc.mkdirs();
		return desc;
	}

	public static File getBaseDir() {

		File cacheDir = null;

		cacheDir = new File(
				android.os.Environment.getExternalStorageDirectory() + BASEPATH);
		// TODO
		// if (SdcardUtil.isSdcardReadable() || SdcardUtil.isSdcardWritable())
		// cacheDir = new
		// File(android.os.Environment.getExternalStorageDirectory() +
		// BASEPATH);
		// else if (null != App.getAppContext().getCacheDir()
		// && App.getAppContext().getCacheDir().exists()) {
		// cacheDir = new File(App.getAppContext().getCacheDir(), BASEPATH);
		// } else {
		// cacheDir = null;
		// }
		// if (null != cacheDir && !cacheDir.exists())
		// cacheDir.mkdirs();
		return cacheDir;
	}

	/**
	 * 获取保存 隐患�?查的图片文件夹名�?
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		// TODO
		return "album";
	}

	public static String getThmubImaePath() {

		File parent = getBaseDir();
		File desc = new File(parent, IAMGE_THUMB_PATH);
		return desc.getPath();
	}
	public static String getBaseImaePath() {
		
		File parent = getBaseDir();
		File desc = new File(parent, IAMGE_PATH);
		return desc.getPath();
	}
	
	/** 图片路径 */
	public static File getImageFilePath() {

		File parent = getBaseDir();
		File desc = new File(parent, IAMGE_PATH);
		if (!desc.exists())
			desc.mkdirs();
		return desc;
	}

}
