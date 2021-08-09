package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import java.time.LocalDate;

/**
* The type Employee
*
* @author laziz
* @since 2021-08-09
*/
public class Employee extends BaseDataContract {

    public static final String EMPLOYEE = "employee";

    public static final String EMPLOYEE_ID = "employee_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(EMPLOYEE_ID)
    private Long employeeId;

    public static final String FIRST_NAME = "first_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(FIRST_NAME)
    private String firstName;

    public static final String LAST_NAME = "last_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(LAST_NAME)
    private String lastName;

    public static final String EMAIL = "email";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(EMAIL)
    private String email;

    public static final String PHONE_NUMBER = "phone_number";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(PHONE_NUMBER)
    private String phoneNumber;

    public static final String HIRE_DATE = "hire_date";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HIRE_DATE)
    private LocalDate hireDate;

    public static final String SALARY = "salary";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(SALARY)
    private Long salary;

    public static final String COMMISSION_PCT = "commission_pct";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(COMMISSION_PCT)
    private Long commissionPct;

    public static final String DEPARTMENT_ID = "department_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(DEPARTMENT_ID)
    private Long departmentId;

    /**
    * employeeId getter.
    *
    * @return the employeeId
    */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
    * * employeeId setter.
    *
    * @param employeeId the employeeId to set
    * @return this
    */
    public Employee setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    /**
    * firstName getter.
    *
    * @return the firstName
    */
    public String getFirstName() {
        return this.firstName;
    }

    /**
    * firstName setter.
    *
    * @param firstName the firstName to set
    * @return this
    */
    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
    * lastName getter.
    *
    * @return the lastName
    */
    public String getLastName() {
        return this.lastName;
    }

    /**
    * lastName setter.
    *
    * @param lastName the lastName to set
    * @return this
    */
    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
    * email getter.
    *
    * @return the email
    */
    public String getEmail() {
        return this.email;
    }

    /**
    * email setter.
    *
    * @param email the email to set
    * @return this
    */
    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
    * phoneNumber getter.
    *
    * @return the phoneNumber
    */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
    * phoneNumber setter.
    *
    * @param phoneNumber the phoneNumber to set
    * @return this
    */
    public Employee setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
    * hireDate getter.
    *
    * @return the hireDate
    */
    public LocalDate getHireDate() {
        return this.hireDate;
    }

    /**
    * hireDate setter.
    *
    * @param hireDate the hireDate to set
    * @return this
    */
    public Employee setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    /**
    * salary getter.
    *
    * @return the salary
    */
    public Long getSalary() {
        return this.salary;
    }

    /**
    * salary setter.
    *
    * @param salary the salary to set
    * @return this
    */
    public Employee setSalary(Long salary) {
        this.salary = salary;
        return this;
    }

    /**
    * commissionPct getter.
    *
    * @return the commissionPct
    */
    public Long getCommissionPct() {
        return this.commissionPct;
    }

    /**
    * commissionPct setter.
    *
    * @param commissionPct the commissionPct to set
    * @return this
    */
    public Employee setCommissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
        return this;
    }

    /**
    * departmentId getter.
    *
    * @return the departmentId
    */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
    * departmentId setter.
    *
    * @param departmentId the departmentId to set
    * @return this
    */
    public Employee setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phoneNumber, hireDate, salary, commissionPct, departmentId,  employeeId);
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#equals(java.lang.Object)
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Employee other = (Employee) obj;
        return
            Objects.equals(firstName, other.firstName) &&
            Objects.equals(lastName, other.lastName) &&
            Objects.equals(email, other.email) &&
            Objects.equals(phoneNumber, other.phoneNumber) &&
            Objects.equals(hireDate, other.hireDate) &&
            Objects.equals(salary, other.salary) &&
            Objects.equals(commissionPct, other.commissionPct) &&
            Objects.equals(departmentId, other.departmentId) &&
            Objects.equals(employeeId, other.employeeId);
    }
}