# Java 静态成员详解

> 全面深入理解 Java 中的静态代码块、静态属性、静态方法和普通成员的区别

---

## 📚 目录

- [1. 完整对比表](#1-完整对比表)
- [2. 静态代码块 (Static Block)](#2-静态代码块-static-block)
- [3. 静态属性 (Static Field)](#3-静态属性-static-field)
- [4. 静态方法 (Static Method)](#4-静态方法-static-method)
- [5. 普通属性 (Instance Field)](#5-普通属性-instance-field)
- [6. 普通方法 (Instance Method)](#6-普通方法-instance-method)
- [7. 综合示例](#7-综合示例)
- [8. 执行顺序](#8-执行顺序)
- [9. 最佳实践](#9-最佳实践)

---

## 1. 完整对比表

| 特性 | 静态代码块 | 静态属性 | 静态方法 | 普通属性 | 普通方法 |
|------|-----------|---------|---------|---------|---------|
| **关键字** | `static {}` | `static` | `static` | 无 | 无 |
| **所属** | 类 | 类 | 类 | 对象实例 | 对象实例 |
| **执行时机** | 类加载时 | 类加载时 | 调用时 | 对象创建时 | 调用时 |
| **执行次数** | 仅一次 | 初始化一次 | 多次 | 每个对象一次 | 多次 |
| **内存位置** | 方法区 | 方法区 | 方法区 | 堆 | 方法区 |
| **访问方式** | 自动执行 | `类名.属性` | `类名.方法()` | `对象.属性` | `对象.方法()` |
| **访问限制** | 只能访问静态成员 | - | 只能访问静态成员 | 可访问所有 | 可访问所有 |
| **是否可被继承** | 否 | 是 | 是 | 是 | 是 |
| **是否可被重写** | 否 | 否 | 否（可隐藏） | 否 | 是 |

---

## 2. 静态代码块 (Static Block)

### 2.1 定义和语法

```java
public class Example {
    static {
        // 静态代码块
        System.out.println("静态代码块执行");
    }
}
```

### 2.2 执行时机

静态代码块在**类加载时**执行，且**只执行一次**。

```java
public class StaticBlockDemo {
    static {
        System.out.println("1. 静态代码块执行");
    }

    public StaticBlockDemo() {
        System.out.println("3. 构造函数执行");
    }

    {
        System.out.println("2. 实例代码块执行");
    }

    public static void main(String[] args) {
        System.out.println("4. main 方法开始");
        new StaticBlockDemo();  // 创建对象
        System.out.println("5. main 方法结束");
    }
}
```

**输出结果：**
```
1. 静态代码块执行
4. main 方法开始
2. 实例代码块执行
3. 构造函数执行
5. main 方法结束
```

### 2.3 执行次数验证

```java
public class StaticBlockCount {
    static {
        System.out.println("静态代码块：只执行一次");
    }

    public static void main(String[] args) {
        new StaticBlockCount();  // 第一次创建对象
        new StaticBlockCount();  // 第二次创建对象
        new StaticBlockCount();  // 第三次创建对象
    }
}
```

**输出：**
```
静态代码块：只执行一次
```

### 2.4 典型应用场景

#### 场景 1：初始化静态资源

```java
public class DatabaseConfig {
    private static Connection connection;

    static {
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db",
                "user",
                "password"
            );
            System.out.println("数据库连接初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
```

#### 场景 2：加载配置文件

```java
public class ConfigManager {
    private static Properties config = new Properties();

    static {
        try {
            // 类加载时自动加载配置文件
            InputStream in = ConfigManager.class
                .getResourceAsStream("config.properties");
            config.load(in);
            System.out.println("配置文件加载成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return config.getProperty(key);
    }
}
```

#### 场景 3：注册驱动

```java
public class JdbcDriver {
    static {
        try {
            // JDBC 驱动注册
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("JDBC 驱动注册成功");
        } catch (SQLException e) {
            throw new RuntimeException("Driver registration failed", e);
        }
    }
}
```

#### 场景 4：初始化静态集合

```java
public class ErrorCodeConfig {
    private static final Map<Integer, String> ERROR_MESSAGES = new HashMap<>();

    static {
        // 初始化错误码映射
        ERROR_MESSAGES.put(400, "Bad Request");
        ERROR_MESSAGES.put(401, "Unauthorized");
        ERROR_MESSAGES.put(403, "Forbidden");
        ERROR_MESSAGES.put(404, "Not Found");
        ERROR_MESSAGES.put(500, "Internal Server Error");
    }

    public static String getMessage(int code) {
        return ERROR_MESSAGES.get(code);
    }
}
```

### 2.5 多个静态代码块

```java
public class MultipleStaticBlocks {
    static int value;

    static {
        System.out.println("第一个静态代码块");
        value = 10;
    }

    static {
        System.out.println("第二个静态代码块");
        value = value * 2;
    }

    static {
        System.out.println("第三个静态代码块");
        System.out.println("最终 value = " + value);
    }
}
```

**输出：**
```
第一个静态代码块
第二个静态代码块
第三个静态代码块
最终 value = 20
```

---

## 3. 静态属性 (Static Field)

### 3.1 定义和语法

```java
public class Example {
    static int count = 0;           // 静态属性
    static String name = "Test";    // 静态属性
    static final double PI = 3.14;  // 静态常量
}
```

### 3.2 内存分配和共享

```java
public class StaticFieldDemo {
    static int staticCount = 0;     // 静态属性：所有对象共享
    int instanceCount = 0;          // 实例属性：每个对象独立

    public StaticFieldDemo() {
        staticCount++;
        instanceCount++;
    }

    public static void main(String[] args) {
        StaticFieldDemo obj1 = new StaticFieldDemo();
        StaticFieldDemo obj2 = new StaticFieldDemo();
        StaticFieldDemo obj3 = new StaticFieldDemo();

        System.out.println("静态计数: " + StaticFieldDemo.staticCount);  // 输出: 3
        System.out.println("obj1 实例计数: " + obj1.instanceCount);      // 输出: 1
        System.out.println("obj2 实例计数: " + obj2.instanceCount);      // 输出: 1
        System.out.println("obj3 实例计数: " + obj3.instanceCount);      // 输出: 1
    }
}
```

### 3.3 内存示意图

```
方法区（Method Area）
┌─────────────────────────┐
│ StaticFieldDemo.class   │
│ ├─ staticCount = 3      │  ← 静态属性（所有对象共享）
│ └─ 方法信息             │
└─────────────────────────┘

堆（Heap）
┌─────────────────────────┐
│ obj1                    │
│ └─ instanceCount = 1    │  ← 实例属性（独立）
├─────────────────────────┤
│ obj2                    │
│ └─ instanceCount = 1    │  ← 实例属性（独立）
├─────────────────────────┤
│ obj3                    │
│ └─ instanceCount = 1    │  ← 实例属性（独立）
└─────────────────────────┘
```

### 3.4 访问方式

```java
public class AccessDemo {
    static String staticField = "静态属性";
    String instanceField = "实例属性";

    public static void main(String[] args) {
        // 静态属性访问方式
        System.out.println(AccessDemo.staticField);        // ✅ 推荐：类名.属性

        AccessDemo obj = new AccessDemo();
        System.out.println(obj.staticField);               // ⚠️ 可以但不推荐：对象.属性

        // 实例属性访问方式
        // System.out.println(AccessDemo.instanceField);   // ❌ 错误：无法访问
        System.out.println(obj.instanceField);             // ✅ 正确：对象.属性
    }
}
```

### 3.5 典型应用场景

#### 场景 1：常量定义

```java
public class Constants {
    public static final String APP_NAME = "MyApp";
    public static final String VERSION = "1.0.0";
    public static final int MAX_CONNECTIONS = 100;
    public static final double PI = 3.14159;
    public static final String[] SUPPORTED_FORMATS = {"jpg", "png", "gif"};
}

// 使用
String appName = Constants.APP_NAME;
int maxConn = Constants.MAX_CONNECTIONS;
```

#### 场景 2：单例模式

```java
public class Singleton {
    // 静态属性保存唯一实例
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

#### 场景 3：计数器

```java
public class Counter {
    private static int totalObjects = 0;  // 统计创建的对象总数
    private int id;

    public Counter() {
        totalObjects++;
        this.id = totalObjects;
    }

    public static int getTotalObjects() {
        return totalObjects;
    }

    public int getId() {
        return id;
    }
}

// 使用
Counter c1 = new Counter();  // id = 1
Counter c2 = new Counter();  // id = 2
Counter c3 = new Counter();  // id = 3
System.out.println(Counter.getTotalObjects());  // 输出: 3
```

#### 场景 4：缓存

```java
public class UserCache {
    // 静态缓存，所有地方共享
    private static final Map<Long, User> CACHE = new ConcurrentHashMap<>();

    public static void put(Long id, User user) {
        CACHE.put(id, user);
    }

    public static User get(Long id) {
        return CACHE.get(id);
    }

    public static void clear() {
        CACHE.clear();
    }
}
```

---

## 4. 静态方法 (Static Method)

### 4.1 定义和语法

```java
public class Example {
    public static void staticMethod() {
        System.out.println("静态方法");
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
```

### 4.2 访问限制

```java
public class StaticMethodDemo {
    static int staticVar = 10;
    int instanceVar = 20;

    // 静态方法
    public static void staticMethod() {
        System.out.println(staticVar);           // ✅ 可以访问静态属性
        // System.out.println(instanceVar);      // ❌ 不能访问实例属性

        staticMethod2();                         // ✅ 可以调用静态方法
        // instanceMethod();                     // ❌ 不能调用实例方法

        // System.out.println(this);             // ❌ 不能使用 this
        // System.out.println(super);            // ❌ 不能使用 super
    }

    public static void staticMethod2() {
        System.out.println("另一个静态方法");
    }

    // 实例方法
    public void instanceMethod() {
        System.out.println(staticVar);           // ✅ 可以访问静态属性
        System.out.println(instanceVar);         // ✅ 可以访问实例属性

        staticMethod();                          // ✅ 可以调用静态方法
        instanceMethod2();                       // ✅ 可以调用实例方法

        System.out.println(this);                // ✅ 可以使用 this
    }

    public void instanceMethod2() {
        System.out.println("另一个实例方法");
    }
}
```

### 4.3 调用方式

```java
public class CallDemo {
    public static void staticMethod() {
        System.out.println("静态方法");
    }

    public void instanceMethod() {
        System.out.println("实例方法");
    }

    public static void main(String[] args) {
        // 静态方法调用
        CallDemo.staticMethod();                 // ✅ 推荐：类名.方法()
        staticMethod();                          // ✅ 同一个类中可以直接调用

        CallDemo obj = new CallDemo();
        obj.staticMethod();                      // ⚠️ 可以但不推荐：对象.方法()

        // 实例方法调用
        // CallDemo.instanceMethod();            // ❌ 错误：无法调用
        obj.instanceMethod();                    // ✅ 正确：对象.方法()
    }
}
```

### 4.4 典型应用场景

#### 场景 1：工具类方法

```java
public class StringUtils {
    // 工具方法：不需要创建对象
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

// 使用
boolean empty = StringUtils.isEmpty("test");
String capitalized = StringUtils.capitalize("hello");
```

#### 场景 2：数学工具类

```java
public class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static double sqrt(double n) {
        return Math.sqrt(n);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}

// 使用
int sum = MathUtils.add(10, 20);
int maximum = MathUtils.max(5, 8);
```

#### 场景 3：工厂方法

```java
public class UserFactory {
    public static User createUser(String type) {
        if ("admin".equals(type)) {
            return new AdminUser();
        } else if ("vip".equals(type)) {
            return new VipUser();
        } else {
            return new NormalUser();
        }
    }

    public static User createUserFromJson(String json) {
        // 从 JSON 创建用户对象
        return JSON.parseObject(json, User.class);
    }
}

// 使用
User admin = UserFactory.createUser("admin");
User user = UserFactory.createUserFromJson(jsonString);
```

#### 场景 4：配置管理

```java
public class ConfigManager {
    private static Properties config = new Properties();

    static {
        // 加载配置文件
        try (InputStream in = ConfigManager.class
                .getResourceAsStream("config.properties")) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 静态方法：无需创建对象即可使用
    public static String getValue(String key) {
        return config.getProperty(key);
    }

    public static int getIntValue(String key) {
        return Integer.parseInt(getValue(key));
    }

    public static boolean getBooleanValue(String key) {
        return Boolean.parseBoolean(getValue(key));
    }
}

// 使用
String host = ConfigManager.getValue("HOST");
int port = ConfigManager.getIntValue("PORT");
```

#### 场景 5：日期工具类

```java
public class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        return DATE_FORMAT.parse(dateStr);
    }

    public static boolean isToday(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        Calendar cal2 = Calendar.getInstance();

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
```

---

## 5. 普通属性 (Instance Field)

### 5.1 定义和语法

```java
public class Example {
    int age;              // 普通属性
    String name;          // 普通属性
    private double salary;// 私有属性
}
```

### 5.2 每个对象独立

```java
public class Person {
    String name;          // 实例属性
    int age;              // 实例属性

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Person p1 = new Person("张三", 20);
        Person p2 = new Person("李四", 25);

        System.out.println(p1.name);  // 输出: 张三
        System.out.println(p2.name);  // 输出: 李四

        // 修改 p1 不影响 p2
        p1.age = 30;
        System.out.println(p1.age);   // 输出: 30
        System.out.println(p2.age);   // 输出: 25
    }
}
```

### 5.3 初始化方式

```java
public class InitDemo {
    // 方式 1：声明时初始化
    int count = 0;
    String name = "Default";

    // 方式 2：实例代码块初始化
    {
        count = 10;
        name = "Initialized";
    }

    // 方式 3：构造函数初始化
    public InitDemo() {
        count = 20;
        name = "Constructor";
    }

    // 方式 4：带参构造函数
    public InitDemo(int count, String name) {
        this.count = count;
        this.name = name;
    }
}
```

---

## 6. 普通方法 (Instance Method)

### 6.1 定义和语法

```java
public class Example {
    public void instanceMethod() {
        System.out.println("普通方法");
    }

    public int calculate(int a, int b) {
        return a + b;
    }
}
```

### 6.2 可以访问所有成员

```java
public class FullAccessDemo {
    static int staticVar = 10;
    int instanceVar = 20;

    public void instanceMethod() {
        // 可以访问静态成员
        System.out.println(staticVar);
        staticMethod();

        // 可以访问实例成员
        System.out.println(instanceVar);
        anotherInstanceMethod();

        // 可以使用 this
        System.out.println(this.instanceVar);

        // 可以修改实例变量
        this.instanceVar = 30;
    }

    public static void staticMethod() {
        System.out.println("静态方法");
    }

    public void anotherInstanceMethod() {
        System.out.println("另一个实例方法");
    }
}
```

### 6.3 典型应用场景

```java
public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // 实例方法：操作对象状态
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("存款成功: " + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("取款成功: " + amount);
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public void printStatement() {
        System.out.println("账号: " + accountNumber);
        System.out.println("余额: " + balance);
    }
}
```

---

## 7. 综合示例

```java
public class ComprehensiveDemo {
    // ========== 静态成员 ==========
    static int staticCount = 0;                    // 静态属性
    static final String COMPANY = "MyCompany";     // 静态常量

    static {
        // 静态代码块
        System.out.println("1. 静态代码块执行");
        staticCount = 100;
    }

    public static void staticMethod() {
        // 静态方法
        System.out.println("3. 静态方法执行");
        System.out.println("   静态计数: " + staticCount);
    }

    // ========== 实例成员 ==========
    int instanceCount = 0;                         // 实例属性
    String name;                                   // 实例属性

    {
        // 实例代码块
        System.out.println("2. 实例代码块执行");
        instanceCount = 1;
    }

    public ComprehensiveDemo(String name) {
        // 构造函数
        System.out.println("4. 构造函数执行");
        this.name = name;
    }

    public void instanceMethod() {
        // 实例方法
        System.out.println("5. 实例方法执行");
        System.out.println("   实例计数: " + instanceCount);
        System.out.println("   名称: " + name);

        // 实例方法可以访问静态成员
        System.out.println("   静态计数: " + staticCount);
        staticMethod();
    }

    // ========== 测试 ==========
    public static void main(String[] args) {
        System.out.println("\n========== 创建第一个对象 ==========");
        ComprehensiveDemo obj1 = new ComprehensiveDemo("对象1");
        obj1.instanceMethod();

        System.out.println("\n========== 创建第二个对象 ==========");
        ComprehensiveDemo obj2 = new ComprehensiveDemo("对象2");
        obj2.instanceMethod();

        System.out.println("\n========== 修改静态属性 ==========");
        ComprehensiveDemo.staticCount = 200;
        System.out.println("obj1 看到的静态计数: " + obj1.staticCount);
        System.out.println("obj2 看到的静态计数: " + obj2.staticCount);

        System.out.println("\n========== 修改实例属性 ==========");
        obj1.instanceCount = 999;
        System.out.println("obj1 实例计数: " + obj1.instanceCount);
        System.out.println("obj2 实例计数: " + obj2.instanceCount);
    }
}
```

**输出结果：**
```
1. 静态代码块执行

========== 创建第一个对象 ==========
2. 实例代码块执行
4. 构造函数执行
5. 实例方法执行
   实例计数: 1
   名称: 对象1
   静态计数: 100
3. 静态方法执行
   静态计数: 100

========== 创建第二个对象 ==========
2. 实例代码块执行
4. 构造函数执行
5. 实例方法执行
   实例计数: 1
   名称: 对象2
   静态计数: 100
3. 静态方法执行
   静态计数: 100

========== 修改静态属性 ==========
obj1 看到的静态计数: 200
obj2 看到的静态计数: 200

========== 修改实例属性 ==========
obj1 实例计数: 999
obj2 实例计数: 1
```

---

## 8. 执行顺序

### 8.1 完整执行流程

```
类加载阶段（只执行一次）
    ↓
1. 父类静态属性初始化
    ↓
2. 父类静态代码块执行
    ↓
3. 子类静态属性初始化
    ↓
4. 子类静态代码块执行
    ↓
对象创建阶段（每次 new 都执行）
    ↓
5. 父类实例属性初始化
    ↓
6. 父类实例代码块执行
    ↓
7. 父类构造函数执行
    ↓
8. 子类实例属性初始化
    ↓
9. 子类实例代码块执行
    ↓
10. 子类构造函数执行
    ↓
方法调用阶段
    ↓
11. 静态方法调用（类名.方法()）
12. 实例方法调用（对象.方法()）
```

### 8.2 继承关系下的执行顺序

```java
class Parent {
    static {
        System.out.println("1. 父类静态代码块");
    }

    {
        System.out.println("3. 父类实例代码块");
    }

    public Parent() {
        System.out.println("4. 父类构造函数");
    }
}

class Child extends Parent {
    static {
        System.out.println("2. 子类静态代码块");
    }

    {
        System.out.println("5. 子类实例代码块");
    }

    public Child() {
        System.out.println("6. 子类构造函数");
    }

    public static void main(String[] args) {
        new Child();
    }
}
```

**输出：**
```
1. 父类静态代码块
2. 子类静态代码块
3. 父类实例代码块
4. 父类构造函数
5. 子类实例代码块
6. 子类构造函数
```

---

## 9. 最佳实践

### 9.1 应该使用静态的场景 ✅

#### 1. 工具类方法
```java
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
```

#### 2. 常量定义
```java
public class Constants {
    public static final String APP_NAME = "MyApp";
    public static final int MAX_SIZE = 100;
}
```

#### 3. 单例模式
```java
public class Singleton {
    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

#### 4. 配置管理
```java
public class ConfigManager {
    public static String getValue(String key) {
        // 获取配置值
    }
}
```

#### 5. 工厂方法
```java
public class UserFactory {
    public static User createUser(String type) {
        // 创建用户对象
    }
}
```

#### 6. 缓存管理
```java
public class CacheManager {
    private static final Map<String, Object> CACHE = new HashMap<>();

    public static void put(String key, Object value) {
        CACHE.put(key, value);
    }
}
```

### 9.2 不应该使用静态的场景 ❌

#### 1. 需要多态的方法
```java
// ❌ 错误：静态方法不能被重写
public class Animal {
    public static void makeSound() {
        System.out.println("Animal sound");
    }
}

// ✅ 正确：使用实例方法
public class Animal {
    public void makeSound() {
        System.out.println("Animal sound");
    }
}
```

#### 2. 需要访问实例状态的方法
```java
// ❌ 错误：静态方法无法访问实例变量
public class BankAccount {
    private double balance;

    public static void deposit(double amount) {
        // balance += amount;  // 编译错误
    }
}

// ✅ 正确：使用实例方法
public class BankAccount {
    private double balance;

    public void deposit(double amount) {
        balance += amount;
    }
}
```

#### 3. 有状态的业务逻辑
```java
// ❌ 错误：静态变量在多线程环境下不安全
public class OrderService {
    private static Order currentOrder;  // 所有线程共享

    public static void processOrder(Order order) {
        currentOrder = order;  // 线程不安全
        // 处理订单
    }
}

// ✅ 正确：使用实例变量
public class OrderService {
    private Order currentOrder;  // 每个实例独立

    public void processOrder(Order order) {
        this.currentOrder = order;
        // 处理订单
    }
}
```

### 9.3 编码规范

#### 1. 静态常量命名
```java
// ✅ 正确：全大写，下划线分隔
public static final int MAX_SIZE = 100;
public static final String DEFAULT_NAME = "Unknown";

// ❌ 错误
public static final int maxSize = 100;
public static final String defaultName = "Unknown";
```

#### 2. 工具类设计
```java
// ✅ 正确：私有构造函数，防止实例化
public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
```

#### 3. 静态方法访问
```java
// ✅ 推荐：使用类名调用
String result = StringUtils.isEmpty("test");

// ⚠️ 不推荐：使用对象调用
StringUtils utils = new StringUtils();
String result = utils.isEmpty("test");
```

#### 4. 避免静态变量滥用
```java
// ❌ 错误：过度使用静态变量
public class UserService {
    private static User currentUser;  // 多线程不安全
    private static List<User> users;  // 内存泄漏风险
}

// ✅ 正确：使用实例变量或局部变量
public class UserService {
    public User getCurrentUser(Long userId) {
        // 从数据库或缓存获取
        return userRepository.findById(userId);
    }
}
```

### 9.4 性能考虑

#### 1. 静态变量的生命周期
```java
// ⚠️ 注意：静态变量在类加载时创建，程序结束时销毁
public class DataCache {
    // 这个 Map 会一直占用内存，直到程序结束
    private static final Map<String, byte[]> CACHE = new HashMap<>();
}
```

#### 2. 懒加载优化
```java
// ✅ 推荐：使用懒加载减少启动时间
public class HeavyResource {
    private static HeavyResource instance;

    public static HeavyResource getInstance() {
        if (instance == null) {
            synchronized (HeavyResource.class) {
                if (instance == null) {
                    instance = new HeavyResource();
                }
            }
        }
        return instance;
    }
}
```

---

## 10. 常见面试题

### Q1: 静态代码块、构造代码块、构造函数的执行顺序？

**答案：** 静态代码块 → 构造代码块 → 构造函数

### Q2: 静态方法能否被重写？

**答案：** 不能。静态方法属于类，不属于对象，不存在多态性。子类可以定义同名静态方法，但这是方法隐藏，不是重写。

### Q3: 为什么静态方法不能访问非静态成员？

**答案：** 因为静态方法在类加载时就存在，而非静态成员只有在对象创建后才存在。静态方法调用时可能还没有对象实例。

### Q4: 静态变量存储在哪里？

**答案：** JDK 8 之前存储在方法区（永久代），JDK 8 及之后存储在元空间（Metaspace）。

### Q5: 如何避免静态变量的线程安全问题？

**答案：**
1. 使用 `final` 修饰（不可变）
2. 使用线程安全的集合（如 `ConcurrentHashMap`）
3. 使用 `synchronized` 同步
4. 使用 `ThreadLocal`

---

## 11. 总结

### 核心要点

1. **静态成员属于类，实例成员属于对象**
2. **静态代码块在类加载时执行一次**
3. **静态方法不能访问实例成员**
4. **实例方法可以访问所有成员**
5. **静态变量被所有对象共享**
6. **合理使用静态可以提高代码复用性和性能**

### 记忆口诀

```
静态属于类，实例属于对象
静态先加载，实例后创建
静态访静态，实例访所有
工具用静态，业务用实例
```

---

**文档版本：** 1.0
**最后更新：** 2025-12-05
**作者：** AI Assistant

---

## 参考资料

- [Java Language Specification](https://docs.oracle.com/javase/specs/)
- [Effective Java (3rd Edition)](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)
- [Java Performance: The Definitive Guide](https://www.oreilly.com/library/view/java-performance-the/9781449363512/)

