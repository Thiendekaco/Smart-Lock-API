package com.labmanager.project.dto;

import java.time.LocalDateTime;

public class MemberLabDTO extends MemberDTOBase{

    private LocalDateTime dateJoined;
    private String statusJoined;
    private String role;

    private RoleMemberDTO laboratory;


    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getStatusJoined() {
        return statusJoined;
    }

    public void setStatusJoined(String statusJoined) {
        this.statusJoined = statusJoined;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RoleMemberDTO getLaboratory() {
        return laboratory;
    }

    public MemberLabDTO(String name, String email, String university, int age, LocalDateTime dateJoined, String statusJoined, String role, RoleMemberDTO laboratory) {
        super(name, email, university, age);
        this.dateJoined = dateJoined;
        this.statusJoined = statusJoined;
        this.role = role;
        this.laboratory = laboratory;
    }

    public void setLaboratory(RoleMemberDTO laboratory) {
        this.laboratory = laboratory;
    }

    public MemberLabDTO(){
        super();
    }


}
