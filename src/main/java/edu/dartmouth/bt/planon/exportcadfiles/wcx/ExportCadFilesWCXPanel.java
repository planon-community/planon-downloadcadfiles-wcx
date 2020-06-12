package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.io.IOUtils;

import nl.planon.enterprise.service.api.IPnESWebDAVResource;
import nl.planon.enterprise.service.api.PnESBusinessException;
import nl.planon.util.pnlogging.PnLogger;
import nl.planon.zeus.clientextension.cxinterface.ICXContext;

public class ExportCadFilesWCXPanel extends Panel {

	private static final long serialVersionUID = 16L;
	private String errorString = "";
	private static final PnLogger LOG = PnLogger.getLogger(ExportCadFilesWCXPanel.class);
	private final ModalWindow resultWindow = new ModalWindow("popupPanel");
	private final ModalWindow completedWindow = new ModalWindow("popupPanelCompleted");
	protected final AJAXDownload ajaxDownload = new AJAXDownload();
	private final int limit = 1000;


	// ~ Constructors
	// ---------------------------------------------------------------------------------
	/**
	 * Creates a new ExportCadFilesWCXPanel object.
	 *
	 * @param aClientContext
	 * @param aWicketID
	 * @param aParameter
	 */
	public ExportCadFilesWCXPanel(ICXContext aClientContext, String aWicketID, String aParameter) {
		super(aWicketID);
		LOG.info("Generating panel " + getClass().getName());

		DataProvider userProvider = new DataProvider(aClientContext);
		if (aClientContext.getSelectedBOs().size() == 0)
			this.errorString = "Please select a BO!";

		addComponents(userProvider);
	}

	// ~ Methods
	// --------------------------------------------------------------------------------------

	/**
	 * Add components
	 *
	 * @param aDataProvider
	 */
	private void addComponents(DataProvider aDataProvider) {

		// Make sure the user can not request more than the limit.
		List<FieldDetails> fieldDetailsList = aDataProvider.getFieldDetailsList();
		if (fieldDetailsList.size() > this.limit)
			this.errorString = "You have selected "+fieldDetailsList.size()+" files. Please select less than or equal to "+Integer.toString(this.limit)+" files.";

		// Initialize variables
		Label error = new Label("error", this.errorString);
		Label totalLabel = new Label("totalLabel", "CAD drawing(s) to be downloaded: ");
		Label total = new Label("total", Integer.toString(aDataProvider.getFieldDetailsList().size()));

		AjaxLink<Void> cancel = new AjaxLink<Void>("Cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				resultWindow.close(target);
			}
		};
		AjaxLink<Void> export = new AjaxLink<Void>("Export") {
			private static final long serialVersionUID = 2L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				Map<InputStream, String> dictInputStream = new HashMap<InputStream, String>();

				for (int i = 0; i < fieldDetailsList.size(); i++) {
					String webdavURL = fieldDetailsList.get(i).getFieldDetail().getFieldValue();
					String[] webdavURL_split = webdavURL.split("/webdav");
					String hostname = webdavURL_split[0].split("://")[1];
					File file = new File(webdavURL_split[1]);

					InputStream inStream = getInputStream(hostname, "/webdav" + webdavURL_split[1]);
					dictInputStream.put(inStream, file.getName());
				}

				ZipFiles zipf = new ZipFiles();
				zipf.setDictionary(dictInputStream);
				InputStream instreamzip;

				try {
					instreamzip = zipf.compress();
					byte[] byteArr = IOUtils.toByteArray(instreamzip);

					DownloadFile downloadFile = new DownloadFile("CAD Drawings.zip", byteArr);
					ExportCadFilesWCXPanel.this.ajaxDownload.setDownloadFile(downloadFile);
					ExportCadFilesWCXPanel.this.ajaxDownload.initiate(target);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		// Add the components to the modal
		add(new Component[] { this.resultWindow });
		if (this.errorString.isEmpty()){
			LOG.info(this.errorString);
			add(totalLabel.setVisible(true));
			add(total.setVisible(true));
			add(cancel.setVisible(true));
			add(ExportCadFilesWCXPanel.this.ajaxDownload);
			add(export.setVisible(true));
			add(error.setVisible(false));
		}
		else {
			LOG.info(this.errorString);
			add(totalLabel.setVisible(false));
			add(total.setVisible(false));
			add(cancel.setVisible(true));
			add(ExportCadFilesWCXPanel.this.ajaxDownload);
			add(export.setVisible(false));
			add(error.setVisible(true));
		}
	}

	private java.io.InputStream getInputStream(String hostname, String filepath) {
		try {

			// Initialize the Context class
			Context enterpriseContext = new Context();

			// Get webDAVContext from Enterprise Service API Context
			WebDAVContext webdavcontextFromClass = new WebDAVContext(enterpriseContext);

			// Get webDAVResource from webDAVContext
			IPnESWebDAVResource webDAVResource = webdavcontextFromClass.getWebDAVResource(hostname, filepath);

			// Get the InputStream from webDAVResource
			InputStream inputstream = webDAVResource.get();

			return inputstream;

		} catch (IOException | PnESBusinessException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}