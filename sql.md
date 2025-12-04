### MySQL SQL基础与进阶知识总结文档

---

#### 1. SQL基础语法与常见操作

**1.1 基本增删改查**
- **INSERT**  
  插入数据  
  `INSERT INTO table (col1, col2) VALUES (val1, val2);`
- **SELECT**  
  查询数据  
  `SELECT col1, col2 FROM table WHERE condition;`
- **UPDATE**  
  更新数据  
  `UPDATE table SET col1 = val1 WHERE condition;`
- **DELETE**  
  删除数据  
  `DELETE FROM table WHERE condition;`

**1.2 多表查询与JOIN**
- **INNER JOIN**  
  `SELECT ... FROM A JOIN B ON A.id = B.a_id WHERE ...;`
- **LEFT JOIN**  
  `SELECT ... FROM A LEFT JOIN B ON ... WHERE ...;`
- JOIN后，WHERE条件可以引用所有参与JOIN的表的字段。

**1.3 分组与聚合**
- **GROUP BY** 用于分组统计  
  `SELECT col, COUNT(*) FROM table GROUP BY col;`
- 可结合聚合函数如 `SUM()`, `AVG()`, `MIN()`, `MAX()`
- 可用 `HAVING` 对分组结果再过滤  
  `SELECT col, COUNT(*) FROM table GROUP BY col HAVING COUNT(*) > 5;`

**1.4 排序与分页**
- **ORDER BY** 排序  
  `SELECT ... FROM ... ORDER BY col DESC;`
- **LIMIT** 分页  
  `SELECT ... FROM ... LIMIT 10 OFFSET 20;`

---

#### 2. SQL语法书写与执行顺序

**书写顺序：**
```sql
SELECT ...
FROM ... [JOIN ...]
WHERE ...           -- 先过滤原始数据
GROUP BY ...        -- 再分组
HAVING ...          -- 分组后过滤
ORDER BY ...        -- 排序
LIMIT ...           -- 分页
```
**执行顺序：**  
FROM/JOIN → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT

---

#### 3. 常用函数

- **字符串函数:**  
  `CONCAT(a, b)`, `UPPER(str)`, `LOWER(str)`, `SUBSTRING(str, start, len)`, `LENGTH(str)`
- **日期函数:**  
  `NOW()`, `DATE_FORMAT(dt, '%Y-%m-%d')`, `YEAR(dt)`, `MONTH(dt)`, `DAY(dt)`, `DATEDIFF(a, b)`
- **数学函数:**  
  `ROUND(num, digits)`, `FLOOR(num)`, `CEIL(num)`, `ABS(num)`
- **条件与流程控制:**  
  `CASE WHEN ... THEN ... ELSE ... END`, `IFNULL(a, b)`, `COALESCE(a, b, ...)`

---

#### 4. 分组（GROUP BY）的理解与应用

- 用于将数据按指定字段分组，每组可用聚合函数统计。
- 只有分组字段和聚合函数能出现在SELECT中。
- 可多字段分组：`GROUP BY col1, col2`
- 典型业务场景：统计人数、平均值、总金额等。

---

#### 5. SQL练习题示例

- 按性别统计人数：  
  `SELECT gender, COUNT(*) FROM user GROUP BY gender;`
- 按城市统计人数和平均年龄：  
  `SELECT city, COUNT(*) AS cnt, AVG(age) FROM user GROUP BY city;`
- 按商品统计总销售额：  
  `SELECT product_id, SUM(price * quantity) FROM order_item GROUP BY product_id;`
- 按月统计订单数：  
  `SELECT DATE_FORMAT(create_time, '%Y-%m') AS month, COUNT(*) FROM orders GROUP BY month;`
- 按部门统计人数，人数大于5：  
  `SELECT department, COUNT(*) FROM employee GROUP BY department HAVING COUNT(*) > 5;`

---

#### 6. WHERE与GROUP BY的关系

- WHERE用于分组前过滤数据。
- GROUP BY用于分组。
- HAVING用于分组后过滤结果。

**举例：统计每个城市年龄大于20的用户数，只显示人数大于5的城市**
```sql
SELECT city, COUNT(*) AS cnt
FROM user
WHERE age > 20
GROUP BY city
HAVING COUNT(*) > 5;
```

---

#### 7. 事务与隔离级别

- **事务ACID特性：**  
  原子性、一致性、隔离性、持久性。
- **四种隔离级别：**  
  READ UNCOMMITTED（脏读）、READ COMMITTED（不可重复读）、REPEATABLE READ（MySQL默认，避免不可重复读）、SERIALIZABLE（可串行化，彻底避免幻读，性能最低）。
- **不可重复读：**  
  同一事务内多次读取同一行，结果不同。
- **幻读：**  
  同一事务内两次范围查询，结果行数不同（新增/删除）。
- **SERIALIZABLE隔离级别：**  
  所有事务串行执行，读写都加锁，性能最低但最安全。

---

#### 8. 自动提交与事务控制

- MySQL默认自动提交（autocommit=1），每条DML/DDL语句自动提交事务。
- 只有显式开启事务（`START TRANSACTION` 或 `SET autocommit=0`）才需手动COMMIT/ROLLBACK。
- DDL语句（如CREATE/ALTER/DROP）会隐式提交事务。

---

#### 9. SQL表设计与范式

- **三大范式：**
    1. 第一范式（字段不可再分）
    2. 第二范式（消除部分依赖）
    3. 第三范式（消除传递依赖）
- 实际建表时需考虑字段类型、主键、索引、外键等。

---

#### 10. 索引基础

- 用于加速查询，常建在WHERE/JOIN/ORDER BY常用字段上。
- 单列索引、联合索引、唯一索引。
- `EXPLAIN`可查看SQL执行计划和索引使用情况。

---

#### 11. 综合业务场景SQL示例

**统计每个用户本月下单的商品总数和总金额，只显示总金额大于1000元的前10名用户：**
```sql
SELECT
  u.id AS user_id,
  u.name,
  SUM(oi.quantity) AS total_products,
  SUM(oi.price * oi.quantity) AS total_amount
FROM
  user u
  JOIN orders o ON u.id = o.user_id
  JOIN order_item oi ON o.id = oi.order_id
WHERE
  o.create_time >= '2025-12-01' AND o.create_time < '2026-01-01'
GROUP BY
  u.id, u.name
HAVING
  total_amount > 1000
ORDER BY
  total_amount DESC
LIMIT 10;
```

---

#### 12. 学习建议与练习路径

1. 熟练基础语法（增删改查、分组、聚合、排序、分页）。
2. 多表JOIN与复杂条件筛选。
3. 理解事务和隔离级别，实际演练事务一致性。
4. 表设计与索引优化，理解范式。
5. 按业务场景写完整SQL，结合EXPLAIN分析性能。
6. 逐步练习窗口函数、子查询、复杂分组统计等进阶内容。

---
