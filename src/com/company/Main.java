package com.company;

import java.net.URL;
import java.util.Scanner;
import java.io.IOException;
import java.util.Vector;
import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Main {

    public static void main(String[] args) throws Exception {
//        //  TESTS

//        Vector<String> links = new Vector<>();
//       links = robotFetcher("https://twitter.com/robots.txt");//https://www.facebook.com/robots.txt
//        for(String lstitem : links){
//
//            System.out.println("List item: " + lstitem);
//        }
//        Vector<String> links = new Vector<>();
//        String abzo = getSiteMapLink("http://www.mbc.net/robots.txt");
//        System.out.println("i am " + abzo);
//      links = siteMapFetcher(abzo);
//
//        for(String lstitem : links){
//
//            System.out.println("List item: " + lstitem);
//        }

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



    /**
     * #@param robotLink the link of robot.txt
     *
     * @return string link of siteMap if found or -1 if not found
     */
    public static String getSiteMapLink(String robotLink) throws Exception
    {
        URL robotFile = new URL(robotLink);

        String siteMap = "-1";

        //read robot.txt line by line
        Scanner robotScanner = new Scanner(robotFile.openStream());
        while (robotScanner.hasNextLine()) {
            String line = robotScanner.nextLine();

            //search for disallow
            if (line.contains("Sitemap:") || line.contains("sitemap:") ) //websites have either one

               siteMap = line.toLowerCase().replaceAll("sitemap: ", "");
        }
        return siteMap;
    }



    /**
     * #@param  xmlLink the link of sitemap
     *
     * @return string vector of links
     */
    public static Vector<String> siteMapFetcher(String xmlLink) throws Exception{

        Vector<String> siteMapLinks = new Vector<>();
        Document doc = Jsoup.connect(xmlLink).get();

        //get the urls between loc  tag
        Elements urls = doc.getElementsByTag("loc");

        //saving the urls
        for (Element url : urls) {
            siteMapLinks.add(url.text());
        }

        return siteMapLinks;
    }

}
