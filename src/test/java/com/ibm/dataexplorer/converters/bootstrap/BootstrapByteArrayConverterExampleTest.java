package com.ibm.dataexplorer.converters.bootstrap;

import static org.fest.assertions.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.ibm.dataexplorer.converter.ConverterOptions;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO: Refactor this class' name to match the refactored 'BootstrapParser' class.
 * 
 */
public class BootstrapByteArrayConverterExampleTest {

    private static final File testResourcesDir = new File("src/test/resources/");

    @BeforeClass
    public static void setUp() {
        PropertyConfigurator.configure(new File(testResourcesDir, "log4j.properties").getAbsolutePath());
    }

    /*
     * TODO: Provide more meaningful tests to verify your converter works correctly
     */
    @Test
    public void bootstrap_converter_converts() throws Exception {
        byte[] input = convertToBytes("Hello World!");
        ConverterOptions options = new ConverterOptions("");
        BootstrapByteArrayConverterExample bp = new BootstrapByteArrayConverterExample(options);

        byte[] actual = bp.convert(input);

        assertThat(convertToString(actual).equals(convertToString(input)));
    }

    private String convertToString(byte[] in)
            throws UnsupportedEncodingException {
        return in == null ? null : new String(in, "UTF-8");
    }

    private byte[] convertToBytes(String in) throws UnsupportedEncodingException {
        return in == null ? null : in.getBytes("UTF-8");
    }
}
