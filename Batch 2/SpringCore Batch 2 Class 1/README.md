# Spring Core – Batch 2, Class 1

Notes and a working example covering the core ideas of the Spring Framework: **IoC, Dependency Injection, the Spring Container, bean annotations, bean scopes and the bean lifecycle**.

Everything below is explained using the classes in this project (`Laptop`, `Student`, `User`, `AppConfig`, `Main`). Where the project does not demonstrate a topic yet, a short snippet built on the **same classes** is shown and marked **➕ Extension**.

---

## Table of Contents

1. [The Example Code](#the-example-code)
2. [1. IoC (Inversion of Control)](#1-ioc-inversion-of-control)
3. [2. DI (Dependency Injection)](#2-di-dependency-injection)
4. [3. Spring Container](#3-spring-container)
5. [4. Bean Creation Annotations](#4-bean-creation-annotations)
6. [5. Bean Configuration Annotations](#5-bean-configuration-annotations)
7. [6. Bean Scope](#6-bean-scope)
8. [7. Bean Lifecycle](#7-bean-lifecycle)
9. [Quick Revision Cheat Sheet](#quick-revision-cheat-sheet)

---

## The Example Code

**`Laptop.java`** – the dependency
```java
public class Laptop {
    public void building() {
        System.out.println("Building Project");
    }
}
```

**`Student.java`** – needs a `Laptop` to work
```java
public class Student {

    private Laptop laptop;

    @Autowired
    public Student(Laptop laptop) {
        this.laptop = laptop;
    }

    public void learn() {
        laptop.building();
        System.out.println("Learning");
    }
}
```

**`User.java`** – plain class with `name` and `roll` (getters/setters + constructor)

**`AppConfig.java`** – tells Spring which beans to create
```java
@Configuration
@ComponentScan("org.example")
public class AppConfig {

    @Bean
    public User doesnotmatter() {
        return new User("swap", "21");
    }

    @Bean
    public Laptop laptop() {
        return new Laptop();
    }

    @Bean
    public Student student(Laptop laptop) {
        return new Student(laptop);
    }
}
```

**`Main.java`** – starts the container and uses the beans
```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

Student student = context.getBean(Student.class);
student.learn();

User user = context.getBean(User.class);
System.out.println(user.getName());
System.out.println(user.getRoll());
```
---

## 1. IoC (Inversion of Control)

**Definition:** Normally *your code* creates and controls its objects. With IoC, that control is **inverted** – the **Spring container** creates, wires and manages the objects for you.

**Without IoC (traditional way):**
```java
Laptop laptop = new Laptop();
Student student = new Student(laptop);   // we create and wire everything ourselves
student.learn();
```

**With IoC (this project):**
```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

Student student = context.getBean(Student.class);   // Spring built it for us
student.learn();
```

In `Main.java` we never write `new Student(...)` or `new Laptop()`. We only *ask* the container for a `Student`. Spring decides **when** to create it and **what** to give it.

**Why it matters:**
- Loose coupling – `Student` does not decide *which* `Laptop` it gets
- Easier testing (swap in a fake `Laptop`)
- Less boilerplate, centralized configuration

> **IoC is the principle. Dependency Injection (next section) is the technique Spring uses to achieve it.**

---

## 2. DI (Dependency Injection)

**Definition:** DI is the process of the container **supplying (injecting) the dependencies** an object needs, instead of the object creating them itself.

Here, `Student` **depends on** `Laptop`. Spring injects the `Laptop` into it.

### a. Constructor Injection *(used in this project)*

The dependency is passed through the **constructor**.

```java
public class Student {

    private Laptop laptop;

    @Autowired                              // tells Spring: "inject Laptop here"
    public Student(Laptop laptop) {
        this.laptop = laptop;
    }
}
```

and in `AppConfig`:
```java
@Bean
public Student student(Laptop laptop) {     // Spring passes the Laptop bean in
    return new Student(laptop);
}
```

**Why it's the recommended approach:**
- The object can never exist in a half-built state (the dependency is mandatory)
- Fields can be `final` (immutable)
- Easy to unit test without Spring

> Since Spring 4.3, if a class has **only one constructor**, `@Autowired` is optional.

### b. Setter Injection  ➕ *Extension*

The dependency is passed through a **setter method**, after the object is created.

```java
public class Student {

    private Laptop laptop;

    @Autowired
    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public void learn() {
        laptop.building();
        System.out.println("Learning");
    }
}
```

```java
@Bean
public Student student(Laptop laptop) {
    Student s = new Student();      // needs a no-arg constructor
    s.setLaptop(laptop);
    return s;
}
```

### Constructor vs Setter – when to use which

| | Constructor Injection | Setter Injection |
|---|---|---|
| Dependency type | **Mandatory** | **Optional** |
| Object state | Always fully initialized | May be partially initialized |
| Immutability | Fields can be `final` | Not possible |
| Circular dependencies | Not supported | Can work |
| Recommended | Default choice | Only for optional deps |

---

## 3. Spring Container

The **Spring Container** is the core of Spring. It:
1. Reads configuration (`AppConfig`)
2. Creates the beans
3. Injects dependencies
4. Manages the full lifecycle until shutdown

Spring provides two container types.

### a. BeanFactory

- The **basic / root** container (`org.springframework.beans.factory.BeanFactory`)
- Provides only core DI features
- **Lazy** – beans are created only when requested via `getBean()`
- Lightweight; rarely used directly in modern apps

### b. ApplicationContext *(used in this project)*

- A **sub-interface** of `BeanFactory` – everything BeanFactory has, plus more
- **Eager** – singleton beans are created at startup
- Adds: annotation support, internationalization (i18n), event publishing, easy integration with Spring AOP, etc.

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

`AnnotationConfigApplicationContext` is the `ApplicationContext` implementation that reads **Java-based configuration** (`@Configuration` classes).

| Feature | BeanFactory | ApplicationContext |
|---|---|---|
| Bean instantiation | Lazy (on demand) | Eager (at startup) for singletons |
| Annotation support | Limited | Full |
| i18n, events | No | Yes |
| Typical use | Very low-memory environments | **Almost everything** |

> Rule of thumb: **always use `ApplicationContext`.**

---

## 4. Bean Creation Annotations

A **bean** is simply an object that is created and managed by the Spring container.

These annotations are placed **on a class** so that Spring detects it during component scanning (see `@ComponentScan` in the next section) and registers it as a bean.

### a. `@Component`
Generic stereotype for any Spring-managed class.

```java
@Component
public class Laptop {
    public void building() {
        System.out.println("Building Project");
    }
}
```
`@Component` is a class-level annotation that tells Spring to automatically detect the class during component scanning, create an object of it using its constructor, and manage that object as a bean in the container. Spring does the creation, so every constructor argument must be something Spring can supply, such as another bean.

### b. `@Service`
Same as `@Component`, but marks the **business / service layer**.

```java
@Service
public class StudentService { ... }
```

### c. `@Repository`
Marks the **data-access (DAO) layer**. Also translates database exceptions into Spring's `DataAccessException`.

```java
@Repository
public class StudentRepository { ... }
```

### d. `@Controller`
Marks a **web layer** class that handles HTTP requests (Spring MVC).

```java
@Controller
public class StudentController { ... }
```

> `@Service`, `@Repository` and `@Controller` are all **specializations of `@Component`**. Functionally they all create a bean – the different names just communicate the layer and allow layer-specific features.

### e. `@Autowired`
Not a creation annotation – it **injects** an existing bean into another. *(used in `Student.java`)*

```java
@Autowired
public Student(Laptop laptop) {
    this.laptop = laptop;
}
```

It can be placed on a **constructor**, **setter** or **field**.

> **Note about this project:** `Student.java` imports `@Component`, `@Service`, `@Repository` and `@Controller`, but doesn't use them – `Student` is registered through `@Bean` in `AppConfig` instead. If you annotate `Student` with `@Component` **and** keep the `@Bean student(...)` method, you'd get two `Student` beans and `getBean(Student.class)` would fail with `NoUniqueBeanDefinitionException`. Pick **one** way per class.

### ➕ Extension: same project using annotations only

```java
@Component
public class Laptop { ... }

@Component
public class Student {
    private final Laptop laptop;

    @Autowired
    public Student(Laptop laptop) { this.laptop = laptop; }
    ...
}
```

```java
@Configuration
@ComponentScan("org.example")
public class AppConfig { }       // no @Bean methods needed
```

---

## 5. Bean Configuration Annotations

These annotations are used in **Java configuration classes** (`AppConfig`) to tell Spring *how* to create beans. All three are used in this project.

### a. `@Configuration`
Marks a class as a **source of bean definitions**.

```java
@Configuration
public class AppConfig { ... }
```

### b. `@Bean`
Placed on a **method**; the returned object is registered as a bean in the container.

```java
@Bean
public Laptop laptop() {
    return new Laptop();
}
```

- The **bean name = method name** by default (`laptop`).
- Great for classes you **can't edit** (third-party libraries) or when you need custom construction logic.

Look at this one in the project:
```java
@Bean
public User doesnotmatter() {
    return new User("swap", "21");
}
```
The bean is named `doesnotmatter`, but `Main` fetches it by **type** (`getBean(User.class)`), so the name doesn't matter – hence the method name!

> `@Bean` is a method-level annotation, used inside a @Configuration class. It tells Spring that the object returned by the method should be registered and managed as a bean. You write the creation code yourself, so you control which values or setup the object gets. This makes it the right choice for classes that need custom values, such as User("swap", "21"), or classes from libraries you can’t edit.

Dependencies between beans are resolved through **method parameters**:
```java
@Bean
public Student student(Laptop laptop) {   // Spring injects the "laptop" bean
    return new Student(laptop);
}
```

### c. `@ComponentScan`
Tells Spring **which packages to scan** for `@Component`, `@Service`, `@Repository`, `@Controller` classes.

```java
@ComponentScan("org.example")
```

### `@Bean` vs `@Component`

| | `@Component` family | `@Bean` |
|---|---|---|
| Placed on | **Class** | **Method** (inside `@Configuration`) |
| Detected by | `@ComponentScan` | `@Configuration` class |
| Use when | You own the class | Third-party class / custom setup |

---

## 6. Bean Scope

**Scope** defines **how many instances** of a bean Spring creates and how long they live. Set it with `@Scope`.

```java
@Scope(value = "singleton")
@Scope(value = "prototype")
```

### 1. `singleton` (default)

**One shared instance** per container. Every request for the bean returns the *same* object.

```java
@Bean
@Scope(value = "singleton")      // default – can be omitted
public Laptop laptop() {
    return new Laptop();
}
```

```java
Laptop l1 = context.getBean(Laptop.class);
Laptop l2 = context.getBean(Laptop.class);
System.out.println(l1 == l2);    // true  → same object
```

### 2. `prototype`

A **new instance every time** the bean is requested.

```java
@Bean
@Scope(value = "prototype")
public Laptop laptop() {
    return new Laptop();
}
```

```java
Laptop l1 = context.getBean(Laptop.class);
Laptop l2 = context.getBean(Laptop.class);
System.out.println(l1 == l2);    // false → different objects
```

### Comparison

| | singleton | prototype |
|---|---|---|
| Instances | 1 per container | New one per request |
| Created | At startup (eager) | When requested (lazy) |
| Lifecycle managed by Spring | Fully (including destroy) | Only up to creation – **`@PreDestroy` is not called** |
| Use for | Stateless services, repositories | Stateful objects |

> In web applications there are more scopes: `request`, `session`, `application`, `websocket`.

---

## 7. Bean Lifecycle

Spring manages a bean from creation to destruction. You can hook into two important moments.

```
Container starts
      │
      ▼
 Instantiate bean              (constructor runs)
      │
      ▼
 Inject dependencies           (@Autowired / @Bean params)
      │
      ▼
 @PostConstruct method runs    ← initialization hook
      │
      ▼
 Bean is READY to use
      │
      ▼
 Container closes
      │
      ▼
 @PreDestroy method runs       ← cleanup hook
      │
      ▼
 Bean destroyed
```

### a. `@PostConstruct`
Runs **once, after** the bean is created and its dependencies are injected. Use it for initialization (open connections, load data, etc.).

### b. `@PreDestroy`
Runs **just before** the bean is destroyed when the container shuts down. Use it for cleanup (close connections, release resources).

### ➕ Extension: applying it to `Laptop`

```java
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Laptop {

    @PostConstruct
    public void init() {
        System.out.println("Laptop is starting up...");
    }

    public void building() {
        System.out.println("Building Project");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Laptop is shutting down...");
    }
}
```

To see `@PreDestroy` fire, close the context in `Main`:

```java
AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

// ... use beans ...

context.close();    // triggers @PreDestroy
```

**Output:**
```
Laptop is starting up...
Building Project
Learning
swap
21
Laptop is shutting down...
```

> **Dependency note:** In Spring 6 / 7, these annotations come from **Jakarta** (`jakarta.annotation.*`), not the old `javax.annotation.*`. If your IDE can't find them, add this to `pom.xml`:
> ```xml
> <dependency>
>     <groupId>jakarta.annotation</groupId>
>     <artifactId>jakarta.annotation-api</artifactId>
>     <version>3.0.0</version>
> </dependency>
> ```
>
> Because `Laptop` is created with `@Bean`, these annotations are still honored by Spring. Alternatively, use `@Bean(initMethod = "init", destroyMethod = "cleanup")`.

---

## Quick Revision Cheat Sheet

| Topic | One-line summary | Seen in this project |
|---|---|---|
| **IoC** | Container, not your code, creates and controls objects | `Main` never uses `new Student()` |
| **DI** | Container supplies a class's dependencies | `Student(Laptop laptop)` |
| **Constructor Injection** | Dependency via constructor (preferred) | `Student` + `@Autowired` |
| **Setter Injection** | Dependency via setter (optional deps) | ➕ extension |
| **BeanFactory** | Basic, lazy container | – |
| **ApplicationContext** | Advanced, eager container (use this) | `AnnotationConfigApplicationContext` |
| **@Component / @Service / @Repository / @Controller** | Class-level bean detection | imported only |
| **@Autowired** | Inject a bean | `Student` constructor |
| **@Configuration** | Class that defines beans | `AppConfig` |
| **@Bean** | Method whose return value is a bean | `laptop()`, `student()`, `doesnotmatter()` |
| **@ComponentScan** | Packages to scan for stereotype beans | `@ComponentScan("org.example")` |
| **@Scope** | `singleton` (default) / `prototype` | ➕ extension |
| **@PostConstruct / @PreDestroy** | Init and cleanup hooks | ➕ extension |
