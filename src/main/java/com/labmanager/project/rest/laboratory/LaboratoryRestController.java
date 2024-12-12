package com.labmanager.project.rest.laboratory;


import com.labmanager.project.entity.laboratory.LaboratoryGeneral;
import com.labmanager.project.service.laboratory.LaboratoryDetailService;
import com.labmanager.project.service.laboratory.LaboratoryGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LaboratoryRestController {

    private LaboratoryGeneralService laboratoryGeneralService;
    private LaboratoryDetailService laboratoryDetailService;

    @Autowired
    public  LaboratoryRestController(LaboratoryDetailService laboratoryDetailService, LaboratoryGeneralService laboratoryGeneralService){
        this.laboratoryDetailService = laboratoryDetailService;
        this.laboratoryGeneralService = laboratoryGeneralService;
    }


    @GetMapping("/laboratories/{laboratoryName}")
    public List<LaboratoryGeneral> getLaboratoryByName (@PathVariable String laboratoryName){
        return laboratoryGeneralService.findByName(laboratoryName);
    }


    @GetMapping("/laboratories")
    public List<LaboratoryGeneral> getAllLaboratory (){
        return laboratoryGeneralService.findAll();
    }

    @PutMapping("/laboratories")
    public LaboratoryGeneral createNewLaboratory (@RequestBody ParamRequestCreateLab param) {
        if(param.getLaboratoryGeneral() == null){
            return null;
        }
        laboratoryGeneralService.createNewLaboratory(param.getAdmin(), param.getLaboratoryGeneral());

        return laboratoryGeneralService.findByName(param.getLaboratoryGeneral().getNameLab()).get(0);
    }

    @PostMapping("/laboratories/{nameLab}")
    public LaboratoryGeneral updateGeneralLaboratory (@RequestBody LaboratoryGeneral lab, @PathVariable String nameLab){
        return laboratoryGeneralService.updateLaboratory(lab, nameLab);
    }

    @DeleteMapping("/laboratories/{nameLab}")
    public void deleteGeneralLaboratory (@PathVariable String nameLab){
        laboratoryGeneralService.deleteLaboratory(nameLab);
    }

}

 class ParamRequestCreateLab {
    private String admin;

    private LaboratoryGeneral laboratoryGeneral;

    public ParamRequestCreateLab(String admin, LaboratoryGeneral laboratoryGeneral) {
        this.admin = admin;
        this.laboratoryGeneral = laboratoryGeneral;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public LaboratoryGeneral getLaboratoryGeneral() {
        return laboratoryGeneral;
    }

    public void setLaboratoryGeneral(LaboratoryGeneral laboratoryGeneral) {
        this.laboratoryGeneral = laboratoryGeneral;
    }
}