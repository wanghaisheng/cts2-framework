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
package edu.mayo.cts2.framework.webapp.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.conceptdomain.ConceptDomainCatalogEntry;
import edu.mayo.cts2.framework.model.conceptdomain.ConceptDomainCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.conceptdomain.ConceptDomainCatalogEntryList;
import edu.mayo.cts2.framework.model.conceptdomain.ConceptDomainCatalogEntryMsg;
import edu.mayo.cts2.framework.model.core.Message;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.service.core.Query;
import edu.mayo.cts2.framework.model.service.exception.UnknownConceptDomain;
import edu.mayo.cts2.framework.model.util.ModelUtils;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainHistoryService;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainMaintenanceService;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainQueryService;
import edu.mayo.cts2.framework.service.profile.conceptdomain.ConceptDomainReadService;
import edu.mayo.cts2.framework.webapp.rest.command.QueryControl;
import edu.mayo.cts2.framework.webapp.rest.command.RestFilter;
import edu.mayo.cts2.framework.webapp.rest.command.RestReadContext;
import edu.mayo.cts2.framework.webapp.rest.query.ResourceQueryBuilder;

/**
 * The Class ConceptDomainController.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@Controller
public class ConceptDomainController extends AbstractMessageWrappingController {
	
	@Cts2Service
	private ConceptDomainReadService conceptDomainReadService;

	@Cts2Service
	private ConceptDomainQueryService conceptDomainQueryService;
	
	@Cts2Service
	private ConceptDomainMaintenanceService conceptDomainMaintenanceService;
	
	@Cts2Service
	private ConceptDomainHistoryService conceptDomainHistoryService;

	private final static UrlTemplateBinder<ConceptDomainCatalogEntry> URL_BINDER =
			new UrlTemplateBinder<ConceptDomainCatalogEntry>(){

		@Override
		public Map<String,String> getPathValues(ConceptDomainCatalogEntry resource) {
			Map<String,String> returnMap = new HashMap<String,String>();
			returnMap.put(VAR_CONCEPTDOMAINID,resource.getConceptDomainName());
			
			return returnMap;
		}

	};
	
	private final static MessageFactory<ConceptDomainCatalogEntry> MESSAGE_FACTORY = 
			new MessageFactory<ConceptDomainCatalogEntry>() {

		@Override
		public Message createMessage(ConceptDomainCatalogEntry resource) {
			ConceptDomainCatalogEntryMsg msg = new ConceptDomainCatalogEntryMsg();
			msg.setConceptDomainCatalogEntry(resource);

			return msg;
		}
	};
	
	@RequestMapping(value=PATH_CONCEPTDOMAIN_CHANGEHISTORY, method=RequestMethod.GET)
	@ResponseBody
	public ConceptDomainCatalogEntryList getChangeHistory(
			HttpServletRequest httpServletRequest,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName,
			@RequestParam(required=false) Date PARAM_FROMDATE,
			@RequestParam(required=false) Date PARAM_TODATE,
			Page page) {
	
		DirectoryResult<ConceptDomainCatalogEntry> result = 
				this.conceptDomainHistoryService.getChangeHistory(
						ModelUtils.nameOrUriFromName(conceptDomainName),
						PARAM_FROMDATE,
						PARAM_TODATE);
		
		return this.populateDirectory(
				result, 
				page, 
				httpServletRequest, 
				ConceptDomainCatalogEntryList.class);	
	}
	
	@RequestMapping(value=PATH_CONCEPTDOMAIN_EARLIESTCHANGE, method=RequestMethod.GET)
	@ResponseBody
	public ConceptDomainCatalogEntryMsg getEarliesChange(
			HttpServletRequest httpServletRequest,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName) {
	
		ConceptDomainCatalogEntry result = 
				this.conceptDomainHistoryService.getEarliestChangeFor(ModelUtils.nameOrUriFromName(conceptDomainName));
		
		ConceptDomainCatalogEntryMsg msg = new ConceptDomainCatalogEntryMsg();
		msg.setConceptDomainCatalogEntry(result);
		
		return this.wrapMessage(msg, httpServletRequest);
	}
	
	@RequestMapping(value=PATH_CONCEPTDOMAIN_LATESTCHANGE, method=RequestMethod.GET)
	@ResponseBody
	public ConceptDomainCatalogEntryMsg getLastChange(
			HttpServletRequest httpServletRequest,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName) {
	
		ConceptDomainCatalogEntry result = 
				this.conceptDomainHistoryService.getLastChangeFor(ModelUtils.nameOrUriFromName(conceptDomainName));
	
		ConceptDomainCatalogEntryMsg msg = new ConceptDomainCatalogEntryMsg();
		msg.setConceptDomainCatalogEntry(result);
		
		return this.wrapMessage(msg, httpServletRequest);
	}
	
	/**
	 * Gets the concept domains.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param resolvedFilter the filter
	 * @param page the page
	 * @return 
	 * @return the concept domains
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAINS, method=RequestMethod.GET)
	public Object getConceptDomains(
			HttpServletRequest httpServletRequest,
			RestReadContext restReadContext,
			QueryControl queryControl,
			RestFilter restFilter,
			Page page,
			boolean list) {
		
		return this.getConceptDomains(
				httpServletRequest,
				restReadContext,
				queryControl,
				null, 
				restFilter, 
				page,
				list);
	}

	/**
	 * Gets the concept domains.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param query the query
	 * @param resolvedFilter the filter
	 * @param page the page
	 * @return 
	 * @return the concept domains
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAINS, method=RequestMethod.POST)
	public Object getConceptDomains(
			HttpServletRequest httpServletRequest,
			RestReadContext restReadContext,
			QueryControl queryControl,
			@RequestBody Query query,
			RestFilter restFilter,
			Page page,
			boolean list) {
		
		ResourceQueryBuilder builder = this.getNewResourceQueryBuilder();
		
		ResourceQuery resourceQuery = builder.
				addQuery(query).
				addRestFilter(restFilter).
				addRestReadContext(restReadContext).
				build();
		
		return this.doQuery(
				httpServletRequest,
				list, 
				this.conceptDomainQueryService,
				resourceQuery,
				page, 
				queryControl,
				ConceptDomainCatalogEntryDirectory.class, 
				ConceptDomainCatalogEntryList.class);
	}
	
	/**
	 * Does concept domain exist.
	 *
	 * @param httpServletResponse the http servlet response
	 * @param conceptDomainName the concept domain name
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAIN_BYID, method=RequestMethod.HEAD)
	@ResponseBody
	public void doesConceptDomainExist(
			HttpServletResponse httpServletResponse,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName) {
	
		this.doExists(httpServletResponse, 
				this.conceptDomainReadService,
				UnknownConceptDomain.class,
				ModelUtils.nameOrUriFromName(conceptDomainName));
	}
	
	/**
	 * Gets the concept domains count.
	 *
	 * @param httpServletResponse the http servlet response
	 * @param query the query
	 * @param resolvedFilter the filter
	 * @return the concept domains count
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAINS, method=RequestMethod.HEAD)
	@ResponseBody
	public void getConceptDomainsCount(
			HttpServletResponse httpServletResponse,
			RestReadContext restReadContext,
			RestFilter restFilter) {
		
		ResourceQueryBuilder builder = this.getNewResourceQueryBuilder();
		
		ResourceQuery resourceQuery = builder.
				addRestFilter(restFilter).
				addRestReadContext(restReadContext).
				build();
		
		int count = this.conceptDomainQueryService.count(resourceQuery);
		
		this.setCount(count, httpServletResponse);
	}

	/**
	 * Gets the concept domain by name.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param conceptDomainName the concept domain name
	 * @return 
	 * @return the concept domain by name
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAIN_BYID, method=RequestMethod.GET)
	public Object getConceptDomainByName(
			HttpServletRequest httpServletRequest,
			RestReadContext restReadContext,
			QueryControl queryControl,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName) {
			
		return this.doRead(
				httpServletRequest, 
				MESSAGE_FACTORY, 
				this.conceptDomainReadService,
				restReadContext,
				UnknownConceptDomain.class,
				ModelUtils.nameOrUriFromName(conceptDomainName));
	}
	
	/**
	 * Creates the concept domain.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param conceptDomain the concept domain
	 * @param changeseturi the changeseturi
	 * @param conceptDomainName the concept domain name
	 * @return 
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAIN, method=RequestMethod.POST)
	public Object createConceptDomain(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestBody ConceptDomainCatalogEntry conceptDomain,
			@RequestParam(value=PARAM_CHANGESETCONTEXT, required=false) String changeseturi) {
			
		return this.doCreate(
				httpServletResponse,
				conceptDomain, 
				changeseturi, 
				PATH_CONCEPTDOMAIN_BYID, 
				URL_BINDER, 
				this.conceptDomainMaintenanceService);
	}
	
	@RequestMapping(value=PATH_CONCEPTDOMAIN_BYID, method=RequestMethod.PUT)
	public Object updateConceptDomain(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestBody ConceptDomainCatalogEntry conceptDomain,
			@RequestParam(value=PARAM_CHANGESETCONTEXT, required=false) String changeseturi,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName) {
	
		return this.doUpdate(
				httpServletResponse,
				conceptDomain, 
				changeseturi, 
				ModelUtils.nameOrUriFromName(conceptDomainName), 
				this.conceptDomainMaintenanceService);
	}
	
	@RequestMapping(value=PATH_CONCEPTDOMAIN_BYID, method=RequestMethod.DELETE)
	public Object deleteConceptDomain(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@PathVariable(VAR_CONCEPTDOMAINID) String conceptDomainName,
			@RequestParam(PARAM_CHANGESETCONTEXT) String changeseturi) {

			return this.doDelete(
					httpServletResponse,
					ModelUtils.nameOrUriFromName(
							conceptDomainName), 
							changeseturi,
					this.conceptDomainMaintenanceService);
	}
	
	/**
	 * Gets the concept domain by uri.
	 *
	 * @param httpServletRequest the http servlet request
	 * @param uri the uri
	 * @return the concept domain by uri
	 */
	@RequestMapping(value=PATH_CONCEPTDOMAIN_BYURI, method=RequestMethod.GET)
	public ModelAndView getConceptDomainByUri(
			HttpServletRequest httpServletRequest,
			RestReadContext restReadContext,
			QueryControl queryControl,
			@RequestParam(PARAM_URI) String uri,
			@RequestParam(value="redirect", defaultValue=DEFAULT_REDIRECT) boolean redirect) {
		
		return this.doReadByUri(
				httpServletRequest, 
				MESSAGE_FACTORY, 
				PATH_CONCEPTDOMAIN_BYURI, 
				PATH_CONCEPTDOMAIN_BYID, 
				URL_BINDER, 
				this.conceptDomainReadService, 
				restReadContext,
				UnknownConceptDomain.class,
				ModelUtils.nameOrUriFromUri(uri),
				redirect);
	}
	
	private ResourceQueryBuilder getNewResourceQueryBuilder(){
		return new ResourceQueryBuilder(
			this.conceptDomainQueryService, 
			this.getFilterResolver(),
			this.getReadContextResolver());
	}

}