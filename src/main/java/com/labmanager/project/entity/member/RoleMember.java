package com.labmanager.project.entity.member;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "member_lab")
public class RoleMember {
    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "member_lab_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;




    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_id")
    @JsonIgnore
    private LaboratoryDetail laboratoryDetail;

    @Column(name = "role")
    private String role = "member";


    @Column(name = "status_joined")
    private String statusJoined = "pending";

    @Column(name = "time_change_state")
    private LocalDateTime timeJoined;

    public String getStatusJoined() {
        return statusJoined;
    }


    public RoleMember(String role, String statusJoined, LocalDateTime timeJoined) {
        this.role = role;
        this.statusJoined = statusJoined;
        this.timeJoined = timeJoined;
    }

    public RoleMember(Member member, LaboratoryDetail laboratoryDetail, String role, String statusJoined) {
        this.member = member;
        this.laboratoryDetail = laboratoryDetail;
        this.role = role;
        this.statusJoined = statusJoined;
        this.timeJoined = LocalDateTime.now();
    }

    public RoleMember(Member member, LaboratoryDetail laboratoryDetail, String role) {
        this.member = member;
        this.laboratoryDetail = laboratoryDetail;
        this.role = role;
        this.timeJoined = LocalDateTime.now();
    }

    public void setStatusJoined(String statusJoined) {
        this.statusJoined = statusJoined;
        this.timeJoined = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "RoleMember{" +
                " role='" + role + '\'' +
                '}';
    }

    public RoleMember() {}

    public RoleMember(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getTimeJoined() {
        return timeJoined;
    }

    public void setTimeJoined(LocalDateTime timeJoined) {
        this.timeJoined = timeJoined;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public LaboratoryDetail getLaboratoryDetail() {
        return laboratoryDetail;
    }

    public void setLaboratoryDetail(LaboratoryDetail laboratoryDetail) {
        this.laboratoryDetail = laboratoryDetail;
    }


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
