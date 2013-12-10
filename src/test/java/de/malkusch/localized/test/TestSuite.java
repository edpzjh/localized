package de.malkusch.localized.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.malkusch.localized.test.cases.TestDefaultResolver;
import de.malkusch.localized.test.cases.TestDelete;
import de.malkusch.localized.test.cases.TestLoad;
import de.malkusch.localized.test.cases.TestInsert;
import de.malkusch.localized.test.cases.TestThreadLocalResolver;
import de.malkusch.localized.test.cases.TestUpdate;
import de.malkusch.localized.test.cases.TestUtil;

@RunWith(Suite.class)
@SuiteClasses({ TestLoad.class, TestInsert.class, TestUpdate.class,
		TestDelete.class, TestUtil.class, TestDefaultResolver.class,
		TestThreadLocalResolver.class })
public class TestSuite {

}
