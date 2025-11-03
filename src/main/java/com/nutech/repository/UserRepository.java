package com.nutech.repository;

import com.nutech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName WHERE u.email = :email")
    void updateProfile(@Param("email") String email,
                       @Param("firstName") String firstName,
                       @Param("lastName") String lastName);


    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance + :amount WHERE u.email = :email")
    void updateBalance(@Param("email") String email, @Param("amount") Long amount);


    @Modifying
    @Query("UPDATE User u SET u.profileImage = :profileImage WHERE u.email = :email")
    void updateProfileImage(@Param("email") String email, @Param("profileImage") String profileImage);

}