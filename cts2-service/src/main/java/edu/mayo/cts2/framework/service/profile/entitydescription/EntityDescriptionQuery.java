/*
 * Copyright: (c) 2004-2012 Mayo Foundation for Medical Education and 
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
package edu.mayo.cts2.framework.service.profile.entitydescription;

import edu.mayo.cts2.framework.service.command.restriction.EntityDescriptionQueryServiceRestrictions;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;

/**
 * The Interface EntityDescriptionQuery.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public interface EntityDescriptionQuery extends ResourceQuery {
	

	/**
	 * If the EntityDescription Query is the result of an 'Association' Query,
	 * those details will be returned here. For example, if the Query is intended
	 * to represent all "SOURCE" Entities of a group of Associations, and so forth.
	 * 
	 * If this Query is not the result of an 'Association' Query, this should return null.
	 *
	 * @return the entities from associations query
	 */
	public EntitiesFromAssociationsQuery getEntitiesFromAssociationsQuery();
	 
	/**
	 * EntityDescription Query restrictions to narrow/filter the result set.
	 *
	 * @return the restrictions
	 */
	public EntityDescriptionQueryServiceRestrictions getRestrictions();

}
