package com.bancoazteca.bdm.cliente.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import com.mongodb.MongoClientURI;

/**
 * @author B170830 JECS
 *
 */
@Configuration
public class SpringMongoConfig {

	/**
	 * @return MongoClientURI
	 */
	public MongoClientURI mongoDos() {
		return new MongoClientURI("mongodb://UsrBazDigital:B4zD1g1T4l20164$78@10.63.32.180:27021/BAZBDMDESA");
	}

	/**
	 * @return MongoTemplate
	 * @throws UnknownHostException
	 * @throws Exception
	 */
	@Primary
	@Bean
	public MongoTemplate primaryMongoTemplate() throws UnknownHostException {
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoDos());
		MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory);
		((MappingMongoConverter) mongoTemplate.getConverter()).setTypeMapper(new DefaultMongoTypeMapper(null));
		return mongoTemplate;
	}
}
