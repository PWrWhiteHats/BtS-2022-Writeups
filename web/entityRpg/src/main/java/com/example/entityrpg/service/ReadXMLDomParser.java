package com.example.entityrpg.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReadXMLDomParser {

    private final String FILENAME;
    private CharacterService characterService;

    public ReadXMLDomParser(String filename, CharacterService characterService) {
        FILENAME = filename;
        this.characterService = characterService;
    }

    public void read(String uuid) throws Exception {

        // Instantiate the Factory
        System.out.println("Filename is " + FILENAME);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML not securely, vuln to attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
            //dbf.setFeature(XMLConstants.ACCESS_EXTERNAL_SCHEMA,false);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <staff>
            NodeList list = doc.getElementsByTagName("account");

            for (int temp = 0; temp < 1/*list.getLength()*/; temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get staff's attribute
                    String id = element.getAttribute("id");

                    // get text
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String health = element.getElementsByTagName("health").item(0).getTextContent();
                    String currHealth = element.getElementsByTagName("currHealth").item(0).getTextContent();
                    String attack = element.getElementsByTagName("attack").item(0).getTextContent();
                    String characterHash = element.getElementsByTagName("characterHash").item(0).getTextContent();


                    if(characterService.decryptz(characterHash).equals(attack+":"+health+":"+currHealth))
                    {
                        try{
                            if(attack.length()>=10)
                                attack="2000000000";
                            if(health.length()>=10)
                                health="2000000000";
                            if(currHealth.length()>=10)
                                currHealth="2000000000";
                            if(name.length()>=50)
                                name="NAME_TOO_LONG";
                            characterService.setNewData(uuid,Integer.parseInt(attack),Integer.parseInt(health),Integer.parseInt(currHealth),name);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    }
                    else {
                        characterService.setCharInfo(uuid,"File character hash is not valid. Data was not loaded!");
                    }

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

}