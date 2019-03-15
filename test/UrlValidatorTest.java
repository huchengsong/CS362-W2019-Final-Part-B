

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import java.util.Arrays;

public class UrlValidatorTest extends TestCase {
	public static final int NUM_RANDOM_TESTS = 1000;

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

	/**
	 * Makes an random valid urls and asserts that they are valid.
	 *
	 */
	   public void testYourFirstPartition()
   {
	   // Build random URL
	   ResultPair scheme;
	   ResultPair authority;
	   ResultPair path;
	   ResultPair port;
	   ResultPair query;
	   String validURL;
	   UrlValidator validator = new UrlValidator(validSchemes);

		for (int i = 0; i < NUM_RANDOM_TESTS; i++) {
			do {
				scheme = makeRandomScheme();
			} while (!scheme.valid);
			do {
				authority = makeRandomAuthority();
			} while (!authority.valid);
			do {
				path = makeRandomPath();
			} while (!path.valid);
			do {
				port = makeRandomPort();
			} while (!port.valid);
			do {
				query = makeRandomQuery();
			} while (!query.valid);
			validURL = scheme.item + "://" + authority.item + port.item + path.item + query.item;
			System.out.println("Testing valid url: " + validURL);
			try {
				assertTrue(validator.isValid(validURL));
			} catch (AssertionFailedError e) {
				System.out.println("Something went wrong: " + e);
			}
		}
   }

	/**
	 * Makes an random invalid urls and asserts that they are invalid.
	 *
	 */
   public void testYourSecondPartition(){
	   // Build random URL
	   ResultPair scheme;
	   ResultPair authority;
	   ResultPair path;
	   ResultPair port;
	   ResultPair query;
	   String invalidURL;
	   UrlValidator validator = new UrlValidator(validSchemes);

	   for (int i = 0; i < NUM_RANDOM_TESTS; i++) {
		   do {
			   scheme = makeRandomScheme();
			   authority = makeRandomAuthority();
			   path = makeRandomPath();
			   port = makeRandomPort();
			   query = makeRandomQuery();
		   } while (scheme.valid && authority.valid && query.valid && port.valid && path.valid);
		   invalidURL = scheme.item + "://" + authority.item + path.item + port.item + query.item;
		   System.out.println("Testing invalid url: " + invalidURL);
		   try {
			   assertFalse(validator.isValid(invalidURL));
		   } catch (AssertionFailedError e) {
			   System.out.println("Something went wrong: " + e);
		   }
	   }
   }

	/**
	 * Makes a random string of up to maxlength. The string contains only lowercase
	 * letters
	 *
	 * @return random scheme ResultPair
	 */
	public String makeRandomString(int maxlength) {
		String randomString = "";
		int length = (int) (Math.random() * maxlength + 1);
		for (int i = 0; i < length; i++) {
			int n = (int) (Math.random() * 25 + 1);
			char c = (char) (n + 97);
			randomString += c;
		}
		return randomString;
	}

	/**
	 * Makes a random Scheme ResultPair. If a true result is made, it is selected
	 * from the validSchemes array. Otherwise the result is false and a random string
	 * is generated and returned.
	 *
	 * @return random scheme ResultPair
	 */
   public ResultPair makeRandomScheme() {
      boolean isTrueResult = Math.random() < 0.5 ? true : false;

      if (isTrueResult) {
         int validSchemesIndex = (int)(Math.random() * validSchemes.length);
      	return new ResultPair(validSchemes[validSchemesIndex] , true);
      } else {
         String randomString = makeRandomString(10);
         // Make another random string if a valid scheme is generated
         while (Arrays.asList(validSchemes).contains(randomString)) {
         	randomString = makeRandomString(10);
         }
			return new ResultPair(randomString, false);
      }
   }

	/**
	 * Makes a random Authority ResultPair. If a true result is made, it is generated from the forms:
	 * [0-255].[0-255].[0-255].[0-255], www.<randomstring>.<tld>, or <randomstring>.<tld>
	 * Otherwise, a false result is generated from the forms www.<randomstring>.<invalidtld> or
	 * <randomstring>.
	 *
	 * @return random authority ResultPair
	 */
   public ResultPair makeRandomAuthority() {
      int randomChoice = (int)(Math.random() * 5);
      String authorityString = "";

		if (randomChoice == 0) {
			// Generate valid authority like [0-255].[0-255].[0-255].[0-255]
			for (int i = 0; i < 4; i++) {
				authorityString += (int) (Math.random() * 256) + ".";
			}
			authorityString = authorityString.substring(0, authorityString.length() - 1);
			return new ResultPair(authorityString, true);
		} else if (randomChoice == 1) {
			// Generate valid authority like www.<randomstring>.<tld>
			authorityString = "www." + makeRandomString(15)
									+ mostCommonTlds[(int)(Math.random() * mostCommonTlds.length)];
			return new ResultPair(authorityString, true);
		} else if (randomChoice == 2) {
			// Generate valid authority like www.<randomstring>.<tld>
			authorityString = makeRandomString(15)
									+ mostCommonTlds[(int)(Math.random() * mostCommonTlds.length)];
			return new ResultPair(authorityString, true);
		} else if (randomChoice == 3) {
			// Generate invalid authority like  www.<randomstring>.<invalidtld>
			String invalidTld = "." + makeRandomString(10);
			// Make another random string if a valid scheme is generated
			while (Arrays.asList(mostCommonTlds).contains(invalidTld)) {
				invalidTld = "." + makeRandomString(10);
			}
			authorityString = "www." + makeRandomString(15)
									+ invalidTld;
			return new ResultPair(authorityString, false);
		} else {
			return new ResultPair(makeRandomString(10), false);
		}
   }

	/**
	 * Makes a random Path ResultPair. If a true result is made, it is either a blank path or
	 * a path of up to 5 valid path segments. Otherwise, an invalid path is generated.
	 *
	 * @return random authority ResultPair
	 */
   public ResultPair makeRandomPath() {
	   int randomChoice = (int)(Math.random() * 3);
	   String pathString = "";

	   if (randomChoice == 0) {
			// Generate valid empty path
			return new ResultPair("", true);
	   } else if (randomChoice == 1) {
			// Generate valid random path
			int numPathSegments = (int)(Math.random() * 5 + 1);

			for (int i = 0; i < numPathSegments; i++) {
				pathString += "/" + makeRandomString(10);
			}
			return new ResultPair(pathString, true);
	   } else {
	      // Generate invalid path
			int numRandomInvalidChars = (int)(Math.random()*10 + 2);
			String invalidPathCharSet = "/.!@#$%^&*(";

			for (int i = 0; i < numRandomInvalidChars; i++) {
				pathString += invalidPathCharSet.charAt((int)(Math.random() * invalidPathCharSet.length()));
			}
			return new ResultPair(pathString, false);
	   }
   }

	/**
	 * Makes a random Port ResultPair. If a true result is made, it is either an empty port or
	 * a port between 0 and 65535. Otherwise, an invalid path is generated.
	 *
	 * @return random port ResultPair
	 */
	public ResultPair makeRandomPort() {
		int randomChoice = (int)(Math.random() * 3);
		String portString = "";

		if (randomChoice == 1) {
			return new ResultPair(portString, true);
		} else if (randomChoice == 2) {
			// Port between 0 and 65535
			portString += ":" + (int)(Math.random()*65536);
			return new ResultPair(portString, true);
		} else {
			// Invalid port random string
			portString += ":" + makeRandomString(10);
			return new ResultPair(portString, false);
		}
	}

	/**
	 * Makes a random Query ResultPair. All queries returned are valid.
	 *
	 * @return random authority ResultPair
	 */
	public ResultPair makeRandomQuery() {
		int randomChoice = (int)(Math.random() * testUrlQuery.length);

		return testUrlQuery[randomChoice];
	}

   public void testIsValid()
   {

   }

	ResultPair[] testUrlQuery = { new ResultPair("?action=view", true),
		new ResultPair("?action=edit&mode=up", true),
		new ResultPair("", true),
		new ResultPair("?thisisnotavalidquery", false)
	};

	String[] mostCommonTlds = new String[] { ".com", ".org", ".edu", ".gov", ".uk", ".net", ".ca", ".de", ".jp", ".fr",
		".au", ".us", ".ru", ".ch", ".it", ".nl", ".se", ".no", ".es", ".mil"
	};
   
	String[] validSchemes = new String[] {   "aaa",   "aaas",  "about", "acap",  "acct",  "acr",   "adiumxtra",   "afp",
	   "afs",   "aim",   "appdata",  "apt",   "attachment",  "aw", "barion",   "beshare",  "bitcoin",  "bitcoincash",
	   "blob",  "bolo",  "browserext",  "calculator",  "callto",   "cap",   "chrome",   "chrome-extension",  "cid",
	   "coap",  "coap+tcp", "coap+ws",  "coaps", "coaps+tcp",   "coaps+ws", "com-eventbrite-attendee", "content",
	   "conti", "crid",  "cvs",   "data",  "dav",   "diaspora", "dict",  "did",   "dis",   "dlna-playcontainer",
	   "dlna-playsingle",   "dns",   "dntp",  "dpp",   "dtn",   "dvb",   "ed2k",  "elsi",  "example",  "facetime", "fax",
	   "feed",  "feedready",   "file",  "filesystem",  "finger",   "fish",  "ftp",   "geo",   "gg", "git",   "gizmoproject",
	   "go", "gopher",   "graph", "gtalk", "h323",  "ham",   "hcap",  "hcp",   "http",  "https", "hxxp",  "hxxps", "hydrazone",
	   "iax",   "icap",  "icon",  "im", "imap",  "info",  "iotdisco", "ipn",   "ipp",   "ipps",  "irc",   "irc6",  "ircs",
	   "iris",  "iris.beep",   "iris.lwz", "iris.xpc", "iris.xpcs",   "isostore", "itms",  "jabber",   "jar",   "jms",
	   "keyparc",  "lastfm",   "ldap",  "ldaps", "leaptofrogans",  "lvlt",  "magnet",   "mailserver",  "mailto",   "maps",
	   "market",   "message",  "microsoft.windows.camera",   "microsoft.windows.camera.multipicker",
	   "microsoft.windows.camera.picker",  "mid",   "mms",   "modem", "mongodb",  "moz",   "ms-access",
	   "ms-browser-extension", "ms-calculator",  "ms-drive-to", "ms-enrollment",  "ms-excel", "ms-eyecontrolspeech",
	   "ms-gamebarservices",   "ms-gamingoverlay",  "ms-getoffice",   "ms-help",  "ms-infopath", "ms-inputapp",
	   "ms-lockscreencomponent-config", "ms-media-stream-id",   "ms-mixedrealitycapture",  "ms-mobileplans", "ms-officeapp",
	   "ms-people",   "ms-project",  "ms-powerpoint",  "ms-publisher",   "ms-restoretabcompanion",  "ms-screenclip",
	   "ms-screensketch",   "ms-search",   "ms-search-repair",  "ms-secondary-screen-controller",   "ms-secondary-screen-setup",
	   "ms-settings", "ms-settings-airplanemode",   "ms-settings-bluetooth",   "ms-settings-camera",   "ms-settings-cellular",
	   "ms-settings-cloudstorage",   "ms-settings-connectabledevices",   "ms-settings-displays-topology",
	   "ms-settings-emailandaccounts",  "ms-settings-language", "ms-settings-location", "ms-settings-lock",
	   "ms-settings-nfctransactions",   "ms-settings-notifications",  "ms-settings-power", "ms-settings-privacy",
	   "ms-settings-proximity",   "ms-settings-screenrotation", "ms-settings-wifi",  "ms-settings-workplace",   "ms-spd",
	   "ms-sttoverlay",  "ms-transit-to",  "ms-useractivityset",   "ms-virtualtouchpad",   "ms-visio", "ms-walk-to",
	   "ms-whiteboard",  "ms-whiteboard-cmd", "ms-word",  "msnim", "msrp",  "msrps", "mss",   "mtqp",  "mumble",   "mupdate",
	   "mvn",   "news",  "nfs",   "ni", "nih",   "nntp",  "notes", "ocf",   "oid",   "onenote",  "onenote-cmd", "opaquelocktoken",
	   "openpgp4fpr", "pack",  "palm",  "paparazzi",   "payto", "pkcs11",   "platform", "pop",   "pres",  "prospero",
	   "proxy", "pwid",  "psyc",  "qb", "query", "redis", "rediss",   "reload",   "res",   "resource", "rmi",   "rsync",
	   "rtmfp", "rtmp",  "rtsp",  "rtsps", "rtspu", "secondlife",  "service",  "session",  "sftp",  "sgn",   "shttp",
	   "sieve", "simpleledger",   "sip",   "sips",  "skype", "smb",   "sms",   "smtp",  "snews", "snmp",  "soap.beep",
	   "soap.beeps",  "soldat",   "spiffe",   "spotify",  "ssh",   "steam", "stun",  "stuns", "submit",   "svn",   "tag",
	   "teamspeak",   "tel",   "teliaeid", "telnet",   "tftp",  "things",   "thismessage", "tip",   "tn3270",   "tool",
	   "turn",  "turns", "tv", "udp",   "unreal",   "urn",   "ut2004",   "v-event",  "vemmi", "ventrilo", "videotex",
	   "vnc",   "view-source", "wais",  "webcal",   "wpid",  "ws", "wss",   "wtai",  "wyciwyg",  "xcon",  "xcon-userid",
	   "xfire", "xmlrpc.beep", "xmlrpc.beeps",   "xmpp",  "xri",   "ymsgr", "z39.50",   "z39.50r",  "z39.50s"
	};
}