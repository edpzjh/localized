# @Localized
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

## LocaleResolver
That's the job of a `LocaleResolver` implementation. Implement or choose one
from the package `de.malkusch.localized.localeResolver`. There are several
ways of registering the LocaleResolver:

Specify the fully qualified class name in the hibernate property 
*hibernate.listeners.localized.locale_resolver*:
```xml
<property name="hibernate.listeners.localized.locale_resolver">de.malkusch.localized.localeResolver.ThreadLocalLocaleResolver</property>
``` 
Register it programmatically:
```java
LocalizedIntegrator.setLocaleResolver(new ThreadLocalLocaleResolver());
```

# Maven
You find this package in Maven Central repository:
```xml
<dependency>
    <groupId>de.malkusch.localized</groupId>
    <artifactId>localized</artifactId>
    <version>0.2.8</version>
</dependency>
```

# Concept
I tried my best to stay in the JPA layer, but I failed hard. So for now
this i18n extension works in Hibernate's Session world. The mechanism is 
straight forward: A `@Localized` field is backed by many `LocalizedProperty`
entities (each locale one entity). Hibernate's event system provides the
infrastructure for replacing and storing transparently the `@Localized` fields.
This concept increases the Session communication. Don't use `@Localized` when
performance is a concern.

# Limitations
I18n happens around Hibernate's `POST_LOAD`, `POST_UPDATE`, `POST_INSERT` and `POST_DELETE`
events. In between nothing happens. I.e. you have to fix the locale at Session begin
(before the first `POST_LOAD` event on a localized entity). If you change the locale during
a session you have to synchronize the entities with `Session.flush()` and `Session.refresh(Object)`.

# Disclaimer
I'm currently using this i18n method in a project which is under development.
There is no practical knowledge about stability, scalabilty or performance.
Use it at your own risk! 
