# How to contribute

## General instruction

1. Fork the project
2. Checkout the `develop` branch
2. Create a branch describing your contribution for instance: `git checkout -b feat/myWonderfulFeature`
3. Develop your changes
4. Check if license headers are up to date by executing: `mvn license:format`
5. Generate package to make sure everything is ok by executing: `mvn clean package`
6. Commit your changes by following the [Angular commit message convention](https://gist.github.com/stephenparish/9941e89d80e2bc58a153) 
7. Submit your work as a pull request to our `develop` branch by documenting changes

## About the release process

This project is available from Maven Central thanks to the [Sonatype OSS repository](http://central.sonatype.org/) and thus follows its associated [recommendations](http://central.sonatype.org/pages/apache-maven.html).
Thus, make sure to fit with the associated requirements (e.g. gpg key ready, allow to push to OSSRH, local `settings.xml` correctly set) before to release this project.

After that, a release can be simply done by:

First executing:

```bash
$ mvn release:prepare
```

Then, answering the prompts for versions and tags and finally executing:

```bash
$ mvn release:perform
``` 