package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        List<Company> expectedCompanies = Arrays.asList(
            new Company(1, "Company1"),
            new Company(2, "Company2")
        );

        when(this.companyRepository.findAll()).thenReturn(expectedCompanies);

        //when
        List<Company> returnedCompanies = this.companyService.findAll();

        //then
        assertEquals(expectedCompanies, returnedCompanies);
    }

    @Test
    void should_return_correct_company_when_find_company_by_id_given_all_companies() {
        //given
        Company company1 = new Company(1, "Company1");
        Company company2 = new Company(2, "Company2");

        when(this.companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2));
        when(this.companyRepository.findCompanyById(1)).thenCallRealMethod();

        //when
        Company company = this.companyService.findCompanyById(1);

        //then
        assertEquals(company1, company);
    }
}
