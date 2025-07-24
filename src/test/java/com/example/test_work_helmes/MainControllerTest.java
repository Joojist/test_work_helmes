package com.example.test_work_helmes;

import com.example.test_work_helmes.controller.MainController;
import com.example.test_work_helmes.entity.Sector;
import com.example.test_work_helmes.entity.User;
import com.example.test_work_helmes.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MainControllerTest {

    @Mock
    private UserService userService;

    private MainController mainController;

    private MockMvc mockMvc;

    private Sector mockSector;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        mockSector = new Sector("Test Sector", null);
        Field idField = Sector.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(mockSector, 1L);

        when(userService.getSectors()).thenReturn(List.of(mockSector));
        mainController = new MainController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void showForm_withoutUserId_returnsFormWithEmptyValues() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("selectedSectors", List.of()))
                .andExpect(view().name("index"));
    }

    @Test
    void showForm_withUserId_prefillsForm() throws Exception {
        User user = new User();
        user.setId(123L);
        user.setName("Alice");
        user.setAgreeToTerms(true);
        user.setSectors(Set.of(mockSector));

        when(userService.findUser(123L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/").sessionAttr("userId", 123L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("selectedSectors", user.getSectors()))
                .andExpect(view().name("index"));
    }

    @Test
    void saveForm_withValidationErrors_returnsToForm() throws Exception {
        mockMvc.perform(post("/save")
                        .param("name", "") // invalid name
                        .param("agreeToTerms", "true")
                        .param("selectedSectorIds", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("index"));

        verify(userService, never()).saveUser(any(), any());
    }

    @Test
    void saveForm_validForm_savesUserAndRedirects() throws Exception {
        User user = new User();
        user.setId(456L);
        user.setName("Bob");

        when(userService.findUser(456L)).thenReturn(Optional.of(user));
        when(userService.saveUser(any(User.class), anyList())).thenReturn(user);

        mockMvc.perform(post("/save")
                        .param("name", "Bob")
                        .param("agreeToTerms", "true")
                        .param("selectedSectorIds", "1")
                        .sessionAttr("userId", 456L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?success"));

        verify(userService).saveUser(any(User.class), eq(List.of(1L)));
    }

    @Test
    void removeSector_callsServiceAndRedirects() throws Exception {
        mockMvc.perform(post("/remove-sector")
                        .param("sectorId", "1")
                        .sessionAttr("userId", 789L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService).removeSector(789L, 1L);
    }

    @Test
    void deleteUser_invalidatesSessionAndRedirects() throws Exception {
        mockMvc.perform(post("/delete-user")
                        .sessionAttr("userId", 321L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?deleted"));

        verify(userService).deleteUser(321L);
    }
}

