package br.com.mercado.controller;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.repository.AdminRepository;
import br.com.mercado.repository.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
@ActiveProfiles("test")
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdminRepository adminRepository;

    static String admin_API = "/api/v1/admin";


    @Test
    @DisplayName("deve criar um adm")
    public void deveCriarAdmin() throws Exception{
        AdminNewDTO adminNewDTO = new AdminNewDTO();
        adminNewDTO.setId(1);
        adminNewDTO.setLogin("user099");
        adminNewDTO.setSenha("123456");

        String json = new ObjectMapper().writeValueAsString(adminNewDTO);

        Admin admin = new Admin(1, "user099", "123456");

        Mockito.when(adminRepository.save(Mockito.any(Admin.class))).thenReturn(admin);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(admin_API)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("login").value(admin.getLogin()));
    }

    @Test
    public void deveLogarAdmin(){

    }
}
