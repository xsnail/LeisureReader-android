package com.xsnail.leisurereader.data.support;

public class DownloadProgress {

    public String bookId;

    public String message;

    public boolean isAlreadyDownload = false;

    public DownloadProgress(String bookId, String message, boolean isAlreadyDownload) {
        this.bookId = bookId;
        this.message = message;
        this.isAlreadyDownload = isAlreadyDownload;
    }
}
