package com.labmanager.project.entity.laboratory;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labmanager.project.entity.member.RoleMember;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "lab_details")
public class LaboratoryDetail {


    @Id
    @Column(name = "id_lab_details")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "character varying[]")
    private List<String> roles;

    @Column(name = "number_of_member")
    private int numberOfMember = 0;

    @Column(name = "description")
    private String description = "";

    @OneToMany(mappedBy = "laboratoryDetail",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoleMember> members;

    @OneToOne(mappedBy = "laboratoryDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private LaboratoryGeneral laboratoryGeneral;

    public List<RoleMember> getMembers() {
        if(members == null){
            members = new ArrayList<RoleMember>();
        }

        return members.stream().filter(roleMember -> Objects.equals(roleMember.getStatusJoined(), "success")).toList();
    }

    public void setMembers(List<RoleMember> members) {
        this.members = members;
    }

    public void appendMember ( RoleMember member){
        if(this.members == null){
            this.members = new ArrayList<RoleMember>();
        }
        this.members.add(member);
    }


    public void setRoleMemberList(List<RoleMember> roleMemberList) {
        this.members = roleMemberList;
        Stream<RoleMember> filterMember =  this.members.stream().filter(roleMember -> Objects.equals(roleMember.getStatusJoined(), "success"));
        this.numberOfMember = (int) filterMember.count();
    }

    @Override
    public String toString() {
        return "LaboratoryDetail{" +
                "id=" + id +
                ", roles=" + roles +
                ", numberOfMember=" + numberOfMember +
                ", description='" + description + '\'' +
                ", members=" + members +
                ", laboratoryGeneral=" + laboratoryGeneral +
                '}';
    }

    public void addMemberJoinedLab(RoleMember roleMember){
        if(members == null){
            members = new ArrayList<RoleMember>();
        }
        members.add(roleMember);
        if(Objects.equals(roleMember.getStatusJoined(), "success")){
            this.numberOfMember += 1;
        }

    }

    public List<String> getRoles() {

        return roles;
    }

    public LaboratoryDetail() {}

    public LaboratoryDetail(List<String> roles, int numberOfMember , String description) {
        this.roles = roles;
        this.numberOfMember = numberOfMember;
        this.description = description;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public void addRoles( String role ) {
        if(roles == null){
            roles = new ArrayList<String>();
        }
        roles.add(role);
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public LaboratoryGeneral getLaboratoryGeneral() {
        return laboratoryGeneral;
    }

    public void setLaboratoryGeneral(LaboratoryGeneral laboratoryGeneral) {
        this.laboratoryGeneral = laboratoryGeneral;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LaboratoryDetail  setLaboratory(LaboratoryDetail laboratoryDetail){
        if(laboratoryDetail == null){
            return this;
        }
        this.roles = laboratoryDetail.getRoles() == null ? this.roles : laboratoryDetail.getRoles();
        this.description = laboratoryDetail.getDescription();

        if(this.members == null || this.members.size() < laboratoryDetail.getMembers().size()){
            this.members = laboratoryDetail.getMembers();
        };
        return this;
    }



}
