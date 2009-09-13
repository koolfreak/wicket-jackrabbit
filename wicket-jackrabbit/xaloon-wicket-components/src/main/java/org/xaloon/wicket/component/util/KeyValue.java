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
package org.xaloon.wicket.component.util;

import java.io.Serializable;

public class KeyValue <T, K> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T key;
	private K value;
	
	public KeyValue(T key, K value) {
		this.key = key;
		this.value = value;
	}
	
	public KeyValue() {
	}

	public T getKey() {
		return key;
	}
	public void setKey(T key) {
		this.key = key;
	}
	public K getValue() {
		return value;
	}
	public void setValue(K value) {
		this.value = value;
	}

	public boolean isEmpty() {
		return (key == null) && (value == null);
	}
}
