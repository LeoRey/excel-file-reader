package excel.file.reader.Service;

import excel.file.reader.DTO.UserRightsDTO;
import excel.file.reader.Entity.User;
import excel.file.reader.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserRightsDTO searchByName(String name){
        Optional<UserRightsDTO> isUser = userRepository.searchByName(name);
        if (isUser.isPresent()){
            return isUser.get();
        }else{
            return null;
        }
    }
}
