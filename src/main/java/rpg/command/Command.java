package rpg.command;

/** Contrat Command : une action exécutable qui retourne un message de log. */
public interface Command {
    String execute();
}
