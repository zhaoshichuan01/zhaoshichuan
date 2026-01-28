# Maven 核心知识点总结文档
## 一、Maven 核心概念
### 1. 什么是Maven
Maven是一款基于Java的**项目管理和构建自动化工具**，核心解决项目构建过程中的依赖管理、项目标准化构建、多模块项目协调等问题，简化开发流程，统一项目结构和构建规范。

### 2. 核心核心思想
- **约定优于配置（CoC）**：定义统一的项目目录结构、构建生命周期、插件执行规则，无需手动大量配置，遵循约定即可快速构建。
- **依赖管理**：通过坐标唯一标识依赖，自动下载、管理依赖包及依赖的传递性，解决依赖冲突问题。
- **生命周期与插件**：将项目构建拆分为标准化生命周期，每个生命周期阶段由对应插件完成具体操作，插件可复用、可扩展。

### 3. 核心术语
| 术语         | 核心说明                                                                 |
|--------------|--------------------------------------------------------------------------|
| **坐标（GAV）** | 唯一标识Maven项目/依赖，`GroupId:ArtifactId:Version`（必要），可扩展`Packaging`/`Classifier` |
| **仓库**     | 存储依赖包和项目构建产物的位置，分为本地仓库、远程仓库（中央/私服/第三方）|
| **POM**      | 项目对象模型（Project Object Model），核心配置文件`pom.xml`，描述项目信息、依赖、构建配置等 |
| **依赖传递** | 项目引入的依赖会自动引入其自身的依赖，Maven默认解析并下载传递性依赖       |
| **依赖范围** | 控制依赖在项目不同构建生命周期（编译/测试/运行）的生效范围，如`compile`/`test`/`provided` |
| **生命周期** | 标准化的项目构建流程，分为**清洁生命周期（clean）**、**默认生命周期（default）**、**站点生命周期（site）** |
| **插件**     | 执行Maven构建具体操作的组件，如编译插件`maven-compiler-plugin`、打包插件`maven-jar-plugin` |
| **多模块项目** | 将大型项目拆分为多个子模块，通过父POM统一管理依赖、版本，子模块相互依赖、协同构建 |

## 二、Maven 核心配置（pom.xml）
`pom.xml`是Maven项目的核心，所有项目配置、依赖管理、构建规则均通过该文件定义，以下为核心配置节点。

### 1. 基础坐标配置（必选）
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- POM模型版本，固定为4.0.0 -->
    <modelVersion>4.0.0</modelVersion>
    <!-- 集团/公司/组织标识，反向域名命名，如com.xxx -->
    <groupId>com.example</groupId>
    <!-- 项目/模块名称，唯一标识groupId下的项目 -->
    <artifactId>maven-demo</artifactId>
    <!-- 项目版本，RELEASE(正式版)/SNAPSHOT(快照版)/Beta(测试版) -->
    <version>1.0.0-SNAPSHOT</version>
    <!-- 打包类型，默认jar，可选war/pom/ear等 -->
    <packaging>jar</packaging>
    <!-- 项目基础信息 -->
    <name>maven-demo</name>
    <description>Maven核心演示项目</description>
</project>
```

### 2. 依赖管理核心配置
#### （1）基础依赖配置`dependencies`
```xml
<dependencies>
    <!-- 单个依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.20</version>
        <!-- 依赖范围，核心属性 -->
        <scope>compile</scope>
        <!-- 排除传递性依赖，解决依赖冲突 -->
        <exclusions>
            <exclusion>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
            </exclusion>
        </exclusions>
        <!-- 是否可选依赖，避免传递给子项目 -->
        <optional>false</optional>
    </dependency>
    <!-- 测试依赖，仅测试阶段生效 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### （2）依赖范围详解（核心）
| 范围        | 编译阶段 | 测试阶段 | 运行阶段 | 传递性 | 核心使用场景                     |
|-------------|----------|----------|----------|--------|----------------------------------|
| `compile`   | ✅        | ✅        | ✅        | ✅      | 项目核心依赖，如Spring核心包      |
| `test`      | ❌        | ✅        | ❌        | ❌      | 测试框架依赖，如JUnit、TestNG    |
| `provided`  | ✅        | ✅        | ❌        | ❌      | 容器/服务器提供的依赖，如servlet-api |
| `runtime`   | ❌        | ✅        | ✅        | ✅      | 运行时依赖，如数据库驱动mysql-connector-java |
| `system`    | ✅        | ✅        | ❌        | ❌      | 本地系统依赖，需配合`systemPath`使用（不推荐） |
| `import`    | -        | -        | -        | -      | 仅在`dependencyManagement`中使用，导入外部POM的依赖管理 |

#### （3）统一版本管理`dependencyManagement`
**作用**：统一管理项目/多模块的依赖版本，子模块引入依赖时无需指定版本，便于版本统一维护，**仅做版本管理，不自动引入依赖**。
```xml
<dependencyManagement>
    <dependencies>
        <!-- 管理Spring核心依赖版本 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.20</version>
        </dependency>
        <!-- 导入第三方依赖管理POM（如Spring Boot） -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.6.8</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3. 构建配置`build`
控制Maven构建过程，指定源码目录、输出目录、插件配置等，核心节点`sourceDirectory`、`plugins`、`pluginManagement`。
```xml
<build>
    <!-- 项目构建最终产物名称 -->
    <finalName>maven-demo-1.0.0</finalName>
    <!-- 源码目录，默认src/main/java -->
    <sourceDirectory>src/main/java</sourceDirectory>
    <!-- 测试源码目录，默认src/test/java -->
    <testSourceDirectory>src/test/java</testSourceDirectory>
    <!-- 资源文件目录，默认src/main/resources -->
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <!-- 开启资源文件过滤，替换${属性} -->
            <filtering>true</filtering>
        </resource>
    </resources>
    <!-- 插件配置 -->
    <plugins>
        <!-- 编译插件，指定JDK版本 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>8</source> <!-- 源码JDK版本 -->
                <target>8</target> <!-- 编译后字节码JDK版本 -->
                <encoding>UTF-8</encoding> <!-- 编码 -->
            </configuration>
        </plugin>
        <!-- 打包插件 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.2.0</version>
        </plugin>
    </plugins>
    <!-- 插件版本管理，统一管理插件版本，子模块直接使用 -->
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

### 4. 多模块项目配置
#### （1）父项目配置（打包类型为`pom`）
```xml
<!-- 父项目pom.xml -->
<packaging>pom</packaging>
<!-- 声明子模块，子模块为项目根目录下的子文件夹 -->
<modules>
    <module>maven-demo-core</module>
    <module>maven-demo-web</module>
</modules>
<!-- 父项目统一管理依赖和插件版本 -->
<dependencyManagement>
    <dependencies>
        <!-- 子模块核心依赖版本 -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>maven-demo-core</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### （2）子模块配置
通过`parent`节点继承父项目，无需重复配置公共依赖、版本，可按需引入其他子模块。
```xml
<!-- 子模块maven-demo-web的pom.xml -->
<parent>
    <groupId>com.example</groupId>
    <artifactId>maven-demo</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <!-- 父项目pom.xml相对路径，若在同一目录则为../pom.xml -->
    <relativePath>../pom.xml</relativePath>
</parent>
<!-- 子模块坐标，无需指定groupId和version，继承父项目 -->
<artifactId>maven-demo-web</artifactId>
<packaging>war</packaging>
<!-- 引入其他子模块依赖 -->
<dependencies>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>maven-demo-core</artifactId>
    </dependency>
</dependencies>
```

## 三、Maven 仓库体系
### 1. 仓库分类
| 仓库类型   | 核心说明 | 默认路径/地址 |
|------------|----------|---------------|
| **本地仓库** | 位于开发者本地的仓库，缓存远程仓库下载的依赖，优先从本地仓库查找依赖 | Windows：`C:\Users\用户名\.m2\repository`；Linux/Mac：`~/.m2/repository` |
| **中央仓库** | Maven官方维护的远程仓库，存储海量开源依赖，Maven默认远程仓库 | https://repo.maven.apache.org/maven2 |
| **私服仓库** | 企业内部搭建的远程仓库，用于存储企业内部私有依赖、缓存中央仓库依赖，统一企业依赖管理 | 如Nexus、Artifactory搭建的内部地址，如http://ip:port/repository/maven-public/ |
| **第三方仓库** | 非官方的远程仓库，用于存储中央仓库未收录的依赖，如阿里云镜像、Spring仓库 | 阿里云镜像：https://maven.aliyun.com/repository/public |

### 2. 仓库配置
通过`settings.xml`（Maven全局配置）或`pom.xml`（项目局部配置）配置仓库，**推荐使用settings.xml统一配置**。
#### （1）全局配置（settings.xml，位于`Maven安装目录/conf`或`~/.m2/`）
```xml
<settings>
    <!-- 本地仓库路径配置 -->
    <localRepository>D:\maven\repository</localRepository>
    <!-- 远程仓库镜像，优先访问镜像仓库（如阿里云），替代中央仓库 -->
    <mirrors>
        <mirror>
            <id>aliyunmaven</id>
            <mirrorOf>central</mirrorOf> <!-- 镜像中央仓库 -->
            <url>https://maven.aliyun.com/repository/public</url>
        </mirror>
    </mirrors>
    <!-- 配置私服仓库的服务器信息（如账号密码） -->
    <servers>
        <server>
            <id>private-repo</id> <!-- 与仓库id一致 -->
            <username>admin</username>
            <password>123456</password>
        </server>
    </servers>
    <!-- 配置仓库组，聚合多个仓库 -->
    <profiles>
        <profile>
            <id>dev-repo</id>
            <repositories>
                <repository>
                    <id>private-repo</id>
                    <url>http://192.168.1.100:8081/repository/maven-public/</url>
                    <releases><enabled>true</enabled></releases>
                    <snapshots><enabled>true</enabled></snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <!-- 激活仓库配置 -->
    <activeProfiles>
        <activeProfile>dev-repo</activeProfile>
    </activeProfiles>
</settings>
```

## 四、Maven 生命周期与常用命令
### 1. 三大核心生命周期
Maven的生命周期是**标准化、可叠加**的，执行某个阶段时，会自动执行该阶段之前的所有阶段，核心分为三大生命周期，彼此独立。
| 生命周期   | 核心作用 | 核心阶段（按执行顺序） |
|------------|----------|------------------------|
| **clean**  | 清理项目构建产物 | `pre-clean` → `clean`（删除target目录） → `post-clean` |
| **default**（核心） | 项目构建的核心流程，如编译、测试、打包、部署 | `compile`（编译源码）→ `test-compile`（编译测试源码）→ `test`（执行测试）→ `package`（打包）→ `install`（安装到本地仓库）→ `deploy`（部署到远程仓库） |
| **site**   | 生成项目站点文档 | `pre-site` → `site`（生成文档） → `post-site` → `site-deploy`（部署文档） |

### 2. 常用Maven命令（核心）
命令均在项目根目录（含pom.xml）执行，**命令区分大小写**，核心命令如下：
| 命令                | 核心作用 | 所属生命周期 | 关键说明 |
|---------------------|----------|--------------|----------|
| `mvn clean`         | 清理项目 | clean        | 删除target目录（构建产物） |
| `mvn compile`       | 编译源码 | default      | 编译src/main/java，产物输出到target/classes |
| `mvn test`          | 执行测试 | default      | 先编译再执行src/test/java的测试用例，需引入测试依赖 |
| `mvn package`       | 项目打包 | default      | 先测试再打包，产物为jar/war，输出到target目录 |
| `mvn install`       | 安装到本地仓库 | default | 先打包再将产物安装到本地仓库，供本地其他项目依赖 |
| `mvn deploy`        | 部署到远程仓库 | default | 先install再将产物部署到私服/远程仓库，需配置远程仓库信息 |
| `mvn clean package` | 清理并打包 | clean+default | 组合命令，先执行clean，再执行package，跳过已完成的阶段 |
| `mvn package -DskipTests` | 打包并跳过测试 | default | 跳过test阶段，快速打包，适用于开发阶段 |
| `mvn dependency:tree` | 查看依赖树 | 插件命令 | 展示项目所有依赖（含传递性依赖），用于排查依赖冲突 |
| `mvn help:describe -Dplugin=compiler -Ddetail` | 查看插件详情 | 插件命令 | 查看指定插件的功能、配置参数等 |

### 3. 命令行参数（常用）
- `-DskipTests`：跳过测试用例执行（不编译测试代码）
- `-Dmaven.test.skip=true`：跳过测试代码编译和执行
- `-Pxxx`：激活指定的Profile（如开发环境/生产环境）
- `-U`：强制更新快照版依赖（SNAPSHOT）
- `-X`：开启调试模式，打印详细的构建日志（排查问题用）
- `-e`：开启错误日志，打印简洁的错误信息

## 五、Maven 依赖冲突及解决
### 1. 依赖冲突产生原因
Maven的**传递性依赖**导致项目中引入多个版本的同一依赖，如项目引入A（依赖B1.0）和C（依赖B2.0），此时项目中同时存在B1.0和B2.0，引发冲突。

### 2. Maven 依赖解析规则（默认）
Maven会通过以下规则自动选择依赖版本，避免冲突，**规则优先级从高到低**：
1. **就近原则**：依赖树中，离项目层级越近的版本优先（如项目直接引入的版本 > 传递性依赖的版本）
2. **声明优先原则**：同一层级的依赖，在`dependencies`中声明靠前的版本优先
3. **版本仲裁原则**：若以上规则无法判断，Maven默认选择**最高版本**

### 3. 依赖冲突排查与解决
#### （1）排查：查看依赖树
使用命令`mvn dependency:tree`打印项目依赖树，查找冲突的依赖，例如：
```
[INFO] +- org.springframework:spring-context:jar:5.3.20:compile
[INFO] |  +- org.springframework:spring-core:jar:5.3.20:compile
[INFO] |  |  \- org.springframework:spring-jcl:jar:5.3.20:compile
[INFO] |  +- org.springframework:spring-beans:jar:5.3.20:compile
[INFO] |  +- org.springframework:spring-expression:jar:5.3.20:compile
[INFO] |  \- commons-logging:commons-logging:jar:1.2:compile  <!-- 冲突依赖 -->
```

#### （2）解决方式（核心）
##### 方式1：排除传递性依赖（最常用）
在引入的依赖中，通过`exclusions`排除冲突的传递性依赖，示例：
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.20</version>
    <exclusions>
        <!-- 排除冲突的commons-logging -->
        <exclusion>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

##### 方式2：直接引入指定版本依赖
利用**就近原则**，在项目`dependencies`中直接引入冲突依赖的指定版本，Maven会优先选择该版本，示例：
```xml
<!-- 直接引入指定版本，解决冲突 -->
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.1.1</version>
</dependency>
```

##### 方式3：通过`dependencyManagement`统一版本
在父POM的`dependencyManagement`中统一声明冲突依赖的版本，子模块引入时无需指定版本，强制统一，示例：
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.1.1</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 六、Maven 常用插件推荐
Maven的所有构建操作均由插件完成，以下为开发中最常用的核心插件：
| 插件GAV | 插件名称 | 核心作用 | 常用配置 |
|---------|----------|----------|----------|
| `org.apache.maven.plugins:maven-compiler-plugin` | 编译插件 | 指定JDK编译版本、编码 | `source`/`target`/`encoding` |
| `org.apache.maven.plugins:maven-jar-plugin` | JAR打包插件 | 生成JAR包，配置JAR包信息 | `finalName`/`archive`（清单文件） |
| `org.apache.maven.plugins:maven-war-plugin` | WAR打包插件 | 生成WAR包，适配Web项目 | `warSourceDirectory`（Web源码目录） |
| `org.apache.maven.plugins:maven-surefire-plugin` | 测试插件 | 执行JUnit/TestNG测试用例 | `skipTests`（跳过测试） |
| `org.apache.maven.plugins:maven-dependency-plugin` | 依赖插件 | 查看依赖树、复制依赖、分析依赖 | `dependency:tree`/`dependency:copy-dependencies` |
| `org.apache.maven.plugins:maven-resources-plugin` | 资源插件 | 处理资源文件，过滤属性 | `filtering`（开启过滤）/`encoding` |
| `org.springframework.boot:spring-boot-maven-plugin` | Spring Boot打包插件 | 生成可执行JAR/WAR包，启动Spring Boot项目 | `repackage`（重新打包） |

## 七、Maven 开发常见问题与解决方案
### 1. 依赖下载失败
- **原因**：网络问题、仓库配置错误、依赖包不存在、本地仓库缓存损坏
- **解决方案**：
    1. 配置国内镜像（如阿里云），提高下载速度；
    2. 执行`mvn clean -U`强制更新依赖；
    3. 删除本地仓库对应依赖的缓存目录（如`~/.m2/repository/com/example/xxx`），重新下载；
    4. 检查依赖GAV坐标是否正确，确认远程仓库存在该依赖。

### 2. 编译报错：无效的目标发行版/源发行版
- **原因**：maven-compiler-plugin指定的JDK版本与本地安装的JDK版本不一致
- **解决方案**：
    1. 检查本地JDK版本（`java -version`）；
    2. 在pom.xml中配置maven-compiler-plugin，指定与本地一致的JDK版本；
    3. 确认IDE的Maven运行JDK与项目JDK一致。

### 3. 测试用例执行失败：找不到测试类
- **原因**：测试类命名不符合Maven约定、测试方法未加注解、测试依赖范围错误
- **解决方案**：
    1. 测试类命名遵循`XxxTest`，测试方法加`@Test`注解（JUnit）；
    2. 测试依赖（如JUnit）的scope为`test`，不可改为`compile`；
    3. 确认测试源码在`src/test/java`目录下，IDE已识别该目录为测试源码目录。

### 4. 多模块项目构建失败：找不到子模块依赖
- **原因**：子模块未先安装到本地仓库、父项目modules声明错误、子模块parent坐标错误
- **解决方案**：
    1. 先执行`mvn clean install`构建父项目和所有子模块，将子模块安装到本地仓库；
    2. 检查父项目`modules`中的子模块名称与实际文件夹名称一致；
    3. 检查子模块`parent`节点的`groupId`/`artifactId`/`version`/`relativePath`是否正确。

### 5. 可执行JAR包运行失败：找不到主类
- **原因**：未指定主类、打包插件配置错误（如普通jar-plugin替代spring-boot-maven-plugin）
- **解决方案**：
    1. 普通Java项目：在MANIFEST.MF中指定主类（`Main-Class: com.example.Main`）；
    2. Spring Boot项目：使用`spring-boot-maven-plugin`，插件会自动配置主类，执行`mvn clean package`生成可执行JAR。

## 八、Maven 与 IDE 集成（IDEA/Eclipse）
### 1. IDEA 集成Maven
- **配置Maven**：File → Settings → Build, Execution, Deployment → Build Tools → Maven，配置`Maven home directory`（Maven安装目录）、`User settings file`（settings.xml）、`Local repository`（本地仓库）；
- **导入Maven项目**：File → Open → 选择项目根目录的pom.xml，选择`Open as Project`；
- **常用操作**：右侧Maven面板可执行clean/compile/package/install等命令，可快速查看依赖、刷新项目。

### 2. Eclipse 集成Maven
- **配置Maven**：Window → Preferences → Maven → Installations，添加Maven安装目录；Maven → User Settings，配置settings.xml和本地仓库；
- **导入Maven项目**：File → Import → Maven → Existing Maven Projects，选择项目根目录，勾选pom.xml；
- **常用操作**：右键项目 → Run As → Maven build，输入目标命令（如clean package）执行。

### 3. 核心注意点
- IDE的Maven配置需**与本地Maven配置一致**，避免出现“IDE编译正常，命令行编译失败”的问题；
- 项目刷新：修改pom.xml后，需在IDE中刷新Maven项目（IDEA：Reload Project；Eclipse：Update Project），使配置生效；
- 统一编码：在pom.xml和IDE中均配置UTF-8编码，避免中文乱码。

---
### 文档说明
本文档为Maven核心知识点的汇总，涵盖Maven基础概念、核心配置、仓库、生命周期、命令、依赖管理、常见问题等内容，适用于Java开发人员快速入门和日常开发参考，重点突出**实用配置**和**解决问题的方法**，简化理论，侧重实战。

我可以帮你将这份文档转换成**Markdown可直接复制的纯文本格式**，也可以根据你的实际开发需求（如Spring Boot整合Maven、多模块实战）补充具体内容，需要吗？