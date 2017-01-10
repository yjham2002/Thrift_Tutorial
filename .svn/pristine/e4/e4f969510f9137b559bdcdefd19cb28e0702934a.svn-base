package core.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.appg.gpack.common.util.DateUtil;

import core.common.constants.Constants;
import core.logic.bean.persistence.FileBean;

public class FileUpload
{
	public List<FileBean> imgFileUpload(List<FileBean> fileBeans, int[] sizeList)
	{
		List<FileBean> fileList = new ArrayList<FileBean>();
		String dirName = "";
		String path = "";
		String fileName = "";
		
		System.out.println("ARRAY == > " + fileBeans);
		for(int i = 0; i < fileBeans.size(); i++){
			System.out.println("FileName == > " + fileBeans.get(i).getFileName());
			System.out.println("Estention ==> " + fileBeans.get(i).getExtension());
			System.out.println("FileKey ==> " + fileBeans.get(i).getFileKey());
			System.out.println("getFileSize ==> " + fileBeans.get(i).getFile().array().length);
		}
		try
		{
			dirName = DateUtil.getString("yyyyMMdd");
			path = ThisUtil.getAbsoluteBasePath() + "/" + Constants.FILE_UPLOAD_IMAGE_DIRECTORY + "/origin/" + dirName;

			for (int f = 0; f < fileBeans.size(); f++)
			{
				fileName = makeUniqueFileName() + "." + fileBeans.get(f).getExtension();

				// byte[] bytes = new byte[fileBeans.get(f).getFile().remaining()];
				// fileBeans.get(f).getFile().get(bytes);

				// 원본 이미지 저장
				byte[] bytes = fileBeans.get(f).getFile().array();
				InputStream arrayInputStream = new ByteArrayInputStream(bytes);
				BufferedImage uploadImage = ImageIO.read(arrayInputStream);
				File originFile = new File(getFilePath(path, fileName));

				ImageIO.write(uploadImage, fileBeans.get(f).getExtension(), originFile);

				// 리사이즈 이미지 저장
				for (int i = 0; i < sizeList.length; i++)
				{
					int imageSize = sizeList[i];
					String resizePath = ThisUtil.getAbsoluteBasePath() + "/" + Constants.FILE_UPLOAD_IMAGE_DIRECTORY + "/" + String.valueOf(imageSize) + "/" + dirName;

					BufferedImage resizeImage = Scalr.resize(uploadImage, imageSize);
					
					File targetFile = new File(getFilePath(resizePath, fileName));
					ImageIO.write(resizeImage, fileBeans.get(f).getExtension(), targetFile);
				}

				FileBean fileBean = new FileBean();
				fileBean.setFileKey(fileBeans.get(f).getFileKey());
				fileBean.setFilePath(dirName + "/" + fileName);

				fileList.add(fileBean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fileList;
	}

	public List<FileBean> fileUpload(List<FileBean> fileBeans)
	{
		List<FileBean> fileList = new ArrayList<FileBean>();
		String dirName = "";
		String path = "";
		String fileName = "";

		try
		{
			dirName = DateUtil.getString("yyyyMMdd");
			path = ThisUtil.getAbsoluteBasePath() + "/" + Constants.FILE_UPLOAD_FILE_DIRECTORY + "/" + dirName;

			for (int f = 0; f < fileBeans.size(); f++)
			{
				fileName = makeUniqueFileName() + "." + fileBeans.get(f).getExtension();

				byte[] bytes = fileBeans.get(f).getFile().array();
				FileOutputStream fos = new FileOutputStream(getFilePath(path, fileName));
				fos.write(bytes);
				fos.close();

				FileBean fileBean = new FileBean();
				fileBean.setFileKey(fileBeans.get(f).getFileKey());
				fileBean.setFilePath(dirName + "/" + fileName);

				fileList.add(fileBean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fileList;
	}

	public String getFilePath(String path, String fileName)
	{
		String result = path + "/" + fileName;

		File directory = new File(path);

		if(!directory.isDirectory())
			directory.mkdirs();

		return result;
	}

	synchronized private static String makeUniqueFileName()
	{
		return String.valueOf(System.nanoTime());
	}
}
