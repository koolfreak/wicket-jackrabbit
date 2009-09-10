/**
 * 
 */
package org.dms.component.file;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 4 - 16:11:51
 */
public class FileDescription implements Serializable
{

    	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Date lastModified;
	private String mimeType;
	private String UUID;
	private String path;
	
	private Long size;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uuid) {
		UUID = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Long getSize() {
		return size;
	}
	
	public String getFilePath()
	{
	    return this.path +'/'+ this.name;
	}
}
