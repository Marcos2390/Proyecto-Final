package com.planillarural.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.planillarural")
@IncludeTags("unit")
public class UnitTestSuite {
}
