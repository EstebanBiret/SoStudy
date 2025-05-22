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
| /auth/login                   | GET     | AuthController     | Page de connexion                                       |                         |
| /auth/login                   | POST    | AuthController     | Traite la connexion à un compte                                    | Formulaire              |
| /auth/register                | GET    | AuthController     | Page de création de compte                                      |               |
| /auth/register                | POST    | AuthController     | Traite la création d'un compte                                      | Formulaire              |
| /auth/logout                  | POST    | AuthController     | Déconnexion + suppression session                       |                         |
| /user/{pseudo}               | GET     | UserController     | Profil d’un utilisateur                           | pseudo                  |
| /user/edit                    | GET     | UserController     | Formulaire d’édition de profil                          |                         |
| /user/edit                    | POST    | UserController     | Sauvegarde des modifications de profil                  | Formulaire              |
| /user/delete                  | POST    | UserController     | Supprimer le compte utilisateur                         |                         |
| /user/search?pseudo={pseudo}  | GET    | UserController     | Afficher les résultats de la rechercher d'utilisateur   |                  pseudo       |
| /user/follow/{pseudo}        | POST    | UserController     | Suivre un utilisateur                                   | pseudo                  |
| /user/unfollow/{pseudo}      | POST    | UserController     | Ne plus suivre un utilisateur                           | pseudo                  |
| /channels                     | GET     | ChannelController  | Liste des conversations                                 |                         |
| /channels/new                 | GET     | ChannelController  | Formulaire de nouvelle conversation                     |                         |
| /channels/new                 | POST    | ChannelController  | Création de conversation                                | Formulaire              |
| /channels/update                     | POST     | ChannelController  | Met à jour les channels                                 |                         |
| /channels/delete                     | POST     | ChannelController  | Supprime un channel                                 |                         |
| /channels/leave                     | POST     | ChannelController  | Permet à un user de quitter un channel                                 |                         |
| /message/{idChannel}/send               | POST     | MessageController  | Envoyer un message dans une conversation    | id                      |
| /community                    | GET     | CommunityController| Affiche la liste des communautés                     |                         |
| /community/new                | POST    | CommunityController| Traite la création de communauté                        | Formulaire              |
| /community/edit/{communityId} | POST    | CommunityController| Sauvegarde les modifications d'une communauté                      | communityId, Formulaire |
| /community/delete/{communityId} | POST  | CommunityController| Supprime une communauté                                 | communityId             |
| /community/join/{communityId} | POST    | CommunityController| Rejoindre une communauté                                | communityId             |
| /community/leave/{communityId}| POST    | CommunityController| Quitter une communauté                                  | communityId             |
| /community/{communityId}      | GET     | CommunityController| Liste des posts associés à cette communauté    | communityId             |
| /event                       | GET     | EventController    | Liste des événements                                 |                  |
| /event/new                   | POST    | EventController    | Traite la création d’un événement                       | Formulaire              |
| /event/edit/{eventId}        | POST    | EventController    | Sauvegarde des modifications                            | eventId, Formulaire     |
| /event/delete/{eventId}      | POST    | EventController    | Supprime un événement                                   | eventId                 |
| /event/join/{eventId}        | POST    | EventController    | Rejoindre un événement                                  | eventId                 |
| /event/leave/{eventId}       | POST    | EventController    | Quitter un événement                                    | eventId                 |
| /post/{id}                   | GET     | PostController     | Détails d’un post + commentaires et réactions           | id                      |
| /post/publish                | GET    | PostController     | Formulaire de création d'un post                    |               |
| /post/publish                | POST    | PostController     | Publier post, commentaire ou réponse à un commentaire                    | Formulaire              |
| /post/delete/{postid}        | POST    | PostController     | Supprime un post                                        | postid                  |
| /post/comment/{parentPostid}        | POST    | PostController     | Permet de commenter un post et de répondre à un commentaire         | parentPostid                  |
| /reaction/update/{postId}/{reactionType}           | POST    | ReactionController | Ajouter, modifier ou supprimer une réaction sur un post          | postId, reactionType          |
| /repost/{postId}        | POST    | RepostController    | Reposter un post                                  | postId                 |
| /repost/delete/{postId}       | POST    | RepostController    | Supprimer le repost d'un post                                    | postId                 |
