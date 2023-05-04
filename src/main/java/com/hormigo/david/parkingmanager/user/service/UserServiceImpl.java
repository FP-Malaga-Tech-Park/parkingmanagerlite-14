package com.hormigo.david.parkingmanager.user.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> getAll() {

        return this.repository.findAll();
    }
    @Override
    public User register(UserDao userDao) throws UserExistsException {
        if (userExists(userDao.getEmail())){
            throw new UserExistsException();
        }
        User user = new User();
        
        BeanUtils.copyProperties(userDao, user);
        return this.repository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        return this.repository.findByEmail(email) != null ? true : false;

    }
    @Override
    public Optional<User> getUser(long id) {
        return this.repository.findById(id);
    }
    @Override
    public void deleteUserById(long id) throws UserDoesNotExistsException {
        if (!this.repository.existsById(id)){
            throw new UserDoesNotExistsException();
        }
        this.repository.deleteById(id);
        
    }


}
