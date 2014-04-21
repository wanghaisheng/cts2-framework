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
package edu.mayo.cts2.framework.service.profile.mapversion;

import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.core.EntityReferenceList;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.mapversion.MapVersionDirectoryEntry;
import edu.mayo.cts2.framework.model.mapversion.MapVersionListEntry;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.model.service.core.types.StructuralProfile;
import edu.mayo.cts2.framework.model.service.mapversion.types.MapRole;
import edu.mayo.cts2.framework.model.service.mapversion.types.MapStatus;
import edu.mayo.cts2.framework.service.profile.Cts2Profile;
import edu.mayo.cts2.framework.service.profile.QueryService;
import edu.mayo.cts2.framework.service.profile.StructuralConformance;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;

/**
 * The Interface MapVersionQueryService.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@StructuralConformance(StructuralProfile.SP_MAP_VERSION)
public interface MapVersionQueryService extends 
	QueryService<MapVersionListEntry,
		MapVersionDirectoryEntry, 
		MapVersionQuery>, Cts2Profile {
	
	/**
	 * Map version entities.
	 *
	 * @param mapVersion the map version
	 * @param mapRole the map role
	 * @param mapStatus the map status
	 * @param query the query
	 * @param sort the sort
	 * @param page the page
	 * @return the directory result
	 */
	public DirectoryResult<EntityDirectoryEntry> mapVersionEntities(
			NameOrURI mapVersion,
			MapRole mapRole, 
			MapStatus mapStatus, 
			EntityDescriptionQuery query,
			SortCriteria sort,
			Page page);
	
	/**
	 * Map version entity list.
	 *
	 * @param mapVersion the map version
	 * @param mapRole the map role
	 * @param mapStatus the map status
	 * @param query the query
	 * @param sort the sort
	 * @param page the page
	 * @return the directory result
	 */
	public DirectoryResult<EntityDescription> mapVersionEntityList(
			NameOrURI mapVersion,
			MapRole mapRole, 
			MapStatus mapStatus, 
			EntityDescriptionQuery query,
			SortCriteria sort,
			Page page);
	
	/**
	 * Map version entity references.
	 *
	 * @param mapVersion the map version
	 * @param mapRole the map role
	 * @param mapStatus the map status
	 * @param query the query
	 * @param sort the sort
	 * @param page the page
	 * @return the entity reference list
	 */
	public EntityReferenceList mapVersionEntityReferences(
			NameOrURI mapVersion,
			MapRole mapRole, 
			MapStatus mapStatus, 
			EntityDescriptionQuery query,
			SortCriteria sort,
			Page page);

}
