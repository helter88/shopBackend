package com.artur.shop.admin.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UploadedFilesNameUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test test.png, test-test.png",
            "hello world.png, hello-world.png",
            "ąęćżź.png, aeczz.png",
            "Prod 1.png, prod-1.png",
            "Prod__1.png, prod-1.png"
    })
    void shouldSlugifyFileName(String in, String out) {
        String slugifyFileName = UploadedFilesNameUtils.slugifyFileName(in);
        assertEquals(slugifyFileName,out);
    }
}