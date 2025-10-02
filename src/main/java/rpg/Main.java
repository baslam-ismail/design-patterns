package rpg;

import rpg.mvc.*;
import rpg.settings.GameSettings;

public class Main {
    public static void main(String[] args) {
        GameSettings.getInstance().setMaxStatPoints(35);
        Controller controller = new Controller(new Model(), new ConsoleView());
        controller.run();
    }
}
