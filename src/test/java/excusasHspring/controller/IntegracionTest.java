package excusasHspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import excusasHspring.dto.ExcusaRequest;
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
    public void cuandoTipoExcusaInvalido_devuelve400() throws Exception {
        ExcusaRequest request = new ExcusaRequest();
        request.setNombreEmpleado("Juan");
        request.setEmailEmpleado("juan@mail.com");
        request.setLegajoEmpleado(123);
        request.setDescripcion("No vino");
        // Tipo de excusa inexistente
        String jsonConTipoInvalido = """
                {
                    "nombreEmpleado": "Juan",
                    "emailEmpleado": "juan@mail.com",
                    "legajoEmpleado": 123,
                    "descripcion": "No vino",
                    "tipo": "FALSO"
                }
                """;

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConTipoInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tipo de excusa inválido: nulo o no reconocido"));
    }

    @Test
    public void cuandoTipoExcusaValido_devuelve200YRespuestaCorrecta() throws Exception {
        String requestJson = """
            {
                "nombreEmpleado": "Lucía",
                "emailEmpleado": "lucia@empresa.com",
                "legajoEmpleado": 456,
                "descripcion": "Se le pinchó la bicicleta",
                "tipo": "TRIVIAL"
            }
            """;

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.excusa.nombreEmpleado").value("Lucía"))
                .andExpect(jsonPath("$.excusa.tipoExcusa").value("Trivial"))
                .andExpect(jsonPath("$.excusa.descripcion").value("Se le pinchó la bicicleta"))
                .andExpect(jsonPath("$.notificaciones").isArray())
                .andExpect(jsonPath("$.prontuarios").isArray());
    }

    @Test
    public void cuandoFaltaCampoObligatorio_devuelve400YMensajeDeValidacion() throws Exception {
        String jsonInvalido = """
            {
                "emailEmpleado": "sinNombre@empresa.com",
                "legajoEmpleado": 789,
                "descripcion": "Falta nombre",
                "tipo": "MODERADA"
            }
            """;

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("nombreEmpleado")));
    }

    @Test
    public void cuandoHayMultiplesErroresDeValidacion_devuelve400YTodosLosMensajes() throws Exception {
        String jsonInvalido = """
            {
                "nombreEmpleado": "",
                "emailEmpleado": "invalido@empresa.com",
                "legajoEmpleado": 101,
                "descripcion": "Datos mal ingresados"
            }
            """;

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("nombreEmpleado")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("tipo")));
    }


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
