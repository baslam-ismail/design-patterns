package rpg.mvc;

import rpg.core.CharacterProfile;
import java.util.*;

public class ConsoleView implements View {
    private final Scanner in = new Scanner(System.in);
    @Override public void showHeader(){ System.out.println("=== RPG – MVC (Builder/Decorator/DAO/Command/Replay) ==="); }
    @Override public void showMenu() {
        System.out.println("\nMenu:");
        System.out.println(" 1) Créer un personnage (Builder)");
        System.out.println(" 2) Lister personnages (DAO)");
        System.out.println(" 3) Appliquer capacité (Decorator)");
        System.out.println(" 4) Ajouter à la Party");
        System.out.println(" 5) Retirer de la Party");
        System.out.println(" 6) Puissance totale Party");
        System.out.println(" 7) Party triée (puissance desc)");
        System.out.println(" 8) Party triée (nom asc)");
        System.out.println(" 9) Valider un perso (GameSettings)");
        System.out.println("10) Combat simple (comparaison PL)");
        System.out.println("11) Combat Command (enregistre log)");
        System.out.println("12) REPLAY derniers coups");
        System.out.println(" 0) Quitter");
    }
    @Override public int readInt(String prompt){ System.out.print(prompt+" "); while(!in.hasNextInt()){ in.next(); System.out.print("Entier: "); } int v=in.nextInt(); in.nextLine(); return v; }
    @Override public String readString(String prompt){ System.out.print(prompt+" "); return in.nextLine().trim(); }
    @Override public void showMessage(String msg){ System.out.println(msg); }
    @Override public void showCharacters(String title, List<CharacterProfile> list){
        System.out.println("\n"+title);
        if (list.isEmpty()){ System.out.println("(aucun)"); return; }
        list.forEach(c -> System.out.println(" - " + c.getDescription() + " | PL=" + c.getPowerLevel()));
    }
    @Override public void showLog(List<String> lines){ lines.forEach(System.out::println); }
}
