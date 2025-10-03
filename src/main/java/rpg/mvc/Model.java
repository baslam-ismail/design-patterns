package rpg.mvc;

import java.util.ArrayList;
import java.util.List;

import rpg.builder.CharacterBuilder;
import rpg.command.BattleEngine;
import rpg.command.Command;
import rpg.core.Character;
import rpg.core.CharacterProfile;
import rpg.core.Party;
import rpg.dao.CharacterDao;
import rpg.decorator.FireResistance;
import rpg.decorator.Invisibility;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;

public class Model {
    private final CharacterDao dao = new CharacterDao();
    private final Party party = new Party("Default Party");

    private List<Command> lastHistory = null; // historique du DERNIER combat
    private String lastNameA = null;
    private String lastNameB = null;

    // --- CRUD basique ---
    public void createCharacter(String name, int str, int agi, int intl) {
        Character c = new CharacterBuilder().setName(name).setStrength(str).setAgility(agi).setIntelligence(intl).build();
        dao.save(c);
    }
    public List<CharacterProfile> listAll(){ return dao.findAll(); }
    public boolean applyCapability(String name, String cap) {
        CharacterProfile base = dao.findByName(name); if (base == null) return false;
        CharacterProfile deco = switch (cap.toLowerCase()) {
            case "invisibility", "invisible" -> new Invisibility(base);
            case "telepathy" -> new Telepathy(base);
            case "fire", "fireresistance", "fire_resistance" -> new FireResistance(base);
            default -> null;
        };
        if (deco == null) return false;
        dao.save(deco);
        return true;
    }
    public boolean addToParty(String name){ var c=dao.findByName(name); if(c==null) return false; party.add(c); return true; }
    public boolean removeFromParty(String name){ return party.removeByName(name); }
    public int totalPartyPower(){ return party.getTotalPower(); }
    public List<CharacterProfile> partyByPowerDesc(){ return party.sortByPowerDesc(); }
    public List<CharacterProfile> partyByNameAsc(){ return party.sortByNameAsc(); }
    public Boolean validateByName(String name){ var c=dao.findByName(name); return c==null? null : GameSettings.getInstance().isValid(c); }

    // --- Combat simple (comparaison PL) ---
    public String fightCompare(String aName, String bName) {
        var a = dao.findByName(aName); var b = dao.findByName(bName);
        if (a==null || b==null) return "Introuvable.";
        int pa=a.getPowerLevel(), pb=b.getPowerLevel();
        String A=a.getDescription(), B=b.getDescription();
        if (pa==pb) return "Égalité entre " + A + " et " + B;
        return (pa>pb?A:B) + " gagne (|Δ|=" + Math.abs(pa-pb) + ")";
    }


    /** Combat command enregistré : renvoie le log à afficher par la vue. */
    public List<String> startRecordedBattle(String aName, String bName) {
        var a = dao.findByName(aName);
        var b = dao.findByName(bName);
        if (a == null || b == null) return List.of("Introuvable : vérifie les noms.");

        BattleEngine engine = new BattleEngine(a, b);
        engine.runBattle();

        lastHistory = new ArrayList<>(engine.getInvoker().getHistory());
        // <<< mémoriser les noms EXACTS utilisés dans les commandes
        lastNameA = a.getName();
        lastNameB = b.getName();

        return engine.getInvoker().getLog();
    }


    public List<String> replayLastBattleOnFreshFighters(String newAName, String newBName) {
        if (lastHistory == null || lastNameA == null || lastNameB == null)
            return List.of("Aucun combat à rejouer.");

        var newA = dao.findByName(newAName);
        var newB = dao.findByName(newBName);
        if (newA == null || newB == null)
            return List.of("Introuvable pour reconstruire les Fighters.");

        // on garde les MÊMES CLÉS que dans l'historique : lastNameA/lastNameB
        return BattleEngine.replayOnNewFighters(lastHistory, lastNameA, newA, lastNameB, newB);
    }
}
