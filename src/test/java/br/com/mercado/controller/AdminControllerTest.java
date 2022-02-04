//package br.com.mercado.controller;
//
//import br.com.mercado.dto.AdminNewDTO;
//import br.com.mercado.model.entity.Admin;
//import br.com.mercado.model.entity.AdminLogin;
//import br.com.mercado.repository.AdminRepository;
//import br.com.mercado.repository.CategoriaRepository;
//import br.com.mercado.service.AdminService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@EnableWebMvc
//@ActiveProfiles("test")
//public class AdminControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private AdminRepository adminRepository;
//
//    static String admin_API = "/api/v1/admin";
//
//    public String tokenGerado;
//
//    @Autowired
//    AdminService adminService;
//
//    @BeforeEach
//    public void tokenGenerate(){
//        AdminLogin admin = new AdminLogin();
//        admin.setLogin("admin");
//        admin.setSenha("admin");
//        Admin admin1 = new Admin(1,admin.getLogin(), admin.getSenha());
//        adminRepository.save(admin1);
//        admin = adminService.logar(admin);
//        tokenGerado = admin.getToken();
//    }
//
//    @Test
//    @DisplayName("deve criar um adm")
//    public void deveCriarAdmin() throws Exception{
//        AdminNewDTO adminNewDTO = new AdminNewDTO();
//        adminNewDTO.setId(2);
//        adminNewDTO.setLogin("tantoFaz");
//        adminNewDTO.setSenha("whatever");
//
//        String json = new ObjectMapper().writeValueAsString(adminNewDTO);
//
//        Admin admin = new Admin(1, "user099", "123456");
//
//        Mockito.when(adminRepository.save(Mockito.any(Admin.class))).thenReturn(admin);
//
//        AdminLogin admLogado = new AdminLogin();
//        Mockito.when(adminService.logar(Mockito.any(AdminLogin.class))).thenReturn(admLogado);
//
//        admLogado.setToken(tokenGerado);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(admin_API)
//                .content(json)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON).header("Authorization", admLogado.getToken());
//
//        mvc.perform(request)
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("login").value(admin.getLogin()));
//    }
//
//    @Test
//    public void deveLogarAdmin(){
//
//    }
//}
