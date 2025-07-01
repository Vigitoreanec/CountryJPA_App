package org.top.countrydirectoryapp.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.top.countrydirectoryapp.model.Country;
import org.top.countrydirectoryapp.model.CountryNotFoundException;
import org.top.countrydirectoryapp.model.CountryScenario;
import org.top.countrydirectoryapp.model.CountryStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CountryScenario scenario;
    private CountryStorage storageMock;

    @BeforeEach
    public void setUp() {
        storageMock = Mockito.mock(CountryStorage.class, invocation -> {
            throw new UnsupportedOperationException(invocation.getMethod().getName());
        });
        scenario = new CountryScenario(storageMock);
    }

    @Nested
    public class ListAllTest {
        @Test
        void getAllCountriesTest() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/api/country"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(4));

        }

        @Test
        void getCountryTest() throws Exception {
            String originCode = "JPN";
            Country expected =
                    new Country("", "", "", originCode, "", 1L, 1L);
            Mockito.doReturn(Optional.of(expected))
                    .when(storageMock)
                    .get(originCode);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/country/" + originCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shortName").isString())
                    .andExpect(jsonPath("$.fullName").isString())
                    .andExpect(jsonPath("$.isoAlpha2").isString())
                    .andExpect(jsonPath("$.isoAlpha3").value(originCode))
                    .andExpect(jsonPath("$.isoNumeric").isString())
                    .andExpect(jsonPath("$.population").isNumber())
                    .andExpect(jsonPath("$.square").isNumber());

        }

        @Test
        void storeCountryTest() throws Exception {
            Country origin = new Country("", "", "JJ", "", "", 1L, 1L);

            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(origin.isValid());
            // конфигурация Mock
            Mockito.doNothing().when(storageMock).store(origin);
            scenario.store(origin);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/country")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "shortName": "Canada1",
                                        "fullName": "Canada1",
                                        "isoAlpha2": "CC",
                                        "isoAlpha3": "CCC",
                                        "isoNumeric": "123",
                                        "population": 38005238,
                                        "square": 9984670
                                    }"""))

                    .andExpect(status().isCreated());
            Mockito.verify(storageMock).store(any(Country.class));
        }

        @Test
        void editCountryTest() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.patch("/api/country")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "shortName": "Canada",
                                      "fullName": "Canada11",
                                      "isoAlpha2": "CA",
                                      "isoAlpha3": "CAN",
                                      "isoNumeric": "124",
                                      "population": 126476461,
                                      "square": 377975
                                    }"""))

                    .andExpect(status().isOk());

        }

        @Test
        void deleteCountryTest() throws Exception {
            String originCode = "JJ";
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/country/" + originCode)
                            .contentType(MediaType.APPLICATION_JSON))

                    .andExpect(status().isNoContent());
        }

        @Test
        void handleAirportNotFound() throws Exception {
            String originCode = "XX";
            Country expected =
                    new Country("", "", "", originCode, "", 1L, 1L);
            Mockito.doReturn(Optional.of(expected))
                    .when(storageMock)
                    .get(originCode);
            //Mockito.when(storageMock.get("XX")).doThrow(new CountryNotFoundException("XX"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/country/XX"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("CountryNotFoundException"))
                    .andExpect(jsonPath("$.details").value("Country with code XX not found"));

        }

        @Test
        void handleDuplicatedCode() throws Exception {
            Country origin = new Country("Japan1", "Japan", "JP", "JPN", "3922", 1L, 1L);
            Mockito.doReturn(Optional.empty())
                    .when(storageMock)
                    .get(origin.isValid());
            // конфигурация Mock
            Mockito.doNothing().when(storageMock).store(origin);
            scenario.store(origin);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/country")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                     {
                                      "shortName": "Japan1",
                                      "fullName": "Japan",
                                      "isoAlpha2": "JP",
                                      "isoAlpha3": "JPN",
                                      "isoNumeric": "3922",
                                      "population": 126476461,
                                      "square": 377975
                                    }"""))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value("DuplicatedCodeException"))
                    .andExpect(jsonPath("$.details").value("Country with code JP already exists"));

            Mockito.verify(storageMock).store(origin);
        }

        @Test
        void handleInvalidCode() throws Exception {


            mockMvc.perform(MockMvcRequestBuilders.patch("/api/country")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                     {
                                      "shortName": "",
                                      "fullName": "",
                                      "isoAlpha2": "",
                                      "isoAlpha3": "asd",
                                      "isoNumeric": "",
                                      "population": 126476461,
                                      "square": 377975
                                    }"""))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("InvalidCodeException"))
                    .andExpect(jsonPath("$.details").value("Code Country is invalid"));


        }
    }
}