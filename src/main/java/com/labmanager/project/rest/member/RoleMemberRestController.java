package com.labmanager.project.rest.member;

import com.labmanager.project.dto.MemberDTO;
import com.labmanager.project.dto.MemberLabDTO;
import com.labmanager.project.dto.RoleMemberDTO;
import com.labmanager.project.entity.laboratory.LaboratoryDetail;
import com.labmanager.project.entity.laboratory.LaboratoryGeneral;
import com.labmanager.project.entity.member.Member;
import com.labmanager.project.entity.member.RoleMember;
import com.labmanager.project.service.member.RoleMemberService;
import com.labmanager.project.service.member.RoleMemberServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleMemberRestController {

    private RoleMemberService roleMemberService;

    public RoleMemberRestController(RoleMemberService roleMemberService) {
        this.roleMemberService = roleMemberService;
    }

    @GetMapping("/laboratories/{laboratory}/pending")
    public ResponseEntity<List<MemberLabDTO>> getMemberIsPending (@PathVariable String laboratory){
        List<RoleMember> roleMember = roleMemberService.findMemberIsPending(laboratory);

        return ResponseEntity.ok(
                roleMember.stream().map(this::ConvertEntityToDTO).toList()
        );

    };

    @GetMapping("/members/{emailMember}/pending")
    public ResponseEntity<List<RoleMemberDTO>> getLaboratoryIsPending (@PathVariable String emailMember){
        List<LaboratoryDetail> roleMember = roleMemberService.findLabIsPending(emailMember);

        return ResponseEntity.ok(
                roleMember.stream().map(this::ConvertEntityToDTO).toList()
        );

    };

    @PutMapping("/members/join")
    public ResponseEntity<MemberDTO> putMemberJoinIntoLab (
            @RequestParam("email") String email,
            @RequestParam("lab") String nameLab,
            @RequestParam("role") String role
    ){
        RoleMember roleMember = roleMemberService.AddMemberJoined(email, nameLab, role);

        return ResponseEntity.ok(ConvertEntityToDTO(roleMember.getMember()));

    }

    @PostMapping("/members/role")
    public ResponseEntity<MemberLabDTO> updateRoleMemberJoinIntoLab (
            @RequestParam("email") String email,
            @RequestParam("lab") String nameLab,
            @RequestParam("role") String role
    ){
        RoleMember roleMember = roleMemberService.updateRoleOfMember(email, nameLab, role);

        return ResponseEntity.ok(ConvertEntityToDTO(roleMember));
    }

    @PostMapping("/members/join")
    public ResponseEntity<MemberLabDTO> updateMemberJoinIntoLab (
            @RequestParam("email") String email,
            @RequestParam("lab") String nameLab
    ){
        RoleMember roleMember = roleMemberService.updateStatusJoined(email, nameLab);

        return ResponseEntity.ok(ConvertEntityToDTO(roleMember));

    }


    private MemberLabDTO ConvertEntityToDTO (RoleMember roleMember) {

        Member member = roleMember.getMember();
        LaboratoryGeneral laboratoryGeneral = roleMember.getLaboratoryDetail().getLaboratoryGeneral();

        RoleMemberDTO roleMemberDTO = new RoleMemberDTO(
                laboratoryGeneral.getNameLab(),
                laboratoryGeneral.getNameSchool(),
                laboratoryGeneral.getField(),
                laboratoryGeneral.getRanking()
        );

        return new MemberLabDTO(
                member.getName(),
                member.getUser().getEmailUser(),
                member.getUniversity(),
                member.getAge(),
                roleMember.getTimeJoined(),
                roleMember.getStatusJoined(),
                roleMember.getRole(),
                roleMemberDTO
        );
    }

    private RoleMemberDTO ConvertEntityToDTO (LaboratoryDetail laboratoryDetail){
        LaboratoryGeneral laboratoryGeneral = laboratoryDetail.getLaboratoryGeneral();

        return new RoleMemberDTO(
                laboratoryGeneral.getNameLab(),
                laboratoryGeneral.getNameSchool(),
                laboratoryGeneral.getField(),
                laboratoryGeneral.getRanking()
        );
    };

    private MemberDTO ConvertEntityToDTO (Member member) {

        return getMemberDTO(member, roleMemberService);
    }

    static MemberDTO getMemberDTO(Member member, RoleMemberService roleMemberService) {
        List<RoleMemberDTO> roleMemberDTOList = new ArrayList<RoleMemberDTO>();
        roleMemberService.findLabIsSuccess(member.getUser().getEmailUser()).forEach(lab -> {
            LaboratoryGeneral laboratoryGeneral = lab.getLaboratoryGeneral();

            roleMemberDTOList.add(new RoleMemberDTO(
                    laboratoryGeneral.getNameLab(),
                    laboratoryGeneral.getNameSchool(),
                    laboratoryGeneral.getField(),
                    laboratoryGeneral.getRanking()
            ));
        });

        return new MemberDTO(
                member.getName(),
                member.getUser().getEmailUser(),
                member.getUniversity(),
                member.getAge(),
                roleMemberDTOList
        );
    }


}
