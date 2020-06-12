package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import nl.planon.util.pnlogging.PnLogger;

public class Activator implements BundleActivator {
    private static final PnLogger LOG = PnLogger.getLogger(ExportCadFilesWCX.class);
    private final List<ServiceRegistration> serviceRegisterList = new ArrayList<>();

    public void start(BundleContext aContext) throws Exception {
        LOG.info("WCX " + getClass().getName() + " starting..." + ExportCadFilesWCX.class.getName());
        Dictionary<String, String> props = new Hashtable<>();
        props.put("ExportCadFilesWCX - Description ", "This web client extension will export the CAD drawing files for all selected Spaces.");
        this.serviceRegisterList.add(aContext.registerService(ExportCadFilesWCX.class.getName(), new ExportCadFilesWCX(), props));
    }
    public void stop(BundleContext bundleContext) throws Exception  {
        LOG.info("WCX " + getClass().getName() + " stopping..." + ExportCadFilesWCX.class.getName());
        for (ServiceRegistration registration : this.serviceRegisterList) {
          registration.unregister();
        }
        this.serviceRegisterList.clear();
    }
}