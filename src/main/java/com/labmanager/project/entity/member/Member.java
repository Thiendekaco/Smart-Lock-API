package com.labmanager.project.entity.member;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.labmanager.project.entity.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @Column(name = "id_member")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String  name;

    public List<RoleMember> getRoleMemberList() {
        return roleMemberList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, university, age, user);
    }

    @Column(name = "university")
    private String university;


    @Column( name = "age")
    private int age;

    public Member(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoleMember> roleMemberList;

    public void addMemberJoinedLab(RoleMember roleMember) {
        if(roleMemberList == null){
            roleMemberList = new ArrayList<RoleMember>();
        }
        roleMemberList.add(roleMember);
    }

    public void setRoleMemberList(List<RoleMember> roleMemberList) {
        this.roleMemberList = roleMemberList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Member() {}

    public String getName() {
        return name;
    }

    public Member(String name, String university, int age, User user) {
        this.name = name;
        this.university = university;
        this.age = age;
        this.user = user;
    }




    public Member(String name, String university, int age) {
        this.name = name;
        this.university = university;
        this.age = age;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", university='" + university + '\'' +
                ", age=" + age +
                ", user=" + user +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAllFeatureMember ( Member memberUpdate ) {
        this.name = memberUpdate.getName();
        this.university = memberUpdate.getUniversity();
        this.age = memberUpdate.getAge();
    }


}

