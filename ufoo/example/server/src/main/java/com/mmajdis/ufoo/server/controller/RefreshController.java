package com.mmajdis.ufoo.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * Created by mato.majdis on 10.5.2017.
 */
@RestController
public class RefreshController {

    @RequestMapping("/analysis/getCSV")
    public String getCSV() {
        try(BufferedReader br = new BufferedReader(new FileReader("./distances.csv"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Error reading statistics data");
            return "";
        }


    }



}
