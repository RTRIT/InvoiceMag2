package com.example.invoiceProject.Controller;

import com.example.invoiceProject.DTO.response.ApiResponse;
import com.example.invoiceProject.DTO.requests.PrivilegeRequest;
import com.example.invoiceProject.DTO.response.PrivilegeResponse;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Service.PrivilegeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/privileges")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PrivilegeController {
    PrivilegeService permissionService;

    @PostMapping
    ApiResponse<PrivilegeResponse> create(@RequestBody PrivilegeRequest request) {
        return ApiResponse.<PrivilegeResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PrivilegeResponse>> getAll() {
        return ApiResponse.<List<PrivilegeResponse>>builder()
                .result(permissionService.getList())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable Long privilege) {
        permissionService.delete(privilege);
        return ApiResponse.<Void>builder().build();
    }
}
