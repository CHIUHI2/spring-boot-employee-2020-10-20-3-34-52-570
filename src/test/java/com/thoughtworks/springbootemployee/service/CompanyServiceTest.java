package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
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
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void should_return_correct_company_when_find_company_by_id_given_found_id() {
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

    @Test
    void should_return_null_when_find_company_by_id_given_not_found_id() {
        //given
        Company company1 = new Company(1, "Company1");
        Company company2 = new Company(2, "Company2");

        when(this.companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2));
        when(this.companyRepository.findCompanyById(3)).thenCallRealMethod();

        //when
        Company company = this.companyService.findCompanyById(3);

        //then
        assertNull(company);
    }

    @Test
    void should_return_correct_employees_when_find_company_employees_by_id_given_found_id() {
        //given
        Company company1 = new Company(1, "Company1");

        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        company1.addEmployee(employee1);

        Employee employee2 = new Employee(2, "Ken", 20, "Male", 20000);
        company1.addEmployee(employee2);

        Company company2 = new Company(2, "Company2");

        when(this.companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2));
        when(this.companyRepository.findCompanyById(1)).thenCallRealMethod();

        //when
        List<Employee> employees = this.companyService.findCompanyEmplyeesById(1);

        //then
        assertEquals(Arrays.asList(employee1, employee2), employees);
    }
}
