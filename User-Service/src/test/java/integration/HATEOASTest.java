package integration;

import app.UserServiceApplication;
import app.dto.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HATEOASTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHATEOASLinksInGetUserResponse() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("HATEOAS Test");
        request.setEmail("hateoas@example.com");
        request.setAge(25);

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='self')]").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='update')]").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='delete')]").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='all-users')]").exists());
    }

    @Test
    void testHATEOASLinksInAllUsersResponse() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='create')]").exists())
                .andExpect(jsonPath("$.links[?(@.rel=='documentation')]").exists());
    }
}
