package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        List<Company> expectedCompanies = Arrays.asList(
            new Company("Company1"),
            new Company("Company2")
        );

        when(this.companyRepository.findAll()).thenReturn(expectedCompanies);

        //when
        List<Company> returnedCompanies = this.companyService.findAll();

        //then
        assertEquals(expectedCompanies, returnedCompanies);
    }

    @Test
    void should_return_correct_company_when_find_company_by_id_given_found_id() throws CompanyNotFoundException {
        //given
        Company company = new Company("Company1");

        when(this.companyRepository.findById("1")).thenReturn(Optional.of(company));

        //when
        Company returnedCompany = this.companyService.findCompanyById("1");

        //then
        assertEquals(company, returnedCompany);
    }

    @Test
    void should_throw_company_not_found_exception_when_find_company_by_id_given_not_found_id() {
        //given
        when(this.companyRepository.findById("1")).thenReturn(Optional.empty());

        //then
        assertThrows(CompanyNotFoundException.class, () -> {
            //when
            this.companyService.findCompanyById("1");
        });
    }

    @Test
    void should_return_correct_employees_when_find_company_employees_by_id_given_found_id() throws CompanyNotFoundException {
        //given
        Company company = new Company("Company1");

        List<Employee> employees = Arrays.asList(
                new Employee("Sam", 20, "Male", 200000, "1"),
                new Employee("Ken", 20, "Male", 200000, "1")
        );

        when(this.companyRepository.findById("1")).thenReturn(Optional.of(company));
        when(this.employeeRepository.findAllByCompanyId("1")).thenReturn(employees);

        //when
        List<Employee> returnedEmployees = this.companyService.findCompanyEmployeesById("1");

        //then
        assertEquals(employees, returnedEmployees);
    }

    @Test
    void should_throw_company_not_found_exception_when_find_company_employees_by_id_given_not_found_id() {
        //given
        when(this.companyRepository.findById("1")).thenReturn(Optional.empty());

        //then
        assertThrows(CompanyNotFoundException.class, () -> {
            //when
            this.companyService.findCompanyEmployeesById("1");
        });
    }

    @Test
    void should_return_last_two_companies_when_find_companies_with_pagination_given_companies_2_page_1_page_size_2() {
        //given
        List<Company> companies = Arrays.asList(
                new Company("Company1"),
                new Company("Company2")
        );

        Pageable pageable = PageRequest.of(1, 2);
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
        Company company = new Company("Company1");

        when(this.companyRepository.insert(company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.add(company);

        //then
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
    }

    @Test
    void should_return_replaced_company_when_replace_given_found_company_id() throws CompanyNotFoundException {
        //given
        Company company = new Company("Company1");

        when(this.companyRepository.existsById("1")).thenReturn(true);
        when(this.companyRepository.save(company)).thenReturn(company);

        //when
        Company returnedCompany = this.companyService.replace("1", company);

        //then
        assertEquals(company.getCompanyName(), returnedCompany.getCompanyName());
    }

    @Test
    void should_throw_company_not_found_exception_when_replace_given_not_found_company_id() {
        //given
        Company company = new Company("Company1");

        when(this.companyRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(CompanyNotFoundException.class, () -> {
           //when
            this.companyService.replace("1", company);
        });
    }

    @Test
    void should_call_company_repository_delete_by_id_once_when_delete_given_found_company_id() throws CompanyNotFoundException {
        //given
        when(this.companyRepository.existsById("1")).thenReturn(true);

        //when
        this.companyService.delete("1");

        //then
        verify(this.companyRepository, times(1)).deleteById("1");
    }

    @Test
    void should_throw_company_not_found_exception_when_delete_given_not_found_company_id() {
        //given
        when(this.companyRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(CompanyNotFoundException.class, () -> {
           //then
            this.companyService.delete("1");
        });
    }
}
