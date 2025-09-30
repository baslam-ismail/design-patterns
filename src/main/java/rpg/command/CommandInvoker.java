package rpg.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker qui exécute des commandes, garde un log et un historique pour replay.
 */
public class CommandInvoker {
    private final List<Command> history = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public void execute(Command command) {
        String entry = command.execute();
        history.add(command);
        log.add(entry);
        System.out.println(entry);
    }

    public List<String> getLog() { return List.copyOf(log); }
    public List<Command> getHistory() { return List.copyOf(history); }

    public void clear() {
        history.clear();
        log.clear();
    }

    /** Rejoue l’historique à partir de nouveaux Fighters. */
    public void replay() {
        System.out.println("\n=== Replay des actions ===");
        for (Command c : history) {
            System.out.println(c.execute());
        }
    }
}
