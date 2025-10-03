package rpg.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Groupe de personnages (interface) avec opérations de base.
 * Utilise CharacterProfile pour accepter aussi les décorateurs.
 */
public class Party implements CharacterProfile {
    private final List<CharacterProfile> members = new ArrayList<>();
    private final String name;

    public Party(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public String getName() { return this.name; }
    @Override
    public String getDescription() {
        return String.format("Groupe '%s' avec %d membre(s)", this.name, this.members.size());
    }
    @Override
    public int getPowerLevel() { return getTotalPower(); }

    /** Ajoute un personnage (refus des valeurs null). */
    public void add(CharacterProfile character) {
        members.add(Objects.requireNonNull(character, "character must not be null"));
    }

    /** Retire le(s) membre(s) portant ce nom (insensible à la casse). */
    public boolean removeByName(String name) {
        return members.removeIf(c -> c.getName().equalsIgnoreCase(name));
    }

    /** Vue non modifiable des membres. */
    public List<CharacterProfile> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /** Puissance totale du groupe. */
    public int getTotalPower() {
        return members.stream().mapToInt(CharacterProfile::getPowerLevel).sum();
    }

    /** Nouvelle liste triée par puissance décroissante (la liste interne n’est pas modifiée). */
    public List<CharacterProfile> sortByPowerDesc() {
        return members.stream()
                .sorted(Comparator.comparingInt(CharacterProfile::getPowerLevel).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

    /** Nouvelle liste triée par nom (A→Z, insensible à la casse). */
    public List<CharacterProfile> sortByNameAsc() {
        return members.stream()
                .sorted(Comparator.comparing(CharacterProfile::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int getStrength()      { return members.stream().mapToInt(CharacterProfile::getStrength).sum(); }
    @Override
    public int getAgility()       { return members.stream().mapToInt(CharacterProfile::getAgility).sum(); }
    @Override
    public int getIntelligence()  { return members.stream().mapToInt(CharacterProfile::getIntelligence).sum(); }
}
