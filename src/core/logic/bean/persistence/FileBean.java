package core.logic.bean.persistence;

import java.nio.ByteBuffer;
import java.util.List;

import com.appg.gpack.common.exception.ServiceException;
import com.appg.gpack.logic.basic.svc.file.IFile;
import com.appg.gpack.logic.basic.svc.file.bean.FilePathAndUrlBean;
import com.appg.gpack.logic.basic.svc.file.bean.ThumbImgSpecBean;

public class FileBean
{
	private String		fileKey		= "";
	private String		fileName	= "";
	private String		extension	= "";
	private ByteBuffer	file		= null;
	private String		filePath	= "";

	public String getFileKey()
	{
		return fileKey;
	}

	public void setFileKey(String fileKey)
	{
		this.fileKey = fileKey;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getExtension()
	{
		return extension;
	}

	public void setExtension(String extension)
	{
		this.extension = extension;
	}

	public ByteBuffer getFile()
	{
		return file;
	}

	public void setFile(ByteBuffer file)
	{
		this.file = file;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileBean [fileKey=" + fileKey + ", fileName=" + fileName + ", extension=" + extension + ", file=" + file
				+ ", filePath=" + filePath + "]";
	}

	
}
