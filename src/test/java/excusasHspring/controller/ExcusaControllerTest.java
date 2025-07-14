package excusasHspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import excusasHspring.dto.ExcusaRequest;
import excusasHspring.modelo.excusas.TipoExcusa ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExcusaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void enviarExcusa_valida_devuelve200() throws Exception {
        ExcusaRequest request = new ExcusaRequest();
        request.setNombreEmpleado("Ana");
        request.setEmailEmpleado("ana@empresa.com");
        request.setLegajoEmpleado(101);
        request.setDescripcion("Se descompuso el auto");
        request.setTipo(TipoExcusa.TRIVIAL);

        mockMvc.perform(post("/api/excusas/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.excusa.nombreEmpleado").value("Ana"))
                .andExpect(jsonPath("$.excusa.tipoExcusa").value("Trivial"));
    }

    @Test
    public void enviarExcusa_tipoInvalido_devuelve400() throws Exception {
        String jsonConTipoInvalido = """
            {
                "nombreEmpleado": "Carlos",
                "emailEmpleado": "carlos@empresa.com",
                "legajoEmpleado": 102,
                "descripcion": "No tenía ganas",
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
    public void obtenerExcusasProcesadas_devuelve200() throws Exception {
        mockMvc.perform(get("/api/excusas/procesadas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void obtenerProntuarios_devuelve200() throws Exception {
        mockMvc.perform(get("/api/excusas/prontuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void obtenerNotificaciones_devuelve200() throws Exception {
        mockMvc.perform(get("/api/excusas/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
