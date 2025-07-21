package com.example.test_work_helmes.controller;

import com.example.test_work_helmes.dto.UserForm;
import com.example.test_work_helmes.entity.Sector;
import com.example.test_work_helmes.entity.User;
import com.example.test_work_helmes.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("sectors")
    public List<Sector> loadSectors() {
        return userService.getSectors();
    }

    // show
    @GetMapping("/")
    public String showForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        UserForm form = new UserForm();
        List<Sector> selectedSectors = List.of();

        if (userId != null) {
            userService.findUser(userId).ifPresent(user -> {
                form.setName(user.getName());
                form.setAgreeToTerms(user.isAgreeToTerms());
                form.setSelectedSectorIds(
                        user.getSectors().stream().map(Sector::getId).toList()
                );
                model.addAttribute("selectedSectors", user.getSectors());
            });
        } else {
            model.addAttribute("selectedSectors", selectedSectors);
        }

        model.addAttribute("user", form);
        return "index";
    }

    // save
    @PostMapping("/save")
    public String saveForm(@Valid @ModelAttribute("user") UserForm form,
                           BindingResult result,
                           Model model,
                           HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("selectedSectors", userService.getSectorsByIds(form.getSelectedSectorIds()));
            return "index";
        }

        Long userId = (Long) session.getAttribute("userId");

        User user = userId != null
                ? userService.findUser(userId).orElse(new User())
                : new User();

        user.setName(form.getName());
        user.setAgreeToTerms(form.isAgreeToTerms());
        user = userService.saveUser(user, form.getSelectedSectorIds());

        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(15 * 60);

        return "redirect:/?success";
    }

    // remove
    @PostMapping("/remove-sector")
    public String removeSector(@RequestParam Long sectorId, HttpSession session) {
        System.out.println("🔧 Called removeSector with sectorId = " + sectorId);

        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            userService.removeSector(userId, sectorId);
        }
        return "redirect:/";
    }

    // delete
    @PostMapping("/delete-user")
    public String deleteUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            userService.deleteUser(userId);
            session.invalidate();
        }
        return "redirect:/?deleted";
    }

}
