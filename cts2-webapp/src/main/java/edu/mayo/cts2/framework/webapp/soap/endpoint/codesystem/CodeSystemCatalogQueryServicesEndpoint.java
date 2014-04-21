package edu.mayo.cts2.framework.webapp.soap.endpoint.codesystem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedFilter;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.service.core.FilterComponent;
import edu.mayo.cts2.framework.model.service.core.Query;
import edu.mayo.cts2.framework.model.service.core.QueryControl;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetKnownProperty;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetKnownPropertyResponse;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetSupportedMatchAlgorithm;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetSupportedMatchAlgorithmResponse;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetSupportedModelAttribute;
import edu.mayo.cts2.framework.model.wsdl.basequeryservice.GetSupportedModelAttributeResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Count;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.CountResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Difference;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.DifferenceResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.GetAllCodeSystems;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.GetAllCodeSystemsResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Intersect;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.IntersectResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Resolve;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.ResolveResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Restrict;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.RestrictResponse;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.Union;
import edu.mayo.cts2.framework.model.wsdl.codesystemquery.UnionResponse;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemQueryService;
import edu.mayo.cts2.framework.webapp.soap.directoryuri.DirectoryUriUtils;
import edu.mayo.cts2.framework.webapp.soap.directoryuri.SoapDirectoryUri;
import edu.mayo.cts2.framework.webapp.soap.directoryuri.SoapDirectoryUriRequest;
import edu.mayo.cts2.framework.webapp.soap.endpoint.AbstractQueryServiceEndpoint;
import edu.mayo.cts2.framework.webapp.soap.resolver.FilterResolver;

@Endpoint("CodeSystemCatalogQueryServicesEndpoint")
public class CodeSystemCatalogQueryServicesEndpoint extends AbstractQueryServiceEndpoint {
	
	@Cts2Service 
	private CodeSystemQueryService codeSystemQueryService;
	
	@PayloadRoot(localPart = "getAllCodeSystems", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
	@ResponsePayload
	public GetAllCodeSystemsResponse getAllCodeSystems(@RequestPayload final GetAllCodeSystems request) {
		GetAllCodeSystemsResponse response = new GetAllCodeSystemsResponse();
		
		SoapDirectoryUriRequest<?> directoryUri = 
				(SoapDirectoryUriRequest<?>) DirectoryUriUtils.buildSoapDirectoryUriRequest(0, null, null);
		
		response.setReturn(DirectoryUriUtils.serialize(directoryUri));
	
		return response;
	}

	@PayloadRoot(localPart = "resolve", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
	@ResponsePayload
	public ResolveResponse resolve(@RequestPayload final Resolve request) {

        return this.doResolve(this.codeSystemQueryService, request, CodeSystemCatalogEntryDirectory.class,
                new ResponseBuilder<ResolveResponse, CodeSystemCatalogEntryDirectory>() {
            @Override
            public ResolveResponse buildResponse(CodeSystemCatalogEntryDirectory directory) {
                ResolveResponse response = new ResolveResponse();
                response.setCodeSystemCatalogEntryDirectory(directory);

                return response;
            }
        });
	}

  @PayloadRoot(localPart = "getSupportMatchAlgorithm", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public GetSupportedMatchAlgorithmResponse getSupportMatchAlgorithm(@RequestPayload GetSupportedMatchAlgorithm request) {
    GetSupportedMatchAlgorithmResponse response = new GetSupportedMatchAlgorithmResponse();
//    response.setReturn(this.codeSystemQueryService.getSupportedMatchAlgorithms());
    return response;
  }

  @PayloadRoot(localPart = "getSupportedModelAttribute", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public GetSupportedModelAttributeResponse getSupportedModelAttribute(@RequestPayload GetSupportedModelAttribute request) {
    GetSupportedModelAttributeResponse response = new GetSupportedModelAttributeResponse();
    return response;
  }

  @PayloadRoot(localPart = "getKnownProperty", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public GetKnownPropertyResponse getKnownProperty(@RequestPayload GetKnownProperty request) {
    GetKnownPropertyResponse response = new GetKnownPropertyResponse();
    return response;
  }

  @PayloadRoot(localPart = "count", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public CountResponse count(@RequestPayload Count request) {
    CountResponse response = new CountResponse();
    return response;
  }

  @PayloadRoot(localPart = "difference", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public DifferenceResponse difference(@RequestPayload Difference request) {
    DifferenceResponse response = new DifferenceResponse();
    return response;
  }

  @PayloadRoot(localPart = "intersect", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public IntersectResponse intersect(@RequestPayload Intersect request) {
    IntersectResponse response = new IntersectResponse();
    return response;
  }

  @PayloadRoot(localPart = "restrict", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public RestrictResponse restrict(@RequestPayload Restrict request) {
    RestrictResponse response = new RestrictResponse();
    return response;
  }

  @PayloadRoot(localPart = "union", namespace = CTS2_NAMESPACE_ROOT + "CodeSystemCatalogQueryServices")
  @ResponsePayload
  public UnionResponse union(@RequestPayload Union request) {
    UnionResponse response = new UnionResponse();
    return response;
  }
	


}
