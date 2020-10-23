package com.hencoder.plus;

import com.hencoder.plus.bean.User;
interface IUserAIDLInterface {

            boolean addUser(in User user);
            List<User> getCurrentUser();
}
