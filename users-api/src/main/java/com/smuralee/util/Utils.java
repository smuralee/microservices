package com.smuralee.util;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.domain.Name;
import com.smuralee.domain.User;
import com.smuralee.entity.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

@XRayEnabled
public class Utils {

    /**
     * <p>
     * Transforms user entity list to user domain list
     * </p>
     *
     * @param userInfos
     * @return List<User>
     */
    public static List<User> getUsers(final List<UserInfo> userInfos) {
        return userInfos.stream().map(Utils::getUserObject).collect(Collectors.toList());
    }

    /**
     * <p>
     * Transforms userInfo to user
     * </p>
     *
     * @param userInfo
     * @return User
     */
    public static User getUser(final UserInfo userInfo) {
        return getUserObject(userInfo);
    }

    /**
     * <p>
     * Get User domain object
     * </p>
     *
     * @param userInfo
     * @return User
     */
    private static User getUserObject(UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        Name name = new Name(
                userInfo.getFName(),
                userInfo.getMName(),
                userInfo.getLName()
        );
        user.setName(name);
        user.setAge(userInfo.getAge());
        return user;
    }
}
