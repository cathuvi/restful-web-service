package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount =3;

    static {
        users.add(new User(1,"huvi", new Date()));
        users.add(new User(2,"han", new Date()));
        users.add(new User(3,"mung", new Date()));
    }

    public List<User> findALl(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        user.setJoinDate(new Date());
        users.add(user);
        return  user;
    }

    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId()==id){
                users.remove(user);
                return user;
            }

        }
        return null;
    }
    public User modifiedUser(User user){
        for(User u : users){
            if(user.getId() == u.getId()){
                u.setName(user.getName());
                u.setJoinDate(new Date());
                return u;
            }
        }
        return null;
    }

}
