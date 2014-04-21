package edu.mayo.cts2.framework.core.json;

import edu.mayo.cts2.framework.model.service.core.types.LoggingLevel;
import edu.mayo.cts2.framework.model.service.exception.CTS2Exception;
import edu.mayo.cts2.framework.model.util.ModelUtils;

/**
 * Thrown when a JSON Marshalling/Unmarshalling problem is encountered.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class JsonUnmarshallingException extends CTS2Exception {

	private static final long serialVersionUID = -4301298625407627572L;

	public JsonUnmarshallingException(String message) {
        super();
        this.setCts2Message(ModelUtils.createOpaqueData(message));
        this.setSeverity(LoggingLevel.ERROR);
    }
}
