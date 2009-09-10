/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dms.wicket.repository.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@Entity
@Table(name = "jcr_file_repo")
@Indexed(index="FileDescription")
public class FileDescription implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id_", length = 32)
    @DocumentId
    private String id;
    
    @Version
    private int version;

    
    @Fields(
	{ @Field(index = Index.TOKENIZED), // same property indexed multiple
		@Field(name = "name_sort", index = Index.UN_TOKENIZED) // use a
	})
    @Boost(2)
    @Basic
    @Column(name = "name_")
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

    @Basic
    @Column(name="size_")
    private Long size;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

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

    public void setSize(Long size)
    {
	this.size = size;
    }

    public Long getSize()
    {
	return size;
    }
    
    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getFilePath()
    {
	return this.path + '/' + this.name;
    }
}
