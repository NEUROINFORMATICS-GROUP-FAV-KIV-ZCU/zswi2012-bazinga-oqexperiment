package kiv.eeg.dataProcessor;

import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
/**
 * JavaBean utility class.
 * Reads from config XML file and sets up whole program.
 * @author Bazinga
 *
 */
public class BeanUtils {
	
	public static <T> T readBean(Class<T> doClass, String filename, String beanName) {
		XmlBeanFactory xbf = new XmlBeanFactory(new FileSystemResource(filename));
		xbf.addBeanPostProcessor(new RequiredAnnotationBeanPostProcessor());
		PropertyPlaceholderConfigurer placeHolder = new PropertyPlaceholderConfigurer();
		placeHolder.postProcessBeanFactory(xbf);
		T ret = xbf.getBean(beanName, doClass);
		return ret;
	}
}
