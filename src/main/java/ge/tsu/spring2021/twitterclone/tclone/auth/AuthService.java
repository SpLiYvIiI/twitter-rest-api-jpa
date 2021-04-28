package ge.tsu.spring2021.twitterclone.tclone.auth;

import ge.tsu.spring2021.twitterclone.tclone.user.User;
import ge.tsu.spring2021.twitterclone.tclone.user.UserRepository;
import ge.tsu.spring2021.twitterclone.tclone.utils.JwtUtils;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.InvalidCredentialsException;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.RecordAlreadyExistsException;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean login(LoginView user) throws RecordNotFoundException{
        if(userRepository.existsByUserName(user.getUserName())) {
            User findUser = userRepository.findByUserName(user.getUserName());
            if(bCryptPasswordEncoder.matches(user.getPassword(),findUser.getPassword()))
                return true;
            else return false;
        }
        else throw new RecordNotFoundException("User with such username doesnt exist");
    }
    public void register(RegisterView newUser) throws RecordAlreadyExistsException, InvalidCredentialsException {
        if(newUser.getUserName().equals("") || newUser.getEmail().equals("") || newUser.getPassword().equals("")){
            throw new InvalidCredentialsException("Wrong data");
        }
        if(userRepository.existsByEmail(newUser.getEmail()) || userRepository.existsByUserName(newUser.getUserName()))
        {
            throw new RecordAlreadyExistsException("User with such email or username already exists");
        }
        else{
            String userName = newUser.getUserName();
            String password = newUser.getPassword();
            String email = newUser.getEmail();
            userRepository.save(new User(userName,bCryptPasswordEncoder.encode(password),email));
        }
    }
}
