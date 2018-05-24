package com.waben.stock.datalayer.futures.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class OrderSequenceGenerator implements IdentifierGenerator, Configurable {

	public static final String TYPE = "com.waben.stock.datalayer.futures.generators.OrderSequenceGenerator";

	private static final String SQL = "select nextval('%s')";

	public static final String SEQUENCE_PARAM = "sequence_name";

	public static final String CRYPT_PARAM = "crypt_key";

	private String sequenceName;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		sequenceName = determineSequenceName(params);
	}

	@Override
	public Serializable generate(SessionImplementor session, Object obj) {
		sequenceName = StringUtils.defaultIfBlank(sequenceName, obj.getClass().getSimpleName().toLowerCase());
		StringBuffer sb = new StringBuffer();
		String currentDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
		long nextSeq = getNextSeq(session, sequenceName);
		sb.append(currentDate).append(String.format("%05d", nextSeq));
		return sb.toString();
	}

	public long getNextSeq(SessionImplementor session, String name) {
		Connection connection = session.connection();
		Long sequenceValue = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL, name));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				sequenceValue = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sequenceValue;
	}

	protected String determineSequenceName(Properties params) {
		return ConfigurationHelper.getString(SEQUENCE_PARAM, params, null);
	}
}
