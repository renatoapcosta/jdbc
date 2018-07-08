package br.com.ractecnologia.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.ractecnologia.jdbc.entity.Empresa;
import br.com.ractecnologia.jdbc.enums.TipoEmpresa;

@Repository
public class EmpresaDAO {

	private static final String INSERT = "INSERT INTO empresa " + "( id, razao_social, cnpj, tipo, ativo, descricao, "
			+ "data_create, data_update, version) VALUES " + "(null, ?,?,?,?,?,?,?,?)";

	private static final String UPDATE = "UPDATE empresa SET razao_social=?, cnpj=?, tipo=?, ativo=?, descricao=?, "
			+ "data_create=?, data_update=?, version=? WHERE id=? ";

	private static final String FIND_BY_ID = "SELECT id, razao_social, cnpj, tipo, ativo, descricao, " + 
			" data_create, data_update, version FROM empresa WHERE id =?";

	private static final String FIND_ALL = "SELECT id, razao_social, cnpj, tipo, ativo, descricao, " + 
			" data_create, data_update, version FROM empresa";
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	private SingleConnectionDataSource dataSource;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DataSource dataSource1;
	
	

	public void save(Empresa empresa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, empresa.getRazaoSocial());
			stmt.setString(2, empresa.getCnpj());
			stmt.setString(3, empresa.getTipo().name());
			stmt.setBoolean(4, empresa.getAtivo());
			stmt.setString(5, empresa.getDescricao());
			stmt.setDate(6, new Date(empresa.getDataCriacao().getTime()));
			stmt.setTimestamp(7, empresa.getDataAtualizacao());
			stmt.setInt(8, empresa.getVersion());
			

			stmt.execute();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	empresa.setId(generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {
			log.error("", e);
		} finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}

	}

	public void update(Empresa empresa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setString(1, empresa.getRazaoSocial());
			stmt.setString(2, empresa.getCnpj());
			stmt.setString(3, empresa.getTipo().name());
			stmt.setBoolean(4, empresa.getAtivo());
			stmt.setString(5, empresa.getDescricao());
			stmt.setDate(6, new Date(empresa.getDataCriacao().getTime()));
			stmt.setTimestamp(7, empresa.getDataAtualizacao());
			stmt.setInt(8, empresa.getVersion());
			stmt.setLong(9, empresa.getId());
			
			stmt.execute();
			

		} catch (SQLException e) {
			log.error("", e);
		} finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
	}

	public Empresa findById(Long id) {
		Empresa empresa = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(FIND_BY_ID);
			
			stmt.setLong(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
			
				empresa = new Empresa();		
				empresa.setId(rs.getLong("id")); 
				empresa.setRazaoSocial(rs.getString("razao_social"));
				empresa.setCnpj(rs.getString("cnpj"));
				empresa.setTipo(TipoEmpresa.J);
				empresa.setAtivo(rs.getBoolean("ativo"));
				empresa.setDataCriacao(rs.getDate("data_create"));
				empresa.setDataAtualizacao(rs.getTimestamp("data_update"));
				empresa.setDescricao(rs.getString("razao_social"));
				empresa.setVersion(rs.getInt("version"));
				
				
			}
			

		} catch (SQLException e) {
			log.error("", e);
		} finally {
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return empresa;
	}
	
	public List<Empresa> findAll() {
		List<Empresa> empresas = new ArrayList<Empresa>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Connection conn2 = dataSource1.getConnection();
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(FIND_ALL);
						
			ResultSet rs = stmt.executeQuery();
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(rs.next()) {
			
				Empresa empresa = new Empresa();		
				empresa.setId(rs.getLong("id")); 
				empresa.setRazaoSocial(rs.getString("razao_social"));
				empresa.setCnpj(rs.getString("cnpj"));
				empresa.setTipo(TipoEmpresa.J);
				empresa.setAtivo(rs.getBoolean("ativo"));
				empresa.setDataCriacao(rs.getDate("data_create"));
				empresa.setDataAtualizacao(rs.getTimestamp("data_update"));
				empresa.setDescricao(rs.getString("razao_social"));
				empresa.setVersion(rs.getInt("version"));
				
				empresas.add(empresa);
			}
			
			conn2.close();
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			
			try {
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return empresas;
	}

}
