package com.video.rental.store;


import org.apache.commons.io.IOUtils;

public final class FileUtil {
    private FileUtil() {
    }

    public static String readJsonResource(final String fileName) throws Exception {
        return IOUtils.toString(FileUtil.class.getResourceAsStream("/json/" + fileName + ".json"));
    }
}
