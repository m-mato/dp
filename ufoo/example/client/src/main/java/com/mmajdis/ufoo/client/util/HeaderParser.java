package com.mmajdis.ufoo.client.util;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * @author Matej Majdis
 */
public class HeaderParser {

    private static final String key = "Host: domain-sample.com";
    private static final int keylength = key.length();
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        Set<Set<String>> allSets = new HashSet<>();

        File folder = new File("C:\\Users\\matej.majdis\\Downloads\\Archive");
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                boolean isInside = false;
                BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                String line;
                Set<String> actualHeaders = new HashSet<>(Collections.singletonList(key));
                while ((line = br.readLine()) != null) {
                    if (!isInside) {
                        if (!line.isEmpty() && line.length() > keylength) {
                            int start = line.length() - keylength;
                            if (line.substring(start, line.length()).equals(key)) {
                                isInside = true;
                            }
                        }
                    } else {
                        if (line.contains("\"")) {
                            if (!allSets.contains(actualHeaders)) {
                                allSets.add(actualHeaders);
                            }
                            actualHeaders = new HashSet<>(Collections.singletonList(key));
                            isInside = false;
                        } else {
                            actualHeaders.add(line);
                        }
                    }
                }
            }
        }

        String allSetsJson = gson.toJson(allSets, allSets.getClass());
        System.out.println(allSetsJson);

        PrintWriter out = new PrintWriter("C:\\Users\\matej.majdis\\Downloads\\headers-groups.json");
        out.println(allSetsJson);

    }
}
