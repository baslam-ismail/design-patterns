package rpg.command;

import java.util.Random;
import rpg.core.CharacterProfile;
import rpg.decorator.Invisibility;
import rpg.decorator.Telepathy;

/**
 * Moteur de combat simple : deux fighters s'affrontent jusqu'à K.O.
 */
public class BattleEngine {
    private final Fighter f1;
    private final Fighter f2;
    private final CommandInvoker invoker = new CommandInvoker();
    private final Random random = new Random();

    public BattleEngine(CharacterProfile c1, CharacterProfile c2) {
        this.f1 = new Fighter(c1);
        this.f2 = new Fighter(c2);
    }

    public void runBattle() {
        System.out.println("\n=== Combat : " + f1.getName() + " vs " + f2.getName() + " ===");
        int round = 1;

        while (f1.isAlive() && f2.isAlive()) {
            System.out.println("\n--- Tour " + round + " ---");

            // Tour du Fighter 1
            takeTurn(f1, f2);

            if (!f2.isAlive()) break;

            // Tour du Fighter 2
            takeTurn(f2, f1);

            round++;
        }

        // Résultat final
        if (f1.isAlive() && !f2.isAlive()) {
            System.out.println(">>> " + f1.getName() + " est vainqueur !");
        } else if (!f1.isAlive() && f2.isAlive()) {
            System.out.println(">>> " + f2.getName() + " est vainqueur !");
        } else {
            System.out.println(">>> Match nul (KO simultané)");
        }
    }

    private void takeTurn(Fighter actor, Fighter opponent) {
        int action = random.nextInt(3); // 0=attaque,1=defense,2=pouvoir
        switch (action) {
            case 0 -> invoker.execute(new AttackCommand(actor, opponent));
            case 1 -> invoker.execute(new DefendCommand(actor));
            case 2 -> {
                if (random.nextBoolean()) {
                    invoker.execute(new UsePowerCommand(actor, p -> new Invisibility(p), "Invisibilité"));
                } else {
                    invoker.execute(new UsePowerCommand(actor, p -> new Telepathy(p), "Télépathie"));
                }
            }
        }
    }

    public void replayBattle() {
        invoker.replay();
    }
}
