package com.labmanager.project.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labmanager.project.entity.member.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Column( name = "email")
    private String emailUser;

    @OneToOne( mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Member member;


    public User(String emailUser) {
        this.emailUser = emailUser;
    }

    public User() {}

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "UserEntity{}";
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }


    public void setId(int id) {
        this.id = id;
    }

}









