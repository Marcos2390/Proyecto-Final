package com.planillarural.suites;

import org.junit.platform.suite.api.*;

@Suite
@SelectPackages({
                "com.planillarural.enums",
                "com.planillarural.model",
                "com.planillarural.service"
})
public class AllTestsSuite {
}
