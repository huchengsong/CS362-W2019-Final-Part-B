

import junit.framework.TestCase;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!





public class UrlValidatorTest extends TestCase {


   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   
   public void testManualTest()
   {
	   //You can use this function to implement your manual testing
	   
	   // test block 1: test different URLs
	   UrlValidator validator = new UrlValidator();
	   // test valid URL
	   assertTrue(validator.isValid("http://www.google.com"));
	   assertTrue(validator.isValid("http://www.google.com:80/test1"));
	   assertTrue(validator.isValid("http://www.google.com:80/test1/test2"));
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   // test wrong Scheme
	   assertFalse(validator.isValid("3ht://www.google.com:80/test1?action=view"));
	   // test wrong Authority
	   assertFalse(validator.isValid("http://256.256.256.256:80/test1?action=view"));
	   // test wrong Port
	   assertFalse(validator.isValid("http://www.google.com:65536/test1?action=view"));
	   // test wrong Path
	   assertFalse(validator.isValid("http://www.google.com:80/..?action=view"));
	   
	   // test block2: different schemes
	   // only allow "http"
	   String[] schemes1 = {"http"};
	   validator = new UrlValidator(schemes1);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertFalse(validator.isValid("https://www.google.com:80/test1?action=view"));
	   assertFalse(validator.isValid("ftp://www.google.com:80/test1?action=view"));
	   // only allow "http" and "https"
	   String[] schemes2 = {"http", "https"};
	   validator = new UrlValidator(schemes2);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("https://www.google.com:80/test1?action=view"));
	   assertFalse(validator.isValid("ftp://www.google.com:80/test1?action=view"));
	   // allow "http", "https", "ftp"
	   String[] schemes3 = {"http", "https", "ftp"};
	   validator = new UrlValidator(schemes3);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("https://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("ftp://www.google.com:80/test1?action=view"));
	   
	   // test block 3: different options
	   // ALLOW_2_SLASHES
	   validator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("http://www.google.com:80/test1//123?action=view"));
	   // ALLOW_ALL_SCHEMES
	   validator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("https://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("ftp://www.google.com:80/test1?action=view"));
	   assertTrue(validator.isValid("abc://www.google.com:80/test1?action=view"));
	   // ALLOW_LOCAL_URLS
	   validator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
	   assertTrue(validator.isValid("http://localhost/test1?action=view"));
	   assertTrue(validator.isValid("http://machine/test1?action=view"));
	   // NO_FRAGMENTS
	   validator = new UrlValidator(UrlValidator.NO_FRAGMENTS);
	   assertTrue(validator.isValid("http://www.google.com:80/test1?action=view"));
	   assertFalse(validator.isValid("https://www.google.com:80/test1?action=view#123"));
	   
   }
   
   
   public void testYourFirstPartition()
   {
	 //You can use this function to implement your First Partition testing	   

   }
   
   public void testYourSecondPartition(){
		 //You can use this function to implement your Second Partition testing	   

   }
   //You need to create more test cases for your Partitions if you need to 
   
   public void testIsValid()
   {
	   //You can use this function for programming based testing

   }
   


}