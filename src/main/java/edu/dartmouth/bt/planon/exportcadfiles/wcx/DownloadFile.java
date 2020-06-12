/*
 * Decompiled with CFR 0.146.
 */
package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.Serializable;
import java.util.Arrays;

public class DownloadFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String fileName;
    private final byte[] byteArr;

    public DownloadFile(String fileName, byte[] byteArr) {
        this.fileName = fileName;
        this.byteArr = Arrays.copyOf(byteArr, byteArr.length);
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getByteArr() {
        return Arrays.copyOf(this.byteArr, this.byteArr.length);
    }
}

