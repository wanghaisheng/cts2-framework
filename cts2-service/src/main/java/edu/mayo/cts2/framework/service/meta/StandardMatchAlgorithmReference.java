/*
 * Copyright: (c) 2004-2011 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mayo.cts2.framework.service.meta;

import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;

/**
 * The Enum StandardMatchAlgorithmReference.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public enum StandardMatchAlgorithmReference {

	EXACT_MATCH (
			"exactMatch", 
			null, 
			null ),
	
	CONTAINS (
			"contains", 
			null, 
			null ),
	
	STARTS_WITH (
			"startsWith", 
			null, 
			null );
	
	private final MatchAlgorithmReference reference;
	
	/**
	 * Instantiates a new standard match algorithm reference.
	 *
	 * @param name the name
	 * @param uri the uri
	 * @param href the href
	 */
	private StandardMatchAlgorithmReference(String name, String uri, String href){
		MatchAlgorithmReference ref = new MatchAlgorithmReference();
		ref.setContent(name);
		ref.setUri(uri);
		ref.setHref(href);
		
		this.reference = ref;
	}
	
	/**
	 * Gets the match algorithm reference.
	 *
	 * @return the match algorithm reference
	 */
	public MatchAlgorithmReference getMatchAlgorithmReference(){
		return this.reference;
	}
}
