package de.malkusch.localized.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.malkusch.localized.test.cases.LoadTest;
import de.malkusch.localized.test.cases.TestInsert;
import de.malkusch.localized.test.cases.TestUpdate;

@RunWith(Suite.class)
@SuiteClasses({ LoadTest.class, TestInsert.class, TestUpdate.class })
public class TestSuite {

}
