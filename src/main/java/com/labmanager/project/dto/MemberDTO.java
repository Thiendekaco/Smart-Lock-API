package com.labmanager.project.dto;

import java.util.List;
import java.util.Set;

public class MemberDTO extends MemberDTOBase {


    private List<RoleMemberDTO> laboratories;



    public List<RoleMemberDTO> getLaboratories() {
        return laboratories;
    }

    public MemberDTO(List<RoleMemberDTO> laboratories) {
        super();
        this.laboratories = laboratories;
    }

    public MemberDTO(String name, String email, String university, int age, List<RoleMemberDTO> laboratories) {
        super(name, email, university, age);
        this.laboratories = laboratories;
    }

    public void setLaboratories(List<RoleMemberDTO> laboratories) {
        this.laboratories = laboratories;
    }





}
