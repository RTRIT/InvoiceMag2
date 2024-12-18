package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.requests.RoleRequest;
import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.PrivilegeRequest;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
import com.example.invoiceProject.DTO.response.RoleResponse;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Service.PrivilegeService;
import com.itextpdf.text.pdf.qrcode.Mode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/privilege")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PrivilegeController {
    PrivilegeService permissionService;

    @GetMapping("/list")
    public String privilegeList(ModelMap model){
        model.addAttribute("privileges", permissionService.getList());
        return "privilege/index";
    }



    @GetMapping("/new")
    public String create(ModelMap model) {
        PrivilegeRequest privilegeRequest = new PrivilegeRequest();
        model.addAttribute("privilege", privilegeRequest);
        return "privilege/new";
    }

    @PostMapping("/new")
    public String create(ModelMap model, @ModelAttribute("privilege") PrivilegeRequest request){
        try{
            PrivilegeResponse privilegeResponse = permissionService.create(request);
            model.addAttribute("message", "Registration successful!");
            return "redirect:/privilege/list";
        }catch (Exception ex){
            model.addAttribute("error",ex.getMessage());
            return "new";
        }
    }

    @GetMapping("/update/{id}")
    public String update(ModelMap model, @PathVariable("id") Long id){
        PrivilegeResponse privilege = permissionService.getPrivilege(id);

        // Add the role itself as a model attribute
        model.addAttribute("privilege", privilege);

        return "privilege/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("privilege") PrivilegeRequest request) {
        System.out.println(request);
        permissionService.update(id, request);
        return "redirect:/privilege/list";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, ModelMap model) {
        try{
            permissionService.delete(id);
            System.out.println("Delete privilege successfully!!");
            model.addAttribute("message", "Delete privilege successfully!");

        }catch (Exception e){
            System.out.println("Can not delete privilege");
            model.addAttribute("errorMessage", "Can not delete privilege successfully!");
        }
        return "redirect:/privilege/list";
    }

//    @DeleteMapping("/{privilege}")
//    ApiResponse<Void> delete(@PathVariable Long privilege) {
//        permissionService.delete(privilege);
//        return ApiResponse.<Void>builder().build();
//    }
}
