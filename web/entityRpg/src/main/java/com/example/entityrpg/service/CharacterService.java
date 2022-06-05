package com.example.entityrpg.service;

import com.example.entityrpg.models.Character;
import com.example.entityrpg.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.Key;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) throws InterruptedException {
        this.characterRepository = characterRepository;
        TimeUnit.SECONDS.sleep(10);
        //save(new Character(1L,generateUUID(),"Ja",1,1,100,100,false,false,null,null,null   ));
    }

    public UUID generateUUID()
    {
        return UUID.randomUUID();
    }

    public Character createCharacter(String name)
    {
        return new Character(generateUUID(),name,1,1,100,100,false,false);
    }

    public boolean validateString(String string)
    {
        if(string==null || string.length() == 0 || string.length()>=40 || string.contains(">")
        || string.contains("<") || string.contains("'") || string.contains(":") || string.contains("\\")
        || string.contains("\"") || string.contains("|") || string.contains("$") || string.contains("/"))
            return false;
        return true;
    }

    public boolean isInBattle(Character character)
    {
        if(character.getTimestampToEndFight() == null)
            return false;
        Date date = new Date();
        if(character.getTimestampToEndFight().before(new Timestamp(date.getTime())))
        {
            character.setAttack(character.getAttack()+character.getAddAtk());
            character.setHealth(character.getHealth()+character.getAddHealth());
            character.setCurrHealth(Math.max(character.getCurrHealth()-character.getRemoveHealth(),0));
            character.setAddAtk(0);
            character.setAddHealth(0);
            character.setRemoveHealth(0);
            character.setTimestampToEndFight(null);
            save(character);
            return false;
        }
        return true;
    }

    public boolean isInRegen(Character character)
    {
        if(character.getTimeStampToEndRegen() == null)
            return false;
        Date date = new Date();
        if(character.getTimeStampToEndRegen().before(new Timestamp(date.getTime())))
        {
            character.setCurrHealth(character.getRemoveHealth());
            character.setRemoveHealth(0);
            character.setTimeStampToEndRegen(null);
            save(character);
            return false;
        }
        return true;
    }

    public Character validateUUID(String stringUUID)
    {
        if(stringUUID==null || stringUUID.length()!=36)
            return null;
        Character character = findByUUID(UUID.fromString(stringUUID));
        if(character==null)
            return null;
        return character;
    }

    public void save(Character character){
        characterRepository.save(character);
    }

    public void regenHp(Character character)
    {
        if(!character.isInRegen() && !character.isInBattle() && character.getCurrHealth() < character.getHealth())
        {
            Date date = new Date();
            Long timeCalc = Math.min((character.getHealth()-character.getCurrHealth())*100L,1000000L);
            character.setTimeStampToEndRegen(new Timestamp(date.getTime()+(timeCalc)));
            character.setInRegen(true);
            character.setRemoveHealth(character.getHealth());
            save(character);
        }
    }

    public Character fightMonster(Character character,String monsterid)
    {
        int monsterAtk=0;
        int monsterHealth=0;
        if(monsterid.equals("1"))
        {
            character.setInBattle(true);
            Random r = new Random();
            int low = 2;
            int high = 5;
            monsterAtk = r.nextInt(high-low) + low;
            low = 15;
            high = 25;
            monsterHealth = r.nextInt(high-low) + low;
            int rounds = Math.min(character.getCurrHealth()/monsterAtk,monsterHealth/character.getAttack()) + 1;
            character.setRemoveHealth(rounds*monsterAtk);
            if(character.getRemoveHealth()>0)
            {
                Date date = new Date();
                character.setTimestampToEndFight(new Timestamp((date.getTime()+rounds*10000L)+100000L));
                if(character.getCurrHealth()-character.getRemoveHealth()>0)
                {
                    character.setAddAtk(monsterAtk);
                    character.setAddHealth(monsterHealth);
                }
            }
        }
        else if(monsterid.equals("2"))
        {
            character.setInBattle(true);
            Random r = new Random();
            int low = 20;
            int high = 51;
            monsterAtk = r.nextInt(high-low) + low;
            low = 150;
            high = 250;
            monsterHealth = r.nextInt(high-low) + low;
            int rounds = Math.min(character.getCurrHealth()/monsterAtk,monsterHealth/character.getAttack()) + 1;
            character.setRemoveHealth(rounds*monsterAtk);
            if(character.getRemoveHealth()>0)
            {
                Date date = new Date();
                character.setTimestampToEndFight(new Timestamp((date.getTime()+rounds*10000L)+100000L));
                if(character.getCurrHealth()-character.getRemoveHealth()>0)
                {
                    character.setAddAtk(monsterAtk);
                    character.setAddHealth(monsterHealth);
                }
            }
        }
        else if(monsterid.equals("3"))
        {
            character.setInBattle(true);
            Random r = new Random();
            int low = 390;
            int high = 591;
            monsterAtk = r.nextInt(high-low) + low;
            low = 1305;
            high = 2059;
            monsterHealth = r.nextInt(high-low) + low;
            int rounds = Math.min(character.getCurrHealth()/monsterAtk,monsterHealth/character.getAttack()) + 1;
            character.setRemoveHealth(rounds*monsterAtk);
            if(character.getRemoveHealth()>0)
            {
                Date date = new Date();
                character.setTimestampToEndFight(new Timestamp((date.getTime()+rounds*10000L)+100000L));
                if(character.getCurrHealth()-character.getRemoveHealth()>0)
                {
                    character.setAddAtk(monsterAtk);
                    character.setAddHealth(monsterHealth);
                }
            }
        }
        else if(monsterid.equals("4"))
        {
            character.setInBattle(true);
            Random r = new Random();
            int low = 9999990;
            int high = 9999999;
            monsterAtk = r.nextInt(high-low) + low;
            low = 9999990;
            high = 9999999;
            monsterHealth = r.nextInt(high-low) + low;
            int rounds = Math.min(character.getCurrHealth()/monsterAtk,monsterHealth/character.getAttack()) + 1;
            character.setRemoveHealth(rounds*monsterAtk);
            if(character.getRemoveHealth()>0 && character.getCurrHealth()>0)
            {
                Date date = new Date();
                character.setTimestampToEndFight(new Timestamp((date.getTime()+rounds*10000L)+100000L));
                if(character.getCurrHealth()-character.getRemoveHealth()>0)
                {
                    character.setAddAtk(monsterAtk);
                    character.setAddHealth(monsterHealth);
                    character.setBossKilled(true);
                    System.out.println("Congratuations - User " + character.getName() + " with UUID " + character.getUUID() + " got The flag!");
                }
            }
        }
        save(character);
        return character;
    }

    public Character findByUUID(UUID UUID)
    {
        return characterRepository.findCharacterByUUID(UUID);
    }

    public Character findByName(String name)
    {
        return characterRepository.findCharacterByName(name);
    }

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue =
            new byte[] { 'A', 'D', 'M', 'I', 'N', 'C', 'T', 'F', 'P', 'A', 'S', 'S', 'W', 'O', 'R', 'D' };

    public String encryptz(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encValue);
        return encryptedValue;
    }

    public String decryptz(String encryptedValue) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public String createXMLDoc(Character character)
            throws Exception {

        if(character == null)
            return "";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("entityRPG");
        doc.appendChild(rootElement);

        Element account = doc.createElement("account");
        rootElement.appendChild(account);
        account.setAttribute("id", "1001");

        Element name = doc.createElement("name");

        name.setTextContent(character.getName());
        account.appendChild(name);

        Element health = doc.createElement("health");
        health.setTextContent(character.getHealth().toString());
        account.appendChild(health);

        Element currHealth = doc.createElement("currHealth");
        currHealth.setTextContent(character.getCurrHealth().toString());
        account.appendChild(currHealth);

        Element attack = doc.createElement("attack");
        attack.setTextContent(character.getAttack().toString());
        account.appendChild(attack);

        Element hash = doc.createElement("characterHash");
        hash.setTextContent(encryptz(character.getAttack().toString()+":"+character.getHealth().toString()+":"+character.getCurrHealth().toString()));

        account.appendChild(hash);
        Comment comment = doc.createComment(
                "Due to safety reasons the XML data is checked by decrypting text in format attack:health:currentHealth which is signed AES with password stored in /app/home/password.txt .");
        account.appendChild(comment);
        //https://8gwifi.org/CipherFunctions.jsp
        return writeXmlDocumentToXmlFile(doc);
    }

    private String writeXmlDocumentToXmlFile(Document xmlDocument)
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();
            return xmlString;
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String readXML(String s,String uuid) throws Exception {
        ReadXMLDomParser readXMLDomParser = new ReadXMLDomParser(s, this);
        readXMLDomParser.read(uuid);
        return "a";
    }

    public void setNewData(String uuid,Integer attack,Integer health,Integer currHealth,String name)
    {
        Character character = this.validateUUID(uuid);
        if(character!=null)
        {
            character.setAttack(attack);
            character.setHealth(health);
            character.setCurrHealth(currHealth);
            if (name == null || name.length()==0) {
                character.setName("Undefinied");
            } else {
                character.setName(name);
            }
            character.setInBattle(false);
            character.setInRegen(false);
            character.setInfo("");
            character.setTimeStampToEndRegen(null);
            character.setTimestampToEndFight(null);
            character.setRemoveHealth(0);
            character.setAddHealth(0);
            character.setAddAtk(0);
            this.save(character);
        }
    }

    public void setCharInfo(String uuid,String info)
    {
        Character character = this.validateUUID(uuid);
        if(character!=null)
        {
            character.setInfo(info);
        }
        this.save(character);
    }
    public String readFlag() throws IOException {
        FileReader file = new FileReader("/secretFlagTxtFileToHideBeforeXMLEntityReading.txt");
        BufferedReader buffer = new BufferedReader(file);
        String line = buffer.readLine();
        //System.out.println("Someone get the flag! Congrats " + line);
        return line;
    }

}
