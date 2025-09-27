package rpg.core;
import java.util.*;


/**
 * Groupe de personnages avec opérations de base (ajout, retrait, lecture, tris).
 */
public class Party {
    private final List<Character> members = new ArrayList<>();


    /**
     * Ajoute un personnage (refus des valeurs null).
     */
    public void add(Character character) {
        members.add(Objects.requireNonNull(character, "character must not be null"));
    }

    /**
     * Retire le(s) membre(s) portant ce nom (comparaison insensible à la casse).
     * @return true si au moins un membre a été retiré.
     */
    public boolean removeByName(String name) {
        return members.removeIf(c -> c.getName().equalsIgnoreCase(name));
    }

    /**
     * Expose une vue non modifiable des membres (copie immuable).
     */
    public List<Character> getMembers() {
        return List.copyOf(members);
    }

    /**
     * Retourne une nouvelle liste triée par puissance décroissante.
     * (la liste interne n'est pas modifiée)
     */
    public List<Character> sortByPowerDesc() {
        return members.stream()
                .sorted(Comparator.comparingInt(Character::getPowerLevel).reversed())
                .toList();
    }

    /**
     * Retourne une nouvelle liste triée par nom (A→Z, insensible à la casse).
     * (la liste interne n'est pas modifiée)
     */
    public List<Character> sortByNameAsc() {
        return members.stream()
                .sorted(Comparator.comparing(Character::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }


    /**
     * Puissance totale du groupe (somme de {@code getPowerLevel()} pour chaque membre).
     */
    public int getTotalPower() {
        return members.stream().mapToInt(Character::getPowerLevel).sum();
    }
}
