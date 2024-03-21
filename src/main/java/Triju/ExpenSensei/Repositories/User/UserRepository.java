package Triju.ExpenSensei.Repositories.User;

import Triju.ExpenSensei.Entities.User.User;
import Triju.ExpenSensei.Repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    List<User> findByEmailLike(String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:searchTerm% OR u.lastName LIKE %:searchTerm%")
    List<User> findByNameLikeOrLastNameLike(@Param("searchTerm") String searchTerm);

    Optional<User> findByEmail(String email);
}
