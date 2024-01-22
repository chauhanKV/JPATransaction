package com.example.JPATransaction.service;


import com.example.JPATransaction.EmployeeDetailDTO;
import com.example.JPATransaction.entity.Address;
import com.example.JPATransaction.entity.Employee;
import com.example.JPATransaction.exception.CardNotCreatedException;
import com.example.JPATransaction.exception.laptopNotAllocatedException;
import com.example.JPATransaction.repo.IEmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {

    private static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private IEmployeeRepo employeeRepo;

    private boolean cardCreated = false;
    private boolean laptopNotAllocated = true;

    // This @Transactional annotation makes this method to be executed in a transaction.
    // The transaction is committed only after the entire method is executed.
    @Transactional(rollbackOn = {CardNotCreatedException.class}, dontRollbackOn = {laptopNotAllocatedException.class})
    public Employee createEmployee(EmployeeDetailDTO employeeDetailDTO) throws CardNotCreatedException, laptopNotAllocatedException {
        Employee employee = Employee.builder().name(employeeDetailDTO.getName()).email(employeeDetailDTO.getEmail()).build();
        Address address = Address.builder().line1(employeeDetailDTO.getLine1()).line2(employeeDetailDTO.getLine2()).city(employeeDetailDTO.getCity()).build();
        employee.setAddress(address);
        Employee emp = employeeRepo.save(employee);
        // Service call for card creation
        // rollbackOn - rollback transaction when this exception occurs.
        if(!cardCreated)
        {
            throw new CardNotCreatedException();
        }
        // dontRollBackOn - transaction will not be rollback when this exception occurs
        if(laptopNotAllocated)
        {
            throw new laptopNotAllocatedException();
        }
        LOGGER.info("Created Employee : {}", emp);
        return emp;
    }

    public Employee getEmployees(Long id)
    {
        return employeeRepo.findById(id).orElse(null);
    }

    public Employee getEmployeeByEmail(String email)
    {
        return employeeRepo.findByEmail(email);
    }

    public Employee deleteEmployee(Long id)
    {
        Employee emp = getEmployees(id);
        employeeRepo.delete(emp);
        return emp;
    }

}
