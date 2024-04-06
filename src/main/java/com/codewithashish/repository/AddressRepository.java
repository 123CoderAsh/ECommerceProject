package com.codewithashish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithashish.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
