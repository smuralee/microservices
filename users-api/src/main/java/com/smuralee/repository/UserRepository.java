package com.smuralee.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@XRayEnabled
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
