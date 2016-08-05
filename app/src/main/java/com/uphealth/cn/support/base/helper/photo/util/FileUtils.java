package com.uphealth.cn.support.base.helper.photo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

	public static String saveBitmap(Bitmap bm, String picName) {
		try {

			File imageFile = PhotoUtil.getImageFilePath();

			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			// PathConfig.getImagePath()
			File f = new File(imageFile, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 60, out);
			out.flush();
			out.close();
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File createSDDir(String dirName) throws IOException {

		// PathConfig.getImagePath()

		File dir = new File(PhotoUtil.getImageFilePath(), dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		// PathConfig.getImagePath()
		File file = new File(PhotoUtil.getImageFilePath(), fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		// PathConfig.getImagePath()
		File file = new File(PhotoUtil.getImageFilePath(), fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	// public static void deleteDir() {
	// File dir = PathConfig.getImagePath();
	// if (dir == null || !dir.exists() || !dir.isDirectory())
	// return;
	//
	// for (File file : dir.listFiles()) {
	// if (file.isFile())
	// file.delete();
	// else if (file.isDirectory())
	// deleteDir();
	// }
	// dir.delete();
	// }

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
