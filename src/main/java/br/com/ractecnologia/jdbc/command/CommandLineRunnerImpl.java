package br.com.ractecnologia.jdbc.command;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.ractecnologia.jdbc.entity.Empresa;
import br.com.ractecnologia.jdbc.enums.TipoEmpresa;
import br.com.ractecnologia.jdbc.service.EmpresaService;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner{
	

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmpresaService empresaService;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		empresa.setAtivo(true);
		empresa.setCnpj("168914");
		empresa.setDataAtualizacao(new Timestamp(new Date().getTime() ));
		empresa.setDataCriacao(new Date());
		empresa.setDescricao("RTRTRT");
		empresa.setRazaoSocial("RAC Tecnologia11");
		empresa.setTipo(TipoEmpresa.J);
		empresa.setVersion(2);
		
		Empresa empresa1 = empresaService.findById(4L);
		log.info(" {}",empresa1);
		
		new Thread(
				() -> log.info(" {}",empresaService.findAll())).start();
			
		
		 empresa1 = empresaService.findById(4L);
		log.info(" {}",empresa1);
		
		
		new Thread(
				() -> log.info(" {}",empresaService.findAll())).start();
			
		 empresa1 = empresaService.findById(4L);
		log.info(" {}",empresa1);
		
		
		new Thread(
			() -> log.info(" {}",empresaService.findAll())).start();
		
		new Thread(
				() -> log.info(" {}",empresaService.findAll())).start();
		
		new Thread(
				() -> log.info(" {}",empresaService.findAll())).start();
		
	}

}
