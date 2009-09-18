/**
 * 
 */
package org.dms.wicket.repository.db.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 17 - 10:13:34
 */
public class FileVersion implements Serializable
{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_", length = 32)
    private String id;
    
    @Version
    private int version;
    
    @Basic
    @Column(name="versionLabel")
    private String versionLabel;
    
    @Basic
    @Column(name="fileVersion")
    private String fileVersion;
    
    @ManyToOne( cascade = {CascadeType.ALL}, targetEntity=FileDescription.class )
    @JoinColumn(name="filedesc_id")
    private FileDescription fileDescription;

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

    public String getVersionLabel()
    {
        return versionLabel;
    }

    public void setVersionLabel(String versionLabel)
    {
        this.versionLabel = versionLabel;
    }

    public String getFileVersion()
    {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion)
    {
        this.fileVersion = fileVersion;
    }

    public FileDescription getFileDescription()
    {
        return fileDescription;
    }

    public void setFileDescription(FileDescription fileDescription)
    {
        this.fileDescription = fileDescription;
    }
    
    @Override
    public boolean equals(Object obj)
    {
	return EqualsBuilder.reflectionEquals(obj, this);
    }
    
}
