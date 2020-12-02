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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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

    @Test
    void should_return_null_when_find_company_employees_by_id_given_not_found_id() {
        //given
        Company company1 = new Company(1, "Company1");
        company1.addEmployee(new Employee(1, "Sam", 20, "Male", 20000));
        company1.addEmployee(new Employee(2, "Ken", 20, "Male", 20000));

        when(this.companyRepository.findAll()).thenReturn(Collections.singletonList(company1));
        when(this.companyRepository.findCompanyById(2)).thenCallRealMethod();

        //when
        List<Employee> employees = this.companyService.findCompanyEmplyeesById(2);

        //then
        assertNull(employees);
    }

    @Test
    void should_return_last_two_companies_when_find_companies_with_pagination_given_companies_3_page_index_2_page_size_2() {
        //given
        Company company1 = new Company(1, "Company1");
        Company company2 = new Company(2, "Company2");
        Company company3 = new Company(3, "Company3");
        Company company4 = new Company(4, "Company4");

        when(this.companyRepository.findAll()).thenReturn(Arrays.asList(company1, company2, company3, company4));
        when(this.companyRepository.findCompaniesWithPagination(2, 2)).thenCallRealMethod();

        //when
        List<Company> companies = this.companyService.findCompaniesWithPagination(2, 2);

        //this
        assertEquals(Arrays.asList(company3, company4), companies);
    }

    @Test
    void should_call_company_repository_save_once_and_return_correct_company_when_add_given_not_existed_company() {
        //given
        Company company = new Company(1, "Company1");

        Employee employee = new Employee(1, "Ken", 19, "Male", 20000);
        company.addEmployee(employee);

        when(this.companyRepository.save(company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.add(company);

        //then
        verify(this.companyRepository, times(1)).save(company);
        assertEquals(company.getId(), returnedCompany.getId());
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(company.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(company.getEmployees(), returnedCompany.getEmployees());
    }

    @Test
    void should_return_null_when_add_given_existed_company() {
        //given
        Company company = new Company(1, "Company1");

        when(this.companyRepository.save(company)).thenReturn(null);

        //when
        Company returnedCompany = this.companyService.add(company);

        //then
        assertNull(returnedCompany);
    }

    @Test
    void should_call_company_repository_update_once_and_return_correct_company_when_update_given_found_company() {
        //given
        Company company = new Company(1, "Company1");

        when(this.companyRepository.update(1, company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.update(1, company);

        //then
        verify(this.companyRepository, times(1)).update(1, company);
        assertEquals(company.getId(), returnedCompany.getId());
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(company.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(company.getEmployees(), returnedCompany.getEmployees());
    }

    @Test
    void should_return_null_when_update_given_not_found_company() {
        //given
        Company company = new Company(1, "Company1");

        when(this.companyRepository.update(1, company)).thenReturn(null);

        //when
        Company returnedCompany = this.companyService.update(1, company);

        //then
        assertNull(returnedCompany);
    }
}
