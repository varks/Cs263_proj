import time
import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys

class CENBotTest(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def test_CEN_func_workflow(self):
		driver = self.driver
		driver.get("http://currencyxchange-notifbot.appspot.com/")
		self.assertIn("CEN", driver.title)
		
		print "------- Cover Page Test Passed -------------------"
		elem = driver.find_element_by_link_text("Please Sign in")
		elem.click()
		
		driver.get("https://accounts.google.com/ServiceLogin?service=ah&passive=true&continue=https%3A%2F%2Fappengine.google.com%2F_ah%2Fconflogin%3Fcontinue%3Dhttp%3A%2F%2Fcurrencyxchange-notifbot.appspot.com%2Freg.jsp&ltmpl=gm&shdf=CicLEgZhaG5hbWUaG0EgQ3VycmVuY3kgWGNoYW5nZSBOb3RpZkJvdAwSAmFoIhSFSKoIWibGPFAnOdn_M3MZYGZplCgBMhTu31s3-ymXiToBy68X1xh9TOQfKg")
		emailid = driver.find_element_by_id("Email")
		emailid.send_keys("your gmail id")

		passw = driver.find_element_by_id("Passwd")
		passw.send_keys("your passwd in plain text")
		
		signin = driver.find_element_by_id("signIn")
		signin.click()

		print "------- Gmail Authentication Test Passed -------------------"
		time.sleep(5)

		driver.get("http://currencyxchange-notifbot.appspot.com/reg.jsp")
		self.assertIn("Registration Form", driver.title)

		print "------- Registration Form Test Passed -------------------"
		first_name = driver.find_element_by_id("first_name")
		first_name.send_keys("Varun")

		last_name = driver.find_element_by_id("last_name")
		last_name.send_keys("KS")
		
		phone_number = driver.find_element_by_id("Phone-Number")
		phone_number.send_keys("8056377338")

		tvalue = driver.find_element_by_id("tvalue")
		tvalue.send_keys("65")        

		submit = driver.find_element_by_id("submit")
		submit.click()

		time.sleep(5)

		print "------- Input Test Cases Passed -------------------"

		driver.get("http://currencyxchange-notifbot.appspot.com/homePage.jsp")
		self.assertIn("Home Page - CEN Bot", driver.title)
		
		print "------- Home Page Test Passed -------------------"
		time.sleep(5)

		elem = driver.find_element_by_link_text("Sign out")
		elem.click()
		
		print "------- Sign Out Test Passed -------------------"
		

	def tearDown(self):
		self.driver.close()

if __name__ == "__main__":
	unittest.main()