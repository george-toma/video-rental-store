/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.video.rental.store.dto.mapper;

import com.video.rental.store.domain.Customer;
import com.video.rental.store.dto.CustomerDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author spykee
 */
@Component(value = "customer")
public class CustomerMapper implements Mapper<CustomerDto, Customer> {

    @Override
    public CustomerDto convertTo(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

}
