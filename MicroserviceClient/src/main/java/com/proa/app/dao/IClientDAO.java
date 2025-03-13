package com.proa.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.proa.app.entities.Client;

public interface IClientDAO extends CrudRepository<Client, Long>{

}
