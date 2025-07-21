package com.example.test_work_helmes.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.AssertTrue;


public class UserForm {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Please select at least one sector.")
    private List<Long> selectedSectorIds = new ArrayList<>();

    @AssertTrue(message = "You must agree to the terms.")
    private boolean agreeToTerms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getSelectedSectorIds() {
        return selectedSectorIds;
    }

    public void setSelectedSectorIds(List<Long> selectedSectorIds) {
        this.selectedSectorIds = selectedSectorIds;
    }

    public boolean isAgreeToTerms() {
        return agreeToTerms;
    }

    public void setAgreeToTerms(boolean agreeToTerms) {
        this.agreeToTerms = agreeToTerms;
    }
}
