package com.successfactors.redis;

import java.io.File;
import java.nio.file.Files;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	/*@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) throws Exception {
		model.addAttribute("name", name);

		//Get image byte array
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringRedisConfig.class);
		try {
			RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
			//sampleValueOperation(redisTemplate);

			//Sample Redis Hash Operation Example
			//sampleHashOpearation(redisTemplate);

			//Sample Image upload.
			//sampleImageUploadOpearation2(redisTemplate);

			//Sample Image Retrieve
			sampleImageRetrieveOpearation3(redisTemplate, model);
		} finally {
			ctx.close();
		}

		return "greeting";
	}*/

	@RequestMapping("/greeting")
	public @ResponseBody
	byte[] greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) throws Exception {
		model.addAttribute("name", name);

		//Get image byte array
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringRedisConfig.class);
		try {
			RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
			//sampleValueOperation(redisTemplate);

			//Sample Redis Hash Operation Example
			//sampleHashOpearation(redisTemplate);

			//Sample Image upload.
			//sampleImageUploadOpearation2(redisTemplate);

			//Sample Image Retrieve
			return sampleImageRetrieveOpearation3(redisTemplate, model);
		} finally {
			ctx.close();
		}

		//return null;
	}

	private void sampleImageRetrieveOpearation(RedisTemplate redisTemplate, Model model) throws Exception {
		ValueOperations values = redisTemplate.opsForValue();
		Object imgVO =  (Object) values.get("searsLogo1");
		model.addAttribute("imageVO", imgVO);
		System.out.println("Sears logo1 retrieved: " + imgVO);
	}

	private void sampleImageUploadOpearation(RedisTemplate redisTemplate) throws Exception {
		File img = new File("C:\\dev\\logo.gif");
		ImageVO imgVO = new ImageVO();
		imgVO.setImageName(img.getName());
		imgVO.setImageContent(Files.readAllBytes(img.toPath()));

		ValueOperations values = redisTemplate.opsForValue();
		values.set("searsLogo1", imgVO);


		System.out.println("Sears logo1 added: " + values.get("searsLogo1"));
	}


	private void sampleImageRetrieveOpearation2(RedisTemplate redisTemplate, Model model) throws Exception {
		ValueOperations values = redisTemplate.opsForValue();
		byte[] imgVO =  (byte[]) values.get("searsLogo1");
		model.addAttribute("imageVO", imgVO);
		System.out.println("Sears logo1 retrieved: " + imgVO);
	}

	private void sampleImageUploadOpearation2(RedisTemplate redisTemplate) throws Exception {
		File img = new File("C:\\dev\\logo.gif");
		ValueOperations values = redisTemplate.opsForValue();
		values.set("searsLogo1", Files.readAllBytes(img.toPath()));
		System.out.println("Sears logo2 added: " + values.get("searsLogo1"));
	}

	private static void sampleImageUploadOpearation3(RedisTemplate redisTemplate) throws Exception {
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

	private static byte[] sampleImageRetrieveOpearation3(RedisTemplate redisTemplate, Model model) throws Exception {
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

		//imgVO.setImageContent(imgContent);

		return imgContent;

		//model.addAttribute("imageVO", imgVO);

	}



}
