package com.ibm.dataexplorer.converters.bootstrap;

import com.ibm.dataexplorer.converter.shaded.org.apache.commons.lang.StringEscapeUtils;
import com.ibm.dataexplorer.xml.XmlUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;

/**
 * A utility class to add a given log4j category name to the default value of the Java converter Logging Config.
 */
public class AddLoggingCategoryToDefaultValue {

    public static final void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Args must be: <path_to_crawler.xsd> <log4j_category_name>");
        }
        String crawlerXsdPath = args[0];
        String categoryName = args[1];

        File crawlerXsdFile = new File(crawlerXsdPath);
        File outputFile = new File(crawlerXsdPath + ".new");
        StringBuilder sb = new StringBuilder();
        LineIterator it = FileUtils.lineIterator(crawlerXsdFile, "UTF-8");
        while (it.hasNext()) {
            String line = it.next();
            if (line.contains("<xs:enumeration value=\"java-parser-logging-config\"")) {
                line = updateDefaultValue(line, categoryName);
            }
            sb.append(line).append(System.getProperty("line.separator"));
        }
        FileUtils.writeStringToFile(outputFile, sb.toString(), "UTF-8");
        it.close();
        if (outputFile.renameTo(crawlerXsdFile)) {
            System.out.println("Completed Successfully!");
        } else {
            System.err.println("Could not rename file: " + outputFile.getAbsolutePath() + " to: "
                    + crawlerXsdFile.getAbsolutePath());
        }

    }

    private static String updateDefaultValue(String line, String categoryName) {
        String defaultAttributeStr = "vs:default=\"";
        int indexOfDefault = line.indexOf(defaultAttributeStr);
        int indexOfEndingQuote = line.indexOf("\"", indexOfDefault + defaultAttributeStr.length());
        String before = line.substring(0, indexOfDefault);
        String existingDefaultValue = line.substring(indexOfDefault + defaultAttributeStr.length(), indexOfEndingQuote);
        String after = line.substring(indexOfEndingQuote);

        String decodedDefaultValue = StringEscapeUtils.unescapeXml(existingDefaultValue);

        Element el = XmlUtils.stringToElement(decodedDefaultValue);
        NodeList categories = el.getElementsByTagName("category");
        for (int i = 0; i < categories.getLength(); i++) {
            Element category = (Element) categories.item(i);
            if (category.getAttribute("name").equals("com.ibm.dataexplorer.converter")) {
                Document doc = el.getOwnerDocument();
                Element newCategory = doc.createElement("category");
                newCategory.setAttribute("name", categoryName);
                Element priority = doc.createElement("priority");
                newCategory.appendChild(priority);
                priority.setAttribute("value", "OFF");
                category.getParentNode().insertBefore(newCategory, category.getNextSibling());
                break;
            }
        }
        String modifiedDefaultValue = XmlUtils.nodeToStringWithNoPreamble(el);
        String modifiedEncodedDefaultValue = StringEscapeUtils.escapeXml(modifiedDefaultValue);

        line = before + defaultAttributeStr + modifiedEncodedDefaultValue + after;

        return line;
    }
}
