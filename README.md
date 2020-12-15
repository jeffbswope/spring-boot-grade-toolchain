# spring-boot-grade-toolchain

Testbed for reproductions of spring boot interactions with the new Gradle toolchain support.

Running Gradle with Java 8, using the Gradle toolchain to apply different Java versions.

## BootRun doesn't use toolchain

By default, `bootRun` will use the toolchain running the Gradle build.

So if we run Gradle with Java 8 but select to use a newer toolchain:

```groovy
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(15)
	}
}
```

`bootRun` will fail unless/until we add the following:

```groovy
bootRun {
	javaLauncher = javaToolchains.launcherFor {
		languageVersion = JavaLanguageVersion.of(15)
	}
}
```

This seems by design from Gradle that it will:
> Setup all compile, test and javadoc tasks to use the defined toolchain which may be different than the one Gradle itself uses
> (https://docs.gradle.org/current/userguide/toolchains.html)

Without reference to `JavaExec`, which I guess are intended to be set manually.

In any case, with this block commented-out, we get an error on `bootRun` related to trying to run the
Java 15-compiled code with Gradle's Java 8.  

So for Spring Boot's `bootRun` at least, if it is not the intent of Gradle to apply this automatically to
`JavaExec` tasks it might be nice to have it applied by default to `BootRun` ones at least.

## BootRun warning

During `bootRun` (with setup as committed here) a warning is emitted:

```
OpenJDK 64-Bit Server VM warning: Options -Xverify:none and -noverify were deprecated in JDK 13 and will likely be removed in a future release.
```

This appears to be a regression related to this fix, which implicitly assumes (in `BootRun#isJava13OrLater`) 
that the JDK running the build is the same as running the application in `bootRun`, which is not true in this setup.

https://github.com/scope-demo/spring-boot/commit/af223c21c512ed1a67e23672905a2986ea23701b

