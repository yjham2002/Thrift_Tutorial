package core.logic.bean.persistence;

import java.util.List;

public class BoardBean {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	
	
	private int id;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BoardBean [id=" + id + ", uid=" + uid + ", title=" + title + ", content=" + content + ", date=" + date
				+ ", userName=" + userName + ", file=" + file + ", likes=" + likes + "]";
	}
	private int uid;
	private String title;
	private String content;
	private String date;
	private String userName;
	private FileBean file;
	private int likes;
	


	/**
	 * @return the likes
	 */
	public int getLikes() {
		return likes;
	}
	/**
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}
	/**
	 * @return the file
	 */
	public FileBean getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(FileBean file) {
		this.file = file;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
