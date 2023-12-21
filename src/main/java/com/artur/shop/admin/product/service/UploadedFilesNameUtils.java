package com.artur.shop.admin.product.service;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

class UploadedFilesNameUtils {
    public static String slugifyFileName(String fileName) {
        String name = FilenameUtils.getBaseName(fileName);
        Slugify slg = new Slugify();
        String changedName = slg
                .withCustomReplacement("_", "-")
                .slugify(name);
        return changedName + "." + FilenameUtils.getExtension(fileName);
    }
}
