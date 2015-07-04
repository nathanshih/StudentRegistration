package edu.jhu.JavaEE.shih.nathan.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import edu.jhu.JavaEE.shih.nathan.beans.SessionBean;

/**
 * This custom validator verifies that the number of login attempts doesn't exceed the set number of login attempts
 * specified in web.xml.
 *
 * @author Nathan
 */
@FacesValidator("edu.jhu.JavaEE.shih.nathan.validators.MaxLoginValidator")
public class MaxLoginValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		HttpSession session = SessionBean.getSession();
		FacesMessage facesMessage = null;
		
		// get or set current login attempt via session variable
        int loginAttempt;
        if (session.getAttribute("loginAttempt") == null) {
            session.setAttribute("loginAttempt", 1);
            loginAttempt = 1;
        } else {
            loginAttempt = (int) session.getAttribute("loginAttempt");        
        }
        
        // check if loginAttempsAllowed has been exceeded
        if (loginAttempt >= SessionBean.getLoginAttemptsAllowed()) {
        	session.invalidate();
        	
        	facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Max number of login attempts exceeded.", null);
        	throw new ValidatorException(facesMessage);
        } else {
        	
        	// increment loginAttempts and set as session variable
            loginAttempt++;
            session.setAttribute("loginAttempt", loginAttempt);
        }
	}
}
