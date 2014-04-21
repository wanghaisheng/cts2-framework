package edu.mayo.cts2.framework.model.exception;

import edu.mayo.cts2.framework.model.service.core.types.LoggingLevel;

public abstract class AbstractCTS2Exception extends RuntimeException {

	private static final long serialVersionUID = 3600583925006355829L;
	
	public AbstractCTS2Exception(){
		super();
		this.setSeverity(LoggingLevel.ERROR);
		this.initCause(null);
	}
	
	  /**
     * Sets the value of field 'cts2Message'.
     * 
     * @param cts2Message the value of field 'cts2Message'.
     */
    public abstract void setCts2Message(
            final edu.mayo.cts2.framework.model.core.OpaqueData cts2Message);
    
    public abstract edu.mayo.cts2.framework.model.core.OpaqueData getCts2Message();

    /**
     * Sets the value of field 'severity'.
     * 
     * @param severity the value of field 'severity'.
     */
    public abstract void setSeverity(
            final edu.mayo.cts2.framework.model.service.core.types.LoggingLevel severity);
    
    
    public String getMessage(){
	    return this.getCts2Message() != null ? this.getCts2Message().getValue().getContent() : "";
    }
}
