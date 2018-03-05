package com.company;

import java.net.URL;
import java.util.Scanner;
import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        Vector<String> links = new Vector<>();
       links = robotFetcher("https://twitter.com/robots.txt");//https://www.facebook.com/robots.txt
        for(String lstitem : links){

            System.out.println("List item: " + lstitem);
        }

    }

    /**
     * #@param  theLink the link in string data type
     *
     * @return string vector
     */
    public static Vector<String> robotFetcher(String theLink) {
        //vector of links
        Vector<String> links = new Vector<>();
        try {
            //fetch the url.txt
            URL robotFile = new URL(theLink);

            //read robot.txt line by line
            Scanner robotScanner = new Scanner(robotFile.openStream());
            while (robotScanner.hasNextLine()) {

                //search for user-agent
                String line = robotScanner.nextLine();
                if (line.equals("User-agent: *")) {

                    //scan till end of file or another User-agent
                    while (robotScanner.hasNextLine()) {
                        line = robotScanner.nextLine();

                        //search for disallow
                        if (line.contains("Disallow:"))

                            //get all the disallowed links
                            links.add(line.replaceAll("Disallow: ", ""));

                            //return if u found User-agent
                        else if (line.contains("User-agent:"))
                            return links;
                    }
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        //return after finishing the file
        return links;
    }
}
