package ima.core;
import java.util.List;

public class Party {
    private List<Character> members;

    public Party(List<Character> members) {
        this.members = members;
    }

    public List<Character> getMembers() {
        return this.members;
    }

    public void addMember(Character character) {
        this.members.add(character);
    }

    public void removeMember(Character character) {
        this.members.remove(character);
    }

    public int getTotalStrength() {
        return members.stream().mapToInt(Character::getPowerLevel).sum();
    }
}
