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
package edu.mayo.cts2.framework.filter.match;

import org.apache.commons.lang.StringUtils;


/**
 * The Class AbstractMatcher.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public abstract class AbstractMatcher implements Matcher {
	
	private boolean caseSensitive = false;
	
	protected static final float NO_MATCH = 0f;
	
	protected static final float EXACT_MATCH = 1f;

	/* (non-Javadoc)
	 * @see org.cts2.internal.match.BaseMatchAlgorithm.Matcher#isMatch(java.lang.String, java.lang.String)
	 */
	@Override
	public float matchScore(String matchText, String candidate) {
		//TODO: null check, validation, etc..
		matchText = matchText.trim();
		
		if(StringUtils.isBlank(candidate)){
			return NO_MATCH;
		}
		
		candidate = candidate.trim();
		
		if(this.caseSensitive){
			return this.doMatchScore(matchText, candidate);
		} else {
			return this.doMatchScore(matchText.toLowerCase(), candidate.toLowerCase());
		}
	}
	
	/**
	 * Do match score.
	 *
	 * @param matchText the match text
	 * @param cadidate the cadidate
	 * @return the float
	 */
	protected abstract float doMatchScore(String matchText, String cadidate);

	/**
	 * Sets the case sensitive.
	 *
	 * @param caseSensitive the new case sensitive
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Checks if is case sensitive.
	 *
	 * @return true, if is case sensitive
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
}
