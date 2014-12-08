package com.example.server.utils;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final String kDataDir = "data/";

    public static void writeToFile(final String fileName, final byte[] buffer, final boolean append) {
        writeToFile(fileName, buffer, 0, buffer.length, append);
    }

    public static void writeToFile(final String fileName,
                                   final byte[] buffer,
                                   final int startPos,
                                   final int length,
                                   final boolean append) {
        final String entirelyFileName = kDataDir + fileName;
        Log.log("about to write: " + entirelyFileName + ", dataSize: " + String.valueOf(length));
        try {
            final FileOutputStream fos = new FileOutputStream(entirelyFileName, append);
            fos.write(buffer, startPos, length);
            fos.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
