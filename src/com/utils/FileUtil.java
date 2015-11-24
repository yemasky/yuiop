package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FileUtil {
	private FileOutputStream fs;
	private BufferedReader br;

	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		FileInputStream fs = new FileInputStream(filePathAndName);
		InputStreamReader isr;
		if (encoding.equals("")) {
			isr = new InputStreamReader(fs);
		} else {
			isr = new InputStreamReader(fs, encoding);
		}
		br = new BufferedReader(isr);
		String data = "";
		while ((data = br.readLine()) != null) {
			str.append(data + " ");
		}
		st = str.toString();
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 
	 * @return 返回目录创建后的路径
	 */
	public boolean createFolder(String folderPath) {
		File myFilePath = new File(folderPath);
		if (!myFilePath.exists()) {
			return myFilePath.mkdir();
		}
		return true;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		String txt;
		txts = folderPath;
		StringTokenizer st = new StringTokenizer(paths, "|");

		for (; st.hasMoreTokens();) {
			txt = st.nextToken().trim();
			if (txts.lastIndexOf("/") != -1) {
				createFolder(txts + txt);
			} else {
				createFolder(txts + txt + "/");
			}
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 * @throws IOException
	 */
	public void createFile(String filePath, String fileName, String fileContent)
			throws IOException {
		File myFilePath = new File(filePath, fileName);
		myFilePath.createNewFile();
		FileWriter resultFile = new FileWriter(myFilePath);
		PrintWriter myFile = new PrintWriter(resultFile);
		myFile.println(fileContent);
		myFile.close();
		resultFile.close();
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 * @throws IOException
	 */
	public void createFile(String filePath, String fileName,
			String fileContent, String encoding) throws IOException {
		File myFilePath = new File(filePath, fileName);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		}
		PrintWriter myFile = new PrintWriter(myFilePath, encoding);
		String strContent = fileContent;
		myFile.println(strContent);
		myFile.close();
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		String filePath = filePathAndName;
		File myDelFile = new File(filePath);
		if (myDelFile.exists()) {
			myDelFile.delete();
			bea = true;
		} else {
			bea = false;
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return 
	 * @return
	 */
	public boolean delFolder(String folderPath) {
		delAllFile(folderPath); // 删除完里面所有内容
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		return myFilePath.delete(); // 删除空文件夹
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 * @throws IOException
	 */
	public void copyFile(String oldPathFile, String newPathFile)
			throws IOException {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPathFile);
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
			fs = new FileOutputStream(newPathFile);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}

	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 * @throws IOException
	 */
	public void copyFolder(String oldPath, String newPath) throws IOException {
		new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		File a = new File(oldPath);
		String[] file = a.list();
		File temp = null;
		for (int i = 0; i < file.length; i++) {
			if (oldPath.endsWith(File.separator)) {
				temp = new File(oldPath + file[i]);
			} else {
				temp = new File(oldPath + File.separator + file[i]);
			}
			if (temp.isFile()) {
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(newPath + "/"
						+ (temp.getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (temp.isDirectory()) {// 如果是子文件夹
				copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		}

	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws IOException
	 */
	public void moveFile(String oldPath, String newPath) throws IOException {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws IOException
	 */
	public void moveFolder(String oldPath, String newPath) throws IOException {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public boolean fileExists(String filePath) {
		File myFilePath = new File(filePath);
		return myFilePath.exists();
	}
}