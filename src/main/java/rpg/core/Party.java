package rpg.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Groupe de personnages (interface) avec opérations de base.
 * Utilise CharacterProfile pour accepter aussi les décorateurs.
 */
public class Party {
    private final List<CharacterProfile> members = new ArrayList<>();

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

    public int getStrength()      { return members.stream().mapToInt(CharacterProfile::getStrength).sum(); }
    public int getAgility()       { return members.stream().mapToInt(CharacterProfile::getAgility).sum(); }
    public int getIntelligence()  { return members.stream().mapToInt(CharacterProfile::getIntelligence).sum(); }
}
