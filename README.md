# Projet DesignPatterns (Builder • Decorator • DAO • Singleton)
Petit projet Java montrant des patterns classiques autour d’un modèle de jeu simple (Character, Party, Decorator, DAO, GameSettings).
Idéal pour TP/démo : construction fluide, capacités empilables, stockage en mémoire, règles globales.

***
## Démarrer

- ### Prérequis

        + Java 17+ (recommandé)
        + Aucune dépendance externe

- ### Compiler & exécuter

    Depuis la racine (là où se trouve le dossier rpg/) :

    ```
    # Compilation
    javac $(find . -name "*.java")
    
    # Exécution
    java rpg.Main
    ```
    Vous devriez voir dans la console :
    
    - La configuration des règles (MaxStatPoints)
    
    - La création des personnages (OK / échec attendu pour un perso trop fort)
    
    - L’ajout de capacités décorées (+PL, description enrichie)
    
    - Le stockage et la lecture via DAO
    
    - La gestion d’un groupe (Party) : tris, total power, suppression par nom
    
    - Une démo de “combat” basée sur la différence de puissance
***
## Structure
```
rpgrpg/
 ├─ builder/
 │   └─ CharacterBuilder.java        # Builder + validation finale au build()
 ├─ core/
 │   ├─ Character.java               # Entité immuable (implémente CharacterProfile)
 │   ├─ CharacterProfile.java        # Contrat lecture seule + power/description
 │   └─ Party.java                   # Groupe: ajout, tris, total power, removeByName
 ├─ dao/
 │   ├─ Dao.java                     # Interface DAO minimale
 │   └─ CharacterDao.java            # DAO en mémoire (Map<String, CharacterProfile>)
 ├─ decorator/
 │   ├─ CharacterDecorator.java      # Décorateur abstrait
 │   ├─ FireResistance.java          # +30 PL, tag description
 │   ├─ Invisibility.java            # +25 PL, tag description
 │   └─ Telepathy.java               # +30 PL, tag description
 ├─ settings/
 │   └─ GameSettings.java            # Singleton des règles (maxStatPoints, isValid)
 └─ Main.java                        # Démo console complète (Builder/Decorator/DAO/Party/Rules/Combat)

```
***
## Concepts clés
- Character (immat.) : name, strength, agility, intelligence → immuables.
getPowerLevel() = somme naïve des 3 stats, getDescription() compacte.


- CharacterBuilder : setters fluents → build() crée et valide via GameSettings.
En cas d’invalidité → IllegalArgumentException.


- Decorator : enrichit un CharacterProfile sans modifier l’original.
Ex. new FireResistance(new Telepathy(hero)).


- DAO en mémoire : save, findByName, findAll. Index par nom (exact).


- Party : gère une liste interne de Character (concrets).
getMembers() renvoie une vue immuable, tris non destructifs, getTotalPower().


- GameSettings (singleton) : maxStatPoints paramétrable, isValid(profile) compare getPowerLevel() au budget (les bonus de décorateurs comptent).




***

## Exemples rapides
### Construire & valider

```
var hero = new CharacterBuilder()
    .setName("Hero")
    .setStrength(10)
    .setAgility(8)
    .setIntelligence(6)
    .build(); // validé via GameSettings

```

### Ajouter des capacités (Decorator)

````
CharacterProfile boosted = new FireResistance(new Telepathy(hero));
System.out.println(boosted.getDescription()); // "... + Capacité: Télépathie + Capacité: Résistance au feu"
System.out.println("PL=" + boosted.getPowerLevel()); // PL hero + 30 + 30

````

### DAO & Party

````
var dao = new CharacterDao();
dao.save(hero);
dao.save(boosted);
dao.findAll().forEach(p -> System.out.println(p.getName() + " -> " + p.getPowerLevel()));

var party = new Party();
party.add(hero);
party.add(new CharacterBuilder().setName("Villain").setStrength(8).setAgility(6).setIntelligence(10).build());

System.out.println("Total PL: " + party.getTotalPower());
party.sortByPowerDesc().forEach(c -> System.out.println(c.getName() + " -> " + c.getPowerLevel()));

````

***
## Commandes utiles
- ### Changer les règles :

````
GameSettings.getInstance().setMaxStatPoints(35);
````
- ### Vérifier la validité :

````
boolean ok = GameSettings.getInstance().isValid(hero); // true/false
````
- ### Tri & suppression dans Party :
````
party.sortByNameAsc();
party.removeByName("villain"); // insensible à la casse
````
***

## Licence
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

© 2025 Contributeurs :
- Ismail B.
- Abdou P. M.
- Maddy S. 

Consultez le fichier **LICENSE** à la racine pour le texte complet.
