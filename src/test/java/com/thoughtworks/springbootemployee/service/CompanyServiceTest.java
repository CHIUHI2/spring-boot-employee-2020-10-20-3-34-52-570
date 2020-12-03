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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
            new Company("1", "Company1"),
            new Company("2", "Company2")
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
        Optional<Company> company = Optional.of(new Company("1", "Company1"));

        when(this.companyRepository.findById("1")).thenReturn(company);

        //when
        Optional<Company> returnedCompany = this.companyService.findCompanyById("1");

        //then
        assertEquals(company, returnedCompany);
    }

    @Test
    void should_return_null_when_find_company_by_id_given_not_found_id() {
        //given
        when(this.companyRepository.findById("1")).thenReturn(Optional.empty());

        //when
        Optional<Company> company = this.companyService.findCompanyById("1");

        //then
        assertEquals(Optional.empty(), company);
    }

    @Test
    void should_return_correct_employees_when_find_company_employees_by_id_given_found_id() {
        //given
        Company company = new Company("1", "Company1");

        Employee employee1 = new Employee("Sam", 20, "Male", 200000);
        company.addEmployee(employee1);

        Employee employee2 = new Employee("Ken", 20, "Male", 200000);
        company.addEmployee(employee2);

        when(this.companyRepository.findById("1")).thenReturn(Optional.of(company));

        //when
        List<Employee> employees = this.companyService.findCompanyEmployeesById("1");

        //then
        assertEquals(Arrays.asList(employee1, employee2), employees);
    }

    @Test
    void should_return_null_when_find_company_employees_by_id_given_not_found_id() {
        //given
        when(this.companyRepository.findById("1")).thenReturn(Optional.empty());

        //when
        List<Employee> employees = this.companyService.findCompanyEmployeesById("1");

        //then
        assertNull(employees);
    }

    @Test
    void should_return_last_two_companies_when_find_companies_with_pagination_given_companies_3_page_index_2_page_size_2() {
        //given
        List<Company> companies = Arrays.asList(
                new Company("3", "Company3"),
                new Company("4", "Company4")
        );

        Pageable pageable = PageRequest.of(2, 2);
        Page<Company> companyPage = new PageImpl<>(companies, pageable, companies.size());

        when(this.companyRepository.findAll(pageable)).thenReturn(companyPage);

        //when
        Page<Company> returnedCompanyPage = this.companyService.findAllWithPagination(pageable);

        //this
        assertEquals(companyPage, returnedCompanyPage);
    }

    @Test
    void should_return_correct_company_when_add_given_not_existed_company() {
        //given
        Company company = new Company("1", "Company1");

        Employee employee = new Employee("Ken", 20, "Male", 200000);
        company.addEmployee(employee);

        when(this.companyRepository.insert(company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.add(company);

        //then
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(company.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(company.getEmployees(), returnedCompany.getEmployees());
    }

    @Test
    void should_return_null_when_add_given_existed_company() {
        //given
        Company company = new Company("1", "Company1");

        when(this.companyRepository.insert(company)).thenReturn(null);

        //when
        Company returnedCompany = this.companyService.add(company);

        //then
        assertNull(returnedCompany);
    }

    @Test
    void should_return_correct_company_when_update_given_found_company() {
        //given
        Company company = new Company("1", "Company1");

        when(this.companyRepository.existsById("1")).thenReturn(true);
        when(this.companyRepository.save(company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.update("1", company);

        //then
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(company.getEmployeesNumber(), returnedCompany.getEmployeesNumber());
        assertEquals(company.getEmployees(), returnedCompany.getEmployees());
    }

    @Test
    void should_return_null_when_update_given_not_found_company() {
        //given
        Company company = new Company("1", "Company1");

        when(this.companyRepository.existsById("1")).thenReturn(false);

        //when
        Company returnedCompany = this.companyService.update("1", company);

        //then
        assertNull(returnedCompany);
    }

    @Test
    void should_return_true_when_delete_given_found_company() {
        //given
        when(this.companyRepository.existsById("1")).thenReturn(true);

        //when
        boolean result = this.companyService.delete("1");

        //then
        assertTrue(result);
    }

    @Test
    void should_return_false_when_delete_given_not_found_company() {
        //given
        when(this.companyRepository.existsById("1")).thenReturn(false);

        //when
        boolean result = this.companyService.delete("1");

        //then
        assertFalse(result);
    }
}
