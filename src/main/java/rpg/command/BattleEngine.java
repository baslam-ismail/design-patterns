package rpg.command;

import rpg.core.CharacterProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Moteur de combat basé Command + historique. */
public class BattleEngine {
    private final Fighter f1, f2;
    private final FighterRegistry reg = new FighterRegistry();
    private final CommandInvoker invoker = new CommandInvoker();
    private final Random rnd = new Random();

    public BattleEngine(CharacterProfile a, CharacterProfile b) {
        this.f1 = new Fighter(a); this.f2 = new Fighter(b);
        reg.put(f1.getName(), f1);
        reg.put(f2.getName(), f2);
    }

    public void runBattle() {
        invoker.note(String.format("=== Combat : %s vs %s ===", f1.getName(), f2.getName()));
        int round = 1, maxRounds = 200;

        while (f1.isAlive() && f2.isAlive() && round <= maxRounds) {
            invoker.note(String.format("-- Tour %d --", round));

            takeTurn(f1, f2);
            if (!f2.isAlive()) break;

            takeTurn(f2, f1);
            if (!f1.isAlive()) break;

            round++;
        }

        if (f1.isAlive() && !f2.isAlive()) {
            invoker.note(">> " + f1.getName() + " est vainqueur !");
        } else if (!f1.isAlive() && f2.isAlive()) {
            invoker.note(">> " + f2.getName() + " est vainqueur !");
        } else if (round > maxRounds) {
            invoker.note(">> Match arrêté (limite de rounds)");
        } else {
            invoker.note(">> Match nul (K.O. simultané)");
        }
    }



    private void takeTurn(Fighter actor, Fighter opponent) {
        int action = rnd.nextInt(3); // 0 attaque, 1 défense, 2 pouvoir
        switch (action) {
            case 0 -> invoker.execute(new AttackByName(actor.getName(), opponent.getName(), reg));
            case 1 -> invoker.execute(new DefendByName(actor.getName(), reg));
            case 2 -> {
                if (rnd.nextBoolean())
                    invoker.execute(new UsePowerByName(actor.getName(), "invisibility", reg));
                else
                    invoker.execute(new UsePowerByName(actor.getName(), "telepathy", reg));
            }
        }
    }


    /** @return l'invoker contenant history+log (session d'enregistrement). */
    public CommandInvoker getInvoker() { return invoker; }

    /** Rejoue l'historique sur de NOUVEAUX Fighters (profils remis à neuf). */
    public static List<String> replayOnNewFighters(
            List<Command> history,
            String originalNameA, CharacterProfile profileA,
            String originalNameB, CharacterProfile profileB
    ) {
        CommandInvoker invoker = new CommandInvoker();
        FighterRegistry reg    = new FighterRegistry();

        // <<< mêmes CLÉS que dans l'historique, mais NOUVEAUX fighters
        reg.put(originalNameA, new Fighter(profileA));
        reg.put(originalNameB, new Fighter(profileB));

        List<String> out = new ArrayList<>();
        out.add(String.format("=== Replay : %s vs %s ===", originalNameA, originalNameB));

        for (Command c : history) {
            c.rebind(reg, invoker);           // brancher sur le NOUVEAU contexte
            String line = c.execute();        // aucune écriture console interne
            invoker.note(line);               // garder la trace
            out.add(line);
        }
        return out;
    }

    // Helpers pour extraire les champs (getters package-private).
    private static String oldAtt(AttackByName a){ try{ var f=AttackByName.class.getDeclaredField("attackerName"); f.setAccessible(true); return (String)f.get(a);}catch(Exception e){throw new RuntimeException(e);} }
    private static String oldDef(AttackByName a){ try{ var f=AttackByName.class.getDeclaredField("defenderName"); f.setAccessible(true); return (String)f.get(a);}catch(Exception e){throw new RuntimeException(e);} }
    private static String oldName(DefendByName d){ try{ var f=DefendByName.class.getDeclaredField("name"); f.setAccessible(true); return (String)f.get(d);}catch(Exception e){throw new RuntimeException(e);} }
    private static String oldTarget(UsePowerByName u){ try{ var f=UsePowerByName.class.getDeclaredField("name"); f.setAccessible(true); return (String)f.get(u);}catch(Exception e){throw new RuntimeException(e);} }
    private static String oldPower(UsePowerByName u){ try{ var f=UsePowerByName.class.getDeclaredField("power"); f.setAccessible(true); return (String)f.get(u);}catch(Exception e){throw new RuntimeException(e);} }
}
