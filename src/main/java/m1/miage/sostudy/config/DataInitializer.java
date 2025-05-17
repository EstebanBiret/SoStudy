package m1.miage.sostudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import m1.miage.sostudy.model.entity.*;
import m1.miage.sostudy.repository.*;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.model.entity.id.UserPostReactionID;

import static m1.miage.sostudy.controller.AuthController.hashPassword;

/**
 * DataInitializer class
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    /**
     * User repository for database operations
     */
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Community repository for database operations
     */
    @Autowired
    private CommunityRepository communityRepository;
    
    /**
     * Post repository for database operations
     */
    @Autowired
    private PostRepository postRepository;
    
    /**
     * Event repository for database operations
     */
    @Autowired
    private EventRepository eventRepository;
    
    /**
     * Message repository for database operations
     */
    @Autowired
    private MessageRepository messageRepository;
    
    /**
     * Reaction repository for database operations
     */
    @Autowired
    private ReactionRepository reactionRepository;
    
    /**
     * UserPostReaction repository for database operations
     */
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    
    /**
     * Channel repository for database operations
     */
    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Repost repository for database operations
     */
    @Autowired
    private RepostRepository repostRepository;
    
    /**
     * Run the data initializer
     * @param args the arguments
     * @throws Exception if an error occurs
     */
    @Override
    public void run(String... args) throws Exception {
        // Vérifier si les données existent déjà
        if (userRepository.count() > 0) {
            System.out.println("Les données initiales existent déjà. Pas de création de données.");
            return;
        }

        /*TODO pour faire + réseau social d'étudiants, créer des comptes types étudiant, 
        avoir des canaux du style "Soirée de x", "Groupe 3", "Promo M1 MIAGE"..., des évènements du style "Soirée au Délirium le 20 mai 2025", "Atelier SpringBoot le 21 mai 2025", "Consultation de copies le 22 mai 2025"...
        des posts qui fassent + étudiant,
        des communautés centrés sur des sujets d'études ou promo par ex.

        + voir si on pourrait implémenter l'envoi de post dans un canal
        */

        // Création des utilisateurs
        User user1 = new User();
        user1.setName("Dupont");
        user1.setFirstName("Jean");
        user1.setEmail("jean.dupont@example.com");
        user1.setPassword(hashPassword("password123"));
        user1.setPseudo("jeandupont");
        user1.setBirthDate("1990-01-01");
        user1.setBioUser("Développeur Java passionné");
        user1.setPersonImagePath("/images/profiles_pictures/1.jpeg");
        
        User user2 = new User();
        user2.setName("Martin");
        user2.setFirstName("Marie");
        user2.setEmail("marie.martin@example.com");
        user2.setPassword(hashPassword("password456"));
        user2.setPseudo("mariecode");
        user2.setBirthDate("1992-05-15");
        user2.setBioUser("Architecte logiciel");
        user2.setPersonImagePath("/images/profiles_pictures/2.jpeg");
        
        User user3 = new User();
        user3.setName("Leroy");
        user3.setFirstName("Pierre");
        user3.setEmail("pierre.leroy@example.com");
        user3.setPassword(hashPassword("password789"));
        user3.setPseudo("devpierre");
        user3.setBirthDate("1995-08-20");
        user3.setBioUser("Développeur Frontend");
        user3.setPersonImagePath("/images/profiles_pictures/3.jpeg");
        
        // Sauvegarde des utilisateurs
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        // Création des channels
        Channel channel1 = new Channel("Developpement Java", "/images/channel/java.png");
        channel1.setCreator(user1);
        
        Channel channel2 = new Channel("Spring Framework", "/images/channel/spring.png");
        channel2.setCreator(user2);
        
        // Sauvegarde des channels
        channel1 = channelRepository.save(channel1);
        channel2 = channelRepository.save(channel2);

        // Ajout des channels aux utilisateurs
        user1.getSubscribedChannels().add(channel1);
        user1.getSubscribedChannels().add(channel2);
        user2.getSubscribedChannels().add(channel1);
        user2.getSubscribedChannels().add(channel2);
        user3.getSubscribedChannels().add(channel1);
        
        // Création des communautés
        Community community1 = new Community("Java Developers", "2025-01-01", " /images/community/java.png", "Communauté des développeurs Java");
        Community community2 = new Community("Spring Framework", "2025-02-01", "/images/community/spring.png", "Communauté des développeurs Spring");

        community1.setUserCreator(user1);
        community2.setUserCreator(user2);

        // Ajout des membres aux communautés
        community1.getUsers().add(user1);
        community1.getUsers().add(user2);
        community1.getUsers().add(user3);
        community2.getUsers().add(user1);
        community2.getUsers().add(user2);
        community2.getUsers().add(user3);

        // Sauvegarde des communautés avec leurs membres
        community1 = communityRepository.save(community1);
        community2 = communityRepository.save(community2);

        // Sauvegarde des utilisateurs avec leurs communautés
        user1.getSubscribedCommunities().add(community1);
        user2.getSubscribedCommunities().add(community1);
        user3.getSubscribedCommunities().add(community1);
        user1.getSubscribedCommunities().add(community2);
        user2.getSubscribedCommunities().add(community2);
        user3.getSubscribedCommunities().add(community2);

        // Création d'un événement
        Event event1 = new Event();
        event1.setEventName("Meetup Java");
        event1.setEventPublicationDate("2025-01-01");
        event1.setEventContent("Rencontre des développeurs Java de la région");
        event1.setEventBeginningDate("2025-01-15");
        event1.setEventEndDate("2025-01-15 17:00");
        event1.setEventPlace("Paris, France");
        event1.setUserCreator(user1);
        
        // Ajout des utilisateurs intéressés par l'événement
        event1.addUser(user2);
        event1.addUser(user3);
        user2.getSubscribedEvents().add(event1);
        user3.getSubscribedEvents().add(event1);
        event1 = eventRepository.save(event1);
        
        // Création d'un autre événement
        Event event2 = new Event();
        event2.setEventName("Atelier Spring Security");
        event2.setEventPublicationDate("2025-01-02");
        event2.setEventContent("Formation pratique sur la sécurisation des applications Spring");
        event2.setEventBeginningDate("2025-01-20");
        event2.setEventEndDate("2025-01-20 18:00");
        event2.setEventPlace("Lyon, France");
        event2.setUserCreator(user2);
        
        // Ajout des utilisateurs intéressés par le deuxième événement
        event2.addUser(user1);
        event2.addUser(user3);
        user1.getSubscribedEvents().add(event2);
        user3.getSubscribedEvents().add(event2);
        event2 = eventRepository.save(event2);

        // Création des posts
        Post post1 = new Post();
        post1.setPostPublicationDate("2025-01-01");
        post1.setPostContent("Bonjour à tous ! Je débute dans Java et j'ai besoin d'aide pour comprendre les interfaces.");
        post1.setUser(user1);
        post1.setCommunity(community1);
        post1.setPostMediaPath("/images/posts_images/nature.jpg");

        Post post2 = new Post();
        post2.setPostPublicationDate("2025-01-02");
        post2.setPostContent("Je viens de terminer un tutoriel sur Spring Boot et je partage mes notes ici.");
        post2.setUser(user2);
        post2.setCommunity(community2);
        post2.setPostMediaPath("/images/posts_images/dev.jpeg");
        
        Post post3 = new Post();
        post3.setPostPublicationDate("2025-01-03");
        post3.setPostContent("Quelqu'un peut m'aider avec les annotations Spring ? Je galère avec @Autowired.");
        post3.setUser(user3);
        post3.setCommunity(community2);
        
        Post post4 = new Post();
        post4.setPostPublicationDate("2025-01-04");
        post4.setPostContent("Je partage mon code source d'une application JavaFX. Feedback bienvenu !");
        post4.setUser(user1);
        post4.setCommunity(community1);
        
        // Sauvegarde des posts originaux
        post1 = postRepository.save(post1);
        post2 = postRepository.save(post2);
        post3 = postRepository.save(post3);
        post4 = postRepository.save(post4);

        // Création du 4ème utilisateur
        User user4 = new User();
        user4.setName("Dupond");
        user4.setFirstName("Emilie");
        user4.setEmail("e@gmail.com");
        user4.setPassword(hashPassword("e"));
        user4.setPseudo("emiliedupond");
        user4.setBirthDate("1998-03-15");
        user4.setBioUser("Débutante en programmation");
        user4.setPersonImagePath("/images/profiles_pictures/defaultProfilePic.jpg");
        user4 = userRepository.save(user4);

        // Création du 5ème utilisateur
        User user5 = new User();
        user5.setName("Lefevre");
        user5.setFirstName("Thomas");
        user5.setEmail("thomas.lefevre@example.com");
        user5.setPassword(hashPassword("password1234"));
        user5.setPseudo("thomasdev");
        user5.setBirthDate("1996-07-22");
        user5.setBioUser("Développeur Full Stack");
        user5.setPersonImagePath("/images/profiles_pictures/defaultProfilePic.jpg");
        user5 = userRepository.save(user5);

        // Faire suivre l'utilisateur 4 par l'utilisateur 5
        user4.getFollowing().add(user5);

        // Création de nouveaux posts pour user2
        Post user2Post1 = new Post();
        user2Post1.setPostPublicationDate("2025-05-15");
        user2Post1.setPostContent("Je viens de terminer un projet Spring Boot avec JWT. Je partage mon expérience !");
        user2Post1.setUser(user2);
        user2Post1.setCommunity(community2);
        user2Post1 = postRepository.save(user2Post1);

        Post user2Post2 = new Post();
        user2Post2.setPostPublicationDate("2025-05-14");
        user2Post2.setPostContent("J'ai découvert une nouvelle bibliothèque Spring pour gérer les websockets. C'est super utile !");
        user2Post2.setUser(user2);
        user2Post2.setCommunity(community2);
        user2Post2 = postRepository.save(user2Post2);
        
        // Création des posts repostés
        Repost repost1 = new Repost(user3, post2, "2025-01-05", "J'ai trouvé ce tutoriel super utile !");
        repost1 = repostRepository.save(repost1);

        // -- Création des commentaires et réponses ----//

        // Commentaire de user2 sur le post1 de user1
        Post comment1 = new Post();
        comment1.setPostPublicationDate("2025-01-01");
        comment1.setPostContent("Je peux t'aider avec les interfaces ! Regarde la documentation officielle de Java.");
        comment1.setUser(user2);
        comment1.setCommentFather(post1);
        post1.getComments().add(comment1);
        
        // Réponse de user1 au commentaire1
        Post reply1 = new Post();
        reply1.setPostPublicationDate("2025-01-01");
        reply1.setPostContent("Merci beaucoup ! Je vais regarder ça de suite.");
        reply1.setUser(user1);
        reply1.setCommentFather(comment1);
        comment1.getComments().add(reply1);

        // Réponse de user2 à la reponse1
        Post reply2 = new Post();
        reply2.setPostPublicationDate("2025-01-02");
        reply2.setPostContent("Fais le loup pour moi");
        reply2.setUser(user2);
        reply2.setCommentFather(reply1);
        reply1.getComments().add(reply2);

        // Réponse de user3 à la reponse1
        Post reply2bis = new Post();
        reply2bis.setPostPublicationDate("2025-01-03");
        reply2bis.setPostContent("Allez, fais ce fichu loup 🐺");
        reply2bis.setUser(user3);
        reply2bis.setCommentFather(reply1);
        reply1.getComments().add(reply2bis);

        // Réponse de user3 à la reponse1
        Post reply2bisbis = new Post();
        reply2bisbis.setPostPublicationDate("2025-01-09");
        reply2bisbis.setPostContent("Alternance des commentaires #elleVeut");
        reply2bisbis.setUser(user2);
        reply2bisbis.setCommentFather(reply2bis);
        reply2bis.getComments().add(reply2bisbis);

        Post reply2bisbisbis = new Post();
        reply2bisbisbis.setPostPublicationDate("2025-05-11");
        reply2bisbisbis.setPostContent("Eva la tana");
        reply2bisbisbis.setUser(user3);
        reply2bisbisbis.setCommentFather(reply2bisbis);
        reply2bisbis.getComments().add(reply2bisbisbis);

        // Commentaire de user3 sur le post1 de user1
        Post reply3 = new Post();
        reply3.setPostPublicationDate("2025-01-02");
        reply3.setPostContent("Hmm les interfaces ...");
        reply3.setUser(user3);
        reply3.setCommentFather(post1);
        post1.getComments().add(reply3);

        // Réponse au reply3
        Post reply3bis = new Post();
        reply3bis.setPostPublicationDate("2025-01-03");
        reply3bis.setPostContent("C'est délicieux les interfaces, avec un peu de miel !");
        reply3bis.setUser(user2);
        reply3bis.setCommentFather(reply3);
        reply3.getComments().add(reply3bis);

        // Réponse au reply3
        Post reply3bisbis = new Post();
        reply3bisbis.setPostPublicationDate("2025-01-03");
        reply3bisbis.setPostContent("Je réponds à mon propre commentaire ! #caillou");
        reply3bisbis.setUser(user3);
        reply3bisbis.setCommentFather(reply3);
        reply3.getComments().add(reply3bisbis);
        
        // Commentaire sur le post2 de user2
        Post comment2 = new Post();
        comment2.setPostPublicationDate("2025-01-02");
        comment2.setPostContent("Super tutoriel ! J'ai appris plein de choses.");
        comment2.setUser(user1);
        comment2.setCommentFather(post2);
        post2.getComments().add(comment2);
        
        // Réponse de user2 au commentaire2
        Post reply4 = new Post();
        reply4.setPostPublicationDate("2025-01-02");
        reply4.setPostContent("Je suis content que ça t'ait plu ! J'en prépare un autre sur Spring Security.");
        reply4.setUser(user2);
        reply4.setCommentFather(comment2);
        comment2.getComments().add(reply4);
        
        // Sauvegarde des commentaires et réponses aux commentaires
        comment1 = postRepository.save(comment1);
        reply1 = postRepository.save(reply1);
        reply2 = postRepository.save(reply2);
        reply2bis = postRepository.save(reply2bis);
        reply2bisbis = postRepository.save(reply2bisbis);
        reply2bisbisbis = postRepository.save(reply2bisbisbis);
        reply3 = postRepository.save(reply3);
        reply3bis = postRepository.save(reply3bis);
        reply3bisbis = postRepository.save(reply3bisbis);
        comment2 = postRepository.save(comment2);
        reply4 = postRepository.save(reply4);

        // Création de nouveaux posts pour l'utilisateur 2
        Post post5 = new Post();
        post5.setPostPublicationDate("2025-01-06");
        post5.setPostContent("Nouveau tutoriel sur Spring Boot disponible !");
        post5.setUser(user2);
        post5.setCommunity(community1);

        Post post6 = new Post();
        post6.setPostPublicationDate("2025-01-07");
        post6.setPostContent("J'ai terminé mon projet JavaFX, besoin de feedback !");
        post6.setUser(user2);
        post6.setCommunity(community1);

        Post post7 = new Post();
        post7.setPostPublicationDate("2025-01-08");
        post7.setPostContent("Besoin d'aide avec Hibernate et JPA");
        post7.setUser(user2);
        post7.setCommunity(community2);

        Post post8 = new Post();
        post8.setPostPublicationDate("2025-01-09");
        post8.setPostContent("J'ai créé une nouvelle application web avec Spring !");
        post8.setUser(user2);
        post8.setCommunity(community1);

        Post post9 = new Post();
        post9.setPostPublicationDate("2025-01-10");
        post9.setPostContent("Besoin de conseils pour l'optimisation des requêtes SQL");
        post9.setUser(user2);
        post9.setCommunity(community2);

        // Sauvegarde des nouveaux posts
        post5 = postRepository.save(post5);
        post6 = postRepository.save(post6);
        post7 = postRepository.save(post7);
        post8 = postRepository.save(post8);
        post9 = postRepository.save(post9);

        Repost repost2 = new Repost(user1, post3, "2025-01-06", "Je partage ce post car je rencontre les mêmes problèmes avec Spring");
        repost2 = repostRepository.save(repost2);

        // Création des réactions
        Reaction reaction1 = new Reaction(ReactionType.LIKE);
        Reaction reaction2 = new Reaction(ReactionType.LOVE);
        Reaction reaction3 = new Reaction(ReactionType.LAUGH);
        Reaction reaction4 = new Reaction(ReactionType.CRY);
        Reaction reaction5 = new Reaction(ReactionType.ANGRY);
        
        // Sauvegarde des réactions
        reaction1 = reactionRepository.save(reaction1);
        reaction2 = reactionRepository.save(reaction2);
        reaction3 = reactionRepository.save(reaction3);
        reaction4 = reactionRepository.save(reaction4);
        reaction5 = reactionRepository.save(reaction5);
        
        // Création des UserPostReactions
        UserPostReaction upr1 = new UserPostReaction();
        upr1.setId(new UserPostReactionID());
        upr1.getId().setUserId(user1.getIdUser());
        upr1.getId().setPostId(post1.getPostId());
        upr1.getId().setReactionId(reaction1.getReactionId());
        upr1.setUser(user1);
        upr1.setPost(post1);
        upr1.setReaction(reaction1);
        
        UserPostReaction upr2 = new UserPostReaction();
        upr2.setId(new UserPostReactionID());
        upr2.getId().setUserId(user2.getIdUser());
        upr2.getId().setPostId(post1.getPostId());
        upr2.getId().setReactionId(reaction2.getReactionId());
        upr2.setUser(user2);
        upr2.setPost(post1);
        upr2.setReaction(reaction2);

        UserPostReaction upr3 = new UserPostReaction();
        upr3.setId(new UserPostReactionID());
        upr3.getId().setUserId(user3.getIdUser());
        upr3.getId().setPostId(post2.getPostId());
        upr3.getId().setReactionId(reaction3.getReactionId());
        upr3.setUser(user3);
        upr3.setPost(post2);
        upr3.setReaction(reaction3);

        UserPostReaction upr4 = new UserPostReaction();
        upr4.setId(new UserPostReactionID());
        upr4.getId().setUserId(user1.getIdUser());
        upr4.getId().setPostId(post2.getPostId());
        upr4.getId().setReactionId(reaction4.getReactionId());
        upr4.setUser(user1);
        upr4.setPost(post2);
        upr4.setReaction(reaction4);

        UserPostReaction upr5 = new UserPostReaction();
        upr5.setId(new UserPostReactionID());
        upr5.getId().setUserId(user2.getIdUser());
        upr5.getId().setPostId(post2.getPostId());
        upr5.getId().setReactionId(reaction5.getReactionId());
        upr5.setUser(user2);
        upr5.setPost(post2);
        upr5.setReaction(reaction5);

        UserPostReaction upr6 = new UserPostReaction();
        upr6.setId(new UserPostReactionID());
        upr6.getId().setUserId(user3.getIdUser());
        upr6.getId().setPostId(post2.getPostId());
        upr6.getId().setReactionId(reaction1.getReactionId());
        upr6.setUser(user3);
        upr6.setPost(post2);
        upr6.setReaction(reaction1);

        UserPostReaction upr7 = new UserPostReaction();
        upr7.setId(new UserPostReactionID());
        upr7.getId().setUserId(user1.getIdUser());
        upr7.getId().setPostId(post3.getPostId());
        upr7.getId().setReactionId(reaction2.getReactionId());
        upr7.setUser(user1);
        upr7.setPost(post3);
        upr7.setReaction(reaction2);

        UserPostReaction upr8 = new UserPostReaction();
        upr8.setId(new UserPostReactionID());
        upr8.getId().setUserId(user2.getIdUser());
        upr8.getId().setPostId(post3.getPostId());
        upr8.getId().setReactionId(reaction3.getReactionId());
        upr8.setUser(user2);
        upr8.setPost(post3);
        upr8.setReaction(reaction3);

        UserPostReaction upr9 = new UserPostReaction();
        upr9.setId(new UserPostReactionID());
        upr9.getId().setUserId(user3.getIdUser());
        upr9.getId().setPostId(post3.getPostId());
        upr9.getId().setReactionId(reaction4.getReactionId());
        upr9.setUser(user3);
        upr9.setPost(post3);
        upr9.setReaction(reaction4);

        UserPostReaction upr10 = new UserPostReaction();
        upr10.setId(new UserPostReactionID());
        upr10.getId().setUserId(user1.getIdUser());
        upr10.getId().setPostId(post9.getPostId());
        upr10.getId().setReactionId(reaction5.getReactionId());
        upr10.setUser(user1);
        upr10.setPost(post9);
        upr10.setReaction(reaction5);

        UserPostReaction upr11 = new UserPostReaction();
        upr11.setId(new UserPostReactionID());
        upr11.getId().setUserId(user2.getIdUser());
        upr11.getId().setPostId(post9.getPostId());
        upr11.getId().setReactionId(reaction1.getReactionId());
        upr11.setUser(user2);
        upr11.setPost(post9);
        upr11.setReaction(reaction1);

        UserPostReaction upr12 = new UserPostReaction();
        upr12.setId(new UserPostReactionID());
        upr12.getId().setUserId(user3.getIdUser());
        upr12.getId().setPostId(post9.getPostId());
        upr12.getId().setReactionId(reaction2.getReactionId());
        upr12.setUser(user3);
        upr12.setPost(post9);
        upr12.setReaction(reaction2);
        
        // Sauvegarde des UserPostReactions
        userPostReactionRepository.save(upr1);
        userPostReactionRepository.save(upr2);
        userPostReactionRepository.save(upr3);
        userPostReactionRepository.save(upr4);
        userPostReactionRepository.save(upr5);
        userPostReactionRepository.save(upr6);
        userPostReactionRepository.save(upr7);
        userPostReactionRepository.save(upr8);
        userPostReactionRepository.save(upr9);
        userPostReactionRepository.save(upr10);
        userPostReactionRepository.save(upr11);
        userPostReactionRepository.save(upr12);

        // Création d'un message
        Message message1 = new Message();
        message1.setContent("Bonjour ! Je viens de rejoindre la communauté.");
        message1.setDateMessage("2025-01-01");
        message1.setSender(user1);
        message1.setChannel(channel1);
        
        // Sauvegarde du message
        message1 = messageRepository.save(message1);
        
        // Followers et following
        user1.addFollowers(user2);
        user1.addFollowing(user2);
        user1.addFollowing(user3);
        user2.addFollowers(user1);
        user2.addFollowing(user1);
        user3.addFollowing(user2);
        user2.addFollowers(user3);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        System.out.println("Données initiales créées avec succès !");
    }
}