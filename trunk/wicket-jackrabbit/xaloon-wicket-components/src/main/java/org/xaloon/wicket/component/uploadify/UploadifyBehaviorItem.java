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
package org.xaloon.wicket.component.uploadify;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.lang.Bytes;
import org.xaloon.wicket.component.jquery.JQueryBehavior;
import org.xaloon.wicket.component.jquery.JQueryBehaviorItem;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class UploadifyBehaviorItem extends JQueryBehaviorItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final ResourceReference JS = new CompressedResourceReference(UploadifyBehaviorItem.class, "jquery.uploadify.js");
	private static final ResourceReference CSS = new CompressedResourceReference(UploadifyBehaviorItem.class, "uploadify.css");
	
	private JQueryBehavior queryBehavior;
	
	public UploadifyBehaviorItem (Class<? extends UploadifyFileProcessPage> fileProcessPageClass) {
		this (new JQueryBehavior(true), fileProcessPageClass);
	}
	
	public UploadifyBehaviorItem (JQueryBehavior queryBehavior, Class<? extends UploadifyFileProcessPage> fileProcessPageClass) {
		this.queryBehavior = queryBehavior;
		queryBehavior.addChild(this);
		
		//default properties
		setAuto(true);
		setFileDescription("Upload images only");
		setFileExtenstion("*.jpg;*.png;*.gif");
		setSizeLimit(Bytes.megabytes(50));
		setUploadLimit(5);
		setSizeLimit(Bytes.megabytes(50));
		setUploadProcessPage(fileProcessPageClass, null);
		init ();
	}
	
	@Override
	public ResourceReference getCss() {
		return CSS;
	}
	
	@Override
	public String getFunctionName() {
		return "fileUpload";
	}
	
	@Override
	public ResourceReference getJs() {
		return JS;
	}	
	
	public void init () {
		addOrReplace ("uploader", RequestCycle.get().urlFor (new CompressedResourceReference(UploadifyBehaviorItem.class, "uploader.swf")).toString(), true);
		addOrReplace ("cancelImg", RequestCycle.get().urlFor (new CompressedResourceReference(UploadifyBehaviorItem.class, "cancel.png")).toString(), true);
		addOrReplace ("multi", "true", true);
		/*addOrReplace ("onError", "function (a, b, c, d) { " +
				" if (d.status == 404) alert('Could not find upload script.); " +
				" else if (d.type === \"HTTP\") alert('error '+d.type+\": \"+d.status); " +
				" else if (d.type ===\"File Size\") alert(c.name+' '+d.type+' Limit: '+Math.round(d.sizeLimit/1024)+'KB'); " +
				" else alert('error '+d.type+\": \"+d.text); }", false);
		addOrReplace ("onComplete", "function(a, b, c, d, e){if (d !== '1') alert(d);}", false);*/
	}
	
	public void setFileDescription (String desc) {
		addOrReplace("fileDesc", desc);
	}
	
	public void setFileExtenstion (String ext) {
		addOrReplace("fileExt", ext);
	}
	
	public void setSizeLimit (Bytes limit) {
		addOrReplace("sizeLimit", String.valueOf(limit.bytes()));
	}
	
	public void setUploadLimit (int limit) {
		addOrReplace("simUploadLimit", String.valueOf(limit));
	}
	
	public void setButtonImage (ResourceReference reference) {
		addOrReplace("buttonImg", RequestCycle.get().urlFor (reference).toString());
	}
	
	public void setAuto (boolean isAuto) {
		addOrReplace ("auto", String.valueOf(isAuto), false);
	}
	
	public void setUploadProcessPage (Class<? extends WebPage> page, PageParameters params) {
		addOrReplace ("script", RequestCycle.get().urlFor(page, params).toString(), true);
	}
	
	@Override
	public void bind(Component component) {
		setMarkupId (component.getMarkupId());
		if (queryBehavior.isCreatedFromComponent()) {
			component.add(queryBehavior);
		}
	}

	public void setWidth(int width) {
		addOrReplace ("width", String.valueOf(width), false);
	}
}
