package excusasHspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import excusasHspring.dto.EmpleadoRequest;
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
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void registrarEmpleado_valido_devuelve201() throws Exception {
        EmpleadoRequest request = new EmpleadoRequest();
        request.setNombre("Juan Pérez");
        request.setEmail("juan@empresa.com");
        request.setLegajo(1001);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    public void registrarEmpleado_duplicado_devuelve400() throws Exception {
        EmpleadoRequest request = new EmpleadoRequest();
        request.setNombre("Duplicado");
        request.setEmail("dup@empresa.com");
        request.setLegajo(1002);


        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registrarEmpleado_datosInvalidos_devuelve400() throws Exception {
        String jsonInvalido = """
            {
                "nombre": "",
                "email": "no-valido",
                "legajo": null
            }
            """;

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void obtenerEmpleado_existente_devuelve200() throws Exception {
        EmpleadoRequest request = new EmpleadoRequest();
        request.setNombre("Lucía");
        request.setEmail("lucia@empresa.com");
        request.setLegajo(999);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/empleados/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Lucía"));
    }

    @Test
    public void obtenerEmpleado_inexistente_devuelve404() throws Exception {
        mockMvc.perform(get("/api/empleados/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listarEmpleados_devuelve200YLista() throws Exception {
        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
