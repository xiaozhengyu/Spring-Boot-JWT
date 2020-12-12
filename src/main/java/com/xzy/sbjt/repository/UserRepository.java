package com.xzy.sbjt.repository;

import com.xzy.sbjt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author xzy
 * @date 2020-12-12 13:44
 * 说明：
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {
    /**
     * 查询用户信息
     *
     * @param useId     - 用户ID
     * @param isDeleted - 0：未删除 1：已删除
     * @return - 用户信息
     */
    Optional<UserEntity> findByIdAndDeleted(String useId, Integer isDeleted);
}
