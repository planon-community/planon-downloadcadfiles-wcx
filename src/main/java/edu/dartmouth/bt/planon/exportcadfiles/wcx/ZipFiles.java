package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import nl.planon.util.pnlogging.PnLogger;

public class ZipFiles {

    private Map<InputStream,String> dictionary;
    private static final PnLogger LOG = PnLogger.getLogger(WebDAVContext.class);
    private final ArrayList<String> errorList = new ArrayList<String>();

    public void setDictionary(Map<InputStream,String> dictionary){
        this.dictionary = dictionary;
    }

    public final void setErrorList(String error){
        errorList.add(error);
    }

    public final ArrayList<String> getErrorList(){
        return errorList;
    }

    public InputStream compress() throws IOException {
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(out);

		for (Map.Entry<InputStream,String> entry : this.dictionary.entrySet()) {
            addZipEntry(entry.getValue(), entry.getKey(), zos, buffer);
        }

        if (!this.getErrorList().isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(String s : this.getErrorList()){
                sb.append(s);
                sb.append("\n");
                LOG.info(s);
            }
            InputStream stream = new ByteArrayInputStream( sb.toString().getBytes("UTF-8") );
            addZipEntry("error.txt", stream, zos, buffer);
        }
        zos.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addZipEntry(String filename, InputStream stream,ZipOutputStream zos, byte buffer[]) throws IOException {
        zos.putNextEntry(new ZipEntry(filename));
        int length;
        try{
            while ((length = stream.read(buffer)) >= 0)
                zos.write(buffer, 0, length);
        }
        catch (NullPointerException | ZipException e) {
            setErrorList(filename);
            LOG.error("NullPointerException has occured for"+ filename);
        }
        zos.closeEntry();
    }
}