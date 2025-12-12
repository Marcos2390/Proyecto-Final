package com.planillarural.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.planillarural.enums",
        "com.planillarural.model",
        "com.planillarural.service"
})
public class AllTestsSuite {
}
