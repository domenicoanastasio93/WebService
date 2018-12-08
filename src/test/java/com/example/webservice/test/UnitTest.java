package com.example.webservice.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProductsUnitTest.class, OrdersUnitTest.class })
public class UnitTest {

}