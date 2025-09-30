package rpg.core;

import java.util.ArrayList;
import java.util.List;

public class Army implements CharacterProfile {
    private final String name;
    List<Party> parties = new ArrayList<>();

    public Army(String name) {
        this.name = name;
    }

    public void addParty(Party party) {
        parties.add(party);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStrength() {
        int totalStrength = 0;
        for (Party party : parties) {
            totalStrength += party.getStrength();
        }
        return totalStrength;
    }

    @Override
    public int getAgility() {
        int totalAgility = 0;
        for (Party party : parties) {
            totalAgility += party.getAgility();
        }
        return totalAgility;
    }

    @Override
    public int getIntelligence() {
        int totalIntelligence = 0;
        for (Party party : parties) {
            totalIntelligence += party.getIntelligence();
        }
        return totalIntelligence;
    }

    @Override
    public int getPowerLevel() {
        int totalPowerLevel = 0;
        for (Party party : parties) {
            totalPowerLevel += party.getTotalPower();
        }
        return totalPowerLevel;
    }

    @Override
    public String getDescription() {
        return "Army: " + name + ", Parties: " + parties.size();
    }
}
