package com.successfactors.redis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class SpringRedisExample {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringRedisConfig.class);
		try {
			RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
			//sampleValueOperation(redisTemplate);

			//Sample Redis Hash Operation Example
			//sampleHashOpearation(redisTemplate);

			//Sample Image upload.
			//sampleImageUploadOpearation(redisTemplate);

			//Sample Image Retrieve
			sampleImageRetrieveOpearation(redisTemplate);
		} finally {
			ctx.close();
		}
	}

	private static void sampleValueOperation(RedisTemplate redisTemplate) {
		ValueOperations values = redisTemplate.opsForValue();
		values.set("joe", "01");
		System.out.println("Employee added: " + values.get("joe"));
	}

	private static void sampleHashOpearation(RedisTemplate redisTemplate) {
		HashOperations<String, String, String> hash = redisTemplate.opsForHash();
		String empJoeKey = "emp:joe";
		String empJohnKey = "emp:john";

		Map<String, String> empJoeMap = new HashMap();
		empJoeMap.put("name", "Joe");
		empJoeMap.put("age", "32");
		empJoeMap.put("id", "01");

		Map<String, String> empJohnMap = new HashMap();
		empJohnMap.put("name", "John");
		empJohnMap.put("age", "42");
		empJohnMap.put("id", "02");

		hash.putAll(empJoeKey, empJoeMap);
		hash.putAll(empJohnKey, empJohnMap);

		System.out.println("Get emp joe details: " + hash.entries(empJoeKey));
		System.out.println("Get emp john details: " + hash.entries(empJohnKey));
	}

	private static void sampleImageUploadOpearation(RedisTemplate redisTemplate) throws Exception {
		File img = new File("C:\\dev\\logo.gif");
		final ImageVO imgVO = new ImageVO();
		imgVO.setImageName(img.getName());
		byte[] imgContent = Files.readAllBytes(img.toPath());
		imgVO.setImageContent(imgContent);
		//ValueOperations values = redisTemplate.opsForValue();
		//values.set("searsLogo", imgVO);

		redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(imgVO.getImageName().getBytes(), imgVO.getImageContent());
				return null;
			}
		});

		//values.se

		System.out.println("Get emp john details: " );
	}

	private static void sampleImageRetrieveOpearation(RedisTemplate redisTemplate) throws Exception {
		//ValueOperations values = redisTemplate.opsForValue();
		//ImageVO imgVO =  (ImageVO) values.get("searsLogo");

		File img = new File("C:\\dev\\logo.gif");
		final ImageVO imgVO = new ImageVO();
		imgVO.setImageName(img.getName());

		byte[] imgContent = (byte[])redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] imgContent = connection.get(imgVO.getImageName().getBytes());
				return imgContent;
			}
		});

	}
}
