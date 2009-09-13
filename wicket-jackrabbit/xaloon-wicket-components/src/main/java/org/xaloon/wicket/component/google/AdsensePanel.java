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
package org.xaloon.wicket.component.google;

import org.apache.wicket.markup.html.panel.Panel;

public class AdsensePanel extends Panel {
	private transient GoogleAdsenseBehavior googleAdsenseBehavior;

	public AdsensePanel(String id, String adSenseClient, String adSenseSlot) {
		super(id);
		add (googleAdsenseBehavior = new GoogleAdsenseBehavior(adSenseClient, adSenseSlot));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoogleAdsenseBehavior getGoogleAdsenseBehavior() {
		return googleAdsenseBehavior;
	}

}
