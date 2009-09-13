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
package org.xaloon.wicket.component.captcha;

import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.StringValidator;

public class CaptchaValidator extends StringValidator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CAPTCHA_NOT_VALID_MESSAGE_KEY = "CAPTCHA_NOT_VALID_MESSAGE_KEY";
	private PropertyModel<String> captchaModel;
	
	public CaptchaValidator (PropertyModel<String> captchaModel) {
		this.captchaModel = captchaModel;
	}
	
	@Override
	protected void onValidate(IValidatable<String> validatable) {
		if (!(validatable.getValue()).equals(captchaModel.getObject())) {
			error(validatable, CAPTCHA_NOT_VALID_MESSAGE_KEY);
		}
	}

}
