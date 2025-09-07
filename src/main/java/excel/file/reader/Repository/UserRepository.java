package excel.file.reader.Repository;

import excel.file.reader.DTO.UserRightsDTO;
import excel.file.reader.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT udb.is_create AS isCreate " +
            "FROM \"user\" u " +
            "JOIN \"user_database_rights\" udb ON u.id = udb.user_id " +
            "WHERE u.name = :name " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<UserRightsDTO> searchByName(@Param("name")String name);
}
