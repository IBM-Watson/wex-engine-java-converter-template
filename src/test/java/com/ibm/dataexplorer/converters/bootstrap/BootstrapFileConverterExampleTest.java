package com.ibm.dataexplorer.converters.bootstrap;

import com.ibm.dataexplorer.converter.ConverterOptions;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.fest.assertions.Assertions.assertThat;

/**
 * TODO: Refactor this class' name to match the refactored 'BootstrapParser' class.
 * 
 */
public class BootstrapFileConverterExampleTest {

    private static final File expectedOutputDir = new File("src/test/resources/data/"
            + BootstrapFileConverterExampleTest.class.getSimpleName()
            + "/expectedoutput/");
    private static final File inputDir = new File("src/test/resources/data/"
            + BootstrapFileConverterExampleTest.class.getSimpleName()
            + "/input/");

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
        ConverterOptions options = new ConverterOptions("");
        BootstrapFileConverterExample bp = new BootstrapFileConverterExample(options);

        Path inputPath = new File(inputDir, "testInput.txt").toPath();
        Path actualOutputPath = Files.createTempFile("converter-test", "txt");
        File expectedOutputFile = new File(expectedOutputDir, "expected.txt");

        bp.convert(inputPath, actualOutputPath);

        String actualOutputContent = FileUtils.readFileToString(actualOutputPath.toFile(), "UTF-8");
        String expectedOutputContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8");

        assertThat(actualOutputContent).isEqualTo(expectedOutputContent);
    }

}
