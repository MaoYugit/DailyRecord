package com.mylearning.app;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*; // 静态导入所有断言方法，方便使用

class CalculatorTest {

    @BeforeAll
    static void setupAll() {
        System.out.println("所有测试即将开始...");
    }

    @BeforeEach
    void setup() {
        System.out.println("一个测试用例即将开始...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("一个测试用例结束了。");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("所有测试都已结束。");
    }

    // 创建一个被测试的对象
    private final Calculator calculator = new Calculator();

    @Test
    @Disabled("这个功能还没做好，暂时不测")
    void mySecondTest() {  }

    @Test // 这是一个测试用例
    void testAdd() {
        // 1. 准备数据 (Arrange)
        int a = 5;
        int b = 3;
        int expectedResult = 8; // 预期结果

        // 2. 执行被测试的方法 (Act)
        int actualResult = calculator.add(a, b); // 实际结果

        // 3. 断言 (Assert) - 判断实际结果是否符合预期
        // assertEquals(预期结果, 实际结果, "如果测试失败，显示的提示信息");
        assertEquals(expectedResult, actualResult, "5 + 3 应该等于 8");
    }

    @Test
    void testSubtract() {
        // 我们可以把三步合在一起写
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-2, calculator.subtract(3, 5));
        assertEquals(0, calculator.subtract(5, 5));
    }

    @Test
    void testDivide_Success() {
        // 测试正常情况
        assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    void testDivide_ByZero() {
        // 我们期望当除数为0时，程序会抛出 IllegalArgumentException 异常
        // 如果异常被正确抛出，则测试通过。如果没有抛出，则测试失败。
        assertThrows(IllegalArgumentException.class, () -> {
            // 把会抛出异常的代码放在一个Lambda表达式里
            calculator.divide(1, 0);
        });
    }
}