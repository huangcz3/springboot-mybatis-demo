///**
// * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
// */
//package com.neo.util;
//
//
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.Resource;
//import org.springframework.util.ResourceUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Properties工具类， 可载入多个properties、yml文件，
// * 相同的属性在最后载入的文件中的值将会覆盖之前的值，
// * 取不到从System.getProperty()获取。
// * @author ThinkGem
// * @version 2017-12-30
// */
//public class PropertiesUtils {
//
//	// 默认加载的文件，可通过继承覆盖（若有相同Key，优先加载后面的）
//	public static final String[] DEFAULT_CONFIG_FILE = new String[]{
//			"classpath:config/application.yml", "classpath:application.yml"};
//
//
//	private final Set<String> configSet = new LinkedHashSet<>();
//	private final Properties properties = new Properties();
//
//	/**
//	 * 当前类的实例持有者（静态内部类，延迟加载，懒汉式，线程安全的单例模式）
//	 */
//	private static final class PropertiesLoaderHolder {
//		private static PropertiesUtils INSTANCE;
//		static {
//			releadInstance();
//		}
//		public static void releadInstance(){
//			// 获取平台及模块相关的配置文件
//			Set<String> configSet = new LinkedHashSet<>();
//			Resource[] resources = ResourceUtils.getResources("classpath*:/config/jeesite-*.*");
//			for(Resource resource : resources){
//				configSet.add("classpath:config/"+resource.getFilename());
//			}
//			//configSet.add("classpath:config/jeesite.yml");
//			// 获取全局设置默认的配置文件（以下是支持环境配置的属性文件）
//			Set<String> set = new LinkedHashSet<>();
//			for (String configFile : DEFAULT_CONFIG_FILE){
//				set.add(configFile);
//			}
//			// 获取 spring.config.location 外部自定义的配置文件
//			String customConfigs = System.getProperty("spring.config.location");
//			if (StringUtils.isNotBlank(customConfigs)){
//				for (String customConfig : StringUtils.split(customConfigs, ",")){
//					if (!customConfig.contains("$")){
//						customConfig = org.springframework.util.StringUtils.cleanPath(customConfig);
//						if (!ResourceUtils.isUrl(customConfig)){
//							customConfig = ResourceUtils.FILE_URL_PREFIX + customConfig;
//						}
//					}
//					set.add(customConfig);
//				}
//			}
//			// 获取 spring.profiles.active 活动环境名称的配置文件
//			String[] configFiles = set.toArray(new String[set.size()]);
//			String profiles = System.getProperty("spring.profiles.active");
//			if (StringUtils.isBlank(profiles)){
//				PropertiesUtils propsTemp = new PropertiesUtils(configFiles);
//				profiles = propsTemp.getProperty("spring.profiles.active");
//			}
//			for (String location : configFiles){
//				configSet.add(location);
//				if (StringUtils.isNotBlank(profiles)){
//					if (location.endsWith(".properties")){
//						configSet.add(StringUtils.substringBeforeLast(location, ".properties")
//								+ "-" + profiles + ".properties");
//					}else if (location.endsWith(".yml")){
//						configSet.add(StringUtils.substringBeforeLast(location, ".yml")
//								+ "-" + profiles + ".yml");
//					}
//				}
//			}
//			configFiles = configSet.toArray(new String[configSet.size()]);
//			logger.debug("Loading jeesite config: {}", (Object)configFiles);
//			INSTANCE = new PropertiesUtils(configFiles);
//		}
//	}
//
//	/**
//	 * 载入多个文件，路径使用Spring Resource格式，相同的属性在最后载入的文件中的值将会覆盖之前的值。
//	 */
//	public PropertiesUtils(String... configFiles) {
//		for (String location : configFiles) {
//			try {
//				Resource resource = ResourceUtils.getResource(location);
//				if (resource.exists()){
//        			if (location.endsWith(".properties")){
//        				try (InputStreamReader is = new InputStreamReader(resource.getInputStream(), "UTF-8")){
//	    					properties.load(is);
//	    					configSet.add(location);
//	        			} catch (IOException ex) {
//	            			logger.error("Load " + location + " failure. ", ex);
//	        			}
//        			}
//        			else if (location.endsWith(".yml")){
//        				YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
//        				bean.setResources(resource);
//        				for (Map.Entry<Object,Object> entry : bean.getObject().entrySet()){
//        					properties.put(ObjectUtils.toString(entry.getKey()),
//        							ObjectUtils.toString(entry.getValue()));
//        				}
//    					configSet.add(location);
//        			}
//				}
//			} catch (Exception e) {
//    			logger.error("Load " + location + " failure. ", e);
//			}
//		}
//	}
//
//	/**
//	 * 获取当前加载的属性文件
//	 */
//	public Set<String> getConfigSet() {
//		return configSet;
//	}
//
//	/**
//	 * 获取当前加载的属性数据
//	 */
//	public Properties getProperties() {
//		return properties;
//	}
//
//	/**
//	 * 当前类实例
//	 */
//	public static PropertiesUtils getInstance(){
//		return PropertiesLoaderHolder.INSTANCE;
//	}
//
//	/**
//	 * 重新加载实例（重新实例化，以重新加载属性文件数据）
//	 */
//	public static void releadInstance(){
//		PropertiesLoaderHolder.releadInstance();
//	}
//
//	// 正则表达式预编译
//	private static Pattern p1 = Pattern.compile("\\$\\{.*?\\}");
//
//	/**
//	 * 获取属性值，取不到从System.getProperty()获取，都取不到返回null
//	 */
//	public String getProperty(String key) {
//        String value = properties.getProperty(key);
//        if (value != null){
//        	// 支持嵌套取值的问题  key=${xx}/yy
//	    	Matcher m = p1.matcher(value);
//	        while(m.find()) {
//	            String g = m.group();
//	            String keyChild = g.replaceAll("\\$\\{", "").replaceAll("\\}", "");
//	            value = StringUtils.replace(value, g, getProperty(keyChild));
//	        }
//	        return value;
//	    }else{
//	    	String systemProperty = System.getProperty(key);
//			if (systemProperty != null) {
//				return systemProperty;
//			}
//	    }
//		return null;
//	}
//
//	/**
//	 * 取出String类型的Property，但以System的Property优先，如果都为null则返回defaultValue值
//	 */
//	public String getProperty(String key, String defaultValue) {
//		String value = getProperty(key);
//		return value != null ? value : defaultValue;
//	}
//
//
//}
