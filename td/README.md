# Développement Fullstack — Polytech

## Pré-requis
Pour que l'application fonctionne de manière optimal, il faut ces logiciels suivants : 
- Avoir Node version 24 au minimun 
- Avoir Angular version 22 au minimum 
- Avoir Java SDK version 26 au minimum 
- Avoir PostGreSQl  

## Démarage de l'application

### Back
Pour démarer le serveur du back, il faut au préalable, démarer le serveur de base de données, une fois celui-ci lancé, lancer le back en cliquant sur run depuis l'IDE depusi le dossier `td/back/`

### Front
Pour démarer le front, le back ne doit pas forcément être démaré au préalable. Il faut utiliser la commande suivante dans `td/front/films-app` : 

```shell
ng serve
```

## Connecter le front et le back

Le serveur du back tourne sur le port `8080`, le front sur le `4200`. \
Afin de les connecter, il faut dans le cors du back dans `td/back/src/main/ressources/application.yaml` autorisé l'url du front. Il faut également ajouter un proxy et le l'ajouter dans les paramètres d'Angular, afin que le back et le front communique entre eux. 

## API du back

| Méthode | End point                      | Usage                                                                |
|:-------:|--------------------------------|----------------------------------------------------------------------|
|   GET   | /films                         | Retourne la liste des films                                          |
|   GET   | /films/{id}                    | Retourne un film par son identifiant avec ses détails et les acteurs |
|   GET   | /films/{id}/acteurs            | Retourne les acteurs du film.                                        |
|   POST  | /films                         | Ajoute un nouveau film                                               |
|   PUT   | /films/{id}                    | Met un film à jour                                                   |
|  DELETE | /films/{id}                    | Supprime un film                                                     |
|   GET   | /films/{id}/commentaires       | Retourne la liste des commentaires du film identifié                 |
|   POST  | /films/{id}/commentaires       | Ajoute un nouveau commentaire au film identifié                      |
|   PUT   | /commentaires/{id}             | Met à jour un commentaire                                            |
|  DELETE | /commentaires/{id}             | Supprime un commentaire                                              |
|   POST  | /films/{id}/acteurs/{acteurId} | Ajoute un acteur au film                                             |
|  DELETE | /films/{id}/acteurs/{acteurId} | Supprime un acteur du film                                           |
|   GET   | /acteurs                       | Retourne la liste des acteurs                                        |
|   GET   | /acteurs/{id}                  | Retourne les détails d'un acteur                                     |
|   POST  | /acteurs                       | Ajoute un nouvel acteur                                              |
|   PUT   | /acteurs/{id}                  | Met à jour un acteur                                                 |
|  DELETE | /acteurs/{id}                  | Supprime un acteurs                                                  |
|   GET   | /acteurs/{id}/films            | L'ensemble des films joué par l'acteur                               |

## Structure des dossiers

### Back
```
src/main/
    java/org/polytech/spring/films/
        config/
        controller/
        dto/
        exception/
        model/
        repository/
        service/
    ressources/
```

### Front

```
films/app/src/app/
    core/
    features/
        acteurs/
            acteur-detail/
            acteur-form/
            acteur-list/
        films/
            film-card/
            film-commentaires/
            film-detail/
            film-form/
            frilm-list/
    models/
    services/
    shared/
        not-found/
        pagination/
```