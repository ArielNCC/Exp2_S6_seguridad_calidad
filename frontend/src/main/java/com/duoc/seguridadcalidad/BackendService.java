package com.duoc.seguridadcalidad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class BackendService {

    private static final Logger log = LoggerFactory.getLogger(BackendService.class);

    private final RestTemplate restTemplate;
    private final String backendBaseUrl;

    public BackendService(RestTemplate restTemplate, @Value("${backend.base-url}") String backendBaseUrl) {
        this.restTemplate = restTemplate;
        this.backendBaseUrl = backendBaseUrl;
    }

    public AuthResponse login(AuthRequest authRequest) {
        log.debug("-> BackendService.login payload={}", authRequest);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(backendBaseUrl + "/login", authRequest, String.class);
            log.debug("Backend login response status={} body={}", response.getStatusCode(), response.getBody());
            String token = response.getBody();
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            log.debug("<- BackendService.login token={}", token);
            return new AuthResponse(token);
        } catch (HttpStatusCodeException ex) {
            log.error("Backend login failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public List<Map<String, Object>> getPatients(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.debug("-> BackendService.getPatients Authorization={}", jwtToken);
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    backendBaseUrl + "/patients",
                    HttpMethod.GET,
                    entity,
                    Map[].class
            );
            log.debug("<- BackendService.getPatients status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getPatients failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> createPatient(String jwtToken, Map<String, Object> patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(patient, headers);

        log.debug("-> BackendService.createPatient Authorization={} patient={}", jwtToken, patient);
        try {
            Map response = restTemplate.postForObject(backendBaseUrl + "/patients", entity, Map.class);
            log.debug("<- BackendService.createPatient response={}", response);
            return response;
        } catch (HttpStatusCodeException ex) {
            log.error("Backend createPatient failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

public List<Map<String, Object>> getAppointments(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);        HttpEntity<Void> entity = new HttpEntity<>(headers);
        log.debug("-> BackendService.getAppointments Authorization={}", jwtToken);
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    backendBaseUrl + "/appointments",
                    HttpMethod.GET,
                    entity,
                    Map[].class
            );
            log.debug("<- BackendService.getAppointments status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getAppointments failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> createAppointment(String jwtToken, Map<String, Object> appointment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(appointment, headers);

        log.debug("-> BackendService.createAppointment Authorization=Bearer {} appointment={}", jwtToken, appointment);
        try {
            Map response = restTemplate.postForObject(backendBaseUrl + "/appointments", entity, Map.class);
            log.debug("<- BackendService.createAppointment response={}", response);
            return response;
        } catch (HttpStatusCodeException ex) {
            log.error("Backend createAppointment failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public List<Map<String, Object>> getPets() {
        log.debug("-> BackendService.getPets");
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    backendBaseUrl + "/pets",
                    HttpMethod.GET,
                    null,
                    Map[].class
            );
            log.debug("<- BackendService.getPets status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getPets failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public List<Map<String, Object>> getAvailablePets() {
        log.debug("-> BackendService.getAvailablePets");
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    backendBaseUrl + "/pets/available",
                    HttpMethod.GET,
                    null,
                    Map[].class
            );
            log.debug("<- BackendService.getAvailablePets status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getAvailablePets failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public List<Map<String, Object>> searchPets(String species, String gender, String location, Integer age, String status) {
        String uri = UriComponentsBuilder
            .fromUriString(backendBaseUrl + "/pets/search")
                .queryParamIfPresent("species", java.util.Optional.ofNullable(species))
                .queryParamIfPresent("gender", java.util.Optional.ofNullable(gender))
                .queryParamIfPresent("location", java.util.Optional.ofNullable(location))
                .queryParamIfPresent("age", java.util.Optional.ofNullable(age))
                .queryParamIfPresent("status", java.util.Optional.ofNullable(status))
                .build()
                .toUriString();

        log.debug("-> BackendService.searchPets uri={}", uri);
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    Map[].class
            );
            log.debug("<- BackendService.searchPets status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend searchPets failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> createPet(String jwtToken, Map<String, Object> pet) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(pet, headers);

        log.debug("-> BackendService.createPet Authorization=Bearer {} pet={}", jwtToken, pet);
        try {
            Map response = restTemplate.postForObject(backendBaseUrl + "/pets", entity, Map.class);
            log.debug("<- BackendService.createPet response={}", response);
            return response;
        } catch (HttpStatusCodeException ex) {
            log.error("Backend createPet failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> updatePet(String jwtToken, Integer id, Map<String, Object> pet) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(pet, headers);

        log.debug("-> BackendService.updatePet Authorization=Bearer {} id={} pet={}", jwtToken, id, pet);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    backendBaseUrl + "/pets/" + id,
                    HttpMethod.PUT,
                    entity,
                    Map.class
            );
            log.debug("<- BackendService.updatePet status={} body={}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Backend updatePet failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> deletePet(String jwtToken, Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.debug("-> BackendService.deletePet Authorization=Bearer {} id={}", jwtToken, id);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    backendBaseUrl + "/pets/" + id,
                    HttpMethod.DELETE,
                    entity,
                    Map.class
            );
            log.debug("<- BackendService.deletePet status={} body={}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Backend deletePet failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public List<Map<String, Object>> getInvoices(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.debug("-> BackendService.getInvoices Authorization=Bearer {}", jwtToken);
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    backendBaseUrl + "/invoices",
                    HttpMethod.GET,
                    entity,
                    Map[].class
            );
            log.debug("<- BackendService.getInvoices status={} body={}", response.getStatusCode(), Arrays.toString(response.getBody()));
            if (response.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getInvoices failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> getInvoiceById(String jwtToken, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.debug("-> BackendService.getInvoiceById Authorization=Bearer {} id={}", jwtToken, id);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    backendBaseUrl + "/invoices/" + id,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );
            log.debug("<- BackendService.getInvoiceById status={} body={}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getInvoiceById failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> getInvoiceByAppointmentId(String jwtToken, Long appointmentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.debug("-> BackendService.getInvoiceByAppointmentId Authorization=Bearer {} appointmentId={}", jwtToken, appointmentId);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    backendBaseUrl + "/invoices/appointment/" + appointmentId,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );
            log.debug("<- BackendService.getInvoiceByAppointmentId status={} body={}", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Backend getInvoiceByAppointmentId failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }

    public Map<String, Object> createInvoice(String jwtToken, Long appointmentId, Map<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        log.debug("-> BackendService.createInvoice Authorization=Bearer {} appointmentId={} payload={}", jwtToken, appointmentId, request);
        try {
            Map response = restTemplate.postForObject(
                    backendBaseUrl + "/invoices/appointments/" + appointmentId,
                    entity,
                    Map.class
            );
            log.debug("<- BackendService.createInvoice response={}", response);
            return response;
        } catch (HttpStatusCodeException ex) {
            log.error("Backend createInvoice failed status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }
}
