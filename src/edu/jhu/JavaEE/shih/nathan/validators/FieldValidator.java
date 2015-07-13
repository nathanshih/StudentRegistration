package edu.jhu.JavaEE.shih.nathan.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This custom validator class uses JSF to verify whether the userId or password meets theses requirements:
 * <ol>
 * 	<li>8 characters
 * 	<li>cannot contain space(s)
 * 	<li>cannot be empty or null
 * </ol>
 *
 * @author Nathan
 */
@FacesValidator("edu.jhu.JavaEE.shih.nathan.validators.FieldValidator")
public class FieldValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {

		if (!validateFieldValue(value.toString())) {
			
			FacesMessage facesMessage = null;
			
			String componentId = component.getId();
			if (componentId.equalsIgnoreCase("userId")) {
				facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"UserId validation failed. Must be not empty, exactly 8 characters, and contain no spaces.", null);
			} else if (componentId.equalsIgnoreCase("password")) {
				facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Password validation failed. Must be not empty, exactly 8 characters, and contain no spaces.", null);
			} else {
				facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Field validation failed. Must be not empty, exactly 8 characters, and contain no spaces.", null);
			}
			
			throw new ValidatorException(facesMessage);
		}
	}

	private boolean validateFieldValue(String value) {
	
		// value cannot be null
		if (value == null) {
			return false;
		}
		
		// value cannot be empty
		if (value.isEmpty()) {
			return false;
		}
		
		// value must be 8 characters
		if (value.length() != 8) {
			return false;
		}
		
		// value cannot contain space(s)
		if (value.contains(" ")) {
			return false;
		}
		
		// value is valid per assignment requirements
		return true;
	}
}