/**
 * 
 */
package org.dms.wicket.page.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.dms.component.file.FileDescription;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 5 - 13:48:04
 */
@Entity
@Table(name="file_descriptor")
@Indexed
public class CustomFileDescription extends FileDescription implements Serializable
{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id_",length = 32)
    @DocumentId
    private String id;
    
    @Version
    private int version;

    @Fields(
	{ @Field(index = Index.TOKENIZED), // same property indexed multiple
		@Field(name = "name_sort", index = Index.UN_TOKENIZED) // use a
	})
    @Boost(1)
    @Basic
    @Column(name="name_")
    private String name;
    
    @Field
    @Basic
    @Column(name="lastmodified_")
    private Date lastModified;
    
    @Field
    @Basic
    @Column(name="mimetype_")
    private String mimeType;
    
    @Basic
    @Column(name="UUID_")
    private String UUID;
    
    @Basic
    @Column(name="path_")
    private String path;
    
    /*@Basic
    @Column(name="name_")
    private Long size;*/

    public String getPath()
    {
	return path;
    }

    public void setPath(String path)
    {
	this.path = path;
    }

    public String getUUID()
    {
	return UUID;
    }

    public void setUUID(String uuid)
    {
	UUID = uuid;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public Date getLastModified()
    {
	return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
	this.lastModified = lastModified;
    }

    public String getMimeType()
    {
	return mimeType;
    }

    public void setMimeType(String mimeType)
    {
	this.mimeType = mimeType;
    }

    /*public void setSize(Long size)
    {
	this.size = size;
    }

    public Long getSize()
    {
	return size;
    }*/

    public String getFilePath()
    {
	return this.path + '/' + this.name;
    }

    public String getId()
    {
	return id;
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public int getVersion()
    {
	return version;
    }

    public void setVersion(int version)
    {
	this.version = version;
    }

}
