package com.techpod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class LaptopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowPublicAccessToLaptops() throws Exception {
        mockMvc.perform(get("/api/laptops"))
                .andExpect(status().isOk());
    }
    @Test 
    void shouldRejectUnauthenticatedAdminAccess() throws Exception{
        mockMvc.perform(get("/api/admin/laptops"))
                .andExpect(status().isForbidden());
    }
}