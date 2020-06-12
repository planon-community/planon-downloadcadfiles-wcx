package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;

import nl.planon.util.pnlogging.PnLogger;


public class AJAXDownload extends AbstractAjaxBehavior {
    private static final long serialVersionUID = 6657210969364945007L;
    private boolean addAntiCache;
    private DownloadFile downloadFile;
    private static final PnLogger LOG = PnLogger.getLogger(AJAXDownload.class);

    public AJAXDownload() {
        this(true);
    }

    public AJAXDownload(boolean addAntiCache) {
        this.addAntiCache = addAntiCache;
    }

    /**
     * Call this method to initiate the download.
     */
    public void initiate(AjaxRequestTarget target) {
        String url = this.getCallbackUrl().toString();
        if (this.addAntiCache) {
            url = url + (url.contains("?") ? "&" : "?");
            url = url + "antiCache=" + System.currentTimeMillis();
        }
        target.appendJavaScript((CharSequence)("setTimeout(\"window.open('" + url + "')\",100);"));
    }

    public void onRequest() {
        ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(this.getResourceStream(), this.downloadFile.getFileName());
        handler.setContentDisposition(ContentDisposition.ATTACHMENT);
        this.getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent((IRequestHandler)handler);
    }


    public DownloadFile getDownloadFile() {
        return this.downloadFile;
    }

    public void setDownloadFile(DownloadFile downloadFile) {
        this.downloadFile = downloadFile;
    }

    protected IResourceStream getResourceStream() {
        return new AbstractResourceStreamWriter(){

            public void write(OutputStream output) throws IOException {
                output.write(AJAXDownload.this.downloadFile.getByteArr());
            }
        };
    }

}

