package br.com.ractecnologia.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ractecnologia.jdbc.dao.EmpresaDAO;
import br.com.ractecnologia.jdbc.entity.Empresa;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaDAO empresaDao;
	
	public void save(Empresa empresa) {
		empresaDao.save(empresa);
	}
	
	public void update(Empresa empresa) {
		empresaDao.update(empresa);
	}
	
	public Empresa findById(Long id) {
		return empresaDao.findById(id);
	}
	
	public List<Empresa> findAll() {
		return empresaDao.findAll();
	}

}
