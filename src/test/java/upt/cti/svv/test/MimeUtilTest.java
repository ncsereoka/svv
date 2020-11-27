package upt.cti.svv.test;

import org.junit.Test;
import upt.cti.svv.util.MimeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class MimeUtilTest {
	@Test
	public void test() {
		assertEquals("text/html", MimeUtil.getContentType("index.html"));
		assertEquals("text/html", MimeUtil.getContentType("index.htm"));
		assertEquals("text/html", MimeUtil.getContentType("www/euro/index.html"));
		assertEquals("text/css", MimeUtil.getContentType("style.css"));
		assertEquals("text/css", MimeUtil.getContentType("header/main.css"));
		assertEquals("image/jpeg", MimeUtil.getContentType("dog_pic.jpg"));
		assertEquals("image/jpeg", MimeUtil.getContentType("cat_pic.jpeg"));
		assertEquals("image/jpeg", MimeUtil.getContentType("landscapes/skyline.jpg"));
		assertEquals("text/plain", MimeUtil.getContentType("passwords.txt"));
		assertEquals("text/plain", MimeUtil.getContentType("db/keys.txt"));
		assertEquals("application/octet-stream", MimeUtil.getContentType("good_luck/double.o7"));
		assertEquals("application/octet-stream", MimeUtil.getContentType("smooth/criminal/aww.pdf"));
		assertEquals("application/octet-stream", MimeUtil.getContentType("cant_touch_this.exe"));
	}

	@Test(expected = InvocationTargetException.class)
	public void instantiation_fails() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<MimeUtil> c = MimeUtil.class.getDeclaredConstructor();
		c.setAccessible(true);
		c.newInstance();
	}
}
