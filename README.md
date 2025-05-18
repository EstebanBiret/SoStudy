# 🪐 SoStudy - Réseau social d'étudiants

## 👥 Équipe

- **Flavien DIAS**
- **Maxime SEGOT**
- **Esteban BIRET-TOSCANO**

## 🔗 Ressources

- 📈 **Analyse SonarCloud** :  
  [Voir le projet sur SonarCloud](https://sonarcloud.io/project/overview?id=estebanbiret_sostudy)

- 📋 **Organisation Kanban** :  
  [Voir le Kanban GitHub](https://github.com/users/EstebanBiret/projects/6/views/1)

- 🎨 **Figma** :  
  [Voir le Figma](https://www.figma.com/design/KRBjK7Laxs0rU8Vn5zHDmb/Untitled?node-id=0-1&t=f5O5uSsJ3pU7vAxe-1)

## 🛣️ Routes

| Route                          | Méthode | Controller         | Description                                             | Paramètres              |
|-------------------------------|---------|--------------------|---------------------------------------------------------|-------------------------|
| /                             | GET     | IndexController     | Index de l'application, affiche le fil principal        |                         |
| /user                         | GET     | UserController     | Affiche le compte utilisateur connecté                  |                         |
| /auth/login                   | GET     | AuthController     | Page de connexion                                       |                         |
| /auth/login                   | POST    | AuthController     | Traite la connexion                                     | Formulaire              |
| /auth/register                | POST    | AuthController     | Création de compte                                      | Formulaire              |
| /auth/logout                  | POST    | AuthController     | Déconnexion + suppression session                       |                         |
| /user/{pseudo}               | GET     | UserController     | Profil d’un utilisateur donné                           | pseudo                  |
| /user/edit                    | GET     | UserController     | Formulaire d’édition de profil                          |                         |
| /user/edit                    | POST    | UserController     | Sauvegarde des modifications de profil                  | Formulaire              |
| /user/delete                  | POST    | UserController     | Supprimer le compte utilisateur                         |                         |
| /user/search?pseudo={pseudo}  | GET    | UserController     | Rechercher un utilisateur par son pseudo    |                  pseudo       |
| /user/search?pseudo={pseudo}  | POST    | UserController     | Rechercher un utilisateur par son pseudo    |               Formulaire          |
| /user/follow/{pseudo}        | POST    | UserController     | Suivre un utilisateur                                   | pseudo                  |
| /user/unfollow/{pseudo}      | POST    | UserController     | Ne plus suivre un utilisateur                           | pseudo                  |
| /channels                     | GET     | ChannelController  | Liste des conversations                                 |                         |
| /channels/new                 | GET     | ChannelController  | Formulaire de nouvelle conversation                     |                         |
| /channels/new                 | POST    | ChannelController  | Création de conversation                                | Formulaire              |
| /channels/{idChannel}               | GET     | ChannelController  | Détail d’une conversation                         | id                      |
| /message/{idChannel}/send               | POST     | MessageController  | Envoyer un message dans une conversation    | id                      |
| /community                    | GET     | CommunityController| Posts des communautés suivies                           |                         |
| /community/new                | GET     | CommunityController| Formulaire de création de communauté                    |                         |
| /community/new                | POST    | CommunityController| Traite la création de communauté                        | Formulaire              |
| /community/edit/{communityId} | GET     | CommunityController| Formulaire de modification d'une communauté             | communityId             |
| /community/edit/{communityId} | POST    | CommunityController| Sauvegarde les modifications                            | communityId, Formulaire |
| /community/delete/{communityId} | POST  | CommunityController| Supprime une communauté                                 | communityId             |
| /community/join/{communityId} | POST    | CommunityController| Rejoindre une communauté                                | communityId             |
| /community/leave/{communityId}| POST    | CommunityController| Quitter une communauté                                  | communityId             |
| /community/{communityId}      | GET     | CommunityController| Détails d'une communauté                                | communityId             |
| /event/{eventId}             | GET     | EventController    | Détails d’un événement                                  | eventId                 |
| /event/new                   | GET     | EventController    | Formulaire de création d'événement                      |                         |
| /event/new                   | POST    | EventController    | Traite la création d’un événement                       | Formulaire              |
| /event/edit/{eventId}        | GET     | EventController    | Formulaire d’édition d’un événement                     | eventId                 |
| /event/edit/{eventId}        | POST    | EventController    | Sauvegarde des modifications                            | eventId, Formulaire     |
| /event/delete/{eventId}      | POST    | EventController    | Supprime un événement                                   | eventId                 |
| /event/join/{eventId}        | POST    | EventController    | Rejoindre un événement                                  | eventId                 |
| /event/leave/{eventId}       | POST    | EventController    | Quitter un événement                                    | eventId                 |
| /post/{id}                   | GET     | PostController     | Détails d’un post + commentaires et réactions           | id                      |
| /post/publish                | GET    | PostController     | Formulaire de création d'un post                    |               |
| /post/publish                | POST    | PostController     | Publier post, commentaire ou réponse                    | Formulaire              |
| /post/delete/{postid}        | POST    | PostController     | Supprime un post                                        | postid                  |
| /reaction/update/{postId}/{reactionType}           | POST    | ReactionController | Ajouter, modifier ou supprimer une réaction sur un post                             | postId, reactionType          |
| /repost/{postId}        | POST    | RepostController    | Reposter un post                                  | postId                 |
| /repost/delete/{postId}       | POST    | RepostController    | Suppirmer le repost d'un post                                    | postId                 |
