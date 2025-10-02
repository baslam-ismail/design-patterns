package rpg.mvc;

import rpg.core.CharacterProfile;
import java.util.List;

public interface View {
    void showHeader();
    void showMenu();
    int readInt(String prompt);
    String readString(String prompt);
    void showMessage(String msg);
    void showCharacters(String title, List<CharacterProfile> list);
    void showLog(List<String> lines);
}
