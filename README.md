Internationalization (i18n) is so boring. There is not one single best
practice. And they are all just a pain in the ass when your `String` is
no more a String but something like a `Map<Locale, String>`. Bye JSR-303
validation annotations. So let's try to find a solution which keeps i18n
simple, without polluting your domain model and keep the type of the 
properties:

```java
@Entity
class Book {

    @Id
    private int id;

    private Author author;

    @Localized
    private String title;

}
```

`@Localized` annotates the property `Book.title` as a translatable `String`.
That looks promising. But how does Hibernate know which `Locale` is relevant?
That's the job of a `LocalizedConfiguration` implementation. Implement one or
choose one of the existing in the package `de.malkusch.localized.configuration`.
Register your implementation statically at `ListenerIntegrator.setConfiguration()`:
```java
ListenerIntegrator.setConfiguration(new ThreadLocalLocalizedConfiguration());
```

If you are in a magic Spring world you should insert `SpringLocalizedConfiguration`
into the application context:
```java
@Configuration
public class I18nConfiguration {

    @Bean public LocalizedConfiguration localizedConfiguration() {
        return new SpringLocalizedConfiguration();
    }

}
```

# Maven
You find this package in my maven repository: http://mvn.malkusch.de
```xml
<repositories>
    <repository>
        <id>malkusch.de</id>
        <url>http://mvn.malkusch.de/</url>
    </repository>
</repositories>
```

Add the following dependency to your pom.xml
```xml
<dependency>
    <groupId>de.malkusch</groupId>
    <artifactId>localized</artifactId>
    <version>0.0.1</version>
</dependency>
```

# Concept
I tried my best to stay in the JPA layer, but I failed hard. So for now
this extension works in Hibernate's Session world. The mechanism is 
straight forward. Each `@Localized` field is backed by a `LocalizedProperty`
entity. Hibernate's event system provides the infrastructure for replacing and
storing transparently the `@Localized` fields. You notice that this concept
increases the Session communication. Don't use `@Localized` when performance
is a concern.

# Limitations
I18n happens around Hibernate's `POST_LOAD`, `POST_UPDATE`, `POST_INSERT` and `POST_DELETE`
events. In between happens nothing. I.e. you have to fix the locale at Session begin
(before the first `POST_LOAD` event on a localized entity). If you change the locale during
a session you have to synchronize the entities with `Session.flush()` and `Session.refresh(Object)`.

# Disclaimer
I'm currently using this i18n method in a project which is under development.
There is no practical knowledge about stability, scalabilty or performance.
Plus I don't have isolated test cases. Use it at your own risk! 
