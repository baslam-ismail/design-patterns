package rpg.command;

import java.util.*;

/** Résout un Fighter par nom -> permet le replay sur de NOUVEAUX Fighters. */
public class FighterRegistry {
    private final Map<String, Fighter> byName = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void put(String name, Fighter f) { byName.put(name, f); }
    public Fighter get(String name)         { return byName.get(name); }
}

