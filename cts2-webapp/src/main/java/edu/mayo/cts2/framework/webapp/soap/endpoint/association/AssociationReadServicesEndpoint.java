package edu.mayo.cts2.framework.webapp.soap.endpoint.association;

import edu.mayo.cts2.framework.model.association.Association;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.FormatReference;
import edu.mayo.cts2.framework.model.extension.LocalIdAssociation;
import edu.mayo.cts2.framework.model.service.core.FunctionalProfileEntry;
import edu.mayo.cts2.framework.model.service.core.ProfileElement;
import edu.mayo.cts2.framework.model.service.core.QueryControl;
import edu.mayo.cts2.framework.model.service.core.ReadContext;
import edu.mayo.cts2.framework.model.service.core.types.FunctionalProfile;
import edu.mayo.cts2.framework.model.service.core.types.ImplementationProfile;
import edu.mayo.cts2.framework.model.service.core.types.StructuralProfile;
import edu.mayo.cts2.framework.model.wsdl.associationread.*;
import edu.mayo.cts2.framework.model.wsdl.baseservice.*;
import edu.mayo.cts2.framework.service.profile.association.AssociationReadService;
import edu.mayo.cts2.framework.service.profile.association.name.AssociationReadId;
import edu.mayo.cts2.framework.webapp.soap.endpoint.AbstractReadServiceEndpoint;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.concurrent.Callable;

@Endpoint("AssociationReadServicesEndpoint")
public class AssociationReadServicesEndpoint extends AbstractReadServiceEndpoint {

  @Cts2Service
  private AssociationReadService associationReadService;

  /* TODO: Implement Method: read */
  @PayloadRoot(localPart = "read", namespace = CTS2_NAMESPACE_ROOT + "AssociationReadServices")
  @ResponsePayload
  public ReadResponse read(@RequestPayload Read request) {
    throw new UnsupportedOperationException("Method not implemented.");
//    Association association = this.doRead(
//        this.associationReadService,
//        request.getAssociationID(),
//        request.getQueryControl(),
//        request.getContext());
//
//    ReadResponse readResponse = new ReadResponse();
//    readResponse.setReturn(association);
//
//    return readResponse;
  }

  /* TODO: Implement Method: exists */
  @PayloadRoot(localPart = "exists", namespace = CTS2_NAMESPACE_ROOT + "AssociationReadServices")
  @ResponsePayload
  public ExistsResponse exists(@RequestPayload Exists request) {
    throw new UnsupportedOperationException("Method not implemented.");
//    boolean exists = this.associationReadService.exists(
//        request.getAssociationID(),
//        request.getContext());
//
//    ExistsResponse existsResponse = new ExistsResponse();
//    existsResponse.setReturn(exists);
//
//    return existsResponse;
  }

  /* TODO: Implement Method: readByExternalStatementId */
  @PayloadRoot(localPart = "readByExternalStatementId", namespace = CTS2_NAMESPACE_ROOT + "AssociationReadServices")
  @ResponsePayload
  public ReadByExternalStatementIdResponse readByExternalStatementId(@RequestPayload ReadByExternalStatementId request) {
    throw new UnsupportedOperationException("Method not implemented.");
//    Association association = this.doReadExternalStatementId();
//    ReadByExternalStatementIdResponse response = new ReadByExternalStatementIdResponse();
//    response.setReturn();
  }

  /* TODO: Implement Method: existsByExternalStatementId */
  @PayloadRoot(localPart = "existsByExternalStatementId", namespace = CTS2_NAMESPACE_ROOT + "AssociationReadServices")
  @ResponsePayload
  public ExistsByExternalStatementIdResponse exists(@RequestPayload ExistsByExternalStatementId request) {
    throw new UnsupportedOperationException("Method not implemented.");
//    boolean exists = this.associationReadService.exists(request.getExternalStatementId(), request.getContext());
//    ExistsByExternalStatementIdResponse response = new ExistsByExternalStatementIdResponse();
//    response.setReturn(exists);
//
//    return response;
  }

  /*******************************************************/
  /*                   BaseServices                      */
  /*******************************************************/
  @PayloadRoot(localPart = "getDefaultFormat", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetDefaultFormatResponse getDefaultFormat(@RequestPayload GetDefaultFormat request) {
    FormatReference format = new FormatReference("SOAP");

    GetDefaultFormatResponse response = new GetDefaultFormatResponse();
    response.setReturn(format);

    return response;
  }

  @PayloadRoot(localPart = "getImplementationType", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetImplementationTypeResponse getImplementationType(@RequestPayload GetImplementationType request) {
    ImplementationProfile implementations[] = new ImplementationProfile[1];
    implementations[0] = ImplementationProfile.IP_SOAP;

    GetImplementationTypeResponse response = new GetImplementationTypeResponse();
    response.setReturn(implementations);

    return response;
  }

  @PayloadRoot(localPart = "getKnownNamespace", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetKnownNamespaceResponse getKnownNamespace(@RequestPayload GetKnownNamespace request) {
    GetKnownNamespaceResponse response = new GetKnownNamespaceResponse();
    response.setReturn(this.associationReadService.getKnownNamespaceList());

    return response;
  }

  @PayloadRoot(localPart = "getServiceDescription", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetServiceDescriptionResponse getServiceDescription(@RequestPayload GetServiceDescription request) {
    GetServiceDescriptionResponse response = new GetServiceDescriptionResponse();
    response.setReturn(this.associationReadService.getServiceDescription());

    return response;
  }

  @PayloadRoot(localPart = "getServiceName", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetServiceNameResponse getServiceName(@RequestPayload GetServiceName request) {
    GetServiceNameResponse response = new GetServiceNameResponse();
    response.setReturn(this.associationReadService.getServiceName());

    return response;
  }

  @PayloadRoot(localPart = "getServiceProvider", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetServiceProviderResponse getServiceProvider(@RequestPayload GetServiceProvider request) {
    GetServiceProviderResponse response = new GetServiceProviderResponse();
    response.setReturn(this.associationReadService.getServiceProvider());

    return response;
  }

  @PayloadRoot(localPart = "getServiceVersion", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetServiceVersionResponse getServiceVersion(@RequestPayload GetServiceVersion request) {
    GetServiceVersionResponse response = new GetServiceVersionResponse();
    response.setReturn(this.associationReadService.getServiceVersion());

    return response;
  }

  @PayloadRoot(localPart = "getSupportedFormat", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetSupportedFormatResponse getSupportedFormat(@RequestPayload GetSupportedFormat request) {
    FormatReference format[] = new FormatReference[1];
    format[0] = new FormatReference("SOAP");

    GetSupportedFormatResponse response = new GetSupportedFormatResponse();
    response.setReturn(format);

    return response;
  }

  @PayloadRoot(localPart = "getSupportedProfile", namespace = CTS2_NAMESPACE_ROOT + "BaseServiceTypes")
  @ResponsePayload
  public GetSupportedProfileResponse getSupportedProfile(@RequestPayload GetSupportedProfile request) {
    ProfileElement profile = new ProfileElement();
    profile.setStructuralProfile(StructuralProfile.SP_ASSOCIATION);

    FunctionalProfileEntry entry = new FunctionalProfileEntry();
	entry.setContent(FunctionalProfile.FP_READ.name());
    profile.addFunctionalProfile(entry);

    ProfileElement profiles[] = new ProfileElement[1];
    profiles[0] = profile;

    GetSupportedProfileResponse response = new GetSupportedProfileResponse();
    response.setReturn(profiles);

    return response;
  }

  private Association doRead(
      final AssociationReadService readService,
      final AssociationReadId associationId,
      final QueryControl queryControl,
      final ReadContext context) {
    final ResolvedReadContext resolvedReadContext = this.resolveReadContext(context);

    return this.doTimedCall(new Callable<Association>() {

      @Override
      public Association call() throws Exception {
        LocalIdAssociation localIdAssociation = readService.read(associationId, resolvedReadContext);
        if(localIdAssociation != null){
            return localIdAssociation.getResource();
        } else {
            return null;
        }
      }
    }, queryControl);
  }
  
}
