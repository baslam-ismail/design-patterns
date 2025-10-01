package rpg.command;

import java.util.Objects;
import java.util.function.Function;
import rpg.core.CharacterProfile;

/**
 * Applique dynamiquement un décorateur comme pouvoir spécial.
 */
public class UsePowerCommand implements Command {
    private final Fighter target;
    private final Function<CharacterProfile, CharacterProfile> applier;
    private final String powerName;

    public UsePowerCommand(Fighter target,
                           Function<CharacterProfile, CharacterProfile> applier,
                           String powerName) {
        this.target = Objects.requireNonNull(target);
        this.applier = Objects.requireNonNull(applier);
        this.powerName = powerName == null ? "Pouvoir" : powerName;
    }

    @Override
    public String execute() {
        CharacterProfile before = target.getProfile();
        CharacterProfile after = applier.apply(before);
        target.setProfile(after);
        return String.format("%s utilise %s → PL %d → %d",
                before.getName(), powerName,
                before.getPowerLevel(), after.getPowerLevel());
    }
}
