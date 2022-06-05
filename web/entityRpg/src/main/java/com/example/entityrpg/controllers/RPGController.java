package com.example.entityrpg.controllers;
import com.example.entityrpg.models.Character;
import com.example.entityrpg.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RPGController {

    private final CharacterService characterService;

    @Autowired
    public RPGController(CharacterService characterService) {
        this.characterService = characterService;
    }
    @GetMapping("/")
    public String index(@CookieValue(value = "user",required = false) String user) throws IOException {
        if(characterService.validateUUID(user)==null)
            return "styledPage";
        Character character = characterService.validateUUID(user);
        if(character!=null)
            return "styledPagePlay";
        return "styledPage";
    }

    @GetMapping("/create")
    public String createAccount(Model model) {
        model.addAttribute("character",new Character());
        return "createPage";
    }

    @GetMapping("/load")
    public String loadAccount(Model model) {
        model.addAttribute("UUID",new String());
        return "loadPage";
    }

    @GetMapping("/createchar")
    public String createCharacter()
    {
        return "errorPage";
    }

    @PostMapping("/createchar")
    public String createCharacter(Model model,@ModelAttribute Character character, HttpServletResponse response) throws Exception {
        if(!characterService.validateString(character.getName()))
            return "errorPage";
        character = characterService.createCharacter(character.getName());
        characterService.save(character);
        response.addCookie(new Cookie("user", character.getUUID().toString()));
        model.addAttribute("character",character);
        return "playPage";
    }

    @PostMapping("/loadchar")
    public String loadchar(Model model,@RequestParam String UUID, HttpServletResponse response) throws Exception {
        Character character = characterService.validateUUID(UUID);
        System.out.println(UUID);
        if(character==null)
            return "errorPageLoadFailed";
        characterService.save(character);
        response.addCookie(new Cookie("user", character.getUUID().toString()));
        model.addAttribute("character",character);
        return "playPage";
    }

    @GetMapping("/play")
    public String playMenu(@CookieValue("user") String user,Model model,@ModelAttribute Character character) throws Exception {
        character = characterService.validateUUID(user);
        if(character==null)
            return "errorPage";
        character.setInBattle(characterService.isInBattle(character));
        character.setInRegen(characterService.isInRegen(character));
        //character.setInfo("");
        characterService.save(character);
        model.addAttribute("character",character);
        model.addAttribute("flag", characterService.readFlag());
        return "gamePage";
    }
    @PostMapping("/play")
    public String attack(@CookieValue("user") String user,Model model,@ModelAttribute Character character,@RequestParam(required = false,name = "monsterid") String monsterid,@RequestParam(required = false,name = "regenerate") String regenerate) throws Exception {
        character = characterService.validateUUID(user);
        int action = 0; // 1 monster 2 regen 0 nothing
        if(character==null)
        {
            System.out.println("nullek");
            return "errorPage";
        }
        if(monsterid!=null && !monsterid.isEmpty())
        {
            if(!monsterid.equals("4")&&!monsterid.equals("3")&&!monsterid.equals("2")&&!monsterid.equals("1"))
            {
                System.out.println("monsterek");
                return "errorPage";
            }
            action=1;
        }
        else if(regenerate != null && !regenerate.isEmpty())
        {
            if(!regenerate.equals("1"))
            {
                System.out.println("regenerak");
                return "errorPage";
            }
            action=2;
        }
        character.setInRegen(characterService.isInRegen(character));
        character.setInBattle(characterService.isInBattle(character));
        character.setInfo("");
        if(character.isInBattle())
        {
            character.setInfo("You are already in battle.");
            characterService.save(character);
            return "redirect:/play";
        }
        if(character.isInRegen())
        {
            character.setInfo("You are regenerating HP now.");
            characterService.save(character);
            return "redirect:/play";
        }
        model.addAttribute("character",character);
        if(action==1 && !character.isInBattle() && !character.isInRegen() && character.getCurrHealth()>0) {
            characterService.fightMonster(character,monsterid);
            character.setInBattle(characterService.isInBattle(character));
        }
        else if(action==2 && !character.isInBattle() && !character.isInRegen())
        {
            characterService.regenHp(character);
            character.setInRegen(characterService.isInRegen(character));
        }
        model.addAttribute("flag", characterService.readFlag());
        //character.setInfo("");
        return "gamePage";
    }
    @GetMapping("/import")
    public String importCharacter(@CookieValue("user") String user,Model model,@ModelAttribute Character character) throws IOException, InterruptedException {
        character = characterService.validateUUID(user);
        if(character==null)
            return "errorPageCreateAccount";
        return "importPage";
    }
    @GetMapping("/export")
    public String exportCharacter(@CookieValue("user") String user,Model model) throws Exception {
        Character character = characterService.validateUUID(user);
        if(character==null)
            return "errorPage";
        character.setInBattle(characterService.isInBattle(character));
        character.setInRegen(characterService.isInRegen(character));
        character.setInfo("");
        characterService.save(character);
        String xml = characterService.createXMLDoc(character);
        model.addAttribute("xml",xml);

        return "exportPage";
    }
}
