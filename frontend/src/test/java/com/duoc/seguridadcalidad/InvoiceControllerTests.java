package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BackendService backendService;

    @Test
    void shouldProxyGetInvoices() throws Exception {
  when(backendService.getInvoices("valid-token"))
    .thenReturn(List.of(
      Map.of("id", 1, "appointmentId", 10, "total", 47600.0),
      Map.of("id", 2, "appointmentId", 11, "total", 25000.0)
    ));

  mockMvc.perform(get("/api/invoices")
      .header("Authorization", "Bearer valid-token"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].id").value(1))
    .andExpect(jsonPath("$[1].appointmentId").value(11));
    }

    @Test
    void shouldProxyCreateInvoice() throws Exception {
  when(backendService.createInvoice(
    org.mockito.ArgumentMatchers.eq("valid-token"),
    org.mockito.ArgumentMatchers.eq(10L),
    org.mockito.ArgumentMatchers.anyMap()
  )).thenReturn(Map.of("id", 1, "appointmentId", 10, "total", 47600.0));

        String payload = """
                {
                  "issueDate": "2026-03-28",
                  "vatRate": 0.19,
                  "notes": "Paciente estable. Control en 10 dias.",
                  "items": [
                    {
                      "type": "SERVICE",
                      "description": "Consulta general",
                      "quantity": 1,
                      "unitPrice": 25000
                    },
                    {
                      "type": "MEDICATION",
                      "description": "Antibiotico",
                      "quantity": 2,
                      "unitPrice": 7500
                    }
                  ]
                }
                """;

              mockMvc.perform(post("/api/invoices/appointments/10")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appointmentId").value(10))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(47600.0));
    }

    @Test
                void shouldProxyGetInvoiceByAppointment() throws Exception {
              when(backendService.getInvoiceByAppointmentId("valid-token", 10L))
                .thenReturn(Map.of("id", 1, "appointmentId", 10, "total", 47600.0));

              mockMvc.perform(get("/api/invoices/appointment/10")
                  .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.appointmentId").value(10));
    }

    @Test
                void shouldRequireBearerToken() throws Exception {
              mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isUnauthorized());
    }
}