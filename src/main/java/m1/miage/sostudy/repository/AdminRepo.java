package m1.miage.sostudy.repository;

import m1.miage.sostudy.model.entity.Admin;
import m1.miage.sostudy.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Admin entity
 */
@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    /**
     * Find an admin by its id
     * @param id the id of the admin
     * @return the admin
     */
    Admin findById(int id);

    /**
     * Find an admin by its email
     * @param email the email of the admin
     * @return the admin
     */
    Admin findByEmail(String email);
}
