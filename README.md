Internationalization (i18n) is so boring. There is not one single best
practice. And they are all just a pain in the ass when your `String` is
no more a String but something like a `Map<Locale, String>`. Bye JSR-303
validation annotations. So let's try to find a solution which keeps i18n
simple, without poluting your domain model and keep the type of the 
properties:

```java
@Entity
class Book {

    @Id
    private String isbn;

    private Author author;

    @Localized
    private String title;

}
```

`@Localized` annotates the property `Book.title` as a translatable `String`.
That looks promissing. But how does hibernate know which `Locale` is relevant? That's the job of a `LocalizedConfiguration` implementation. Implement one or choose one of the existing in the package `de.malkusch.localized.configuration`. You only have to instantiate the implementation. Let's take a Spring implementation as example:

```java
@Configuration
public class I18nConfiguration {

    @Bean public LocalizedConfiguration localizedConfiguration() {
        return new SpringLocalizedConfiguration();
    }

}
```

# Disclaimer
I'm currently using this i18n method in a project which is under development. There is no practical knowledge about stability, scalabilty or performance. Plus I don't have isolated test cases. Use it at your own risk! 
