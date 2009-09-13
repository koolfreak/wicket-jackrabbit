/*
 *    xaloon - http://www.xaloon.org
 *    Copyright (C) 2008-2009 Vytautas Racelis
 *
 *    This file is part of xaloon.
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.xaloon.wicket.component.sitemap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.protocol.http.RequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.xaloon.wicket.component.mounting.MountingUtils;
import org.xaloon.wicket.component.mounting.PageAnnotationScannerContainer;
import org.xaloon.wicket.component.util.KeyValue;

public abstract class SiteMap extends WebPage {
	private static final String CHANGE_DAILY = "daily";
	private static final String CHANGE_MONTHLY = "monthly";
	
	
	public SiteMap () {
		RepeatingView repeating = new RepeatingView("urlList");
		add (repeating);
		
		executeSpecialClassMapping(repeating);
		
		for (Class<?> entry: getPageClassList ()) {
			if (MountingUtils.isMountingAnnotationVisible(entry) && hasPermission(entry) ) {
				addEntry(repeating, entry, null, CHANGE_DAILY, MountingUtils.getPriority(entry));	
			}
		}
		executeMappingByListProvider(repeating);
	}
	
	@SuppressWarnings("unchecked")
	private void executeMappingByListProvider(RepeatingView repeating) {
		for (Map.Entry entry: (Set<Map.Entry>)getContext().getBeansOfType(SiteMapListProvider.class).entrySet()) {
			SiteMapListProvider value = (SiteMapListProvider)entry.getValue();
			for (KeyValue<Class<? extends WebPage>, PageParameters> pc : value.retrieveSiteMapPageList()) {
				if (hasPermission(pc.getKey()) ) {
					addEntry(repeating, pc.getKey(), pc.getValue(), CHANGE_DAILY, MountingUtils.getPriority(pc.getKey()));	
				}
			}
		}
	}

	private void executeSpecialClassMapping(RepeatingView repeating) {
		for (Class<? extends WebPage> entry: getSpecialClassList ()) {
			addEntry(repeating, entry, null, CHANGE_MONTHLY, MountingUtils.getPriority(entry));	
		}
	}

	public abstract List<Class<? extends WebPage>> getSpecialClassList();
	public abstract WebApplicationContext getContext();
	public abstract boolean hasPermission(Class<?> entry);
	
	/**
	 * PageAnnotationScannerContainer should be used in order to get page class list using this method.
	 * Otherwise this method should be overridden.
	 * 
	 * @return
	 */
	public List<Class<?>> getPageClassList() {
		return PageAnnotationScannerContainer.getPageClassList();
	}

	@SuppressWarnings("unchecked")
	private void addEntry(RepeatingView repeating, Class page, PageParameters params, String freqNode, String priorityNode) {
		WebMarkupContainer wmc = new WebMarkupContainer(repeating.newChildId());
		wmc.add (new Label("locNode", RequestUtils.toAbsolutePath(urlFor(page, params).toString())));
		wmc.add(new Label ("lastmodNode", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date())));
		wmc.add(new Label ("changefreqNode", freqNode));
		wmc.add(new Label ("priorityNode", priorityNode));
		repeating.add(wmc);
	}
	
	@Override
	public String getMarkupType() {
		return "xml";
	}
}
