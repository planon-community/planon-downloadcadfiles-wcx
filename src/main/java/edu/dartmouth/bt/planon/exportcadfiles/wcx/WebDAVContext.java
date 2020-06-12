package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import nl.planon.enterprise.service.api.IPnESWebDAVContext;
import nl.planon.enterprise.service.api.IPnESWebDAVResource;
import nl.planon.enterprise.service.api.PnESBusinessException;
import nl.planon.util.pnlogging.PnLogger;

public class WebDAVContext {

    private Context enterpriseContext;
    private static final PnLogger LOG = PnLogger.getLogger(WebDAVContext.class);

    public WebDAVContext(Context enterpriseContext) {
        this.enterpriseContext = enterpriseContext;
    }

    public IPnESWebDAVContext getWebDAVContext() throws IOException, PnESBusinessException {
        return this.enterpriseContext.getContext().getWebDAVContext(true);
    }

    public void setWebDAVContext(Context enterpriseContext) {
        this.enterpriseContext = enterpriseContext;
    }

    public IPnESWebDAVResource getWebDAVResource(String hostname, String filepath) throws IOException, PnESBusinessException, URISyntaxException {
        return this.getWebDAVContext().getWebDAVResource(new URI("https", hostname, filepath, null));
    }
}