package com.intuitcraft.onboarding.controller;

import com.intuitcraft.onboarding.dto.Driver;
import com.intuitcraft.onboarding.service.DriverProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverProfileController.class)
public class DriveProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverProfileService driverProfileService;

    String driver = "{\"firstName\":\"MS\",\"surName\":\"Dhoni\",\"email\":\"msd7@gmail.com\",\"phoneNumber\":\"9123919249\",\"langPreference\":\"en-IN\",\"city\":\"Bangalore\"}";


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createProfile() throws Exception {
        Mockito.when(driverProfileService.createDriverProfileAndOnboardStatus(any(Driver.class))).thenReturn(123L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        "/profile").accept(MediaType.APPLICATION_JSON).content(driver)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"id\":123}", response.getContentAsString());
    }

    @Test
    public void getProfileById() throws Exception {

        Mockito.when(driverProfileService.getDriverById(any(Long.class))).thenReturn(new Driver());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        "/profile/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("{\"firstName\":null,\"surName\":null,\"email\":null,\"phoneNumber\":null,\"langPreference\":null,\"city\":null}", result.getResponse().getContentAsString());
    }
}
