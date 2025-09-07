package excel.file.reader.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(
        name = "user_database_rights"
)
public class UserDatabaseRights {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "is_create")
    private boolean isCreate;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "is_update")
    private boolean isUpdate;
    @Column(name = "is_delete")
    private boolean isDelete;
}
