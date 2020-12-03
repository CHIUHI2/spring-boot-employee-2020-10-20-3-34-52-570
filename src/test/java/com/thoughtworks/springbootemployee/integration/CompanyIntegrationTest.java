package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        this.companyRepository.deleteAll();
    }

    @Test
    void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        Company company = new Company("Company");
        this.companyRepository.save(company);

        //when
        //then
        this.mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("Company"))
                .andExpect(jsonPath("$[0].employeesNumber").value(0))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    void should_return_last_two_companies_when_get_all_with_pagination_given_companies_4_and_page_1_and_page_size_2() throws Exception {
        //given
        Company company1 = new Company("Company1");
        this.companyRepository.save(company1);

        Company company2 = new Company("Company2");
        this.companyRepository.save(company2);

        Company company3 = new Company("Company3");
        this.companyRepository.save(company3);

        Company company4 = new Company("Company4");
        this.companyRepository.save(company4);

        //when
        //then
        this.mockMvc.perform(get("/companies")
                .param("page", "1")
                .param("pageSize", "2")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("Company3"))
                .andExpect(jsonPath("$[0].employeesNumber").value(0))
                .andExpect(jsonPath("$[0].employees").isEmpty())
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].companyName").value("Company4"))
                .andExpect(jsonPath("$[1].employeesNumber").value(0))
                .andExpect(jsonPath("$[1].employees").isEmpty());
    }

    @Test
    void should_return_company_when_get_one_given_found_company_id() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);

        //when
        //then
        this.mockMvc.perform(get("/companies/" + addedCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addedCompany.getId()))
                .andExpect(jsonPath("$.companyName").value("Company"))
                .andExpect(jsonPath("$.employeesNumber").value(0))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_return_404_when_get_one_given_not_found_company_id() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);
        this.companyRepository.deleteAll();

        //when
        //then
        this.mockMvc.perform(get("/companies/" + addedCompany.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_added_company_when_add_given_company() throws Exception {
        //given
        JSONObject requestBody = new JSONObject();
        requestBody.put("companyName", "Company");

        //when
        //then
        this.mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString())
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("Company"))
                .andExpect(jsonPath("$.employeesNumber").value(0))
                .andExpect(jsonPath("$.employees").isEmpty());

        List<Company> companies = this.companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("Company", companies.get(0).getCompanyName());
        assertEquals(0, companies.get(0).getEmployeesNumber());
        assertEquals(new ArrayList<>(), companies.get(0).getEmployees());
    }

    @Test
    void should_return_updated_company_when_replace_given_found_id_and_company() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);

        JSONObject requestBody = new JSONObject();
        requestBody.put("companyName", "Company1");

        //when
        //then
        this.mockMvc.perform(put("/companies/" + addedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString())
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addedCompany.getId()))
                .andExpect(jsonPath("$.companyName").value("Company1"))
                .andExpect(jsonPath("$.employeesNumber").value(0))
                .andExpect(jsonPath("$.employees").isEmpty());

        List<Company> companies = this.companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals(addedCompany.getId(), companies.get(0).getId());
        assertEquals("Company1", companies.get(0).getCompanyName());
        assertEquals(0, companies.get(0).getEmployeesNumber());
        assertEquals(new ArrayList<>(), companies.get(0).getEmployees());
    }

    @Test
    void should_return_404_when_replace_given_not_found_id_and_company() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);
        this.companyRepository.deleteAll();

        JSONObject requestBody = new JSONObject();
        requestBody.put("companyName", "Company1");

        //when
        //then
        this.mockMvc.perform(put("/companies/" + addedCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString())
        ).andExpect(status().isNotFound());

        List<Company> companies = this.companyRepository.findAll();
        assertEquals(0, companies.size());
    }

    @Test
    void should_return_204_when_delete_given_found_id() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);

        //when
        //then
        this.mockMvc.perform(delete("/companies/" + addedCompany.getId()))
                .andExpect(status().isNoContent());

        List<Company> companies = this.companyRepository.findAll();
        assertEquals(0, companies.size());
    }

    @Test
    void should_return_404_when_delete_given_found_id() throws Exception {
        //given
        Company company = new Company("Company");
        Company addedCompany = this.companyRepository.save(company);
        this.companyRepository.deleteAll();

        //when
        //then
        this.mockMvc.perform(delete("/companies/" + addedCompany.getId()))
                .andExpect(status().isNotFound());
    }
}
