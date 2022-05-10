package fr.ostix.questCreator.utils;

import be.pcl.swing.*;

import java.text.*;
import java.util.regex.*;

public class Utils {

    /**
     * Extracts the first number out of a text.
     * Works for 1.000,1 and also for 1,000.1 returning 1000.1 (1000 plus 1 decimal).
     * When only a , or a . is used it is assumed as the float separator.
     *
     * @param sample The sample text.
     * @return A float representation of the number.
     */
    public static Float extractFloat(String sample) {
        Pattern pattern = Pattern.compile("[\\d.,]+");
        Matcher matcher = pattern.matcher(sample);
        if (!matcher.find()) {
            return null;
        }

        String floatStr = matcher.group();

        if (floatStr.matches("\\d+,+\\d+")) {
            floatStr = floatStr.replaceAll(",+", ".");

        } else if (floatStr.matches("\\d+\\.+\\d+")) {
            floatStr = floatStr.replaceAll("\\.\\.+", ".");

        } else if (floatStr.matches("(\\d+\\.+)+\\d+(,+\\d+)?")) {
            floatStr = floatStr.replaceAll("\\.+", "").replaceAll(",+", ".");

        } else if (floatStr.matches("(\\d+,+)+\\d+(.+\\d+)?")) {
            floatStr = floatStr.replaceAll(",", "").replaceAll("\\.\\.+", ".");
        }

        try {
            return new Float(floatStr);
        } catch (NumberFormatException ex) {
            throw new AssertionError("Unexpected non float text: " + floatStr);
        }
    }

    public static ImprovedFormattedTextField createFloatTextField() {
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMaximumFractionDigits(2);
        return new ImprovedFormattedTextField(floatFormat);
    }

    public static ImprovedFormattedTextField createIntTextField() {
        NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
        integerNumberInstance.setParseIntegerOnly(true);
        ImprovedFormattedTextField text = new ImprovedFormattedTextField(integerNumberInstance, 100);
        text.setColumns(5);
        return text;
    }
}
