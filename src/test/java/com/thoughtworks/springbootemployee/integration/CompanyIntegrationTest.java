package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
