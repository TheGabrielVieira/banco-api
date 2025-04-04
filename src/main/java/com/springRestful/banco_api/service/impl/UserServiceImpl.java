package com.springRestful.banco_api.service.impl;

import com.springRestful.banco_api.domain.model.User;
import com.springRestful.banco_api.domain.respository.UserRepository;
import com.springRestful.banco_api.service.UserService;
import com.springRestful.banco_api.service.exception.BusinesException;
import com.springRestful.banco_api.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    public static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User userToCreate){
        ofNullable(userToCreate).orElseThrow(() -> new BusinesException("User to create must not be null."));
        ofNullable(userToCreate.getAccount()).orElseThrow(() -> new BusinesException("User account must not be null."));
        ofNullable(userToCreate.getCard()).orElseThrow(() -> new BusinesException("User card must not be null."));

        this.validateChangeableId(userToCreate.getId(), "created");
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new BusinesException("This account number already exists.");
        }
        if (userRepository.existsByCardNumber(userToCreate.getCard().getNumber())) {
            throw new BusinesException("This card number already exists.");
        }
        return this.userRepository.save(userToCreate);
    }

    @Transactional
    public User update(Long id, User userTopUpdate){
        this.validateChangeableId(id, "updated");
        User dbUser = this.findById(id);
        if (!dbUser.getId().equals(userTopUpdate.getId())) {
            throw new BusinesException("Update IDs must be the same.");
        }
        dbUser.setName(userTopUpdate.getName());
        dbUser.setAccount(userTopUpdate.getAccount());
        dbUser.setCard(userTopUpdate.getCard());
        dbUser.setFeatures(userTopUpdate.getFeatures());
        dbUser.setNews(userTopUpdate.getNews());

        return this.userRepository.save(dbUser);
    }

    @Transactional
    public void delete(Long id){
        this.validateChangeableId(id, "deleted");
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }

    private void validateChangeableId(Long id, String operation){
        if (UNCHANGEABLE_USER_ID.equals(id)){
            throw new BusinesException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
