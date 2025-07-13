package excusasHspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Map<String, Object> excusaValida = Map.of(
            "nombreEmpleado", "Lucía",
            "emailEmpleado", "lucia@empresa.com",
            "legajoEmpleado", 2023,
            "tipo", "Moderada",
            "descripcion", "Se pinchó la bici"
    );

    @Test
    void enviarExcusa_debeProcesarCorrectamente() throws Exception {
        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(excusaValida)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificaciones", isA(Iterable.class)))
                .andExpect(jsonPath("$.prontuarios", isA(Iterable.class)));
    }

    @Test
    void enviarExcusa_tipoDesconocido_debeFallar() throws Exception {
        Map<String, Object> excusaInvalida = Map.of(
                "nombreEmpleado", "Lucía",
                "emailEmpleado", "lucia@empresa.com",
                "legajoEmpleado", 2023,
                "tipo", "imaginaria",
                "descripcion", "Excusa no válida"
        );

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(excusaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarExcusasProcesadas_debeIncluirExcusas() throws Exception {

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(excusaValida)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/excusas/procesadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }
}
