package com.ibm.dataexplorer.converters.bootstrap;

// TODO: Rename package to the name of your converter

import com.ibm.dataexplorer.converter.*;

import com.ibm.dataexplorer.converter.shaded.org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static com.ibm.dataexplorer.converter.LoggingConstants.*;

/**
 * TODO: Rename this class to better describe what this plugin will do
 *
 */
public class BootstrapFileConverterExample implements FileConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapFileConverterExample.class);
    private ConverterOptions options;
    private boolean isAlive;

    public BootstrapFileConverterExample(ConverterOptions options) throws FatalConverterException {
        this.options = options;
        isAlive = true;
    }

    @Override
    public void convert(Path inputPath, Path outputPath) throws ConversionException, FatalConverterException {
        LOGGER.trace(PUBLIC_ENTRY);
        checkIsAlive();

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(inputPath.toFile());
            outputStream = new FileOutputStream(outputPath.toFile());
            /*
             * TODO: Actually perform an operation on inputPath here instead of just copying it.
             */
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, bytesRead);
            }

        } catch (Exception e) {
            throw new ConversionException("Some useful message to the end user goes here.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            try {
                outputStream.close();
            } catch (IOException ioe) {
                LOGGER.error("Couldn't close stream on file:" + outputPath);
                throw new ConversionExecutionException(ioe);
            }

        }
        LOGGER.trace(PUBLIC_EXIT);
    }

    @Override
    public void terminate() {
        checkIsAlive();

        LOGGER.debug("Terminating");
        isAlive = false;

        // Nothing to terminate
    }

    private void checkIsAlive() {
        if (!isAlive()) {
            LOGGER.error("I've already been terminated");
            throw new IllegalStateException("The object has already been terminated");
        }
    }

    boolean isAlive() {
        return isAlive;
    }
}
