package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.wicket.markup.html.panel.*;

import nl.planon.hera.webclientextension.wcxinterface.*;
import nl.planon.zeus.clientextension.cxinterface.*;
import nl.planon.util.pnlogging.PnLogger;

public class ExportCadFilesWCX implements IViewableWebClientExtension {
    private static final PnLogger LOG = PnLogger.getLogger(ExportCadFilesWCX.class);
    private ICXContext icxContext;
    private String parameter;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public final void execute(ICXContext aClientContext, String aParameter) {

        this.icxContext = aClientContext;
        this.parameter = aParameter;

        if (LOG.isInfoEnabled())
            LOG.info("Executing " + getClass().getName());

        // Get referenceDate and convert it into String
        Context enterpriseContext = new Context();
        Date referenceDate = enterpriseContext.getContext().getReferenceDate();
        String strReferenceDate = dateFormat.format(referenceDate);
        if (LOG.isInfoEnabled())
            LOG.info("Reference Date selected: " + strReferenceDate);

    }

    public void refresh() {
        if (LOG.isInfoEnabled())
            LOG.info("Refreshing " + getClass().getName());
    }

    // Getters
    public String getTitle() {
        return "Export CAD Files";
    }

    public String getDescription() {
        return "This web client extension will export the CAD drawing files for all selected Spaces.";
    }

    public Panel getExtensionPanel(String panelID) {
        return new ExportCadFilesWCXPanel(this.icxContext, panelID, this.parameter);
    }

}