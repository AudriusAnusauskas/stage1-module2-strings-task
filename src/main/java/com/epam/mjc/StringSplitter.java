package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
//        throw new UnsupportedOperationException("You should implement this method.");
            List<String> substrings = new ArrayList<>();
            substrings.add(source); // Add the initial source string as the first substring

            for (String delimiter : delimiters) {
                List<String> newSubstrings = new ArrayList<>();
                for (String substring : substrings) {
                    String[] parts = substring.split(delimiter);
                    for (String part : parts) {
                        if (!part.isEmpty()) {
                            newSubstrings.add(part);
                        }
                    }
                }
                substrings = newSubstrings;
            }

            return substrings;
    }
}
