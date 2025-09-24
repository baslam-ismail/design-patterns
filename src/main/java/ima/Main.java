package ima;

import java.util.ArrayList;

import ima.builder.CharacterBuilder;
import ima.core.Character;
import ima.core.Party;
import ima.dao.CharacterDao;
import ima.decorator.Invisibility;
import ima.decorator.PersonnageAmeliore;
import ima.settings.GameSettings;

public class Main {
    public static void main(String[] args) {
        CharacterDao characterDao = new CharacterDao();
        GameSettings settings = GameSettings.getInstance();

        Character hero = new CharacterBuilder()
                .setName("Hero")
                .setStrength(10)
                .setAgility(8)
                .setIntelligence(6)
                .build();

        Character villain = new CharacterBuilder()
                .setName("Villain")
                .setStrength(8)
                .setAgility(6)
                .setIntelligence(10)
                .build();

        Character sidekick = new CharacterBuilder()
                .setName("Sidekick")
                .setStrength(6)
                .setAgility(10)
                .setIntelligence(8)
                .build();

        PersonnageAmeliore invisibleHero = new Invisibility(hero);

        ArrayList<Character> partyMembers = new ArrayList<>();
        partyMembers.add((Character)invisibleHero);
        partyMembers.add(sidekick);
        partyMembers.add(villain);
        Party party1 = new Party(partyMembers);

        /* System.out.println("Party Members:");
        for (Character member : party1.getMembers()) {
            System.out.println("- " + member.getName() + " (Power Level: " + member.getPowerLevel() + ")");
        }
        System.out.println("Total Party Strength: " + party1.getTotalStrength()); */

        characterDao.save((Character)invisibleHero);
        characterDao.save(villain);
        characterDao.save(sidekick);

        System.out.println("All Characters in DAO:");
        for (PersonnageAmeliore character : characterDao.findAll()) {
            System.out.println("- " + ((Character)(character)).getName() + " (Power Level: " + character.getPowerLevel() + ")");
        }
    }
}