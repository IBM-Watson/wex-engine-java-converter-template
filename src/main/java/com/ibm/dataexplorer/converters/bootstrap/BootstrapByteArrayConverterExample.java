package com.ibm.dataexplorer.converters.bootstrap;

// TODO: Rename package to the name of your converter

import com.ibm.dataexplorer.converter.ByteArrayConverter;
import com.ibm.dataexplorer.converter.ConversionException;
import com.ibm.dataexplorer.converter.ConverterOptions;
import com.ibm.dataexplorer.converter.FatalConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static com.ibm.dataexplorer.converter.LoggingConstants.*;

/**
 * TODO: Rename this class to better describe what this plugin will do
 *
 */
public class BootstrapByteArrayConverterExample implements ByteArrayConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapByteArrayConverterExample.class);
    private ConverterOptions options;
    private boolean isAlive;

    public BootstrapByteArrayConverterExample(ConverterOptions options) throws FatalConverterException {
        this.options = options;
        isAlive = true;

    }

    @Override
    public byte[] convert(byte[] data) throws ConversionException, FatalConverterException {
        LOGGER.trace(PUBLIC_ENTRY);
        checkIsAlive();

        try {
            String stringData = convertToString(data);
            /*
             * TODO: Actually perform an operation on stringData here.
             */
            LOGGER.trace(PUBLIC_EXIT);
            return convertToBytes(stringData);
        } catch (Exception e) {
            throw new ConversionException("Some useful message to the end user goes here.", e);
        }
    }

    private byte[] convertToBytes(String data) throws UnsupportedEncodingException {
        return data == null ? null : data.getBytes("UTF-8");
    }

    private String convertToString(byte[] data) throws UnsupportedEncodingException {
        return data == null ? "" : new String(data, "UTF-8");
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
