/**
 * 
 */
package org.dms.wicket.repository.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 17 - 10:13:34
 */
public class FileVersion implements Serializable
{

    
    private Date lastModified;
    
    private String fileVersion;
    
    public FileVersion()
    {
    }
    
    public FileVersion(Date lastModified, String fileVersion)
    {
	this.lastModified = lastModified;
	this.fileVersion = fileVersion;
    }



    public String getFileVersion()
    {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion)
    {
        this.fileVersion = fileVersion;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    
}
