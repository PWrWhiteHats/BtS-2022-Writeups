package com.example.entityrpg.controllers;

import com.example.entityrpg.models.Character;
import com.example.entityrpg.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UploadController {

    private final CharacterService characterService;

    @Autowired
    public UploadController(CharacterService characterService) {
        this.characterService = characterService;
    }

    private final String UPLOAD_DIR = "./uploads/";

    @PostMapping("/upload")
    public String uploadFile(@CookieValue("user") String user, @RequestParam("file") MultipartFile file , RedirectAttributes attributes) throws Exception {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "errorPageXML";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if(!fileName.endsWith(".xml"))
        {

            attributes.addFlashAttribute("message", "Only xml files can be uploaded.");
            return "errorPageXML";
        }
        String content = new String(file.getBytes());
        System.out.println(content);
        if(content.toLowerCase(Locale.ROOT).contains(".dtd")) // nie ma tak latwo
        {
            attributes.addFlashAttribute("message", "You can not load dtd files so simply.");
            return "errorPageDTD";
        }
        Pattern p = Pattern.compile(".*\\/\\/.*\\/"); //sprawdzenie czy nie ma czegos w stylu //127.0.0.1/ :] mozna to obejsc!!! serwujac dtd pythonem po prostu w glownym katalogu http://127.0.0.1 bez ostatniego slasha
        Matcher m = p.matcher(content);
        if (m.find())
        {
            return "errorPageRegex";
        }
        Character character = characterService.validateUUID(user);
        if(character==null)
            return "errorPageBadCookies";
        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            characterService.readXML(UPLOAD_DIR+fileName,user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded file" + '!');

        return "uploadSuccessfull";
    }

}