package com.example.invoiceProject.Controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Add any required model attributes
        return "dashboard"; // Ensure there is a view named "dashboard.html" or "dashboard.jsp"
    }
}