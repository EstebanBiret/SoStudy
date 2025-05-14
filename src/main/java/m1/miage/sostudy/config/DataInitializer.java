package m1.miage.sostudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import m1.miage.sostudy.model.entity.*;
import m1.miage.sostudy.repository.*;
import m1.miage.sostudy.model.enums.ReactionType;
import m1.miage.sostudy.model.entity.id.UserPostReactionID;

/**
 * DataInitializer class
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ReactionRepository reactionRepository;
    
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    
    @Autowired
    private ChannelRepository channelRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Vérifier si les données existent déjà
        if (userRepository.count() > 0) {
            System.out.println("Les données initiales existent déjà. Pas de création de données.");
            return;
        }

        // Création des utilisateurs
        User user1 = new User();
        user1.setName("Dupont");
        user1.setFirstName("Jean");
        user1.setEmail("jean.dupont@example.com");
        user1.setPassword("password123");
        user1.setPseudo("jeandupont");
        user1.setBirthDate("1990-01-01");
        user1.setBioUser("Développeur Java passionné");
        
        User user2 = new User();
        user2.setName("Martin");
        user2.setFirstName("Marie");
        user2.setEmail("marie.martin@example.com");
        user2.setPassword("password456");
        user2.setPseudo("mariecode");
        user2.setBirthDate("1992-05-15");
        user2.setBioUser("Architecte logiciel");
        
        User user3 = new User();
        user3.setName("Leroy");
        user3.setFirstName("Pierre");
        user3.setEmail("pierre.leroy@example.com");
        user3.setPassword("password789");
        user3.setPseudo("devpierre");
        user3.setBirthDate("1995-08-20");
        user3.setBioUser("Développeur Frontend");
        
        // Sauvegarde des utilisateurs
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        // Création des channels
        Channel channel1 = new Channel("Developpement Java", "images/channel/java.png");
        channel1.setCreator(user1);
        channel1.getUsers().add(user1);
        channel1.getUsers().add(user2);
        channel1.getUsers().add(user3);
        
        Channel channel2 = new Channel("Spring Framework", "images/channel/spring.png");
        channel2.setCreator(user2);
        channel2.getUsers().add(user1);
        channel2.getUsers().add(user2);
        
        // Sauvegarde des channels
        channel1 = channelRepository.save(channel1);
        channel2 = channelRepository.save(channel2);

        // Ajout des channels aux utilisateurs
        user1.getSubscribedChannels().add(channel1);
        user1.getSubscribedChannels().add(channel2);
        user2.getSubscribedChannels().add(channel1);
        user2.getSubscribedChannels().add(channel2);
        user3.getSubscribedChannels().add(channel1);
        
        // Sauvegarde des utilisateurs avec les channels
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
        // Création des communautés
        Community community1 = new Community("Java Developers", "2025-01-01", "images/community/java.png", "Communauté des développeurs Java");
        Community community2 = new Community("Spring Framework", "2025-02-01", "images/community/spring.png", "Communauté des développeurs Spring");
        
        // Sauvegarde des communautés
        community1.setUserCreator(user1);
        community2.setUserCreator(user2);
        
        community1 = communityRepository.save(community1);
        community2 = communityRepository.save(community2);
        
        // Ajout des membres aux communautés
        community1.getUsersMembers().add(user2);
        community1.getUsersMembers().add(user3);
        community2.getUsersMembers().add(user1);
        community2.getUsersMembers().add(user3);
        
        // Création des posts
        Post post1 = new Post();
        post1.setPostPublicationDate("2025-01-01");
        post1.setPostContent("Bonjour à tous ! Je débute dans Java et j'ai besoin d'aide pour comprendre les interfaces.");
        post1.setUser(user1);
        post1.setCommunity(community1);
        
        Post post2 = new Post();
        post2.setPostPublicationDate("2025-01-02");
        post2.setPostContent("Je viens de terminer un tutoriel sur Spring Boot et je partage mes notes ici.");
        post2.setUser(user2);
        post2.setCommunity(community2);
        
        // Sauvegarde des posts
        post1 = postRepository.save(post1);
        post2 = postRepository.save(post2);
        
        // Création des réactions
        Reaction reaction1 = new Reaction(ReactionType.LIKE);
        Reaction reaction2 = new Reaction(ReactionType.LOVE);
        
        // Sauvegarde des réactions
        reaction1 = reactionRepository.save(reaction1);
        reaction2 = reactionRepository.save(reaction2);
        
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
        
        // Sauvegarde des UserPostReactions
        userPostReactionRepository.save(upr1);
        userPostReactionRepository.save(upr2);
        
        // Création d'un événement
        Event event1 = new Event();
        event1.setEventName("Meetup Java");
        event1.setEventPublicationDate("2025-01-01");
        event1.setEventContent("Rencontre des développeurs Java de la région");
        event1.setEventBeginningDate("2025-01-15");
        event1.setEventEndDate("2025-01-15 17:00");
        event1.setEventPlace("Paris, France");
        event1.setUserCreator(user1);
        
        // Sauvegarde de l'événement
        event1 = eventRepository.save(event1);
        
        // Création d'un message
        Message message1 = new Message();
        message1.setContent("Bonjour ! Je viens de rejoindre la communauté.");
        message1.setDateMessage("2025-01-01");
        message1.setSender(user1);
        message1.setChannel(channel1);
        
        // Sauvegarde du message
        message1 = messageRepository.save(message1);
        
        System.out.println("Données initiales créées avec succès !");
    }
}
